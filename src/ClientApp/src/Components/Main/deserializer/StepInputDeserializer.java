package Components.Main.deserializer;

import StepDTO.StepInput;
import com.google.gson.*;

import java.lang.reflect.Type;

public class StepInputDeserializer implements JsonDeserializer<StepInput> {
    @Override
    public StepInput deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String necessity = jsonObject.get("necessity").getAsString();
        String connected = jsonObject.get("connected").getAsString();
        String fromOutput = jsonObject.get("fromOutput").getAsString();

        // Create and return the StepInput object
        return new StepInput(name, necessity, connected, fromOutput);
    }
}

