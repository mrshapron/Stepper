package Users.Role;

import Flow.Definition.FlowDefinition;
import java.util.List;

public interface Role {
    String name();
    String description();
    List<FlowDefinition> availableFlows();
    void addFlow(FlowDefinition flow);
    void removeFlow(FlowDefinition flow);
}