package Components.Main.FlowExecutionComponent.ModelView;

import Flow.Execution.History.OutputHistoryData;
import Flow.Execution.History.StepHistoryData;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class StepExecModelView {
    private String name;
    private String result;
    private String timeStarted;
    private String runtime;
    ObservableList<InputStepModelView> inputStepModelViews;
    ObservableList<OutputStepModelView> outputStepModelViews;
    ObservableList<String> logsStep;

    public StepExecModelView(StepHistoryData stepHistoryData, List<OutputHistoryData> outputHistoryData) {
        this.name = stepHistoryData.getName();
        this.result = stepHistoryData.getResult().name();
        this.timeStarted = stepHistoryData.getTimeRunStarted();
        this.runtime = String.valueOf(stepHistoryData.getRunTime());
        this.logsStep = FXCollections.observableArrayList();
        this.inputStepModelViews = FXCollections.observableArrayList();
        this.outputStepModelViews = FXCollections.observableArrayList();
        stepHistoryData.getLogs()
                .forEach(flowLog -> logsStep.add(flowLog.toString()));
        outputHistoryData
                .forEach(outputHistoryData1 -> outputStepModelViews.add(new OutputStepModelView(outputHistoryData1)));

    }

    public String getName() {
        return name;
    }

    public String getResult() {
        return result;
    }

    public String getTimeStarted() {
        return timeStarted;
    }

    public String getRuntime() {
        return runtime;
    }

    public ObservableList<InputStepModelView> getInputStepModelViews() {
        return inputStepModelViews;
    }

    public ObservableList<OutputStepModelView> getOutputStepModelViews() {
        return outputStepModelViews;
    }

    public ObservableList<String> getLogsStep() {
        return logsStep;
    }
}
