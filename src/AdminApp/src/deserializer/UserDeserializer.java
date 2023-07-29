package deserializer;


//import Flow.Definition.FlowDefinition;
import Users.Role.Role;
import Users.Role.RoleImpl;
import Users.User;
import Users.UserImpl;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("username").getAsString();
        List<RoleImpl> roles = context.deserialize(jsonObject.get("roles"), new TypeToken<List<RoleImpl>>() {}.getType());

        // Assuming you have a concrete implementation of the Role interface
        // Replace RoleImpl with the actual implementation class of the Role interface
        return new UserImpl(name, roles);
    }
}