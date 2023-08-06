package Components.Main.FlowExecutionComponent.ModelViews;

public class OutputExecModelView {
    private String finalName;
    private String type;
    private String value;

    public OutputExecModelView(){}
    public OutputExecModelView(String finalName, String type, String value){
        this.finalName = finalName;
        this.type = type;
        this.value = value;
    }

    public String getFinalName() {
        return finalName;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
