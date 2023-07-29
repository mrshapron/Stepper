package Components.Main.deserializer;

import com.google.gson.*;
import Components.Main.FlowDefinitionComponent.ModelViews.StepInputsViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.lang.reflect.Type;

public class ObservableListStepInputsViewModelDeserializer implements JsonDeserializer<ObservableList<StepInputsViewModel>> {
    @Override
    public ObservableList<StepInputsViewModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        ObservableList<StepInputsViewModel> observableList = FXCollections.observableArrayList();
        for (JsonElement element : jsonArray) {
            StepInputsViewModel stepInput = context.deserialize(element, StepInputsViewModel.class);
            observableList.add(stepInput);
        }
        return observableList;
    }
}
