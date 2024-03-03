package Components.Main.deserializer;

import Components.Main.FlowExecutionComponent.ModelViews.InputStepModelView;
import Components.Main.FlowExecutionComponent.ModelViews.OutputStepModelView;
import Components.Main.FlowExecutionComponent.ModelViews.StepExecModelView;
import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Type;

public class StepExecModelViewDeserializer implements JsonDeserializer<StepExecModelView> {
    @Override
    public StepExecModelView deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String result = jsonObject.get("result").getAsString();
        String timeStarted = jsonObject.get("timeStarted").getAsString();
        String runtime = jsonObject.get("runtime").getAsString();

        StepExecModelView stepExecModelView = new StepExecModelView(name, result, timeStarted, runtime);

        // Deserialize inputStepModelViews
        JsonArray inputArray = jsonObject.getAsJsonArray("inputStepModelViews");
        ObservableList<InputStepModelView> inputStepModelViews = FXCollections.observableArrayList();
        for (JsonElement element : inputArray) {
            InputStepModelView inputStep = context.deserialize(element, InputStepModelView.class);
            inputStepModelViews.add(inputStep);
        }
        stepExecModelView.setInputStepModelViews(inputStepModelViews);

        // Deserialize outputStepModelViews
        JsonArray outputArray = jsonObject.getAsJsonArray("outputStepModelViews");
        ObservableList<OutputStepModelView> outputStepModelViews = FXCollections.observableArrayList();
        for (JsonElement element : outputArray) {
            OutputStepModelView outputStep = context.deserialize(element, OutputStepModelView.class);
            outputStepModelViews.add(outputStep);
        }
        stepExecModelView.setOutputStepModelViews(outputStepModelViews);

        // Deserialize logsStep
        JsonArray logsArray = jsonObject.getAsJsonArray("logsStep");
        ObservableList<String> logsStep = FXCollections.observableArrayList();
        for (JsonElement element : logsArray) {
            logsStep.add(element.getAsString());
        }
        stepExecModelView.setLogsStep(logsStep);

        return stepExecModelView;
    }
}
