package Stepper.Flow.Execution.Context;

import Stepper.Flow.Defenition.StepUsageDeclaration;

public interface StepExecutionContext {
    <T> T getDataValue(String dataName, Class<T> expectedDataType);
    boolean storeDataValue(String dataName, Object value);

    void addLog(String log);
    void addSummaryLine(String summary);

    void setCurrentStep(StepUsageDeclaration stepUsageDeclaration);

    // some more utility methods:
    // allow step to store log lines
    // allow steps to declare their summary line
}
