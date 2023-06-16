package Components.Main.FlowExecutionComponent.ModelView;

import Flow.Execution.History.FlowHistoryData;
import Flow.Execution.History.FreeInputHistoryData;
import Flow.Execution.History.OutputHistoryData;
import Flow.Execution.History.StepHistoryData;
import javafx.beans.property.SimpleListProperty;
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

    public FlowExecutionModelView(FlowHistoryData flowHistoryData){
        this.flowName = flowHistoryData.getFlowName();
        this.idExec = flowHistoryData.getFlowID();
        this.timeRun = flowHistoryData.timeStarted();
        this.result = flowHistoryData.getFlowResult().name();
        this.stepExecModelViews = FXCollections.observableArrayList();
        this.timeRunning = String.valueOf(flowHistoryData.getRuntime());
        freeInputsExecViewModels = FXCollections.observableArrayList();
        outputExecModelViews = FXCollections.observableArrayList();
        for (FreeInputHistoryData freeInputHistoryData : flowHistoryData.getFreeInputsHistory().stream()
                .filter(freeInputHistoryData -> freeInputHistoryData.getData() != null)
                .collect(Collectors.toList())) {
            freeInputsExecViewModels.add(new FreeInputsExecViewModel(freeInputHistoryData));
        }
        for (OutputHistoryData outputHistoryData : flowHistoryData.getOutputsHistoryData()) {
            outputExecModelViews.add(new OutputExecModelView(outputHistoryData));
        }
        for (StepHistoryData stepHistoryData : flowHistoryData.getStepsHistoryData()) {
            List<OutputHistoryData> outputSelected = flowHistoryData.getOutputsHistoryData().stream()
                    .filter(outputHistoryData -> outputHistoryData.fromStep().equals(stepHistoryData.getStepDeclaration()))
                    .collect(Collectors.toList());
            stepExecModelViews.add(new StepExecModelView(stepHistoryData,outputSelected));
        }

    }
    public ObservableList<StepExecModelView> getStepExecModelViews() {
        return stepExecModelViews;
    }

    public void addFreeInputExec(FreeInputsExecViewModel freeInputsExecViewModel){
        freeInputsExecViewModels.add(freeInputsExecViewModel);
    }

    public void addOutputExec(OutputExecModelView outputExecModelView){
        outputExecModelViews.add(outputExecModelView);
    }

    public ObservableList<FreeInputsExecViewModel> getFreeInputsExecViewModels(){
        return this.freeInputsExecViewModels;
    }

    public ObservableList<OutputExecModelView> getOutputExecModelViews(){
        return this.outputExecModelViews;
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

}
