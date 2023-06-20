package Flow.Execution;


import BusinessLogic.ProgressCallback;
import Flow.Definition.FreeInputsDefinition;
import Flow.Definition.StepUsageDeclaration;
import Flow.Execution.Context.FlowExecutionResult;
import Flow.Execution.Context.StepExecutionContext;
import Flow.Execution.Context.StepExecutionContextImpl;
import Flow.Execution.History.*;
import Log.Logger;
import Log.LoggerImpl;
import Statistics.FlowStats;
import Statistics.FlowStatsImpl;
import Step.StepResult;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FLowExecutor {
    private final Logger logger;
    private List<FlowHistoryData> flowsHistory;
    private FlowStats flowStats;
    public FLowExecutor(){
        logger = LoggerImpl.getInstance();
        flowsHistory = new ArrayList<>();
        flowStats = new FlowStatsImpl();
    }
    public void executeFlow(FlowExecution flowExecution, ProgressCallback progressCallback) {
        logger.addLog("Starting execution of flow " + flowExecution.getFlowDefinition().getName() + " [ID: " + flowExecution.getUniqueId() + "]");
        StepExecutionContext context = new StepExecutionContextImpl(flowExecution.getFlowDefinition(),flowExecution.getUserFreeInputs()); // actual object goes here...
        FlowHistoryData flowHistoryData = new FlowHistoryDataImpl(flowExecution.getFlowDefinition().getName(),flowExecution.getUniqueId(), flowExecution.getFlowDefinition());
        context.setHistoryData(flowHistoryData);
        // populate context with all free inputs (mandatory & optional) that were given from the user
        // (typically stored on top of the flow execution object)
        flowStats.addFlow(flowExecution.getFlowDefinition());
        // start actual execution
        String timeFlowStarted = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        flowHistoryData.setTimeStarted(timeFlowStarted);
        long startTimeFlow = System.currentTimeMillis();
        boolean continueFlow = true;
        FlowExecutionResult flowResult = FlowExecutionResult.SUCCESS;
        for (int i = 0; i < flowExecution.getFlowDefinition().getFlowSteps().size() && continueFlow; i++) {
            StepUsageDeclaration stepUsageDeclaration = flowExecution.getFlowDefinition().getFlowSteps().get(i);
            StepHistoryData stepHistoryData = new StepHistoryDataImpl(
                    stepUsageDeclaration.getStepDefinition().name(),
                    stepUsageDeclaration.getFinalStepName(), stepUsageDeclaration);
            logger.addLog("Starting to execute step: " + stepUsageDeclaration.getFinalStepName());
            context.setCurrentStep(stepUsageDeclaration, stepHistoryData);

            long timeStartStep = System.currentTimeMillis();
            stepHistoryData.setTimeRunStarted(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            StepResult stepResult = stepUsageDeclaration.getStepDefinition().invoke(context);

            progressCallback.updateProgress((double)(i + 1)/ flowExecution.getFlowDefinition().getFlowSteps().size());

            long timeStepElapsedMS =  System.currentTimeMillis() - timeStartStep;
            stepHistoryData.setRuntime(timeStepElapsedMS);
            flowStats.addStepStats(flowExecution.getFlowDefinition(), stepUsageDeclaration, timeStepElapsedMS);
            stepHistoryData.setResult(stepResult);
            flowHistoryData.addStepHistoryData(stepHistoryData);
            logger.addLog("Done executing step: " + stepUsageDeclaration.getFinalStepName() + ". Result: " + stepResult);

            if (!stepUsageDeclaration.skipIfFail() && stepResult == StepResult.FAILURE) {
                continueFlow = false;
                flowResult = FlowExecutionResult.FAILURE;
                logger.addLog("Stopping flow after failing the last step..");
            } else if (stepUsageDeclaration.skipIfFail() && stepResult == StepResult.FAILURE) {
                flowResult = FlowExecutionResult.WARNING;
            }else if(stepResult == StepResult.WARNING){
                flowResult = FlowExecutionResult.WARNING;
            }
        }

        Map<String, Object> userInputsForHistory = flowExecution.getUserFreeInputs();
        long timeFlowElapsedMS = System.currentTimeMillis() - startTimeFlow;
        HistoryDataFreeInputInitial(flowExecution, flowHistoryData, userInputsForHistory);
        flowStats.addFlowRunTime(flowExecution.getFlowDefinition(), timeFlowElapsedMS);
        flowHistoryData.setFlowResult(flowResult);
        flowHistoryData.setRuntime(timeFlowElapsedMS);
        flowsHistory.add(flowHistoryData);
        logger.addSummaryLine("End execution of flow " + flowExecution.getFlowDefinition().getName() + " [ID: " + flowExecution.getUniqueId() + "]. Status: " + flowExecution.getFlowExecutionResult());
    }

    private static void HistoryDataFreeInputInitial(FlowExecution flowExecution, FlowHistoryData flowHistoryData, Map<String, Object> userInputsForHistory) {
        for (FreeInputsDefinition freeInputsDefinition : flowExecution.getFlowDefinition().getFlowFreeInputs()) {
            FreeInputHistoryData freeInputHistoryData = new FreeInputHistoryDataImpl(
                    freeInputsDefinition.getDataDefinitionDeclaration().dataDefinition().getType(),
                    freeInputsDefinition.getDataDefinitionDeclaration().necessity(),
                    userInputsForHistory.get(freeInputsDefinition.getDataDefinitionDeclaration().getAliasName()),
                            freeInputsDefinition.getDataDefinitionDeclaration().getAliasName(),
                    freeInputsDefinition.getDataDefinitionDeclaration().userString());
            flowHistoryData.addFreeInputHistory(freeInputHistoryData);
        }
    }

    public List<FlowHistoryData> getFlowsHistory(){
        return this.flowsHistory;
    }
    public FlowStats getFlowStats(){return this.flowStats;}
}
