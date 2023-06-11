package src.Step;


import Step.StepDefinition;

public interface StepFactory {
    StepDefinition MakeStep(String stepName);
}
