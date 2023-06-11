package Mapping;

import Flow.Defenition.StepUsageDeclaration;
import Step.Declaration.DataDefinitionDeclaration;
import Step.StepDefinition;

public interface MappingDataDefinition {
    StepUsageDeclaration getSourceStep();
    StepUsageDeclaration getTargetStep();
    DataDefinitionDeclaration getSourceData();
    DataDefinitionDeclaration getTargetData();
}
