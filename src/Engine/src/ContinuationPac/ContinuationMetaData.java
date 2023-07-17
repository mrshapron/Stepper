package ContinuationPac;

import Flow.Definition.FlowDefinition;

import java.util.List;

public interface ContinuationMetaData {
    void addCustomContinuationMapping(CustomContinuationMapping continuationMapping);
    List<CustomContinuationMapping> customContinuationMapping();
    String flowDefinitionSource();
    String flowDefinitionTarget();
    void setFlowDefinitionSource(String flowDefinition);
    void setFlowDefinitionTarget(String flowDefinition);
}
