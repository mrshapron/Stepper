package Components.Main.FlowDefinitionComponent.ModelViews;

import javafx.beans.property.ReadOnlyStringWrapper;

public class FreeInputsViewModel {
    private String name;
    private String type;
    private String necessity;
    private String stepsName;
    private String userString;

    private ReadOnlyStringWrapper nameProperty;
    private ReadOnlyStringWrapper typeProperty;
    private ReadOnlyStringWrapper necessityProperty;
    private ReadOnlyStringWrapper stepsNameProperty;

    public FreeInputsViewModel(String name, String type, String necessity, String stepsName, String userString) {
        this.name = name;
        this.type = type;
        this.nameProperty = new ReadOnlyStringWrapper(name);
        this.userString = userString;
        this.stepsName = stepsName;
        this.stepsNameProperty = new ReadOnlyStringWrapper(stepsName);
        this.necessity = necessity;
        this.typeProperty = new ReadOnlyStringWrapper(type);
        this.necessityProperty = new ReadOnlyStringWrapper(necessity);
    }

    public String getUserString() {return userString;}
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
