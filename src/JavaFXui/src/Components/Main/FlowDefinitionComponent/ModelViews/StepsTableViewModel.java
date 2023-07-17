package Components.Main.FlowDefinitionComponent.ModelViews;

import Flow.Definition.StepUsageDeclaration;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;

public class StepsTableViewModel {
    private String stepName;
    private boolean isReadOnly;
    private ReadOnlyStringWrapper stepNameProperty;
    private ReadOnlyBooleanWrapper isReadOnlyProperty;

    public StepsTableViewModel(StepUsageDeclaration stepUsageDeclaration){
        stepName = stepUsageDeclaration.getFinalStepName();
        isReadOnly = stepUsageDeclaration.getStepDefinition().isReadonly();

        stepNameProperty = new ReadOnlyStringWrapper(stepName);
        isReadOnlyProperty = new ReadOnlyBooleanWrapper(isReadOnly);
    }

    public String getStepName(){
        return stepName;
    }
    public boolean getIsReadOnly(){
        return isReadOnly;
    }
    public String getStepNameProperty() {
        return stepNameProperty.get();
    }

    public ReadOnlyStringWrapper stepNamePropertyProperty() {
        return stepNameProperty;
    }

    public boolean isIsReadOnlyProperty() {
        return isReadOnlyProperty.get();
    }

    public ReadOnlyBooleanWrapper isReadOnlyPropertyProperty() {
        return isReadOnlyProperty;
    }
}
