package Flow.Execution;


import Flow.Defenition.FlowDefinition;
import Flow.Execution.Context.FlowExecutionResult;

import java.time.Duration;
import java.util.Map;


public class FlowExecution {
    private final String uniqueId;
    private final FlowDefinition flowDefinition;
    private Duration totalTime;
    private FlowExecutionResult flowExecutionResult;
    private Map<String, Object> userFreeInputs;

    // lots more data that needed to be stored while flow is being executed...

    public FlowExecution(String uniqueId, FlowDefinition flowDefinition, Map<String, Object> values) {
        this.uniqueId = uniqueId;
        this.flowDefinition = flowDefinition;
        userFreeInputs = values;
    }

    public Map<String, Object> getUserFreeInputs(){
        return userFreeInputs;
    }
    public String getUniqueId() {
        return uniqueId;
    }

    public FlowDefinition getFlowDefinition() {
        return flowDefinition;
    }

    public FlowExecutionResult getFlowExecutionResult() {
        return flowExecutionResult;
    }

    public void setTotalTime(Duration totalTime) {
        this.totalTime = totalTime;
    }

    public void setFlowResult(FlowExecutionResult result) {
        this.flowExecutionResult = result;
    }
}
