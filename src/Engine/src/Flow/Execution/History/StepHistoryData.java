package Flow.Execution.History;


import Flow.Defenition.StepUsageDeclaration;
import Flow.Logger.FlowLog;
import Step.StepResult;

import java.util.List;

public interface StepHistoryData {
    StepUsageDeclaration getStepDeclaration();
    String getName();
    long getRunTime();
    String getTimeRunStarted();
    StepResult getResult();
    List<FlowLog> getLogs();
    void setResult(StepResult result);
    void setRuntime(long runtime);
    void setTimeRunStarted(String timeRun);
    void addLog(FlowLog flowLog);
    void addSummary(FlowLog summary);
    FlowLog getSummary();
}
