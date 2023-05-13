package Flow.Execution.History;


import Flow.Logger.FlowLog;
import Step.StepResult;

import java.util.List;

public interface StepHistoryData {
    String getName();
    long getRunTime();
    StepResult getResult();
    List<FlowLog> getLogs();
    void setResult(StepResult result);
    void setRuntime(long runtime);
    void addLog(FlowLog flowLog);
    void addSummary(FlowLog summary);
    FlowLog getSummary();
}
