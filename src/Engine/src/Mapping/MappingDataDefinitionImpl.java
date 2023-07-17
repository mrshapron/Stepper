package Mapping;


import Flow.Definition.StepUsageDeclaration;
import Step.Declaration.DataDefinitionDeclaration;

public class MappingDataDefinitionImpl implements MappingDataDefinition {
    private StepUsageDeclaration sourceStep;
    private StepUsageDeclaration targetStep;
    private DataDefinitionDeclaration sourceData;
    private DataDefinitionDeclaration targetData;
    public MappingDataDefinitionImpl(
            StepUsageDeclaration sourceStep, StepUsageDeclaration targetStep,
            DataDefinitionDeclaration sourceData, DataDefinitionDeclaration targetData) {
        this.sourceStep = sourceStep;
        this.targetStep = targetStep;
        this.sourceData = sourceData;
        this.targetData = targetData;
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
