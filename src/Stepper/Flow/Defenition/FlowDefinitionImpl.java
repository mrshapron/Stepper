package Stepper.Flow.Defenition;
import Stepper.DataDefinition.DataDefinition;
import Stepper.DataDefinition.Implemantion.DoubleDataDefinition;
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
    private final List<FreeInputsDefinition> freeInputs;
    private final List<MappingDataDefinition> mappedDataDefinitions;
    private final List<MappingDataDefinition> customMappingDefs;

    Map<DataDefinitionDeclaration, Boolean> usedInput;
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
        usedInput=new HashMap<>();
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
        for (int stepIndex = 0; stepIndex < steps.size(); stepIndex++){
            StepUsageDeclaration currStepOutputer = steps.get(stepIndex);
            List<StepUsageDeclaration> stepsAfter = steps.subList(stepIndex + 1, steps.size());
            for(int outputIndexCurStep = 0; outputIndexCurStep < currStepOutputer.getStepDefinition().outputs().size(); outputIndexCurStep++) {
                boolean isOutputFree = true;
                DataDefinitionDeclaration currOutput = currStepOutputer.getStepDefinition().outputs().get(outputIndexCurStep);
                for (StepUsageDeclaration currStepInputer : stepsAfter) {
                    for (int inputIndex = 0; inputIndex < currStepInputer.getStepDefinition().inputs().size(); inputIndex++) {
                        DataDefinitionDeclaration inputCheck = currStepInputer.getStepDefinition().inputs().get(inputIndex);
                        if (inputCheck.getAliasName().equals(currOutput.getAliasName())
                                && inputCheck.dataDefinition().getType().equals(currOutput.dataDefinition().getType())) {
                            if (!usedInput.containsKey(inputCheck)) {
                                usedInput.put(inputCheck, true);
                                MappingDataDefinition mapDataDef = new MappingDataDefinitionImpl(
                                        currStepOutputer.getStepDefinition(), currStepInputer.getStepDefinition(), currOutput, inputCheck);
                                mappedDataDefinitions.add(mapDataDef);
                                isOutputFree = false;
                            } else {
                                logger.addLog("There is a input : " + inputCheck.getAliasName() + "that already been used by other output");
                            }
                        }
                    }
                }
                if (isOutputFree)
                    freeOutputs.add(currOutput);
            }
        }
    }

    private void initializeFreeInputs() {
        for (StepUsageDeclaration stepUsageDec : steps) {
            for (int indexInput = 0; indexInput < stepUsageDec.getStepDefinition().inputs().size(); indexInput++) {
                DataDefinitionDeclaration inputDataDefDec = stepUsageDec.getStepDefinition().inputs().get(indexInput);
                if (!usedInput.containsKey(inputDataDefDec) || !usedInput.get(inputDataDefDec) )
                    if(freeInputs.stream().anyMatch(freeInputsDefinition ->
                            freeInputsDefinition.getDataDefinitionDeclaration().getAliasName().equals(inputDataDefDec.getAliasName())&&
                            freeInputsDefinition.getDataDefinitionDeclaration().dataDefinition().getType().equals(inputDataDefDec.dataDefinition().getType()))){
                        freeInputs.stream().filter(freeInputsDefinition -> freeInputsDefinition.getDataDefinitionDeclaration()
                                        .getAliasName().equals(inputDataDefDec.getAliasName()) &&
                                        freeInputsDefinition.getDataDefinitionDeclaration().dataDefinition().getType()
                                        .equals(inputDataDefDec.dataDefinition().getType())).collect(Collectors.toList())
                                .get(0).addStepUsageDeclaration(stepUsageDec);
                    }else {
                        FreeInputsDefinition freeInputsDefinition = new FreeInputsDefinitionImpl(inputDataDefDec);
                        freeInputsDefinition.addStepUsageDeclaration(stepUsageDec);
                        freeInputs.add(freeInputsDefinition);
                    }
            }
        }
    }


    @Override
    public void customMapping() {
        if(steps == null)
            return;
        mappedDataDefinitions.addAll(customMappingDefs);
        customMappingDefs.forEach(mappingDataDefinition -> usedInput.put(mappingDataDefinition.getTargetData(), true));
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
                for (StepUsageDeclaration otherStepUsageDec : otherSteps) {
                    for (int outputOtherIndex = 0; outputOtherIndex < otherStepUsageDec.getStepDefinition().outputs().size(); outputOtherIndex++) {
                        DataDefinitionDeclaration otherOutputDefDec = otherStepUsageDec.getStepDefinition().outputs().get(outputOtherIndex);
                        if (outputDefDec.getAliasName().equals(otherOutputDefDec.getAliasName())) {
                            logger.addLog("There are two outputs in step " + stepUsageDecCheck.getFinalStepName() + " and "
                                    + otherOutputDefDec.getAliasName() + " with the same names");
                            return false;
                        }
                    }
                }
            }
        }
        initializeFreeInputs();
        long howManyMandatoryInputsUnFriendly =  getMandatoryInputs().stream()
                .filter(dataDefDec -> !dataDefDec.getDataDefinitionDeclaration().dataDefinition().isUserFriendly()).count();
        if(howManyMandatoryInputsUnFriendly > 0){
            logger.addLog("There are mandatory input that are unfriendly");
            return false;
        }
        for (FreeInputsDefinition mandatoryInput: getMandatoryInputs()) {
            List<FreeInputsDefinition> otherMandatoryInputsSameNameDifTypes = getMandatoryInputs().stream().filter(dataDefDec -> dataDefDec != mandatoryInput)
                    .filter(dataDefDec-> mandatoryInput.getDataDefinitionDeclaration().getAliasName().equals(dataDefDec.getDataDefinitionDeclaration().getAliasName()) &&
                            !dataDefDec.getDataDefinitionDeclaration().dataDefinition().getType().equals(mandatoryInput.getDataDefinitionDeclaration().dataDefinition().getType()))
                    .collect(Collectors.toList());
            if(otherMandatoryInputsSameNameDifTypes.size() > 0){
                logger.addLog("There are two mandatory inputs with the same name but different types called " + mandatoryInput.getDataDefinitionDeclaration().getAliasName());
                return false;
            }
        }
        //TODO:: CHECK CUSTOM MAPPING 1) 4.3

        return true;
    }

    @Override
    public List<FreeInputsDefinition> getFlowFreeInputs() {
        return freeInputs;
    }

    @Override
    public List<FreeInputsDefinition> getMandatoryInputs() {
        return freeInputs.stream()
                .filter(freeInputsDefinition-> freeInputsDefinition.getDataDefinitionDeclaration().necessity()
                        == DataNecessity.MANDATORY)
                .collect(Collectors.toList());
    }

    @Override
    public List<FreeInputsDefinition> getOptionalInputs() {
        return freeInputs.stream()
                .filter(freeInputsDefinition-> freeInputsDefinition.getDataDefinitionDeclaration().necessity()
                        == DataNecessity.OPTIONAL)
                .collect(Collectors.toList());
    }

    @Override
    public DataDefinition getDataDefinitionByName(String name) {
        List<StepUsageDeclaration> stepsWithData = steps.stream().filter(stepUsageDeclaration ->
                stepUsageDeclaration.getStepDefinition().inputs().stream().filter(inputData ->
                inputData.getName().equals(name)
                ).count() > 0 || stepUsageDeclaration.getStepDefinition().outputs().stream().filter(outputData ->
                        outputData.getName().equals(name)).count() > 0).collect(Collectors.toList());
        if(stepsWithData.size() == 0)
            return null;
        StepUsageDeclaration stepWithData = stepsWithData.get(0);

        List<DataDefinitionDeclaration> dataDefinitionsInput = stepWithData.getStepDefinition().inputs().stream()
                .filter(dataDefinitionDeclaration -> dataDefinitionDeclaration.getName().equals(name))
                .collect(Collectors.toList());
        List<DataDefinitionDeclaration> dataDefinitionOutput = stepWithData.getStepDefinition().outputs().stream()
                .filter(dataDefinitionDeclaration -> dataDefinitionDeclaration.getName().equals(name)).
                collect(Collectors.toList());
        if(dataDefinitionsInput.size() > 0)
            return dataDefinitionsInput.get(0).dataDefinition();
        else if(dataDefinitionOutput.size() > 0)
            return dataDefinitionOutput.get(0).dataDefinition();
        return null;
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
        return steps.stream().anyMatch(stepUsageDec ->
                stepUsageDec.getStepDefinition().isReadonly());
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
