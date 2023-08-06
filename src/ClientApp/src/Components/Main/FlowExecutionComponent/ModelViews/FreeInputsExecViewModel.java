package Components.Main.FlowExecutionComponent.ModelViews;

public class FreeInputsExecViewModel {
    private String inputFinalName;
    private String type;
    private String value;
    private String userString;
    private String necessity;

    public FreeInputsExecViewModel() {}

    public FreeInputsExecViewModel(String inputFinalName, String type, String value, String userString, String necessity) {
        this.inputFinalName = inputFinalName;
        this.type = type;
        this.value = value;
        this.userString = userString;
        this.necessity = necessity;
    }

    public String getInputFinalName() {
        return inputFinalName;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getUserString() {
        return userString;
    }

    public String getNecessity() {
        return necessity;
    }
}
