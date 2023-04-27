package Stepper.Flow.Defenition;

import Stepper.Step.Declaration.DataDefinitionDeclaration;

import java.util.List;

public interface FlowDefinition {
    String getName();
    String getDescription();
    List<StepUsageDeclaration> getFlowSteps();
    List<String> getFlowFormalOutputs();

    public void addStepUsageDec(StepUsageDeclaration stepUsageDec);
    public void addFlowOutput(String outputName);

    void validateFlowStructure();
    List<DataDefinitionDeclaration> getFlowFreeInputs();
}
