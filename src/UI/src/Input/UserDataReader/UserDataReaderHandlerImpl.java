package Input.UserDataReader;


import Flow.Definition.FlowDefinition;
import Flow.Definition.FreeInputsDefinition;
import Input.InitializerData;
import Input.InitializerDataImpl;
import Step.DataNecessity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserDataReaderHandlerImpl implements UserDataReaderHandler {
    private final InitializerData initializerData;
    private final Scanner scanner;

    public UserDataReaderHandlerImpl() {
        this.scanner = new Scanner(System.in);
        initializerData = new InitializerDataImpl();
    }

    @Override
    public List<FlowDefinition> ReadUserFlowInput() {
        String filePathXML;
        List<FlowDefinition> flowDefinitions = null;
        while (flowDefinitions == null) {
            System.out.println("Please Enter XML File Path :");
            filePathXML = scanner.nextLine();
            if (!checkFilePathXML(filePathXML)) {
                System.out.println("There was a problem in File Path, try again..");
            } else {
                flowDefinitions = initializerData.InitializeStepper(filePathXML).getFlows();
            }
        }
        System.out.println("The file had been loaded properly.");
        return flowDefinitions;
    }

    @Override
    public Map<String, Object> ReadDataInput(List<FreeInputsDefinition> freeInputsDefDec) {
        int choice = -1;
        boolean keepReadDataInput = true;
        Map<String, Object> values = new HashMap<>();
        while (keepReadDataInput && freeInputsDefDec.size() > 0) {
            boolean canFlowRun = !freeInputsDefDec.stream().anyMatch(freeInputsDefinition ->
                    freeInputsDefinition.getDataDefinitionDeclaration().necessity() == DataNecessity.MANDATORY);
            int inputIndex = 1, flowRunChoice = -1;
            System.out.println("Choose what input you want to update");
            for (FreeInputsDefinition freeInput : freeInputsDefDec) {
                System.out.format("%d. INPUT : %s ,NECESSITY %s ", inputIndex++,
                        freeInput.getDataDefinitionDeclaration().userString(),
                        freeInput.getDataDefinitionDeclaration().necessity());
                System.out.format("Connected to Steps: ");
                freeInput.getStepUsageDeclarations().forEach(step -> System.out.format(step.getFinalStepName() + " "));
                System.out.format("\n");
            }
            if (canFlowRun) {
                flowRunChoice = inputIndex;
                System.out.format("%d. Run the flow\n", inputIndex++);
            }
            System.out.format("%d. stop flow execution and return to menu\n", inputIndex++);
            boolean isChoiceRelevant = true;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
                isChoiceRelevant = false;
            }
            if (choice >= 1 && choice < inputIndex && isChoiceRelevant) {
                if (choice == inputIndex - 1)
                    keepReadDataInput = false;
                else if (choice == flowRunChoice)
                    return values;
                else {
                    System.out.println("enter the value: ");
                    String stringValueEntered = scanner.nextLine();
                    Object addValue;
                    Class<?> dataType = freeInputsDefDec.get(choice - 1).getDataDefinitionDeclaration()
                            .dataDefinition().getType();
                    if (dataType.equals(String.class)) {
                        addValue = stringValueEntered;
                    }
                    else if(dataType.equals(Integer.class)){
                        int valueNum = Integer.parseInt(stringValueEntered);
                        addValue = valueNum;
                    }else {
                        double valueNumDouble = Double.parseDouble(stringValueEntered);
                        addValue = valueNumDouble;
                    }
                    FreeInputsDefinition chosenInput = freeInputsDefDec.remove(choice - 1);
                    values.put(chosenInput.getDataDefinitionDeclaration().getAliasName(), addValue);
                }
            }
        }
        return values;
    }

    private static boolean checkFilePathXML(String filePath){
        File file = new File(filePath);
        if (!file.exists())
            return false;
        return file.getPath().lastIndexOf('.') != -1 &&
                filePath.substring(file.getPath().lastIndexOf('.')).equals(".xml");
    }
}
