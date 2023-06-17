package BusinessLogic;

import Flow.Defenition.FlowDefinition;
import Flow.Execution.History.FlowHistoryData;

import java.util.List;
import java.util.Map;

public interface StepperBusinessLogic {
    List<FlowDefinition> initializeStepper(String absolutePath);
    FlowHistoryData startFlow(FlowDefinition flowDefinition, Map<String,String> freeInputsValues, ProgressCallback progressCallback);
    void executeTask(Runnable task);
}
