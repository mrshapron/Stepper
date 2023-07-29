package Users.Role;

import Flow.Definition.FlowDefinition;
import java.util.List;

public interface Role {
    String name();
    String description();
    List<String> availableFlows();
    void addFlow(String flow);
    void removeFlow(String flow);
}