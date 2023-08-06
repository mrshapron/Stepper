
package Components.Main.FlowDefinitionComponent.ModelViews;


import StepDTO.StepInput;
import StepDTO.StepOutput;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.HashMap;
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


    private ObservableMap<String, List<StepInputsViewModel>> inputsMap; //Map between stepName and its inputs
    private ObservableMap<String, List<StepOutputViewModel>> outputsMap;//Map between stepName and its outputs

    //Make instance of this in Client's app and sent via http request to get it updated..

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass()!= TableViewFlowModel.class)
            return false;
        TableViewFlowModel object = (TableViewFlowModel) o;
        return this.flowName.equals(object.flowName);
    }

    public TableViewFlowModel() {
        inputsMap = FXCollections.observableHashMap();
        outputsMap = FXCollections.observableHashMap();
        stepInputsViewModels = FXCollections.observableArrayList();
        stepOutputViewModels = FXCollections.observableArrayList();
        formalOutputs = FXCollections.observableArrayList();
        stepsTableViewModels = FXCollections.observableArrayList();
        freeInputsViewModels = FXCollections.observableArrayList();
        continuationFlows = FXCollections.observableArrayList();
        stepsNameBind = FXCollections.observableArrayList();
        allOutputModelViews = FXCollections.observableArrayList();
        this.flowNameProperty = new ReadOnlyStringWrapper("");
        this.descriptionProperty = new ReadOnlyStringWrapper("");

    }



    public ObservableList<String> getContinuationFlows() {return continuationFlows;}

    public ObservableList<AllOutputModelView> getAllOutputModelViews() { return allOutputModelViews; }

    public ObservableList<StepInputsViewModel> getStepInputs(){
        return stepInputsViewModels;
    }

    public void setStepInputs(String stepName){
        if (stepName == null || inputsMap == null || !inputsMap.containsKey(stepName)) {
            return;
        }

        if (stepInputsViewModels != null) {
            stepInputsViewModels.clear();
        }

        List<StepInputsViewModel> stepInputs = inputsMap.get(stepName);
//        System.out.println(stepInputs.get(0));

        stepInputsViewModels.addAll(stepInputs);
    }

    public void setStepOutputs(String stepName){
        if (stepName == null || outputsMap == null || !outputsMap.containsKey(stepName)) {
            return;
        }
        if (stepOutputViewModels!= null) {
            stepOutputViewModels.clear();
        }

        List<StepOutputViewModel> stepOutputs = outputsMap.get(stepName);
        stepOutputViewModels.addAll(stepOutputs);
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

    public ObservableMap<String, List<StepInputsViewModel>> getInputsMap() {
        return inputsMap;
    }

    public ObservableMap<String, List<StepOutputViewModel>> getOutputsMap() {
        return outputsMap;
    }

    public void addOutputsMap(String a, List<StepOutputViewModel> b){
        outputsMap.put(a, b);
//        System.out.println("Just added " +a +"and outputs: " +b);
    }

    public void addInputsMap(String a, List<StepInputsViewModel> b){
        inputsMap.put(a, b);
//        System.out.println("Just added " +a +"and inputs: " +b);
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
    // Setters


    public void setStepsTableViewModels(ObservableList<StepsTableViewModel> stepsTableViewModels) {
        this.stepsTableViewModels = stepsTableViewModels;
    }

    public void setFreeInputsViewModels(ObservableList<FreeInputsViewModel> freeInputsViewModels) {
        this.freeInputsViewModels = freeInputsViewModels;
    }

    public void setContinuationFlows(ObservableList<String> continuationFlows) {
        this.continuationFlows = continuationFlows;
    }

    public void setStepsNameBind(ObservableList<String> stepsNameBind) {
        this.stepsNameBind = stepsNameBind;
    }

    public void setFlowNameProperty(ReadOnlyStringWrapper flowNameProperty) {
        this.flowNameProperty = flowNameProperty;
    }

    public void setDescriptionProperty(ReadOnlyStringWrapper descriptionProperty) {
        this.descriptionProperty = descriptionProperty;
    }

    public void setStepInputsViewModels(ObservableList<StepInputsViewModel> stepInputsViewModels) {
        this.stepInputsViewModels = stepInputsViewModels;
    }

    public void setAllOutputModelViews(ObservableList<AllOutputModelView> allOutputModelViews) {
        this.allOutputModelViews = allOutputModelViews;
    }

    public void setStepOutputViewModels(ObservableList<StepOutputViewModel> stepOutputViewModels) {
        this.stepOutputViewModels = stepOutputViewModels;
    }


    public void setOutputsMap(ObservableMap<String, List<StepOutputViewModel>> outputsMap) {
        this.outputsMap = outputsMap;
    }

    public void setInputsMap(ObservableMap<String, List<StepInputsViewModel>> intputsMap) {
        this.inputsMap = intputsMap;
    }
}
