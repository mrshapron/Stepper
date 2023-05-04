package Stepper.Flow.Execution.Context;

public interface StepExecutionContext {
    <T> T getDataValue(String dataName, Class<T> expectedDataType);
    boolean storeDataValue(String dataName, Object value);

    void addLog(String log);
    void addSummaryLine(String summary);

    // some more utility methods:
    // allow step to store log lines
    // allow steps to declare their summary line
}
