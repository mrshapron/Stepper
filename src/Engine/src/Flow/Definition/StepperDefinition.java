package Flow.Definition;

import java.util.List;

public interface StepperDefinition {
    Integer getNumberOfThreads();
    List<FlowDefinition> getFlows();
}
