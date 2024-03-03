package Components.Main.deserializer;

import com.google.gson.*;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.lang.reflect.Type;

public class ReadOnlyStringWrapperDeserializer implements JsonDeserializer<ReadOnlyStringWrapper> {
    @Override
    public ReadOnlyStringWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // If the element is a JSON object, extract the "value" property as a String
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            String value = jsonObject.get("value").getAsString();
            return new ReadOnlyStringWrapper(value);
        }
        // If the element is a JSON primitive, treat it as a plain String
        else if (json.isJsonPrimitive()) {
            String value = json.getAsString();
            return new ReadOnlyStringWrapper(value);
        }
        // Handle other cases, if necessary
        else {
            throw new JsonParseException("Unable to deserialize ReadOnlyStringWrapper: " + json.toString());
        }
    }
}
