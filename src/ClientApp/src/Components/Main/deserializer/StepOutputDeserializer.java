package Components.Main.deserializer;

import StepDTO.StepOutput;
import com.google.gson.*;

import java.lang.reflect.Type;

public class StepOutputDeserializer implements JsonDeserializer<StepOutput> {
    @Override
    public StepOutput deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String connected = jsonObject.get("connected").getAsString();
        String toInput = jsonObject.get("toInput").getAsString();

        // Create and return the StepOutput object
        return new StepOutput(name, connected, toInput);
    }
}
