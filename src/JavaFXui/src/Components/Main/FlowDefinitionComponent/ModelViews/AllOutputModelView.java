package Components.Main.FlowDefinitionComponent.ModelViews;

import Flow.Definition.StepUsageDeclaration;
import Step.Declaration.DataDefinitionDeclaration;
import javafx.beans.property.ReadOnlyStringWrapper;

public class AllOutputModelView {
    private String name;
    private String type;
    private String step;

    private ReadOnlyStringWrapper nameProperty;
    private ReadOnlyStringWrapper typeProperty;
    private ReadOnlyStringWrapper stepProperty;

    public AllOutputModelView(StepUsageDeclaration step, DataDefinitionDeclaration data){
        this.name = data.getAliasName();
        this.type = data.dataDefinition().getType().getSimpleName();
        this.step = step.getFinalStepName();

        nameProperty = new ReadOnlyStringWrapper(name);
        typeProperty = new ReadOnlyStringWrapper(type);
        stepProperty = new ReadOnlyStringWrapper(this.step);
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
