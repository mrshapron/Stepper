package Components.Main.deserializer;
import Components.Main.FlowDefinitionComponent.ModelViews.StepOutputViewModel;
import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputsMapDeserializer implements JsonDeserializer<ObservableMap<String, List<StepOutputViewModel>>> {

    @Override
    public ObservableMap<String, List<StepOutputViewModel>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ObservableMap<String, List<StepOutputViewModel>> result = FXCollections.observableHashMap();
        JsonObject jsonObject = json.getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonArray jsonArray = entry.getValue().getAsJsonArray();
            List<StepOutputViewModel> list = new ArrayList<>();

            for (JsonElement element : jsonArray) {
                StepOutputViewModel item = context.deserialize(element, StepOutputViewModel.class);
                list.add(item);
            }

            result.put(key, list);
        }

        return result;
    }
}
