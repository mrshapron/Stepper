package Stepper.UI;

import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Flow.Execution.History.FlowHistoryData;

public interface ConsoleUIPrinter {
    void printMenu();
    void printFlow(FlowDefinition flowDefinition);
    void printHistoryFlow(FlowHistoryData flowHistoryData);
}
