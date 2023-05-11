package Stepper.Flow.Execution.History;

import Stepper.Flow.Logger.FlowLog;
import Stepper.Step.StepResult;
import java.util.ArrayList;
import java.util.List;

public class StepHistoryDataImpl implements StepHistoryData {
    private String name;
    private String aliasName;
    private long runtime;
    private StepResult result;
    private List<FlowLog> logs;
    private FlowLog summary;

    public StepHistoryDataImpl(String name, String aliasName){
        this.name = name;
        if (name.equals(aliasName))
            this.aliasName = "";
        else
            this.aliasName=aliasName;
        logs = new ArrayList<>();
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getRunTime() {
        return runtime;
    }

    @Override
    public StepResult getResult() {
        return result;
    }

    @Override
    public List<FlowLog> getLogs() {
        return logs;
    }

    public void setResult(StepResult result) {
        this.result = result;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    @Override
    public void addLog(FlowLog flowLog) {
        logs.add(flowLog);
    }

    @Override
    public void addSummary(FlowLog summary) {
        this.summary = summary;
    }

    public FlowLog getSummary() {
        return summary;
    }

    public void setSummary(FlowLog summary) {
        this.summary = summary;
    }
}
