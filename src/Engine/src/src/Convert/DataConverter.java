package src.Convert;


import DataDefinitionPack.DataDefinition;

public interface DataConverter {
    <T> DataDefinition dataTypeToDefinition(Class<T> dataType);
}
