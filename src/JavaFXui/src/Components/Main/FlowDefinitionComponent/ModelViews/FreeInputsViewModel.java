package Components.Main.FlowDefinitionComponent.ModelViews;

import Flow.Defenition.FreeInputsDefinition;
import javafx.beans.property.ReadOnlyStringWrapper;

public class FreeInputsViewModel {
    private String name;
    private String type;
    private String necessity;
    private String stepsName;

    private ReadOnlyStringWrapper nameProperty;
    private ReadOnlyStringWrapper typeProperty;
    private ReadOnlyStringWrapper necessityProperty;
    private ReadOnlyStringWrapper stepsNameProperty;

    public FreeInputsViewModel(FreeInputsDefinition freeInputsDefinition){
        name = freeInputsDefinition.getDataDefinitionDeclaration().getAliasName();
        type = freeInputsDefinition.getDataDefinitionDeclaration().dataDefinition().getType().getSimpleName();
        nameProperty = new ReadOnlyStringWrapper(name);
        stepsName = "";
        stepsNameProperty = new ReadOnlyStringWrapper(stepsName);
        necessity = freeInputsDefinition.getDataDefinitionDeclaration().necessity().toString();
        freeInputsDefinition.getStepUsageDeclarations().forEach(stepUsageDeclaration -> stepsName = stepsName + ", " + stepUsageDeclaration.getFinalStepName());
        stepsName =  stepsName.substring(2);
        if (stepsName.length() > 2) stepsName.substring(0, stepsName.length() - 2);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getNecessity() {
        return necessity;
    }
    public String getStepsName(){
        return stepsName;
    }

    public String getNameProperty() {
        return nameProperty.get();
    }

    public ReadOnlyStringWrapper namePropertyProperty() {
        return nameProperty;
    }

    public String getTypeProperty() {
        return typeProperty.get();
    }

    public ReadOnlyStringWrapper typePropertyProperty() {
        return typeProperty;
    }

    public String getNecessityProperty() {
        return necessityProperty.get();
    }

    public ReadOnlyStringWrapper necessityPropertyProperty() {
        return necessityProperty;
    }

    public String getStepsNameProperty() {
        return stepsNameProperty.get();
    }

    public ReadOnlyStringWrapper stepsNamePropertyProperty() {
        return stepsNameProperty;
    }
}
