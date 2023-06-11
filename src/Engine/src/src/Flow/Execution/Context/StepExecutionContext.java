package src.Flow.Execution.Context;


import Flow.Defenition.StepUsageDeclaration;
import Flow.Execution.History.FlowHistoryData;
import Flow.Execution.History.StepHistoryData;

public interface StepExecutionContext {
    <T extends Object> T getDataValue(String dataName, Class<T> expectedDataType);
    boolean storeDataValue(String dataName, Object value);
    void setHistoryData(FlowHistoryData historyData);
    void addLog(String log);
    void addSummaryLine(String summary);

    void setCurrentStep(StepUsageDeclaration stepUsageDeclaration, StepHistoryData historyDataStep);
}
