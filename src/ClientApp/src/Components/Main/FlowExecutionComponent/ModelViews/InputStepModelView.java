package Components.Main.FlowExecutionComponent.ModelViews;

public class InputStepModelView {
    private String outputName;
    private String value;

    public InputStepModelView(String outputName, String value){
        this.outputName= outputName;
        this.value= value;
    }
    public InputStepModelView() {
    }

    public String getOutputName() {
        return outputName;
    }

    public String getValue() {
        return value;
    }
}
