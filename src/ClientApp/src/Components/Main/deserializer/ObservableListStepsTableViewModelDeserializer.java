package Components.Main.deserializer;

import com.google.gson.*;
import Components.Main.FlowDefinitionComponent.ModelViews.StepsTableViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.lang.reflect.Type;

public class ObservableListStepsTableViewModelDeserializer implements JsonDeserializer<ObservableList<StepsTableViewModel>> {
    @Override
    public ObservableList<StepsTableViewModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        ObservableList<StepsTableViewModel> observableList = FXCollections.observableArrayList();
        for (JsonElement element : jsonArray) {
            StepsTableViewModel step = context.deserialize(element, StepsTableViewModel.class);
            observableList.add(step);
        }
        return observableList;
    }
}
