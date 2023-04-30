package Stepper.Flow.Defenition;

import Stepper.Mapping.CustomMappingDefinition;
import Stepper.Mapping.MappingDataDefinition2;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

import java.util.List;

public interface FlowDefinition {
    String getName();
    String getDescription();
    List<StepUsageDeclaration> getFlowSteps();
    List<String> getFlowFormalOutputs();
    List<MappingDataDefinition2> getMappedDataDefinitions();
    void addCustomMapping(MappingDataDefinition2 customMappingDef);
    void addStepUsageDec(StepUsageDeclaration stepUsageDec);
    void addFlowOutput(String outputName);
    void automaticMapping();
    void customMapping();
    void validateFlowStructure();
    List<DataDefinitionDeclaration> getFlowFreeInputs();
}
