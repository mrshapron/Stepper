package Stepper.Mapping;

import Stepper.Step.StepDefinition;

public interface CustomMappingDefinition {
    StepDefinition getSourceStep();
    StepDefinition getTargetStep();
    String getSourceDataName();
    String getTargetDataName();

}
