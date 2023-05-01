package Stepper.Flow.Execution.Context;

import Stepper.Convert.DataConverter;
import Stepper.Convert.DataConverterImpl;
import Stepper.DataDefinition.DataDefinition;
import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Mapping.CustomMappingDefinition;
import Stepper.Mapping.CustomMappingDefinitionImpl;
import Stepper.Step.Declaration.DataDefinitionDeclaration;
import Stepper.Step.StepDefinition;

import java.util.*;
import java.util.stream.Collectors;

public class StepExecutionContextImpl implements StepExecutionContext {
    private final Map<String, Object> dataValues;
    private final List<String> logs;
    private final List<String> summaries;
    private final DataConverter converter;
    public StepExecutionContextImpl(FlowDefinition flowDefinition) {
        dataValues = new HashMap<>();
        logs = new ArrayList<>();
        summaries =new ArrayList<>();
        converter = new DataConverterImpl();
        initializeDataValues(flowDefinition);
    }

    private void initializeDataValues(FlowDefinition flowDefinition){
        flowDefinition.getFlowFreeInputs().
                forEach(outputDataDecDef -> dataValues
                        .put(outputDataDecDef.dataDefinition().getName(),
                                outputDataDecDef.dataDefinition()));

    }

    @Override
    public <T> T getDataValue(String dataName, Class<T> expectedDataType) {
        // assuming that from the data name we can get to its data definition
        DataDefinition theExpectedDataDefinition = converter.dataTypeToDefinition(expectedDataType);

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
        DataDefinition theData = converter.dataTypeToDefinition(dataValues.get(dataName).getClass());

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
