package Stepper.Flow.Execution;

import Stepper.Flow.Defenition.StepUsageDeclaration;
import Stepper.Flow.Execution.Context.StepExecutionContext;
import Stepper.Flow.Execution.Context.StepExecutionContextImpl;
import Stepper.Log.Logger;
import Stepper.Log.LoggerImpl;
import Stepper.Step.StepResult;
public class FLowExecutor {
    private final Logger logger;
    public FLowExecutor(){
        logger = LoggerImpl.getInstance();
    }
    public void executeFlow(FlowExecution flowExecution) {
        logger.addLog("Starting execution of flow " + flowExecution.getFlowDefinition().getName() + " [ID: " + flowExecution.getUniqueId() + "]");
        StepExecutionContext context = new StepExecutionContextImpl(flowExecution.getFlowDefinition(),flowExecution.getUserInputs()); // actual object goes here...
        // populate context with all free inputs (mandatory & optional) that were given from the user
        // (typically stored on top of the flow execution object)

        // start actual execution
        boolean continueFlow = true;
        for (int i = 0; i < flowExecution.getFlowDefinition().getFlowSteps().size() && continueFlow; i++) {
            StepUsageDeclaration stepUsageDeclaration = flowExecution.getFlowDefinition().getFlowSteps().get(i);
            logger.addLog("Starting to execute step: " + stepUsageDeclaration.getFinalStepName());
            context.setCurrentStep(stepUsageDeclaration);
            StepResult stepResult = stepUsageDeclaration.getStepDefinition().invoke(context);
            logger.addLog("Done executing step: " + stepUsageDeclaration.getFinalStepName() + ". Result: " + stepResult);
            // check if should continue etc..
            if(stepUsageDeclaration.skipIfFail() && stepResult == StepResult.FAILURE){
                continueFlow = false;
                logger.addLog("Stopping flow after failing the last step..");
            }
        }

        logger.addSummaryLine("End execution of flow " + flowExecution.getFlowDefinition().getName() + " [ID: " + flowExecution.getUniqueId() + "]. Status: " + flowExecution.getFlowExecutionResult());
    }
}
