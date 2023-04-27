package Stepper.DataDefinition.Implemantion;

import Stepper.DataDefinition.AbstractDataDefinition;
import Stepper.DataDefinition.Implemantion.CustomType.RelationData;


public class RelationDataDefinition extends AbstractDataDefinition {
    public  RelationDataDefinition(){
        super("Relation", false, RelationData.class);
    }
}
