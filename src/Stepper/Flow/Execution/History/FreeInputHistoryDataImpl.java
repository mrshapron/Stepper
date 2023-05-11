package Stepper.Flow.Execution.History;

import Stepper.Step.DataNecessity;

public class FreeInputHistoryDataImpl implements FreeInputHistoryData {
    private Class<?> type;
    private DataNecessity necessity;
    private Object data;
    private String finalName;

    public FreeInputHistoryDataImpl(Class<?> type, DataNecessity necessity, Object data, String finalName) {
        this.type = type;
        this.necessity = necessity;
        this.data = data;
        this.finalName = finalName;
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
