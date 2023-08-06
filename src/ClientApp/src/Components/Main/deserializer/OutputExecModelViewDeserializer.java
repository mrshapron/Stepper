package Components.Main.deserializer;


import Components.Main.FlowExecutionComponent.ModelViews.OutputExecModelView;
import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Type;

public class OutputExecModelViewDeserializer implements JsonDeserializer<OutputExecModelView> {
    @Override
    public OutputExecModelView deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String finalName = jsonObject.get("finalName").getAsString();
        String type = jsonObject.get("type").getAsString();
        String value = jsonObject.get("value").getAsString();

        return new OutputExecModelView(finalName, type, value);
    }
}

