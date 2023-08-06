package Components.Main.deserializer;

import Components.Main.FlowExecutionComponent.ModelViews.OutputStepModelView;
import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Type;

public class OutputStepModelViewDeserializer implements JsonDeserializer<OutputStepModelView> {
    @Override
    public OutputStepModelView deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String outputName = jsonObject.get("outputName").getAsString();
        String value = jsonObject.get("value").getAsString();

        return new OutputStepModelView(outputName, value);
    }
}
