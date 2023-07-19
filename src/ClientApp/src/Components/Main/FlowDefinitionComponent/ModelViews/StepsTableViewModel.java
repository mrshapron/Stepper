package Components.Main.FlowDefinitionComponent.ModelViews;

import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;

public class StepsTableViewModel {
    private String stepName;
    private boolean isReadOnly;

    public StepsTableViewModel(String stepName, boolean isReadOnly) {
        this.stepName = stepName;
        this.isReadOnly = isReadOnly;
    }


    public String getStepName(){
        return stepName;
    }
    public boolean getIsReadOnly(){
        return isReadOnly;
    }
}
