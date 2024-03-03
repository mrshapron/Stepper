package convertion3;

import Components.Main.FlowDefinitionComponent.ModelViews.*;
import Components.Main.FlowExecutionComponent.ModelViews.*;
import Flow.Definition.FlowDefinition;
import Flow.Definition.StepUsageDeclaration;
import Flow.Execution.History.FlowHistoryData;
import Flow.Execution.History.FreeInputHistoryData;
import Flow.Execution.History.OutputHistoryData;
import Flow.Execution.History.StepHistoryData;
import Mapping.MappingDataDefinition;
import Step.Declaration.DataDefinitionDeclaration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlowHistoryDataToFlowExecutionModelViewConvertor {

    public static String convert(FlowHistoryData flowHistoryData) {
        FlowExecutionModelView flowExecutionModelView = new FlowExecutionModelView();

        flowExecutionModelView.setFlowName(flowHistoryData.getFlowName());
        flowExecutionModelView.setIdExec(flowHistoryData.getFlowID());
        flowExecutionModelView.setTimeRun(flowHistoryData.timeStarted());
        flowExecutionModelView.setResult(flowHistoryData.getFlowResult().name());
        flowExecutionModelView.setStepExecModelViews(FXCollections.observableArrayList());
        flowExecutionModelView.setTimeRunning(String.valueOf(flowHistoryData.getRuntime()));
//        ObservableList<FreeInputsExecViewModel> freeInputsExecViewModels = FXCollections.observableArrayList();
        ObservableList<FreeInputsExecViewModel> freeInputsExecViewModels = flowExecutionModelView.getFreeInputsExecViewModels();
        if (freeInputsExecViewModels == null) {
            freeInputsExecViewModels = FXCollections.observableArrayList(); // Initialize the list if it's null
            flowExecutionModelView.setFreeInputsExecViewModels(freeInputsExecViewModels); // Set the initialized list back to the model
        }

        for (FreeInputHistoryData freeInputHistoryData : flowHistoryData.getFreeInputsHistory().stream()
                .filter(freeInputHistoryData -> freeInputHistoryData.getData() != null)
                .collect(Collectors.toList())) {
            freeInputsExecViewModels.add(new FreeInputsExecViewModel(freeInputHistoryData.getFinalName(), freeInputHistoryData.getType().getSimpleName(),
                    freeInputHistoryData.getData().toString(), freeInputHistoryData.userString(), freeInputHistoryData.getNecessity().name()));
        }

//        ObservableList<OutputExecModelView> outputExecModelViews = FXCollections.observableArrayList();
//        ObservableList<StepExecModelView> stepExecModelViews = FXCollections.observableArrayList();
//        for (FreeInputHistoryData freeInputHistoryData : flowHistoryData.getFreeInputsHistory().stream()
//                .filter(freeInputHistoryData -> freeInputHistoryData.getData() != null)
//                .collect(Collectors.toList())) {
//            freeInputsExecViewModels.add(new FreeInputsExecViewModel(freeInputHistoryData.getFinalName(), freeInputHistoryData.getType().getSimpleName(),
//                    freeInputHistoryData.getData().toString(), freeInputHistoryData.userString(), freeInputHistoryData.getNecessity().name()));
//        }
//        for (OutputHistoryData outputHistoryData : flowHistoryData.getOutputsHistoryData()) {
//            outputExecModelViews.add(new OutputExecModelView(outputHistoryData.getFinalName(), outputHistoryData.getType().getSimpleName(), outputHistoryData.getData().toString()));
//        }
//        for (StepHistoryData stepHistoryData : flowHistoryData.getStepsHistoryData()) {
//            List<OutputHistoryData> outputSelected = flowHistoryData.getOutputsHistoryData().stream()
//                    .filter(outputHistoryData -> outputHistoryData.fromStep().equals(stepHistoryData.getStepDeclaration()))
//                    .collect(Collectors.toList());
//            stepExecModelViews.add(new StepExecModelView(stepHistoryData,outputSelected));
//        }

//        for (StepHistoryData stepHistoryData : flowHistoryData.getStepsHistoryData()) {
//            List<OutputHistoryData> outputSelected = flowHistoryData.getOutputsHistoryData().stream()
//                    .filter(outputHistoryData -> outputHistoryData.fromStep().equals(stepHistoryData.getStepDeclaration()))
//                    .collect(Collectors.toList());
//
//            // Populate logs and outputStepModelViews outside the StepExecModelView instantiation
//            List<String> logs = stepHistoryData.getLogs().stream()
//                    .map(Object::toString) // Assuming flowLog has a meaningful toString() method
//                    .collect(Collectors.toList());
//
//            List<OutputStepModelView> outputStepModelViews = outputSelected.stream()
//                    .map(outputHistoryData -> new OutputStepModelView(outputHistoryData.getFinalName(), outputHistoryData.getData().toString())) // Adjust this based on your constructor
//                    .collect(Collectors.toList());
//
//            // Create StepExecModelView instance with basic data
//            StepExecModelView stepExecModelView = new StepExecModelView(stepHistoryData.getName(), stepHistoryData.getResult().name(),
//                    stepHistoryData.getTimeRunStarted(), String.valueOf(stepHistoryData.getRunTime()));
//
//            // Set the populated logs and outputStepModelViews using setters
//            stepExecModelView.setLogsStep(FXCollections.observableArrayList(logs));
//            stepExecModelView.setOutputStepModelViews(FXCollections.observableArrayList(outputStepModelViews));
//
//            // Populate other fields if needed...
//
//            stepExecModelViews.add(stepExecModelView);
        ObservableList<OutputExecModelView> outputExecModelViews = flowExecutionModelView.getOutputExecModelViews();
        ObservableList<StepExecModelView> stepExecModelViews = flowExecutionModelView.getStepExecModelViews();

        for (OutputHistoryData outputHistoryData : flowHistoryData.getOutputsHistoryData()) {
            outputExecModelViews.add(new OutputExecModelView(outputHistoryData.getFinalName(), outputHistoryData.getType().getSimpleName(), outputHistoryData.getData().toString()));
        }

        for (StepHistoryData stepHistoryData : flowHistoryData.getStepsHistoryData()) {
            List<OutputHistoryData> outputSelected = flowHistoryData.getOutputsHistoryData().stream()
                    .filter(outputHistoryData -> outputHistoryData.fromStep().equals(stepHistoryData.getStepDeclaration()))
                    .collect(Collectors.toList());

            // Populate logs and outputStepModelViews outside the StepExecModelView instantiation
            List<String> logs = stepHistoryData.getLogs().stream()
                    .map(Object::toString) // Assuming flowLog has a meaningful toString() method
                    .collect(Collectors.toList());

            List<OutputStepModelView> outputStepModelViews = outputSelected.stream()
                    .map(outputHistoryData -> new OutputStepModelView(outputHistoryData.getFinalName(), outputHistoryData.getData().toString())) // Adjust this based on your constructor
                    .collect(Collectors.toList());

            // Create StepExecModelView instance with basic data
            StepExecModelView stepExecModelView = new StepExecModelView(stepHistoryData.getName(), stepHistoryData.getResult().name(),
                    stepHistoryData.getTimeRunStarted(), String.valueOf(stepHistoryData.getRunTime()));

            // Set the populated logs and outputStepModelViews using setters
            stepExecModelView.setLogsStep(FXCollections.observableArrayList(logs));
            stepExecModelView.setOutputStepModelViews(FXCollections.observableArrayList(outputStepModelViews));

            // Populate other fields if needed...

            stepExecModelViews.add(stepExecModelView);
        }

        //        flowExecutionModelView.setFreeInputsExecViewModels(freeInputsExecViewModels);
//        flowExecutionModelView.setOutputExecModelViews(outputExecModelViews);
//        flowExecutionModelView.setStepExecModelViews(stepExecModelViews);


        Gson gson = new GsonBuilder().create();
        return gson.toJson(flowExecutionModelView);
    }
}
