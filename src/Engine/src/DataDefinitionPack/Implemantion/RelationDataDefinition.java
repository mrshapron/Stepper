package DataDefinitionPack.Implemantion;


import DataDefinitionPack.AbstractDataDefinition;
import DataDefinitionPack.Implemantion.CustomType.RelationData;

public class RelationDataDefinition extends AbstractDataDefinition {
    public  RelationDataDefinition(){
        super("Relation", false, RelationData.class);
    }
}
