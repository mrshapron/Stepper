package Users.Role;

import Flow.Definition.FlowDefinition;
import Users.UserImpl;

import java.util.*;

public class RoleImpl implements Role {
    private String name;
    private String description;
    private List<String> availableFlows;

    public RoleImpl(String name, String description, List<String> availableFlows){
        this.name = name;
        this.description = description;
        if (availableFlows != null)
            this.availableFlows = availableFlows;
        else
            this.availableFlows = new ArrayList<>();
    }

    public RoleImpl(String name, String description) {
        this.name = name;
        this.description = description;
        availableFlows = new ArrayList<>();
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
    public List<String> availableFlows() {
        return availableFlows;
    }

    @Override
    public void addFlow(String flow) {
        availableFlows.add(flow);
    }

    @Override
    public void removeFlow(String flow) {
        availableFlows.removeIf(flowDefinition -> flowDefinition.equals(flow));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        RoleImpl otherRole = (RoleImpl) obj;
        return this.name().equals(otherRole.name()) &&
                this.description().equals(otherRole.description()) &&
                new HashSet<>(availableFlows).containsAll(otherRole.availableFlows()) &&
                new HashSet<>(otherRole.availableFlows()).containsAll(availableFlows);
    }




}