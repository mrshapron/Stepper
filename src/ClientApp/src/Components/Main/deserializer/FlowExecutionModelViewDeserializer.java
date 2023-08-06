package Components.Main.deserializer;

import Components.Main.FlowExecutionComponent.ModelViews.FlowExecutionModelView;
import Components.Main.FlowExecutionComponent.ModelViews.FreeInputsExecViewModel;
import Components.Main.FlowExecutionComponent.ModelViews.OutputExecModelView;
import Components.Main.FlowExecutionComponent.ModelViews.StepExecModelView;
import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Type;

public class FlowExecutionModelViewDeserializer implements JsonDeserializer<FlowExecutionModelView> {
    @Override
    public FlowExecutionModelView deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        FlowExecutionModelView flowExecutionModelView = new FlowExecutionModelView();

        flowExecutionModelView.setFlowName(jsonObject.get("flowName").getAsString());
        flowExecutionModelView.setIdExec(jsonObject.get("idExec").getAsString());
        flowExecutionModelView.setTimeRun(jsonObject.get("timeRun").getAsString());
        flowExecutionModelView.setResult(jsonObject.get("result").getAsString());
        flowExecutionModelView.setTimeRunning(jsonObject.get("timeRunning").getAsString());

        JsonArray freeInputsArray = jsonObject.getAsJsonArray("freeInputsExecViewModels");
        ObservableList<FreeInputsExecViewModel> freeInputs = FXCollections.observableArrayList();
        for (JsonElement element : freeInputsArray) {
            FreeInputsExecViewModel freeInput = context.deserialize(element, FreeInputsExecViewModel.class);
            freeInputs.add(freeInput);
        }
        flowExecutionModelView.setFreeInputsExecViewModels(freeInputs);

        JsonArray outputExecArray = jsonObject.getAsJsonArray("outputExecModelViews");
        ObservableList<OutputExecModelView> outputExecs = FXCollections.observableArrayList();
        for (JsonElement element : outputExecArray) {
            OutputExecModelView outputExec = context.deserialize(element, OutputExecModelView.class);
            outputExecs.add(outputExec);
        }
        flowExecutionModelView.setOutputExecModelViews(outputExecs);

        JsonArray stepExecArray = jsonObject.getAsJsonArray("stepExecModelViews");
        ObservableList<StepExecModelView> stepExecs = FXCollections.observableArrayList();
        for (JsonElement element : stepExecArray) {
            StepExecModelView stepExec = context.deserialize(element, StepExecModelView.class);
            stepExecs.add(stepExec);
        }
        flowExecutionModelView.setStepExecModelViews(stepExecs);

        return flowExecutionModelView;
    }
}

