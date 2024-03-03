package Components.Main.FlowDefinitionComponent.ModelViews;
import javafx.beans.property.ReadOnlyStringWrapper;

public class AllOutputModelView {
    private String name;
    private String type;
    private String step;

    private ReadOnlyStringWrapper nameProperty;
    private ReadOnlyStringWrapper typeProperty;
    private ReadOnlyStringWrapper stepProperty;

    public AllOutputModelView(String aliasName, String type, String step){
        this.name = aliasName;
        this.type = type;
        this.step = step;

        nameProperty = new ReadOnlyStringWrapper(name);
        typeProperty = new ReadOnlyStringWrapper(type);
        stepProperty = new ReadOnlyStringWrapper(step);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getStep() {
        return step;
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

    public String getStepProperty() {
        return stepProperty.get();
    }

    public ReadOnlyStringWrapper stepPropertyProperty() {
        return stepProperty;
    }
}
