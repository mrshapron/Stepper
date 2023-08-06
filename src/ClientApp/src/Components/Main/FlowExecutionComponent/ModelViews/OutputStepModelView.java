package Components.Main.FlowExecutionComponent.ModelViews;

public class OutputStepModelView {
    private String outputName;
    private String value;

    public OutputStepModelView(String outputName, String value) {
        this.outputName = outputName;
        this.value = value;
    }

    public String getOutputName() {
        return outputName;
    }

    public String getValue() {
        return value;
    }
}