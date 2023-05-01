package Stepper.Flow.Defenition;

import Stepper.Log.Logger;
import Stepper.Log.LoggerImpl;
import Stepper.Mapping.MappingDataDefinition;
import Stepper.Mapping.MappingDataDefinitionImpl;
import Stepper.Step.DataNecessity;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

import java.util.*;
import java.util.stream.Collectors;

public class FlowDefinitionImpl implements FlowDefinition{
    private final String name;
    private final String description;
    private final List<String> flowOutputs;
    private final List<StepUsageDeclaration> steps;
    private final List<DataDefinitionDeclaration> freeOutputs;
    private final List<DataDefinitionDeclaration> freeInputs;
    private final List<MappingDataDefinition> mappedDataDefinitions;
    private final List<MappingDataDefinition> customMappingDefs;
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
    public void addCustomMapping(MappingDataDefinition customMappingDef){
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
                    for (int inputIndex = 0; inputIndex < currStepInputer.getStepDefinition().inputs().size(); inputIndex++) {
                        DataDefinitionDeclaration inputCheck = currStepInputer.getStepDefinition().inputs().get(inputIndex);
                        if (inputCheck.getName().equals(currOutput.getName())
                                && inputCheck.dataDefinition().getType().equals(currOutput.dataDefinition().getType())){
                            if(!usedInput.containsKey(inputCheck)){
                                usedInput.put(inputCheck, true);
                                MappingDataDefinition mapDataDef = new MappingDataDefinitionImpl(
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
                if(!usedInput.containsKey(inputDataDefDec) || usedInput.get(inputDataDefDec).booleanValue() == false)
                    freeInputs.add(inputDataDefDec);
            }
        }
    }


    @Override
    public void customMapping() {
        if(steps == null)
            return;
        for (MappingDataDefinition mappingDataDef : customMappingDefs) {
            mappedDataDefinitions.add(mappingDataDef);
        }
    }

    @Override
    public boolean validateFlowStructure() {
        for(int indexStep = 0; indexStep < steps.size() ; indexStep++){
            StepUsageDeclaration stepUsageDecCheck = steps.get(indexStep);
            List<StepUsageDeclaration> otherSteps = steps.stream()
                    .filter(stepUsageDeclaration -> stepUsageDeclaration != stepUsageDecCheck)
                    .collect(Collectors.toList());
            for(int outputIndex = 0; outputIndex < stepUsageDecCheck.getStepDefinition().outputs().size(); outputIndex++){
                DataDefinitionDeclaration outputDefDec = stepUsageDecCheck.getStepDefinition().outputs().get(outputIndex);
                for(int otherStepIndex = 0; otherStepIndex < otherSteps.size(); otherStepIndex++){
                    StepUsageDeclaration otherStepUsageDec = otherSteps.get(otherStepIndex);
                    for(int outputOtherIndex = 0; outputOtherIndex < otherStepUsageDec.getStepDefinition().outputs().size(); outputOtherIndex++){
                        DataDefinitionDeclaration otherOutputDefDec = otherStepUsageDec.getStepDefinition().outputs().get(outputOtherIndex);
                        if(outputDefDec.getName().equals(otherOutputDefDec.getName())){
                            logger.addLog("There are two outputs in step " + stepUsageDecCheck.getFinalStepName() + " and "
                                    + otherOutputDefDec.getName() + " with the same names");
                            return false;
                        }
                    }
                }
            }
        }
        long howManyMandatoryInputsUnFriendly =  getMandatoryInputs().stream()
                .filter(dataDefDec -> dataDefDec.dataDefinition().isUserFriendly() == false).count();
        if(howManyMandatoryInputsUnFriendly > 0){
            logger.addLog("There are mandatory input that are unfriendly");
            return false;
        }
        for (DataDefinitionDeclaration mandatoryInput: getMandatoryInputs()) {
            List<DataDefinitionDeclaration> otherMandatoryInputsSameNameDifTypes = getMandatoryInputs().stream().filter(dataDefDec -> dataDefDec != mandatoryInput)
                    .filter(dataDefDec-> mandatoryInput.getName().equals(dataDefDec.getName()) &&
                            !dataDefDec.dataDefinition().getType().equals(mandatoryInput.dataDefinition().getType()))
                    .collect(Collectors.toList());
            if(otherMandatoryInputsSameNameDifTypes.size() > 0){
                logger.addLog("There are two mandatory inputs with the same name but different types called " + mandatoryInput.getName());
                return false;
            }
        }
        //TODO:: CHECK CUSTOM MAPPING 1) 4.3
        List<DataDefinitionDeclaration> mandatoryInputs = freeInputs.stream()
                .filter(dataDefDec -> dataDefDec.dataDefinition().isUserFriendly() == false).collect(Collectors.toList());

        return true;
    }

    @Override
    public List<DataDefinitionDeclaration> getFlowFreeInputs() {
        return freeInputs;
    }

    @Override
    public List<DataDefinitionDeclaration> getMandatoryInputs() {
        return freeInputs.stream()
                .filter(inputDataDefDec-> inputDataDefDec.necessity() == DataNecessity.MANDATORY)
                .collect(Collectors.toList());
    }

    @Override
    public List<DataDefinitionDeclaration> getOptionalInputs() {
        return freeInputs.stream()
                .filter(inputDataDefDec-> inputDataDefDec.necessity() == DataNecessity.OPTIONAL)
                .collect(Collectors.toList());
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
    public boolean getIsReadOnly() {
        return steps.stream().filter(stepUsageDec ->
                stepUsageDec.getStepDefinition().isReadonly()).count() > 0;
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
    public List<MappingDataDefinition> getMappedDataDefinitions() {
        return mappedDataDefinitions;
    }
}
