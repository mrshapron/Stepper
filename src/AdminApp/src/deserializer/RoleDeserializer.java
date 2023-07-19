package deserializer;

import Flow.Definition.FlowDefinition;
import Users.Role.Role;
import Users.Role.RoleImpl;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class RoleDeserializer implements JsonDeserializer<Role> {
    @Override
    public Role deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        List<FlowDefinition> availableFlows = context.deserialize(jsonObject.get("availableFlows"), new TypeToken<List<FlowDefinition>>() {}.getType());

        // Assuming you have a concrete implementation of the Role interface
        // Replace RoleImpl with the actual implementation class of the Role interface
        return new RoleImpl(name, description, availableFlows);
    }
}