package DataDefinitionPack.Implemantion;


import DataDefinitionPack.AbstractDataDefinition;

import java.util.Map;

public class MappingDataDefinition extends AbstractDataDefinition {
    public MappingDataDefinition(){
        super("Mapping", false, Map.class);
    }
}

