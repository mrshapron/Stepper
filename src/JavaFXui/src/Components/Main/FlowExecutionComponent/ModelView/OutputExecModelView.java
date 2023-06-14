package Components.Main.FlowExecutionComponent.ModelView;

import Flow.Execution.History.OutputHistoryData;

public class OutputExecModelView {
    private String finalName;
    private String type;
    private String value;

    public OutputExecModelView(OutputHistoryData outputHistoryData){
        this.finalName = outputHistoryData.getFinalName();
        this.type = outputHistoryData.getType().getSimpleName();
        this.value = outputHistoryData.getData().toString();
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
