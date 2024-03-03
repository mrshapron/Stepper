package convertion;

import Components.Main.FlowDefinitionComponent.ModelViews.*;
import Flow.Definition.FlowDefinition;
import Flow.Definition.StepUsageDeclaration;
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

public class FlowDefinitionToTableViewFlowModelConverter {
    public static String convert(FlowDefinition flowDefinition) {
        TableViewFlowModel tableViewFlowModel = new TableViewFlowModel();

        tableViewFlowModel.setFlowName(flowDefinition.getName());
        tableViewFlowModel.setFreeInputs(flowDefinition.getFlowFreeInputs().size());
        tableViewFlowModel.setSteps(flowDefinition.getFlowSteps().size());
        tableViewFlowModel.setDescription(flowDefinition.getDescription());
        tableViewFlowModel.setStepsTableViewModels(FXCollections.observableArrayList());
        tableViewFlowModel.setStepOutputViewModels(FXCollections.observableArrayList());
        tableViewFlowModel.setFormalOutputs(FXCollections.observableArrayList());
        tableViewFlowModel.setStepsTableViewModels(FXCollections.observableArrayList());
        tableViewFlowModel.setFreeInputsViewModels(FXCollections.observableArrayList());
        tableViewFlowModel.setStepsNameBind(FXCollections.observableArrayList());
        tableViewFlowModel.setContinuationFlows(FXCollections.observableArrayList());
        tableViewFlowModel.setAllOutputModelViews(FXCollections.observableArrayList());


        flowDefinition.getFlowSteps().forEach(stepUsageDeclaration -> tableViewFlowModel.getStepsTableViewModels().add(new StepsTableViewModel(stepUsageDeclaration.getFinalStepName(), stepUsageDeclaration.getStepDefinition().isReadonly())));
        flowDefinition.getFlowFormalOutputs().forEach(formalOutput -> tableViewFlowModel.getFormalOutputs().add(formalOutput));


        //flowDefinition.getFlowFreeInputs().forEach(freeInputsDefinition -> tableViewFlowModel.getFreeInputsViewModels().add(new FreeInputsViewModel(freeInputsDefinition)));
        ObservableList<FreeInputsViewModel> freeInputsViewModels = FXCollections.observableArrayList();

        flowDefinition.getFlowFreeInputs().forEach(freeInputsDefinition -> {
            String name = freeInputsDefinition.getDataDefinitionDeclaration().getAliasName();
            String type = freeInputsDefinition.getDataDefinitionDeclaration().dataDefinition().getType().getSimpleName();
            String userString = freeInputsDefinition.getDataDefinitionDeclaration().userString();
            String necessity = freeInputsDefinition.getDataDefinitionDeclaration().necessity().toString();
            String stepsName = freeInputsDefinition.getStepUsageDeclarations().stream()
                    .map(StepUsageDeclaration::getFinalStepName)
                    .collect(Collectors.joining(", "));

            // Use the new constructor to create FreeInputsViewModel object
            FreeInputsViewModel freeInputsViewModel = new FreeInputsViewModel(name, type, necessity, stepsName, userString);
            freeInputsViewModels.add(freeInputsViewModel);
        });

        // Set the updated freeInputsViewModels
        tableViewFlowModel.setFreeInputsViewModels(freeInputsViewModels);

        flowDefinition.getFlowSteps().forEach(stepUsageDeclaration -> tableViewFlowModel.getStepsNameBind().add(stepUsageDeclaration.getFinalStepName()));
        flowDefinition.getContinuationFlows().forEach(continuation -> tableViewFlowModel.getContinuationFlows().add(continuation.flowDefinition().getName()));
        Map<StepUsageDeclaration, List<DataDefinitionDeclaration>> mapOutputs = flowDefinition.getAllOutputs();
        mapOutputs.forEach((step, list) -> list.forEach(output -> tableViewFlowModel.getAllOutputModelViews().add(new AllOutputModelView(output.getAliasName(), output.dataDefinition().getType().getSimpleName(), step.getFinalStepName()))));
        tableViewFlowModel.setFlowNameProperty(new ReadOnlyStringWrapper(flowDefinition.getName()));
        tableViewFlowModel.setDescriptionProperty(new ReadOnlyStringWrapper(flowDefinition.getDescription()));

        String connected;
        String fromOutput;

        List<MappingDataDefinition> connectedMap1 = new ArrayList<>();
        List<MappingDataDefinition> connectedMap2 = new ArrayList<>();


        for (StepUsageDeclaration step : flowDefinition.getFlowSteps()) {
            List<StepInputsViewModel> inputsList = new ArrayList<>(); // Create new list for each step
            List<StepOutputViewModel> outputsList = new ArrayList<>(); // Create new list for each step

            for (DataDefinitionDeclaration input : step.getStepDefinition().inputs()) {

                for (MappingDataDefinition mappingDataDefinition : flowDefinition.getMappedDataDefinitions()) {
                    if (mappingDataDefinition.getTargetData().getAliasName().equals(input.getAliasName()) && mappingDataDefinition.getTargetStep().equals(step)) {
                        connectedMap1.add(mappingDataDefinition);
                    }
                }
                if (!connectedMap1.isEmpty()) {
                    MappingDataDefinition mapFound = connectedMap1.get(0);
                    connected = mapFound.getSourceStep().getFinalStepName();
                    fromOutput = mapFound.getSourceData().getAliasName();
                } else {
                    connected = "Free Input";
                    fromOutput = "Free Input";
                }

                StepInputsViewModel stepInput = new StepInputsViewModel(input.getAliasName(), input.necessity().toString(), connected, fromOutput);
                inputsList.add(stepInput);
            }
            String toInput;
            for (DataDefinitionDeclaration output : step.getStepDefinition().outputs()) {
                for (MappingDataDefinition mappingDataDefinition : flowDefinition.getMappedDataDefinitions()) {
                    if (mappingDataDefinition.getSourceData().getAliasName().equals(output.getAliasName()) && mappingDataDefinition.getSourceStep().equals(step)) {
                        connectedMap2.add(mappingDataDefinition);
                    }
                }

                if (!connectedMap2.isEmpty()) {
                    MappingDataDefinition mapFound = connectedMap2.get(0);
                    connected = mapFound.getTargetStep().getFinalStepName();
                    toInput = mapFound.getTargetData().getAliasName();
                } else {
                    connected = "Free Output";
                    toInput = "Free Output";
                }

                StepOutputViewModel stepOutput = new StepOutputViewModel(output.getAliasName(), connected, toInput);
                outputsList.add(stepOutput);
            }
            tableViewFlowModel.addInputsMap(step.getFinalStepName(), inputsList);
            tableViewFlowModel.addOutputsMap(step.getFinalStepName(), outputsList); //need to populate tableview
        }
        Gson gson = new GsonBuilder().create();
        return gson.toJson(tableViewFlowModel);
    }
}