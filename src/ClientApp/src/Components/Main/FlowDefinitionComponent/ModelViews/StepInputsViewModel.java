package Components.Main.FlowDefinitionComponent.ModelViews;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class StepInputsViewModel {
    private String name;
    private String necessity;
    private String connected;
    private String fromOutput;

    public StepInputsViewModel(String name, String necessity, String connected, String fromOutput){
        this.name = name;
        this.necessity = necessity;
        this.connected = connected;
        this.fromOutput = fromOutput;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNecessity() {
        return necessity;
    }

    public void setNecessity(String necessity) {
        this.necessity = necessity;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }

    public String getFromOutput() {
        return fromOutput;
    }

    public void setFromOutput(String fromOutput) {
        this.fromOutput = fromOutput;
    }
}
