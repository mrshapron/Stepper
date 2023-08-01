package Components.Main.deserializer;

import com.google.gson.*;
import Components.Main.FlowDefinitionComponent.ModelViews.AllOutputModelView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.lang.reflect.Type;

public class ObservableListAllOutputModelViewDeserializer implements JsonDeserializer<ObservableList<AllOutputModelView>> {
    @Override
    public ObservableList<AllOutputModelView> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        ObservableList<AllOutputModelView> observableList = FXCollections.observableArrayList();
        for (JsonElement element : jsonArray) {
            AllOutputModelView outputModel = context.deserialize(element, AllOutputModelView.class);
            observableList.add(outputModel);
        }
        return observableList;
    }
}
