package convertion2;

import Components.Main.FlowExecutionComponent.ModelViews.FreeInputsTableView;
import Flow.Definition.FreeInputsDefinition;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FlowDefinitionToFreeInputsTableView {
    public static String convertToJSON(List<FreeInputsDefinition> freeInputsList) {
        List<FreeInputsTableView> responseData = new ArrayList<>();

        // Convert the free inputs to the response data
        for (FreeInputsDefinition freeInput : freeInputsList) {
            FreeInputsTableView freeInputsTableView = new FreeInputsTableView();
            freeInputsTableView.setAliasName(freeInput.getDataDefinitionDeclaration().getAliasName());
            freeInputsTableView.setNecessity(freeInput.getDataDefinitionDeclaration().necessity().name());
            freeInputsTableView.setType(freeInput.getDataDefinitionDeclaration().dataDefinition().getType());
            responseData.add(freeInputsTableView);
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Class.class, new ClassSerializer())
                .create();

        return gson.toJson(responseData);
    }
}

class ClassSerializer implements JsonSerializer<Class<?>> {
    @Override
    public JsonElement serialize(Class<?> src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getName());
    }
}
