package Components.Main.deserializer;
import Components.Main.FlowDefinitionComponent.ModelViews.*;
import StepDTO.StepInput;
import StepDTO.StepOutput;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class TableViewFlowModelDeserializer implements JsonDeserializer<TableViewFlowModel> {
    @Override
    public TableViewFlowModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String flowName = jsonObject.get("flowName").getAsString();
        int freeInputs = jsonObject.get("freeInputs").getAsInt();
        int steps = jsonObject.get("steps").getAsInt();
        String description = jsonObject.get("description").getAsString();

        // Deserialize formalOutputs
        List<String> formalOutputs = new ArrayList<>();
        JsonArray formalOutputsArray = jsonObject.getAsJsonArray("formalOutputs");
        for (JsonElement element : formalOutputsArray) {
            formalOutputs.add(element.getAsString());
        }

        // Deserialize stepsTableViewModels
        List<StepsTableViewModel> stepsTableViewModels = new ArrayList<>();
        JsonArray stepsTableViewModelsArray = jsonObject.getAsJsonArray("stepsTableViewModels");
        for (JsonElement element : stepsTableViewModelsArray) {
            StepsTableViewModel stepViewModel = context.deserialize(element, StepsTableViewModel.class);
            stepsTableViewModels.add(stepViewModel);
        }

        // Deserialize freeInputsViewModels
        List<FreeInputsViewModel> freeInputsViewModels = new ArrayList<>();
        JsonArray freeInputsViewModelsArray = jsonObject.getAsJsonArray("freeInputsViewModels");
        for (JsonElement element : freeInputsViewModelsArray) {
            FreeInputsViewModel freeInputsViewModel = context.deserialize(element, FreeInputsViewModel.class);
            freeInputsViewModels.add(freeInputsViewModel);
        }

        // Deserialize continuationFlows
        List<String> continuationFlows = new ArrayList<>();
        JsonArray continuationFlowsArray = jsonObject.getAsJsonArray("continuationFlows");
        for (JsonElement element : continuationFlowsArray) {
            continuationFlows.add(element.getAsString());
        }

        // Deserialize stepsNameBind
        List<String> stepsNameBind = new ArrayList<>();
        JsonArray stepsNameBindArray = jsonObject.getAsJsonArray("stepsNameBind");
        for (JsonElement element : stepsNameBindArray) {
            stepsNameBind.add(element.getAsString());
        }

        // Deserialize flowNameProperty, descriptionProperty, and other properties if needed
        // (Assuming they are simple properties like strings or numbers)

        // Deserialize stepInputsViewModels if it exists
        List<StepInputsViewModel> stepInputsViewModels = new ArrayList<>();
        if (jsonObject.has("stepInputsViewModels")) {
            JsonArray stepInputsViewModelsArray = jsonObject.getAsJsonArray("stepInputsViewModels");
            for (JsonElement element : stepInputsViewModelsArray) {
                StepInputsViewModel stepInputsViewModel = context.deserialize(element, StepInputsViewModel.class);
                stepInputsViewModels.add(stepInputsViewModel);
            }
        }

        // Deserialize allOutputModelViews
        List<AllOutputModelView> allOutputModelViews = new ArrayList<>();
        JsonArray allOutputModelViewsArray = jsonObject.getAsJsonArray("allOutputModelViews");
        for (JsonElement element : allOutputModelViewsArray) {
            AllOutputModelView allOutputModelView = context.deserialize(element, AllOutputModelView.class);
            allOutputModelViews.add(allOutputModelView);
        }

        // Deserialize stepOutputViewModels if it exists
        List<StepOutputViewModel> stepOutputViewModels = new ArrayList<>();
        if (jsonObject.has("stepOutputViewModels")) {
            JsonArray stepOutputViewModelsArray = jsonObject.getAsJsonArray("stepOutputViewModels");
            for (JsonElement element : stepOutputViewModelsArray) {
                StepOutputViewModel stepOutputViewModel = context.deserialize(element, StepOutputViewModel.class);
                stepOutputViewModels.add(stepOutputViewModel);
            }
        }

        // Deserialize inputsMap and outputsMap if they exist
        ObservableMap<String, List<StepInputsViewModel>> inputsMap = FXCollections.observableHashMap();
        if (jsonObject.has("inputsMap")) {
            JsonObject inputsMapObject = jsonObject.getAsJsonObject("inputsMap");
            for (Map.Entry<String, JsonElement> entry : inputsMapObject.entrySet()) {
                List<StepInputsViewModel> stepInputs = new ArrayList<>();
                JsonArray stepInputsArray = entry.getValue().getAsJsonArray();
                for (JsonElement element : stepInputsArray) {
                    StepInputsViewModel stepInput = context.deserialize(element, StepInputsViewModel.class);
                    stepInputs.add(stepInput);
                }
                inputsMap.put(entry.getKey(), stepInputs);
            }
        }

        ObservableMap<String, List<StepOutputViewModel>> outputsMap = FXCollections.observableHashMap();
        if (jsonObject.has("outputsMap")) {
            JsonObject outputsMapObject = jsonObject.getAsJsonObject("outputsMap");
            for (Map.Entry<String, JsonElement> entry : outputsMapObject.entrySet()) {
                List<StepOutputViewModel> stepOutputs = new ArrayList<>();
                JsonArray stepOutputsArray = entry.getValue().getAsJsonArray();
                for (JsonElement element : stepOutputsArray) {
                    StepOutputViewModel stepOutput = context.deserialize(element, StepOutputViewModel.class);
                    stepOutputs.add(stepOutput);
                }
                outputsMap.put(entry.getKey(), stepOutputs);
            }
        }

        // Create the TableViewFlowModel object and return it
        TableViewFlowModel tableViewFlowModel = new TableViewFlowModel();
        tableViewFlowModel.setFlowName(flowName);
        tableViewFlowModel.setFreeInputs(freeInputs);
        tableViewFlowModel.setSteps(steps);
        tableViewFlowModel.setDescription(description);
        tableViewFlowModel.setFormalOutputs(FXCollections.observableArrayList(formalOutputs));
        tableViewFlowModel.setStepsTableViewModels(FXCollections.observableArrayList(stepsTableViewModels));
        tableViewFlowModel.setFreeInputsViewModels(FXCollections.observableArrayList(freeInputsViewModels));
        tableViewFlowModel.setContinuationFlows(FXCollections.observableArrayList(continuationFlows));
        tableViewFlowModel.setStepsNameBind(FXCollections.observableArrayList(stepsNameBind));
        tableViewFlowModel.setOutputsMap(outputsMap);
        tableViewFlowModel.setInputsMap(inputsMap);
        // Set other properties and maps if needed

        return tableViewFlowModel;
    }
}
