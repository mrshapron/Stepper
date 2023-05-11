package Menu;

import Flow.Defenition.FlowDefinition;
import Flow.Execution.FLowExecutor;
import Flow.Execution.FlowExecution;
import Flow.Execution.History.FlowHistoryData;
import Input.UserDataReader.UserDataReaderHandler;
import Input.UserDataReader.UserDataReaderHandlerImpl;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;


public class UserMenuImpl implements UserMenu {
    private UserDataReaderHandler userDataReaderHandler ;
    private List<FlowDefinition> currentLoadedFlows = null;
    private FLowExecutor fLowExecutor;
    private ConsoleUIPrinter consoleUIPrinter;
    private Scanner scanner;

    public UserMenuImpl(){
        userDataReaderHandler = new UserDataReaderHandlerImpl();
        scanner = new Scanner(System.in);
        fLowExecutor = new FLowExecutor();
        consoleUIPrinter = new ConsoleUIPrinterImpl();
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
        consoleUIPrinter.printHistoryFlow(flowHistoryData);
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
        consoleUIPrinter.printMenu();
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
        consoleUIPrinter.printFlow(flowDefinition);
    }

}