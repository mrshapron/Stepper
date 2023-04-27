package Stepper.Input.Convert;

import Stepper.Flow.Defenition.FlowDefinitionImpl;
import Stepper.Flow.Defenition.StepUsageDeclaration;
import Stepper.Flow.Defenition.StepUsageDeclarationImpl;
import Stepper.JAXB.Generated.STFlow;
import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.JAXB.Generated.STStepInFlow;
import Stepper.Step.StepDefinition;
import Stepper.Step.StepFactory;
import Stepper.Step.StepFactoryImpl;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FlowConverterImpl implements FlowConverter{
    @Override
    public FlowDefinition Convert(STFlow stFlow) {
        FlowDefinition flow = new FlowDefinitionImpl(stFlow.getName(),stFlow.getSTFlowDescription());
        StepFactory factory = new StepFactoryImpl();
        //Add Steps..
        for (STStepInFlow stepST: stFlow.getSTStepsInFlow().getSTStepInFlow()) {
            boolean isSucceeded = AddStepToFlow(flow, factory, stepST);
            if (!isSucceeded)
                System.out.format("There was a problem initialize step %s to the flow %s\n", stepST.getName(), stFlow.getName());
        }
        //Add Formal outputs..
        Arrays.stream(stFlow.getSTFlowOutput().split(",")).collect(Collectors.toList())
                .forEach(s -> flow.addFlowOutput(s));
        return flow;
    }

    public static boolean AddStepToFlow(FlowDefinition flow, StepFactory factory, STStepInFlow stepST) {
        StepDefinition stepDef = factory.MakeStep(stepST.getName());
        if(stepDef == null) return false;
        StepUsageDeclaration stepUsageDec;
        if(stepST.getAlias() != null && stepST.getAlias() != "")
            if(stepST.isContinueIfFailing() != null)
                stepUsageDec = new StepUsageDeclarationImpl(stepDef, stepST.isContinueIfFailing(), stepST.getAlias());
            else
                stepUsageDec = new StepUsageDeclarationImpl(stepDef, stepST.getAlias());
        else
            stepUsageDec = new StepUsageDeclarationImpl(stepDef);
        flow.addStepUsageDec(stepUsageDec);
        return true;
    }
}
