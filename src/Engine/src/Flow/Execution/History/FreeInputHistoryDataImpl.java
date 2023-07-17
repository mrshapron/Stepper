package Flow.Execution.History;


import Step.DataNecessity;

public class FreeInputHistoryDataImpl implements FreeInputHistoryData {
    private Class<?> type;
    private DataNecessity necessity;
    private Object data;
    private String finalName;
    private String userString;

    public FreeInputHistoryDataImpl(Class<?> type, DataNecessity necessity, Object data, String finalName, String userString) {
        this.type = type;
        this.necessity = necessity;
        this.data = data;
        this.finalName = finalName;
        this.userString = userString;
    }

    @Override
    public String userString() {
        return userString;
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
    public DataNecessity getNecessity() {
        return necessity;
    }

    @Override
    public Object getData() {
        return data;
    }
}
