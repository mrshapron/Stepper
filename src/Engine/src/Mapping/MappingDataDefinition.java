package Mapping;

import Step.Declaration.DataDefinitionDeclaration;
import Step.StepDefinition;

public interface MappingDataDefinition {
    StepDefinition getSourceStep();
    StepDefinition getTargetStep();
    DataDefinitionDeclaration getSourceData();
    DataDefinitionDeclaration getTargetData();
}
