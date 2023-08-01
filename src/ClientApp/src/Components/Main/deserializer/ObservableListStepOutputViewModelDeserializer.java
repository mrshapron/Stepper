package Components.Main.deserializer;

import com.google.gson.*;
import Components.Main.FlowDefinitionComponent.ModelViews.StepOutputViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.lang.reflect.Type;

public class ObservableListStepOutputViewModelDeserializer implements JsonDeserializer<ObservableList<StepOutputViewModel>> {
    @Override
    public ObservableList<StepOutputViewModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        ObservableList<StepOutputViewModel> observableList = FXCollections.observableArrayList();
        for (JsonElement element : jsonArray) {
            StepOutputViewModel stepOutput = context.deserialize(element, StepOutputViewModel.class);
            observableList.add(stepOutput);
        }
        return observableList;
    }
}
