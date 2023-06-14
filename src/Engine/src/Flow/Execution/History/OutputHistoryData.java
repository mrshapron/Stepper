package Flow.Execution.History;


import Flow.Defenition.StepUsageDeclaration;

public interface OutputHistoryData {
    StepUsageDeclaration fromStep();
    String getFinalName();
    Class<?> getType();
    Object getData();
}
