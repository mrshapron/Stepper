package Flow.Definition;



import ContinuationPac.Continuation;
import ContinuationPac.ContinuationMetaDataFlowDef;
import Mapping.MappingDataDefinition;
import Step.Declaration.DataDefinitionDeclaration;

import java.util.List;
import java.util.Map;

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
    List<FreeInputsDefinition> getFlowFreeInputsIncludeInitializedValue();
    List<FreeInputsDefinition> getMandatoryInputs();
    List<FreeInputsDefinition> getOptionalInputs();
    DataDefinitionDeclaration getDataDefinitionByName(String name);
    Map<StepUsageDeclaration, List<DataDefinitionDeclaration>> getAllOutputs();
    void addContinuationFlow(ContinuationMetaDataFlowDef customsContinuation);
    void addInitialValue(String inputName, String initialValue);

    List<Continuation> getContinuationFlows();

    List<InitialInputValue> getInitialInputValues();

    List<DataDefinitionDeclaration> getFreeOutputs();
}
