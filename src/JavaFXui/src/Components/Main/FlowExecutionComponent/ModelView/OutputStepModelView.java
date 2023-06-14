package Components.Main.FlowExecutionComponent.ModelView;

import Flow.Execution.History.OutputHistoryData;

public class OutputStepModelView {
    private String outputName;
    private String value;

    public OutputStepModelView(OutputHistoryData outputHistoryData) {
        this.outputName = outputHistoryData.getFinalName();
        value = outputHistoryData.getData().toString();
    }

    public String getOutputName() {
        return outputName;
    }

    public String getValue() {
        return value;
    }
}
