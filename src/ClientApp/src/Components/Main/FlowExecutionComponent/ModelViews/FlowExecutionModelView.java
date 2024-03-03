package Components.Main.FlowExecutionComponent.ModelViews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class FlowExecutionModelView {
    private String flowName;
    private String idExec;
    private String timeRun;
    private String result;
    private String timeRunning;
    ObservableList<FreeInputsExecViewModel> freeInputsExecViewModels;
    ObservableList<OutputExecModelView> outputExecModelViews;
    ObservableList<StepExecModelView> stepExecModelViews;

    public FlowExecutionModelView() {
        // Initialize the observable lists
        freeInputsExecViewModels = FXCollections.observableArrayList();
        outputExecModelViews = FXCollections.observableArrayList();
        stepExecModelViews = FXCollections.observableArrayList();
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public void setIdExec(String idExec) {
        this.idExec = idExec;
    }

    public void setTimeRun(String timeRun) {
        this.timeRun = timeRun;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setTimeRunning(String timeRunning) {
        this.timeRunning = timeRunning;
    }

    public void setFreeInputsExecViewModels(ObservableList<FreeInputsExecViewModel> freeInputsExecViewModels) {
        this.freeInputsExecViewModels = freeInputsExecViewModels;
    }

    public void setOutputExecModelViews(ObservableList<OutputExecModelView> outputExecModelViews) {
        this.outputExecModelViews = outputExecModelViews;
    }

    public void setStepExecModelViews(ObservableList<StepExecModelView> stepExecModelViews) {
        this.stepExecModelViews = stepExecModelViews;
    }
    public String getFlowName() {
        return flowName;
    }

    public String getIdExec() {
        return idExec;
    }

    public String getTimeRun() {
        return timeRun;
    }

    public String getResult() {
        return result;
    }

    public String getTimeRunning() {
        return timeRunning;
    }

    public ObservableList<FreeInputsExecViewModel> getFreeInputsExecViewModels() {
        return freeInputsExecViewModels;
    }

    public ObservableList<OutputExecModelView> getOutputExecModelViews() {
        return outputExecModelViews;
    }

    public ObservableList<StepExecModelView> getStepExecModelViews() {
        return stepExecModelViews;
    }

    // Other methods and class definitions
}
