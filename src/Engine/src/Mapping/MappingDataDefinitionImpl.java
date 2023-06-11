package Mapping;


import Flow.Defenition.StepUsageDeclaration;
import Step.Declaration.DataDefinitionDeclaration;
import Step.StepDefinition;

public class MappingDataDefinitionImpl implements MappingDataDefinition {
    private StepUsageDeclaration sourceStep;
    private StepUsageDeclaration targetStep;
    private DataDefinitionDeclaration sourceData;
    private DataDefinitionDeclaration targetData;
    public MappingDataDefinitionImpl(
            StepUsageDeclaration sourceStep, StepUsageDeclaration targetStep,
            DataDefinitionDeclaration sourceDataName, DataDefinitionDeclaration targetDataName) {
        this.sourceStep = sourceStep;
        this.targetStep = targetStep;
        this.sourceData = sourceDataName;
        this.targetData = targetDataName;
    }
    @Override
    public StepUsageDeclaration getSourceStep() {
        return sourceStep;
    }
    @Override
    public StepUsageDeclaration getTargetStep() {
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
