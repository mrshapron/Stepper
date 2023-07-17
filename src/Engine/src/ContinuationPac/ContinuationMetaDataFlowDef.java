package ContinuationPac;

import Flow.Definition.FlowDefinition;

import java.util.List;

public interface ContinuationMetaDataFlowDef {
    void addCustomContinuationMapping(CustomContinuationMapping continuationMapping);
    List<CustomContinuationMapping> customContinuationMapping();
    FlowDefinition flowDefinitionTarget();
    void setFlowDefinitionTarget(FlowDefinition flowDefinition);
}

