package src.Flow.Execution.History;


import Step.DataNecessity;

public interface FreeInputHistoryData {
    String getFinalName();
    Class<?> getType();
    DataNecessity getNecessity();
    Object getData();
}
