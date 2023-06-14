package Flow.Execution.History;

import Flow.Defenition.StepUsageDeclaration;
import Flow.Logger.FlowLog;
import Step.StepResult;

import java.util.ArrayList;
import java.util.List;

public class StepHistoryDataImpl implements StepHistoryData {
    private StepUsageDeclaration stepUsageDeclaration;
    private String name;
    private String aliasName;
    private long runtime;
    private StepResult result;
    private List<FlowLog> logs;
    private FlowLog summary;
    private String timeRunStarted;
    public StepHistoryDataImpl(String name, String aliasName, StepUsageDeclaration stepUsageDeclaration){
        this.name = name;
        this.stepUsageDeclaration = stepUsageDeclaration;
        if (name.equals(aliasName))
            this.aliasName = "";
        else
            this.aliasName=aliasName;
        logs = new ArrayList<>();
    }

    @Override
    public StepUsageDeclaration getStepDeclaration() {
        return null;
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
    public String getTimeRunStarted() {return timeRunStarted; }

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
    public void setTimeRunStarted(String timeRun) {this.timeRunStarted = timeRun;}

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
}
