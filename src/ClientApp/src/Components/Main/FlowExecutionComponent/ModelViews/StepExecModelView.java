package Components.Main.FlowExecutionComponent.ModelViews;

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

    public StepExecModelView(){}

    public StepExecModelView(String name, String result, String timeStarted, String runtime){
        this.name = name;
        this.result = result;
        this.timeStarted = timeStarted;
        this.runtime = String.valueOf(runtime);
        this.logsStep = FXCollections.observableArrayList();
        this.inputStepModelViews = FXCollections.observableArrayList();
        this.outputStepModelViews = FXCollections.observableArrayList();
        //Do this outside of here!
//        stepHistoryData.getLogs()
//                .forEach(flowLog -> logsStep.add(flowLog.toString()));
//        outputHistoryData
//                .forEach(outputHistoryData1 -> outputStepModelViews.add(new OutputStepModelView(outputHistoryData1)));

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

    // Setters for lists

    public void setInputStepModelViews(ObservableList<InputStepModelView> inputStepModelViews) {
        this.inputStepModelViews = inputStepModelViews;
    }

    public void setOutputStepModelViews(ObservableList<OutputStepModelView> outputStepModelViews) {
        this.outputStepModelViews = outputStepModelViews;
    }

    public void setLogsStep(ObservableList<String> logsStep) {
        this.logsStep = logsStep;
    }
}
