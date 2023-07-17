package ContinuationPac;
import java.util.List;
import Flow.Definition.FlowDefinition;

public interface Continuation {
    FlowDefinition flowDefinition();
    void addContinuationMapping(ContinuationMapping continuationMapping);
    List<ContinuationMapping> continuationMappings();
}
