package Stepper.Flow.Execution.Context;

import Stepper.DataDefinition.DataDefinition;
import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Mapping.CustomMappingDefinition;
import Stepper.Mapping.CustomMappingDefinitionImpl;
import Stepper.Step.Declaration.DataDefinitionDeclaration;
import Stepper.Step.StepDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StepExecutionContextImpl implements StepExecutionContext {
    private final Map<String, Object> dataValues;
    private final List<String> logs;
    private final List<String> summaries;
    public StepExecutionContextImpl(FlowDefinition flowDefinition) {
        dataValues = new HashMap<>();
        logs = new ArrayList<>();
        summaries =new ArrayList<>();

    }

    public void automaticMapping(FlowDefinition flowDefinition){
        List<StepDefinition> allStepsOrdered =
                flowDefinition.getFlowSteps().stream()
                        .map(stepUsageDec -> stepUsageDec.getStepDefinition())
                        .collect(Collectors.toList());

        for (StepDefinition currentStepDef: allStepsOrdered) {
            //List of all the steps that comes after the current step
            List<StepDefinition> stepsAfter = allStepsOrdered.
                    subList(allStepsOrdered.indexOf(currentStepDef), allStepsOrdered.size());

            for (DataDefinitionDeclaration outDataDefDec: currentStepDef.outputs()) {
                DataDefinition output = outDataDefDec.dataDefinition();
                List<StepDefinition> stepWhoUsingThatInput =
                        stepsAfter.stream().filter(stepDefinition -> stepDefinition.inputs().stream().
                                filter(inputData -> inputData.dataDefinition().getType() == output.getType()
                                        && inputData.getName() == outDataDefDec.getName()).
                                collect(Collectors.toList()).size() > 0).
                                collect(Collectors.toList());
                List<CustomMappingDefinition> mappingThatInput = stepWhoUsingThatInput.stream().map(stepUsingInput -> {
                    CustomMappingDefinition mapping = new CustomMappingDefinitionImpl(
                            currentStepDef, stepUsingInput, output.getName(), output.getName()
                    );
                    return mapping;
                }).collect(Collectors.toList());
            }

        }
    }

    @Override
    public <T> T getDataValue(String dataName, Class<T> expectedDataType) {
        // assuming that from the data name we can get to its data definition
        DataDefinition theExpectedDataDefinition = null;

        if (expectedDataType.isAssignableFrom(theExpectedDataDefinition.getType())) {
            Object aValue = dataValues.get(dataName);
            // what happens if it does not exist ?

            return expectedDataType.cast(aValue);
        } else {
            // error handling of some sort...
        }

        return null;
    }

    @Override
    public boolean storeDataValue(String dataName, Object value) {
        // assuming that from the data name we can get to its data definition
        DataDefinition theData = null;

        // we have the DD type so we can make sure that its from the same type
        if (theData.getType().isAssignableFrom(value.getClass())) {
            dataValues.put(dataName, value);
        } else {
            // error handling of some sort...
        }

        return false;
    }

    @Override
    public void addLogLine(String log) {
        logs.add(log);
        System.out.println(log);
    }

    @Override
    public void addSummaryLine(String summary) {
        summaries.add(summary);
        System.out.println(summary);
    }
}
