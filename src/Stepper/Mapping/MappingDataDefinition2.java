package Stepper.Mapping;

import Stepper.Step.Declaration.DataDefinitionDeclaration;
import Stepper.Step.StepDefinition;

public interface MappingDataDefinition2 {
    StepDefinition getSourceStep();
    StepDefinition getTargetStep();
    DataDefinitionDeclaration getSourceData();
    DataDefinitionDeclaration getTargetData();
}
