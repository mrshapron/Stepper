package Components.Main.FlowDefinitionComponent.ModelViews;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableViewFlowModel {
    private String flowName;
    private int freeInputs;
    private int steps;
    private String description;
    private ObservableList<String> formalOutputs;
    private ObservableList<StepsTableViewModel> stepsTableViewModels;
    private ObservableList<FreeInputsViewModel> freeInputsViewModels;



    private ObservableList<String> continuationFlows;
    private ObservableList<String> stepsNameBind;
    private ReadOnlyStringWrapper flowNameProperty;
    private ReadOnlyStringWrapper descriptionProperty;
    private ObservableList<StepInputsViewModel> stepInputsViewModels;
    private ObservableList<AllOutputModelView> allOutputModelViews;
    private ObservableList<StepOutputViewModel> stepOutputViewModels;

    public TableViewFlowModel(){
        this.flowName = flowDefinition.getName();
        this.freeInputs = flowDefinition.getFlowFreeInputs().size();
        this.steps = flowDefinition.getFlowSteps().size();
        this.description = flowDefinition.getDescription();
        stepInputsViewModels = FXCollections.observableArrayList();
        stepOutputViewModels = FXCollections.observableArrayList();
        formalOutputs = FXCollections.observableArrayList();
        stepsTableViewModels = FXCollections.observableArrayList();
        freeInputsViewModels = FXCollections.observableArrayList();
        stepsNameBind = FXCollections.observableArrayList();
        continuationFlows = FXCollections.observableArrayList();
        allOutputModelViews = FXCollections.observableArrayList();

        flowDefinition.getFlowSteps().forEach(stepUsageDeclaration -> stepsTableViewModels.add(new StepsTableViewModel(stepUsageDeclaration)));
        flowDefinition.getFlowFormalOutputs().forEach(formalOutput-> formalOutputs.add(formalOutput));
        flowDefinition.getFlowFreeInputs().forEach(freeInputsDefinition -> freeInputsViewModels.add(new FreeInputsViewModel(freeInputsDefinition)));
        flowDefinition.getFlowSteps().forEach(stepUsageDeclaration -> stepsNameBind.add(stepUsageDeclaration.getFinalStepName()));
        flowDefinition.getContinuationFlows().forEach(continuation -> continuationFlows.add(continuation.flowDefinition().getName()));
        Map<StepUsageDeclaration, List<DataDefinitionDeclaration>> mapOutputs = flowDefinition.getAllOutputs();
        mapOutputs.forEach((step,list) -> list.forEach(output-> allOutputModelViews.add(new AllOutputModelView(step, output))));
        flowNameProperty = new ReadOnlyStringWrapper(flowName);
        descriptionProperty = new ReadOnlyStringWrapper(description);
    }

    public ObservableList<String> getContinuationFlows() {return continuationFlows;}

    public ObservableList<AllOutputModelView> getAllOutputModelViews() { return allOutputModelViews; }

    public ObservableList<StepInputsViewModel> getStepInputs(){
        return stepInputsViewModels;
    }

    public void setStepInputs(String stepName){
        if(stepName == null)
            return;
        stepInputsViewModels.clear();
        StepUsageDeclaration stepUsageDeclaration = flowDefinition.getFlowSteps().stream()
                .filter(stepUsageDeclaration1 -> stepUsageDeclaration1.getFinalStepName().equals(stepName))
                .collect(Collectors.toList()).get(0);
        stepUsageDeclaration.getStepDefinition().inputs().forEach(inputDefDec -> stepInputsViewModels.add(new StepInputsViewModel(flowDefinition, stepUsageDeclaration, inputDefDec)));
    }

    public void setStepOutputs(String stepName){
        if(stepName == null)
            return;
        stepOutputViewModels.clear();
        StepUsageDeclaration stepUsageDeclaration = flowDefinition.getFlowSteps().stream()
                .filter(stepUsageDeclaration1 -> stepUsageDeclaration1.getFinalStepName().equals(stepName))
                .collect(Collectors.toList()).get(0);
        stepUsageDeclaration.getStepDefinition().outputs().forEach(outputDefDec -> stepOutputViewModels.add(new StepOutputViewModel(flowDefinition, stepUsageDeclaration, outputDefDec)));
    }

    public ObservableList<String> getStepsNameBind(){
        return this.stepsNameBind;
    }

    public ObservableList<FreeInputsViewModel> getFreeInputsViewModels(){
        return freeInputsViewModels;
    }


    public int getSteps() {
        return steps;
    }

    public ObservableList<StepsTableViewModel> getStepsTableViewModels(){
        return stepsTableViewModels;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getFreeInputs() {
        return freeInputs;
    }

    public void setFreeInputs(int freeInputs) {
        this.freeInputs = freeInputs;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObservableList<String> getFormalOutputs() {
        return formalOutputs;
    }

    public void setFormalOutputs(ObservableList<String> formalOutputs) {
        this.formalOutputs = formalOutputs;
    }

    public ReadOnlyStringWrapper getFlowNameProperty() {
        return flowNameProperty;
    }

    public ReadOnlyStringWrapper getDescriptionProperty() {
        return descriptionProperty;
    }

    public ObservableList<StepOutputViewModel> getStepOutputs() {
        return stepOutputViewModels;
    }
}
