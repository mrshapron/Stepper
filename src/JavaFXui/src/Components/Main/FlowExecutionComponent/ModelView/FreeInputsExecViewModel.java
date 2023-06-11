package Components.Main.FlowExecutionComponent.ModelView;

import Flow.Defenition.FreeInputsDefinition;
import javafx.beans.property.ReadOnlyStringWrapper;

public class FreeInputsExecViewModel {
    private String name;
    private String necessity;

    private ReadOnlyStringWrapper nameProperty;
    private ReadOnlyStringWrapper necessityProperty;

    public FreeInputsExecViewModel(FreeInputsDefinition freeInputsDefinition){
        name = freeInputsDefinition.getDataDefinitionDeclaration().getAliasName();
        necessity = freeInputsDefinition.getDataDefinitionDeclaration().necessity().name();

        nameProperty = new ReadOnlyStringWrapper(name);
        necessityProperty = new ReadOnlyStringWrapper(necessity);
    }

    public String getName() {
        return name;
    }

    public String getNecessity() {
        return necessity;
    }

    public String getNameProperty() {
        return nameProperty.get();
    }

    public ReadOnlyStringWrapper namePropertyProperty() {
        return nameProperty;
    }

    public String getNecessityProperty() {
        return necessityProperty.get();
    }

    public ReadOnlyStringWrapper necessityPropertyProperty() {
        return necessityProperty;
    }
}
