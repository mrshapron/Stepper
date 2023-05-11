package Stepper.Flow.Execution.History;


public interface OutputHistoryData {
    String getFinalName();
    Class<?> getType();
    Object getData();
}
