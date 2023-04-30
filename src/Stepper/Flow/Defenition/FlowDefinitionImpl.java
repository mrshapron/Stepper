package Stepper.Flow.Defenition;

import Stepper.Log.Logger;
import Stepper.Log.LoggerImpl;
import Stepper.Mapping.CustomMappingDefinition;
import Stepper.Mapping.MappingDataDefinition2;
import Stepper.Mapping.MappingDataDefinition2Impl;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowDefinitionImpl implements  FlowDefinition{
    private final String name;
    private final String description;
    private final List<String> flowOutputs;
    private final List<StepUsageDeclaration> steps;
    private final List<DataDefinitionDeclaration> freeOutputs;
    private final List<DataDefinitionDeclaration> freeInputs;
    private final List<MappingDataDefinition2> mappedDataDefinitions;
    private final List<MappingDataDefinition2> customMappingDefs;
    private final Logger logger;

    public FlowDefinitionImpl(String name, String description) {
        this.name = name;
        this.description = description;
        flowOutputs = new ArrayList<>();
        steps = new ArrayList<>();
        mappedDataDefinitions = new ArrayList<>();
        logger = LoggerImpl.getInstance();
        freeOutputs = new ArrayList<>();
        freeInputs = new ArrayList<>();
        customMappingDefs = new ArrayList<>();
    }
    public void addCustomMapping(MappingDataDefinition2 customMappingDef){
        customMappingDefs.add(customMappingDef);}
    public void addStepUsageDec(StepUsageDeclaration stepUsageDec){
        steps.add(stepUsageDec);
    }
    public void addFlowOutput(String outputName) {
        flowOutputs.add(outputName);
    }

    @Override
    public void automaticMapping() {
        if (steps == null) {
            return;
        }
        Map<DataDefinitionDeclaration, Boolean> usedInput = new HashMap<>();
        for (int stepIndex = 0; stepIndex < steps.size(); stepIndex++){
            StepUsageDeclaration currStepOutputer = steps.get(stepIndex);
            List<StepUsageDeclaration> stepsAfter = steps.subList(stepIndex + 1, steps.size());
            for(int outputIndexCurStep = 0; outputIndexCurStep < currStepOutputer.getStepDefinition().outputs().size(); outputIndexCurStep++) {
                boolean isOutputFree = true;
                DataDefinitionDeclaration currOutput = currStepOutputer.getStepDefinition().outputs().get(outputIndexCurStep);
                for (int stepAfterIndex = 0; stepAfterIndex < stepsAfter.size(); stepAfterIndex++) {
                    StepUsageDeclaration currStepInputer = stepsAfter.get(stepAfterIndex);
                    for (int inputIndex = 0; inputIndex < steps.get(stepIndex).getStepDefinition().inputs().size(); inputIndex++) {
                        DataDefinitionDeclaration inputCheck = currStepInputer.getStepDefinition().inputs().get(inputIndex);
                        if (inputCheck.getName().equals(currOutput.getName())
                                && inputCheck.dataDefinition().getType().equals(currOutput.dataDefinition().getType())){
                            if(usedInput.get(inputCheck)){
                                usedInput.put(inputCheck, true);
                                MappingDataDefinition2 mapDataDef = new MappingDataDefinition2Impl(
                                        currStepOutputer.getStepDefinition(), currStepInputer.getStepDefinition(),currOutput, inputCheck);
                                mappedDataDefinitions.add(mapDataDef);
                                isOutputFree = false;
                            }
                            else{
                                logger.addLog("There is a input : " + inputCheck.getName() + "that already been used by other output");
                            }
                        }
                    }
                }
                if (isOutputFree)
                    freeOutputs.add(currOutput);
            }
        }
        initializeFreeInputs(usedInput);
    }

    private void initializeFreeInputs(Map<DataDefinitionDeclaration, Boolean> usedInput) {
        for (int indexStep = 0; indexStep < steps.size(); indexStep++){
            StepUsageDeclaration stepUsageDec = steps.get(indexStep);
            for (int indexInput = 0; indexInput < stepUsageDec.getStepDefinition().inputs().size(); indexInput++){
                DataDefinitionDeclaration inputDataDefDec = stepUsageDec.getStepDefinition().inputs().get(indexInput);
                if(!usedInput.containsKey(inputDataDefDec) || usedInput.get(inputDataDefDec).booleanValue())
                    freeInputs.add(inputDataDefDec);
            }
        }
    }


    @Override
    public void customMapping() {
        if(steps == null)
            return;

    }

    @Override
    public void validateFlowStructure() {
        // do some validation logic...
    }

    @Override
    public List<DataDefinitionDeclaration> getFlowFreeInputs() {
        return new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<StepUsageDeclaration> getFlowSteps() {
        return steps;
    }

    @Override
    public List<String> getFlowFormalOutputs() {
        return flowOutputs;
    }

    @Override
    public List<MappingDataDefinition2> getMappedDataDefinitions() {
        return mappedDataDefinitions;
    }
}
