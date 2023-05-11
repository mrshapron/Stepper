package Stepper.Flow.Execution.History;

public class OutputHistoryDataImpl implements OutputHistoryData {
    private String finalName;
    private Class<?> type;
    private Object data;
    public OutputHistoryDataImpl(String finalName, Class<?> type, Object data) {
        this.finalName = finalName;
        this.type = type;
        this.data= data;
    }


    @Override
    public String getFinalName() {
        return finalName;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public Object getData() {
        return data;
    }
}
