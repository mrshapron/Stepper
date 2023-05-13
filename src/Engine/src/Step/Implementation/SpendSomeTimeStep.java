package Step.Implementation;


import DataDefinitionPack.DataDefinitionRegistry;
import Flow.Execution.Context.StepExecutionContext;
import Step.AbstractStepDefinition;
import Step.DataNecessity;
import Step.Declaration.DataDefinitionDeclarationImpl;
import Step.StepResult;

import java.util.concurrent.TimeUnit;

public class SpendSomeTimeStep extends AbstractStepDefinition {
    public SpendSomeTimeStep() {
        super("Spend Some Time", true);

        addInput(new DataDefinitionDeclarationImpl("TIME_TO_SPEND", DataNecessity.MANDATORY, "Total sleeping time(sec)", DataDefinitionRegistry.NUMBER));
    }

    @Override
    public StepResult invoke(StepExecutionContext context) {

        int timeToSleep = context.getDataValue("TIME_TO_SPEND", Integer.class);
        if(timeToSleep <= 0){
            context.addLog("Step had failed because of invalid Time to spend number");
            context.addSummaryLine("step had failed because of invalid Time to spend number");
            return StepResult.FAILURE;
        }
        context.addLog(String.format("About to sleep for %d seconds…\n", timeToSleep));

        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(timeToSleep));
        } catch (InterruptedException e) {
            context.addLog("An Error Occurred trying Thread.Sleep : " + e.getMessage());
            context.addSummaryLine("Step failed because of an error of thread sleep function");
            return StepResult.FAILURE;
        }


        context.addLog("Done sleeping…");
        context.addSummaryLine(String.format("step successfully slept for %d seconds", timeToSleep));
        return StepResult.SUCCESS;
    }
}
