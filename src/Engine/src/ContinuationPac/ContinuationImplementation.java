package ContinuationPac;

import Flow.Definition.FlowDefinition;

import java.util.ArrayList;
import java.util.List;

public class ContinuationImplementation implements Continuation {
    FlowDefinition flowDefinition;
    List<ContinuationMapping> continuationMappings;

    public ContinuationImplementation(FlowDefinition flowDefinition) {
        this.continuationMappings = new ArrayList<>();
        this.flowDefinition=flowDefinition;
    }

    @Override
    public FlowDefinition flowDefinition() {
        return flowDefinition;
    }

    @Override
    public void addContinuationMapping(ContinuationMapping continuationMapping) {
        this.continuationMappings.add(continuationMapping);
    }

    @Override
    public List<ContinuationMapping> continuationMappings() {
        return continuationMappings;
    }
}
