package Components.Main.deserializer;

import Components.Main.FlowDefinitionComponent.ModelViews.StepInputsViewModel;
import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InputsMapDeserializer implements JsonDeserializer<ObservableMap<String, List<StepInputsViewModel>>> {

    @Override
    public ObservableMap<String, List<StepInputsViewModel>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ObservableMap<String, List<StepInputsViewModel>> result = FXCollections.observableHashMap();
        JsonObject jsonObject = json.getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonArray jsonArray = entry.getValue().getAsJsonArray();
            List<StepInputsViewModel> list = new ArrayList<>();

            for (JsonElement element : jsonArray) {
                StepInputsViewModel item = context.deserialize(element, StepInputsViewModel.class);
                list.add(item);
            }

            result.put(key, list);
        }

        return result;
    }
}
