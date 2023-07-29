package Components.Main.deserializer;

import Components.Main.FlowDefinitionComponent.ModelViews.TableViewFlowModel;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TableViewFlowModelListDeserializer implements JsonDeserializer<List<TableViewFlowModel>> {
    @Override
    public List<TableViewFlowModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<TableViewFlowModel> tableViewFlowModels = new ArrayList<>();
        JsonArray jsonArray = json.getAsJsonArray();
        for (JsonElement element : jsonArray) {
            TableViewFlowModel tableViewFlowModel = context.deserialize(element, TableViewFlowModel.class);
            tableViewFlowModels.add(tableViewFlowModel);
        }
        return tableViewFlowModels;
    }
}
