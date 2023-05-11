package DataDefinitionPack;


import DataDefinitionPack.Implemantion.*;

public enum DataDefinitionRegistry implements DataDefinition {
    NUMBER(new NumberDataDefinition()),
    MAP(new MappingDataDefinition()),
    LIST(new ListDataDefinition()),
    STRING(new StringDataDefinition()),
    DOUBLE(new DoubleDataDefinition()),
    RELATION(new RelationDataDefinition())
    ;

    DataDefinitionRegistry(DataDefinition dataDefinition) {
        this.dataDefinition = dataDefinition;
    }

    private final DataDefinition dataDefinition;

    @Override
    public String getName() {
        return dataDefinition.getName();
    }

    @Override
    public boolean isUserFriendly() {
        return dataDefinition.isUserFriendly();
    }

    @Override
    public Class<?> getType() {
        return dataDefinition.getType();
    }

}
