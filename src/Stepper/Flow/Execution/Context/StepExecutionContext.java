package Stepper.Flow.Execution.Context;

import Stepper.Flow.Defenition.StepUsageDeclaration;
import Stepper.Flow.Execution.History.FlowHistoryData;
import Stepper.Flow.Execution.History.StepHistoryData;

public interface StepExecutionContext {
    <T extends Object> T getDataValue(String dataName, Class<T> expectedDataType);
    boolean storeDataValue(String dataName, Object value);
    void setHistoryData(FlowHistoryData historyData);
    FlowHistoryData getHistoryData();
    void addLog(String log);
    void addSummaryLine(String summary);

    void setCurrentStep(StepUsageDeclaration stepUsageDeclaration, StepHistoryData historyDataStep);
    StepUsageDeclaration getCurrentStep();
    // some more utility methods:
    // allow step to store log lines
    // allow steps to declare their summary line
}
