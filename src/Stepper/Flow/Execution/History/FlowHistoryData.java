package Stepper.Flow.Execution.History;

import Stepper.Flow.Execution.Context.FlowExecutionResult;

import java.util.List;

public interface FlowHistoryData {
    String getFlowName();
    String getFlowID();
    String timeStarted();
    FlowExecutionResult getFlowResult();
    long getRuntime();
    List<FreeInputHistoryData> getFreeInputsHistory();
    List<OutputHistoryData> getOutputsHistoryData();
    List<StepHistoryData> getStepsHistoryData();
    void addStepHistoryData(StepHistoryData stepHistoryData);
    void addFreeInputHistory(FreeInputHistoryData freeInputHistoryData);
    void addOutputHistory(OutputHistoryData outputHistoryData);
    void setTimeStarted(String timeStarted);
    void setFlowResult(FlowExecutionResult flowResult);
    void setRuntime(long runtime);
}
