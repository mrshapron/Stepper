package Stepper.DataDefinition.Implemantion;
import Stepper.DataDefinition.AbstractDataDefinition;
import java.util.List;
public class ListDataDefinition extends AbstractDataDefinition {
    public ListDataDefinition(){
        super("List", false, List.class);
    }
}
