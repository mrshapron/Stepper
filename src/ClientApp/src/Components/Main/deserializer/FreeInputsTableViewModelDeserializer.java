package Components.Main.deserializer;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import Components.Main.FlowExecutionComponent.ModelViews.FreeInputsTableView;

import java.lang.reflect.Type;

public class FreeInputsTableViewModelDeserializer implements JsonDeserializer<FreeInputsTableView> {

    @Override
    public FreeInputsTableView deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        FreeInputsTableView freeInputsTableView = new FreeInputsTableView();

        // Deserialize JSON properties and populate the FreeInputsTableView object
        freeInputsTableView.setAliasName(jsonObject.get("aliasName").getAsString());
        freeInputsTableView.setNecessity(jsonObject.get("necessity").getAsString());

        // Deserialize the type field using Class.forName()
        String typeName = jsonObject.get("type").getAsString();
        try {
            Class<?> typeClass = Class.forName(typeName);
            freeInputsTableView.setType(typeClass);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Failed to deserialize type field: " + typeName, e);
        }

        return freeInputsTableView;
    }
}

