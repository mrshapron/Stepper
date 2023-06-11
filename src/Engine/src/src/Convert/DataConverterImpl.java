package src.Convert;

import Convert.DataConverter;
import DataDefinitionPack.DataDefinition;
import DataDefinitionPack.DataDefinitionRegistry;

public class DataConverterImpl implements DataConverter {
    @Override
    public <T> DataDefinition dataTypeToDefinition(Class<T> dataType) {
        switch (dataType.getSimpleName()){
            case "int":
            case "Integer":
                return DataDefinitionRegistry.NUMBER;
            case "Map":
                return DataDefinitionRegistry.MAP;
            case "List":
                return DataDefinitionRegistry.LIST;
            case "String":
                return DataDefinitionRegistry.STRING;
            case "double":
            case "Double":
                return DataDefinitionRegistry.DOUBLE;
            case "RelationData":
                return DataDefinitionRegistry.RELATION;
            default:
                return null;
        }

    }
}
