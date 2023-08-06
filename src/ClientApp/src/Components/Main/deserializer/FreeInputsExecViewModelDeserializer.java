package Components.Main.deserializer;

import Components.Main.FlowExecutionComponent.ModelViews.FreeInputsExecViewModel;
import com.google.gson.*;

import java.lang.reflect.Type;

public class FreeInputsExecViewModelDeserializer implements JsonDeserializer<FreeInputsExecViewModel> {
    @Override
    public FreeInputsExecViewModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String inputFinalName = jsonObject.get("inputFinalName").getAsString();
        String type = jsonObject.get("type").getAsString();
        String value = jsonObject.get("value").getAsString();
        String userString = jsonObject.get("userString").getAsString();
        String necessity = jsonObject.get("necessity").getAsString();

        return new FreeInputsExecViewModel(inputFinalName, type, value, userString, necessity);
    }
}

