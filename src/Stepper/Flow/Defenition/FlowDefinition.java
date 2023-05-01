package Stepper.Flow.Defenition;

import Stepper.Mapping.MappingDataDefinition;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

import java.util.List;

public interface FlowDefinition {
    String getName();
    String getDescription();
    boolean getIsReadOnly();
    List<StepUsageDeclaration> getFlowSteps();
    List<String> getFlowFormalOutputs();
    List<MappingDataDefinition> getMappedDataDefinitions();
    void addCustomMapping(MappingDataDefinition customMappingDef);
    void addStepUsageDec(StepUsageDeclaration stepUsageDec);
    void addFlowOutput(String outputName);
    void automaticMapping();
    void customMapping();
    boolean validateFlowStructure();
    List<DataDefinitionDeclaration> getFlowFreeInputs();
    List<DataDefinitionDeclaration> getMandatoryInputs();
    List<DataDefinitionDeclaration> getOptionalInputs();
}
