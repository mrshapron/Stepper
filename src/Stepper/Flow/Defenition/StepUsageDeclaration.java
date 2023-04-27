package Stepper.Flow.Defenition;

import Stepper.Step.StepDefinition;

public interface StepUsageDeclaration {
    String getFinalStepName();
    StepDefinition getStepDefinition();
    boolean skipIfFail();
}
