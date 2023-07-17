package Components.Main.FlowExecutionComponent.ModelView;

import Flow.Execution.History.FreeInputHistoryData;

public class FreeInputsExecViewModel {
    private String inputFinalName;
    private String type;
    private String value;
    private String userString;
    private String necessity;



    public FreeInputsExecViewModel(FreeInputHistoryData freeInputHistoryData){
        this.inputFinalName = freeInputHistoryData.getFinalName();
        this.type = freeInputHistoryData.getType().getSimpleName();
        this.value = freeInputHistoryData.getData().toString();
        this.necessity = freeInputHistoryData.getNecessity().name();
        this.userString = freeInputHistoryData.userString();
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

    public String getNecessity() {
        return necessity;
    }
    public String getUserString() {return userString;}
}
