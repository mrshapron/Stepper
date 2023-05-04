package Stepper.Flow.Execution;

import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Flow.Execution.Context.FlowExecutionResult;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

import javax.jws.Oneway;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FlowExecution {
    private final String uniqueId;
    private final FlowDefinition flowDefinition;
    private Duration totalTime;
    private FlowExecutionResult flowExecutionResult;
    private Map<String, Object> userInputs;

    // lots more data that needed to be stored while flow is being executed...

    public FlowExecution(String uniqueId, FlowDefinition flowDefinition, Map<String, Object> values) {
        this.uniqueId = uniqueId;
        this.flowDefinition = flowDefinition;
        userInputs = values;
    }


    public Map<String, Object> getUserInputs(){
        return userInputs;
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
}
