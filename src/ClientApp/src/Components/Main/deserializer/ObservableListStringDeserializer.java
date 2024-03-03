package Components.Main.deserializer;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.lang.reflect.Type;

public class ObservableListStringDeserializer implements JsonDeserializer<ObservableList<String>> {
    @Override
    public ObservableList<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (JsonElement element : jsonArray) {
            observableList.add(element.getAsString());
        }
        return observableList;
    }
}
