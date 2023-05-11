package Stepper.Flow.Execution.History;

import Stepper.Step.DataNecessity;

import java.lang.reflect.Type;

public interface FreeInputHistoryData {
    String getFinalName();
    Class<?> getType();
    DataNecessity getNecessity();
    Object getData();
}
