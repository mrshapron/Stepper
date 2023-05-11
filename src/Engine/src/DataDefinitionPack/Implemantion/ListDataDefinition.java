package DataDefinitionPack.Implemantion;

import DataDefinitionPack.AbstractDataDefinition;

import java.util.List;
public class ListDataDefinition extends AbstractDataDefinition {
    public ListDataDefinition(){
        super("List", false, List.class);
    }
}
