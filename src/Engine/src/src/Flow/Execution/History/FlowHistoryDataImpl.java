package src.Flow.Execution.History;


import Flow.Execution.Context.FlowExecutionResult;
import Flow.Execution.History.FlowHistoryData;
import Flow.Execution.History.FreeInputHistoryData;
import Flow.Execution.History.OutputHistoryData;
import Flow.Execution.History.StepHistoryData;
import Step.DataNecessity;

import java.util.*;

public class FlowHistoryDataImpl implements FlowHistoryData {
    private String flowName;
    private String flowId;
    private String timeStarted;
    private FlowExecutionResult flowResult;
    private long runtimeMS;
    private List<FreeInputHistoryData> freeInputsHistoryData;
    private List<OutputHistoryData> outputsHistoryData;
    private List<StepHistoryData> stepsHistoryData;

    public FlowHistoryDataImpl(String flowName, String flowId) {
        this.flowName = flowName;
        this.flowId = flowId;
        freeInputsHistoryData = new ArrayList<>();
        outputsHistoryData = new ArrayList<>();
        stepsHistoryData = new ArrayList<>();
    }

    public void addStepHistoryData(StepHistoryData stepHistoryData){
        stepsHistoryData.add(stepHistoryData);
    }
    public void addFreeInputHistory(FreeInputHistoryData freeInputHistoryData){
        freeInputsHistoryData.add(freeInputHistoryData);
    }
    public void addOutputHistory(OutputHistoryData outputHistoryData){
        outputsHistoryData.add(outputHistoryData);
    }
    @Override
    public String getFlowName() {
        return flowName;
    }

    @Override
    public String getFlowID() {
        return flowId;
    }

    @Override
    public String timeStarted() {
        return timeStarted;
    }

    @Override
    public FlowExecutionResult getFlowResult() {
        return flowResult;
    }

    @Override
    public long getRuntime() {
        return runtimeMS;
    }

    @Override
    public List<FreeInputHistoryData> getFreeInputsHistory() {
        Collections.sort(freeInputsHistoryData, (data1, data2) -> {
            if (data1.getNecessity() == DataNecessity.MANDATORY && data2.getNecessity() != DataNecessity.MANDATORY) {
                // data1 is mandatory and data2 is not, so data1 should come before data2
                return -1;
            } else if (data1.getNecessity() != DataNecessity.MANDATORY && data2.getNecessity() == DataNecessity.MANDATORY) {
                // data2 is mandatory and data1 is not, so data2 should come before data1
                return 1;
            } else {
                // the data elements have the same necessity, so we don't need to change their order
                return 0;
            }
        });
        //Collections.sort(dataList, Comparator.comparingInt(
        //    data -> data.getNecessity() == DataNecessity.MANDATORY ? 0 : data.getNecessity() == DataNecessity.OPTIONAL ? 1 : 2
        //));
        return freeInputsHistoryData;
    }

    @Override
    public List<OutputHistoryData> getOutputsHistoryData() {
        return outputsHistoryData;
    }

    @Override
    public List<StepHistoryData> getStepsHistoryData() {
        return stepsHistoryData;
    }

    public void setTimeStarted(String timeStarted) {
        this.timeStarted = timeStarted;
    }

    public void setFlowResult(FlowExecutionResult flowResult) {
        this.flowResult = flowResult;
    }

    public void setRuntime(long runtime) {
        this.runtimeMS = runtime;
    }
}
