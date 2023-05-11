package Stepper.Flow.Execution.History;

import Stepper.Flow.Logger.FlowLog;
import Stepper.Step.StepResult;

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
    void setSummary(FlowLog summary);
}
