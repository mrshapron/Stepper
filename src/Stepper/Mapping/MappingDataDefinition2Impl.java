package Stepper.Mapping;

import Stepper.Step.Declaration.DataDefinitionDeclaration;
import Stepper.Step.StepDefinition;

public class MappingDataDefinition2Impl implements MappingDataDefinition2 {
    private StepDefinition sourceStep;
    private StepDefinition targetStep;
    private DataDefinitionDeclaration sourceData;
    private DataDefinitionDeclaration targetData;
    public MappingDataDefinition2Impl(
            StepDefinition sourceStep, StepDefinition targetStep,
            DataDefinitionDeclaration sourceDataName, DataDefinitionDeclaration targetDataName) {
        this.sourceStep = sourceStep;
        this.targetStep = targetStep;
        this.sourceData = sourceDataName;
        this.targetData = targetDataName;
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
    public DataDefinitionDeclaration getSourceData() {
        return sourceData;
    }
    @Override
    public DataDefinitionDeclaration getTargetData() {
        return targetData;
    }
}
