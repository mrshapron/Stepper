package Statistics;

import Flow.Definition.FlowDefinition;
import Flow.Definition.StepUsageDeclaration;

public interface FlowStats {
    void addFlow(FlowDefinition flow);
    //void addFlowStats(FlowDefinition flow, long runTimeMS);
    void addStepStats(FlowDefinition flow,StepUsageDeclaration step, long runTimeMS);
    boolean isFlowRun(FlowDefinition flow);

    int getTimesRunFlow(FlowDefinition flow);
    float getAverageRuntimeFlow(FlowDefinition flow);
    int getTimesRunStep(FlowDefinition flow, StepUsageDeclaration step);
    float getAverageRuntimeStep(FlowDefinition flow, StepUsageDeclaration step);
    void addFlowRunTime(FlowDefinition flow, long runtime);
}
