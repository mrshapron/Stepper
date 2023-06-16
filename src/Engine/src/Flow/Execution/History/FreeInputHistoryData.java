package Flow.Execution.History;


import Step.DataNecessity;

public interface FreeInputHistoryData {
    String userString();
    String getFinalName();
    Class<?> getType();
    DataNecessity getNecessity();
    Object getData();
}
