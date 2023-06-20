package Flow.Execution.History;

import Flow.Definition.StepUsageDeclaration;

public class OutputHistoryDataImpl implements OutputHistoryData {
    private String finalName;
    private Class<?> type;
    private Object data;
    private StepUsageDeclaration stepUsageDeclaration;
    public OutputHistoryDataImpl(String finalName, Class<?> type, Object data, StepUsageDeclaration step) {
        this.finalName = finalName;
        this.type = type;
        this.data= data;
        this.stepUsageDeclaration = step;
    }

    @Override
    public StepUsageDeclaration fromStep() {
        return stepUsageDeclaration;
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
