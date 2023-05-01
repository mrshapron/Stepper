package Stepper.Convert;

import Stepper.DataDefinition.DataDefinition;
import Stepper.DataDefinition.DataDefinitionRegistry;

public class DataConverterImpl implements DataConverter{
    @Override
    public <T> DataDefinition dataTypeToDefinition(Class<T> dataType) {
        switch (dataType.getSimpleName()){
            case "Integer":
                return DataDefinitionRegistry.NUMBER;
            case "Map":
                return DataDefinitionRegistry.MAP;
            case "List":
                return DataDefinitionRegistry.LIST;
            case "String":
                return DataDefinitionRegistry.STRING;
            case "Double":
                return DataDefinitionRegistry.DOUBLE;
            case "Relation":
                return DataDefinitionRegistry.RELATION;
            default:
                return null;
        }

    }
}
