package Users.Role;

import Flow.Definition.FlowDefinition;

import java.util.ArrayList;
import java.util.List;

public class RoleImpl implements Role {
    private String name;
    private String description;
    private List<FlowDefinition> availableFlows;
    public RoleImpl(String name, String description) {
        this.name = name;
        this.description = description;
        availableFlows = new ArrayList<>();
    }
    public RoleImpl(String name, String description, List<FlowDefinition> availableFlows) {
        this.name = name;
        this.description = description;
        this.availableFlows = availableFlows;
    }
    @Override
    public String name() {
        return name;
    }
    @Override
    public String description() {
        return description;
    }
    @Override
    public List<FlowDefinition> availableFlows() {
        return availableFlows;
    }

    @Override
    public void addFlow(FlowDefinition flow) {
        availableFlows.add(flow);
    }

    @Override
    public void removeFlow(FlowDefinition flow) {
        availableFlows.removeIf(flowDefinition -> flowDefinition.getName().equals(flow.getName()));
    }
}