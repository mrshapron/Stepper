package ContinuationPac;

import Flow.Definition.FlowDefinition;
import Mapping.MappingDataDefinition;
import Mapping.MappingFlowDataDefinition;

public interface ContinuationMapping {
    MappingDataDefinition mappingFlowDataDefinition();
    FlowDefinition flowDefinitionSource();
    FlowDefinition flowDefinitionTarget();
}
