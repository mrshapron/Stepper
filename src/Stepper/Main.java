package Stepper;

import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Flow.Execution.FLowExecutor;
import Stepper.Flow.Execution.FlowExecution;
import Stepper.Input.UserDataReader.UserDataReaderHandler;
import Stepper.Input.UserDataReader.UserDataReaderHandlerImpl;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

//        FlowDefinition flow1 = new FlowDefinitionImpl("Flow 1", "Hello world");
//
//        flow1.getFlowSteps().add(new StepUsageDeclarationImpl(StepDefinitionRegistry.SPEND_SOME_TIME.getStepDefinition()));
//        flow1.validateFlowStructure();
//
//        FlowDefinition flow2 = new FlowDefinitionImpl("Flow 2", "show two person details");
//        flow2.getFlowSteps().add(new StepUsageDeclarationImpl(StepDefinitionRegistry.SPEND_SOME_TIME.getStepDefinition()));
//        flow2.getFlowSteps().add(new StepUsageDeclarationImpl(StepDefinitionRegistry.COLLECT_FILES_IN_FOLDER.getStepDefinition(), "Person 1 Details"));
//        flow2.getFlowSteps().add(new StepUsageDeclarationImpl(StepDefinitionRegistry.FILES_CONTENT_EXTRACTOR.getStepDefinition(), "Person 2 Details"));
//        flow2.getFlowFormalOutputs().add("DETAILS");
//        flow2.validateFlowStructure();
//
//        FLowExecutor fLowExecutor = new FLowExecutor();
//
//        FlowExecution flow2Execution1 = new FlowExecution("1", flow2);
//        // collect all user inputs and store them on the flow execution object
//        fLowExecutor.executeFlow(flow2Execution1);
//
//        FlowExecution flow2Execution2 = new FlowExecution("2", flow2);
        // collect all user inputs and store them on the flow execution object
        //        fLowExecutor.executeFlow(flow2Execution1);
        //**** CODE OF MINE **** **** CODE OF MINE **** **** CODE OF MINE **** **** CODE OF MINE **** **** CODE OF MINE ****
        //**** CODE OF MINE **** **** CODE OF MINE **** **** CODE OF MINE **** **** CODE OF MINE **** **** CODE OF MINE ****
        //**** CODE OF MINE **** **** CODE OF MINE **** **** CODE OF MINE **** **** CODE OF MINE **** **** CODE OF MINE ****
        UserDataReaderHandler userDataReaderHandler = new UserDataReaderHandlerImpl();
        List<FlowDefinition> flowDefinitions = userDataReaderHandler.ReadUserFlowInput();
        Integer idFlow = 0;
        for (FlowDefinition flowDefinition: flowDefinitions) {
            idFlow++;
            FLowExecutor flow = new FLowExecutor();
            Map<String,Object> values =  userDataReaderHandler.ReadDataInput(flowDefinition.getFlowFreeInputs());
            FlowExecution execution = new FlowExecution(idFlow.toString(), flowDefinition, values);
            flow.executeFlow(execution);
        }
        List<FlowDefinition> flows;
    }
}