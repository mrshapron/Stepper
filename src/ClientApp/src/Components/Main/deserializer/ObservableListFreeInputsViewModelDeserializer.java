package Components.Main.deserializer;

import com.google.gson.*;
import Components.Main.FlowDefinitionComponent.ModelViews.FreeInputsViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.lang.reflect.Type;

public class ObservableListFreeInputsViewModelDeserializer implements JsonDeserializer<ObservableList<FreeInputsViewModel>> {
    @Override
    public ObservableList<FreeInputsViewModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        ObservableList<FreeInputsViewModel> observableList = FXCollections.observableArrayList();
        for (JsonElement element : jsonArray) {
            FreeInputsViewModel freeInput = context.deserialize(element, FreeInputsViewModel.class);
            observableList.add(freeInput);
        }
        return observableList;
    }
}
