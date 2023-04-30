package Stepper.Mapping;

import Stepper.Step.StepDefinition;

public class CustomMappingDefinitionImpl implements CustomMappingDefinition {
    private StepDefinition sourceStep;
    private StepDefinition targetStep;
    private String sourceDataName;
    private String targetDataName;
    public CustomMappingDefinitionImpl(
            StepDefinition sourceStep, StepDefinition targetStep,
            String sourceDataName, String targetDataName) {
        this.sourceStep = sourceStep;
        this.targetStep = targetStep;
        this.sourceDataName = sourceDataName;
        this.targetDataName = targetDataName;
    }
    @Override
    public StepDefinition getSourceStep() {
        return sourceStep;
    }
    @Override
    public StepDefinition getTargetStep() {
        return targetStep;
    }
    @Override
    public String getSourceDataName() {
        return sourceDataName;
    }
    @Override
    public String getTargetDataName() {
        return targetDataName;
    }
}
