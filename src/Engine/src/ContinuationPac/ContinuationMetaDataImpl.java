package ContinuationPac;

import Flow.Definition.FlowDefinition;

import java.util.ArrayList;
import java.util.List;

public class ContinuationMetaDataImpl implements ContinuationMetaData {
    private List<CustomContinuationMapping> continuationMappings;
    private String flowDefinitionTarget;
    private String flowDefinitionSource;
    public ContinuationMetaDataImpl(String  flowDefinitionSource, String flowDefinitionTarget){
        this.continuationMappings = new ArrayList<>();
        this.flowDefinitionTarget = flowDefinitionTarget;
        this.flowDefinitionSource = flowDefinitionSource;
    }

    public ContinuationMetaDataImpl() {
        this.continuationMappings = new ArrayList<>();
        this.flowDefinitionTarget = null;
        this.flowDefinitionSource = null;
    }

    @Override
    public void addCustomContinuationMapping(CustomContinuationMapping continuationMapping) {
        continuationMappings.add(continuationMapping);
    }

    @Override
    public List<CustomContinuationMapping> customContinuationMapping() { return continuationMappings; }

    @Override
    public String flowDefinitionSource() {
        return this.flowDefinitionSource;
    }

    @Override
    public String flowDefinitionTarget() {
        return this.flowDefinitionTarget;
    }

    @Override
    public void setFlowDefinitionSource(String flowDefinition) {
        this.flowDefinitionSource = flowDefinition;
    }

    @Override
    public void setFlowDefinitionTarget(String flowDefinition) {
        this.flowDefinitionSource = flowDefinition;
    }
}
