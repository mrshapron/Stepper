package ContinuationPac;

import Flow.Definition.FlowDefinition;
import Mapping.MappingDataDefinition;
import Mapping.MappingFlowDataDefinition;

public class ContinuationMappingImpl implements ContinuationMapping {
    private FlowDefinition source;
    private FlowDefinition target;
    private MappingDataDefinition mappingFlowDataDefinition;

    public ContinuationMappingImpl(MappingDataDefinition mappingFlowDataDefinition,
                                   FlowDefinition sourceFlow,
                                   FlowDefinition targetFlow) {
        this.mappingFlowDataDefinition = mappingFlowDataDefinition;
        this.source = sourceFlow;
        this.target = targetFlow;
    }

    @Override
    public MappingDataDefinition mappingFlowDataDefinition() {
        return mappingFlowDataDefinition;
    }

    @Override
    public FlowDefinition flowDefinitionSource() {
        return source;
    }

    @Override
    public FlowDefinition flowDefinitionTarget() {
        return target;
    }
}
