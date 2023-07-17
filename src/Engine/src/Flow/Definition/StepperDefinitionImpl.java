package Flow.Definition;

import java.util.List;

public class StepperDefinitionImpl implements StepperDefinition {

    private final List<FlowDefinition> flowDefinitions;
    private final Integer numberOfThreads;

    public StepperDefinitionImpl(List<FlowDefinition> flowDefinitions, int numberOfThreads) {
        this.flowDefinitions = flowDefinitions;
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public Integer getNumberOfThreads() {
        return this.numberOfThreads;
    }

    @Override
    public List<FlowDefinition> getFlows() {
        return this.flowDefinitions;
    }
}
