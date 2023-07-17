package ContinuationPac;

import Flow.Definition.FlowDefinition;

import java.util.ArrayList;
import java.util.List;

public class ContinuationMetaDataFlowDefImpl implements ContinuationMetaDataFlowDef {
    private List<CustomContinuationMapping> customContinuationMappings;
    private FlowDefinition flowDefinitionTarget;

    public ContinuationMetaDataFlowDefImpl(FlowDefinition flowDefinitionTarget) {
        this.flowDefinitionTarget = flowDefinitionTarget;
        customContinuationMappings = new ArrayList<>();
    }

    @Override
    public void addCustomContinuationMapping(CustomContinuationMapping continuationMapping) {
        customContinuationMappings.add(continuationMapping);
    }

    @Override
    public List<CustomContinuationMapping> customContinuationMapping() {
        return customContinuationMappings;
    }

    @Override
    public FlowDefinition flowDefinitionTarget() {
        return flowDefinitionTarget;
    }

    @Override
    public void setFlowDefinitionTarget(FlowDefinition flowDefinition) {
        flowDefinitionTarget = flowDefinition;
    }
}
