package Mapping;

import Flow.Definition.StepUsageDeclaration;
import Step.Declaration.DataDefinitionDeclaration;

public interface MappingDataDefinition {
    StepUsageDeclaration getSourceStep();
    StepUsageDeclaration getTargetStep();
    DataDefinitionDeclaration getSourceData();
    DataDefinitionDeclaration getTargetData();
}
