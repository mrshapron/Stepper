package Stepper.Step.Implementation;

import Stepper.DataDefinition.DataDefinitionRegistry;
import Stepper.DataDefinition.Implemantion.NumberDataDefinition;
import Stepper.Flow.Execution.Context.StepExecutionContext;
import Stepper.Step.AbstractStepDefinition;
import Stepper.Step.Declaration.DataDefinitionDeclarationImpl;
import Stepper.Step.DataNecessity;
import Stepper.Step.StepResult;

import java.util.concurrent.TimeUnit;

public class SpendSomeTimeStep extends AbstractStepDefinition {
    public SpendSomeTimeStep() {
        super("Spend Some Time", true);

        addInput(new DataDefinitionDeclarationImpl("TIME_TO_SPEND", DataNecessity.MANDATORY, "Total sleeping time(sec)", DataDefinitionRegistry.NUMBER));
    }

    @Override
    public StepResult invoke(StepExecutionContext context) {

        int timeToSleep = context.getDataValue("TIME_TO_SPEND", int.class);
        context.addLog(String.format("About to sleep for %d seconds…\n", timeToSleep));

        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(timeToSleep));
        } catch (InterruptedException e) {
            context.addLog("An Error Occurred trying Thread.Sleep : " + e.getMessage());
            return StepResult.FAILURE;
        }


        System.out.println("Done sleeping…");
        return StepResult.SUCCESS;
    }
}
