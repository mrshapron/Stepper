package Flow.Defenition;



import Mapping.MappingDataDefinition;
import Step.Declaration.DataDefinitionDeclaration;

import java.util.List;

public interface FlowDefinition {
    String getName();
    String getDescription();
    boolean getIsReadOnly();
    List<StepUsageDeclaration> getFlowSteps();
    List<String> getFlowFormalOutputs();
    List<MappingDataDefinition> getMappedDataDefinitions();
    List<MappingDataDefinition> getCustomMappingData();
    void addCustomMapping(MappingDataDefinition customMappingDef);
    void addStepUsageDec(StepUsageDeclaration stepUsageDec);
    void addFlowOutput(String outputName);
    void automaticMapping();
    void customMapping();
    boolean validateFlowStructure();
    List<FreeInputsDefinition> getFlowFreeInputs();
    List<FreeInputsDefinition> getMandatoryInputs();
    List<FreeInputsDefinition> getOptionalInputs();
    DataDefinitionDeclaration getDataDefinitionByName(String name);
}