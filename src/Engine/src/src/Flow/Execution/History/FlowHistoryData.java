package src.Flow.Execution.History;

import Flow.Execution.Context.FlowExecutionResult;
import Flow.Execution.History.FreeInputHistoryData;
import Flow.Execution.History.OutputHistoryData;
import Flow.Execution.History.StepHistoryData;

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
