package Stepper.Flow.Defenition;

import Stepper.Step.Declaration.DataDefinitionDeclaration;

import java.util.List;

public interface FreeInputsDefinition {
    DataDefinitionDeclaration getDataDefinitionDeclaration();
    List<StepUsageDeclaration> getStepUsageDeclarations();
    void addStepUsageDeclaration(StepUsageDeclaration stepUsageDeclaration);
}
