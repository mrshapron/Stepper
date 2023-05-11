package Stepper.UI;

import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Flow.Defenition.FreeInputsDefinition;
import Stepper.Flow.Defenition.StepUsageDeclaration;
import Stepper.Flow.Execution.FLowExecutor;
import Stepper.Flow.Execution.FlowExecution;
import Stepper.Flow.Execution.History.FlowHistoryData;
import Stepper.Flow.Execution.History.FreeInputHistoryData;
import Stepper.Flow.Execution.History.OutputHistoryData;
import Stepper.Flow.Execution.History.StepHistoryData;
import Stepper.Flow.Logger.FlowLog;
import Stepper.Input.UserDataReader.UserDataReaderHandler;
import Stepper.Input.UserDataReader.UserDataReaderHandlerImpl;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserMenuImpl implements UserMenu{
    private UserDataReaderHandler userDataReaderHandler ;
    private List<FlowDefinition> currentLoadedFlows = null;
    private FLowExecutor fLowExecutor;
    private Scanner scanner;

    public UserMenuImpl(){
        userDataReaderHandler = new UserDataReaderHandlerImpl();
        scanner = new Scanner(System.in);
        fLowExecutor = new FLowExecutor();
    }

    @Override
    public void startMenu() {
        boolean exitProgram = false;
        while (!exitProgram){
            printMenu();
            int command = chooseNumber(1, MainMenuChoice.values().length);
            MainMenuChoice choiceEnum = MainMenuChoice.values()[command - 1];
            if(MainMenuChoice.READ_FILE != choiceEnum && currentLoadedFlows == null)
            {
                System.out.println("You should load first XML File..");
                continue;
            }
            switch (choiceEnum){
                case READ_FILE: {
                    currentLoadedFlows = userDataReaderHandler.ReadUserFlowInput();
                    break;
                }
                case SHOW_FLOW: {
                    FlowDefinition flowDefinition = chooseFlow();
                    if(flowDefinition == null) continue;
                    printFlow(flowDefinition);
                    break;
                }
                case START_FLOW:{
                    FlowDefinition flowDefinition = chooseFlow();
                    if (flowDefinition == null) continue;
                    startFlow(flowDefinition);
                    break;
                }
                case PAST_DETAILS:
                    FlowHistoryData flowHistoryChoice = showAndChooseAllHistoryFlows();
                    if (flowHistoryChoice == null) continue;
                    printHistoryFlow(flowHistoryChoice);
                    break;
                case STATISTICS:
                    //TODO
                    break;
                case EXIT_SYSTEM:
                    exitProgram=true;
                    break;
            }
        }
    }

    private void printHistoryFlow(FlowHistoryData flowHistoryData) {
        System.out.format("Flow ID: %s\n", flowHistoryData.getFlowID());
        System.out.format("Flow Name: %s\n", flowHistoryData.getFlowName());
        System.out.format("Flow Result: %s\n", flowHistoryData.getFlowResult());
        System.out.format("Flow Runtime: %s\n", flowHistoryData.getRuntime());

        System.out.println("Flow Free Inputs: ");
        for (FreeInputHistoryData freeInputHistoryData : flowHistoryData.getFreeInputsHistory()) {
            System.out.format("Free Input Name (After Alias): %s\n", freeInputHistoryData.getFinalName());
            System.out.format("Free Input Type: %s\n", freeInputHistoryData.getType().getSimpleName());
            System.out.format("Free Input Necessity: %s\n", freeInputHistoryData.getNecessity());
            System.out.println("$$$$$$$$$$$");
        }

        System.out.println("Flow Outputs:");
        for (OutputHistoryData outputHistoryData : flowHistoryData.getOutputsHistoryData()) {
            System.out.format("Output Final name: %s\n", outputHistoryData.getFinalName());
            System.out.format("Output Type: %s\n", outputHistoryData.getType().getSimpleName());
            System.out.format("Output Data: %s\n", outputHistoryData.getData().toString());
            System.out.println("###########");
        }
        System.out.println("Steps Information:");
        for (StepHistoryData stepHistoryData : flowHistoryData.getStepsHistoryData()) {
            System.out.format("Step Name: %s\n",stepHistoryData.getName());
            System.out.printf("Step Runtime: %d ms\n", stepHistoryData.getRunTime());
            System.out.printf("Step Result: %s\n", stepHistoryData.getResult());
            System.out.printf("Summary Line step: %s\b", stepHistoryData.getSummary());
            System.out.println("Steps Logs:");
            for (FlowLog flowLog : stepHistoryData.getLogs()) {
                System.out.printf("#Time: [%s] Content: %s\n",flowLog.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")),
                        flowLog.getContent());
            }
            System.out.println("@@@@@@@@@@@@@");
        }
    }

    private FlowHistoryData showAndChooseAllHistoryFlows(){
        if(fLowExecutor.getFlowsHistory().isEmpty()){
            System.out.println("There are no past flows details");
            return null;
        }

        System.out.println("Choose Past Flow:");
        int indexFlow = 1;
        for (FlowHistoryData flowHistoryData : fLowExecutor.getFlowsHistory()) {
            System.out.format("%d. Flow Name: %s, Flow ID: %s, Time Started: %s\n",
                    indexFlow++, flowHistoryData.getFlowName(),flowHistoryData.getFlowID(), flowHistoryData.timeStarted());
        }
        System.out.println("0. return to main menu");
        int choice = chooseNumber(0, fLowExecutor.getFlowsHistory().size());
        if (choice == 0)
            return null;
        return fLowExecutor.getFlowsHistory().get(choice - 1);
    }

    private void startFlow(FlowDefinition flowDefinition) {
        UUID uuid = UUID.randomUUID();
        Map<String,Object> values =  userDataReaderHandler.ReadDataInput(flowDefinition.getFlowFreeInputs());
        FlowExecution execution = new FlowExecution(uuid.toString(), flowDefinition, values);
        fLowExecutor.executeFlow(execution);
    }

    private void printMenu(){
        System.out.println("****************MENU*****************");
        System.out.println("Choose Command: ");
        System.out.println("1. Read XML File to the System");
        System.out.println("2. Show All Flows in the System");
        System.out.println("3. Execute flow");
        System.out.println("4. Show Details of Past Flow");
        System.out.println("5. Statistics");
        System.out.println("6. Exit System");
        System.out.println("**************************************");
    }

    private Integer chooseNumber(){
        Integer number = null;
        while (number == null){
            try{
                number = scanner.nextInt();
            } catch (Exception e){
                System.out.println("Please enter a valid number");
            }
        }
        return number;
    }

    private Integer chooseNumber(int min, int max){
        Integer number = null;
        while (number == null || (number < min || number > max)){
            try{
                number = scanner.nextInt();
            } catch (Exception e){
                System.out.println("Please enter a valid number");
            }
        }
        return number;
    }

    private FlowDefinition chooseFlow(){
        System.out.println("All Flows in the System");
        int indexFlow = 1;
        for (FlowDefinition flowDef:  currentLoadedFlows) {
            System.out.format("%d. Flow, Flow Name : %s\n",indexFlow++, flowDef.getName());
        }
        System.out.println("0. return to main menu");

        Integer choice = chooseNumber(0,currentLoadedFlows.size());
        if (choice == 0)
            return null;
        return currentLoadedFlows.get(choice - 1);

    }

    private void printFlow(FlowDefinition flowDefinition){
        System.out.format("Flow Name : %s\n", flowDefinition.getName());
        System.out.format("Flow Description : %s\n", flowDefinition.getDescription());
        System.out.print("Formal Outputs of the Flow : ");
        flowDefinition.getFlowFormalOutputs().forEach(formalOutput -> System.out.print(formalOutput + ", "));
        System.out.println();
        System.out.format("Is 'Read-Only' Flow : %s\n", flowDefinition.getIsReadOnly());
        System.out.println("Flow Steps :");
        int indexStep = 1;
        for (StepUsageDeclaration stepDefDec : flowDefinition.getFlowSteps()) {
            System.out.println(">Step Number " + (indexStep++ ) + ":");
            System.out.printf("Step Name : %s", stepDefDec.getStepDefinition().name());
            if (!stepDefDec.getFinalStepName().equals(stepDefDec.getStepDefinition().name()))
                System.out.printf(" Alias Step Name: %s", stepDefDec.getFinalStepName());
            System.out.println();
            System.out.printf("Is 'Read-Only' Step : %s\n", stepDefDec.getStepDefinition().isReadonly());
        }
        int indexFreeInput = 1;
        System.out.println("Free Inputs List :");
        for (FreeInputsDefinition freeInput : flowDefinition.getFlowFreeInputs()) {
            System.out.println("Input Number : " + (indexFreeInput++));
            System.out.printf("Input Final Name : %s\n",
                    freeInput.getDataDefinitionDeclaration().getAliasName());
            System.out.println("Type Input : " + freeInput.getDataDefinitionDeclaration().dataDefinition().getType());
            System.out.print("Connected to Steps : ");
            freeInput.getStepUsageDeclarations().forEach(stepConnected -> System.out.print(stepConnected.getFinalStepName() + ", "));
            System.out.println();
            System.out.println("Necessity Input : " + freeInput.getDataDefinitionDeclaration().necessity().toString().toLowerCase());
        }
        System.out.println("All Outputs in Flow:");
        int indexOutput = 1;
        for (StepUsageDeclaration stepUsageDeclaration : flowDefinition.getFlowSteps()) {
            for (DataDefinitionDeclaration output : stepUsageDeclaration.getStepDefinition().outputs()) {
                System.out.println("Output Number : " + indexOutput++);
                System.out.printf("Output Name : %s\n", output.getAliasName());
                System.out.printf("Output Type : %s\n", output.dataDefinition().getType());
                System.out.printf("Step Producer : %s\n", stepUsageDeclaration.getFinalStepName());
            }
        }
    }

}