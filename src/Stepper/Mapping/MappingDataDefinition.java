package Stepper.Mapping;

import Stepper.Step.Declaration.DataDefinitionDeclaration;
import Stepper.Step.StepDefinition;

public interface MappingDataDefinition {
    StepDefinition getSourceStep();
    StepDefinition getTargetStep();
    DataDefinitionDeclaration getSourceData();
    DataDefinitionDeclaration getTargetData();
}
