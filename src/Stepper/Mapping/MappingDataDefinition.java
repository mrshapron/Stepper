package Stepper.Mapping;

import Stepper.DataDefinition.DataDefinition;
import Stepper.Step.StepDefinition;

public interface MappingDataDefinition {
    StepDefinition getSourceStep();
    StepDefinition getTargetStep();
    String getSourceDataName();
    String getTargetDataName();

}
