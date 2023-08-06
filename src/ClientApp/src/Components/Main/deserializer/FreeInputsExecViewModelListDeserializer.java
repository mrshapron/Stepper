package Components.Main.deserializer;

import Components.Main.FlowExecutionComponent.ModelViews.FreeInputsExecViewModel;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FreeInputsExecViewModelListDeserializer implements JsonDeserializer<List<FreeInputsExecViewModel>> {
    @Override
    public List<FreeInputsExecViewModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<FreeInputsExecViewModel> list = new ArrayList<>();
        JsonArray jsonArray = json.getAsJsonArray();

        for (JsonElement element : jsonArray) {
            FreeInputsExecViewModel model = context.deserialize(element, FreeInputsExecViewModel.class);
            list.add(model);
        }

        return list;
    }
}

