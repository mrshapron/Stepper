package Menu;

import Flow.Definition.FlowDefinition;
import Flow.Execution.History.FlowHistoryData;
import Statistics.FlowStats;

import java.util.List;

public interface ConsoleUIPrinter {
    void printMenu();
    void printFlow(FlowDefinition flowDefinition);
    void printHistoryFlow(FlowHistoryData flowHistoryData);
    void printChooseFlow(List<FlowDefinition> currentLoadedFlows);
    void printStats(FlowStats flowStats, List<FlowDefinition> currentLoadedFlows);
    void printChoosePastFlow(List<FlowHistoryData> flowsHistory);
}
