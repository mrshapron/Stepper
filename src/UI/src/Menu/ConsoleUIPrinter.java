package Menu;

import Flow.Defenition.FlowDefinition;
import Flow.Execution.History.FlowHistoryData;

public interface ConsoleUIPrinter {
    void printMenu();
    void printFlow(FlowDefinition flowDefinition);
    void printHistoryFlow(FlowHistoryData flowHistoryData);
}
