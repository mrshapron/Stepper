package BusinessLogic;

import Flow.Definition.FlowDefinition;
import Flow.Execution.History.FlowHistoryData;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface StepperBusinessLogic {
    List<FlowDefinition> initializeStepper(String absolutePath);
    FlowHistoryData startFlow(FlowDefinition flowDefinition, Map<String,String> freeInputsValues, ProgressCallback progressCallback);
    void executeTask(Runnable task);
    List<FlowDefinition> initializeStepperViaFile (InputStream fileContent);
}
