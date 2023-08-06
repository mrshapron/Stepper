package Components.Main.deserializer;

import com.google.gson.*;
import Components.Main.FlowExecutionComponent.ModelViews.InputStepModelView;

import java.lang.reflect.Type;

public class InputStepModelViewDeserializer implements JsonDeserializer<InputStepModelView> {
    @Override
    public InputStepModelView deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String outputName = jsonObject.get("outputName").getAsString();
        String value = jsonObject.get("value").getAsString();

        return new InputStepModelView(outputName, value);
    }
}

