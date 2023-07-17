package Flow.Execution.History;


import Flow.Definition.StepUsageDeclaration;

public interface OutputHistoryData {
    StepUsageDeclaration fromStep();
    String getFinalName();
    Class<?> getType();
    Object getData();
}
