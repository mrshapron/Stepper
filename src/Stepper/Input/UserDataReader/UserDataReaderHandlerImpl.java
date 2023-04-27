package Stepper.Input.UserDataReader;

import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Input.InitializerData;
import Stepper.Input.InitializerDataImpl;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class UserDataReaderHandlerImpl implements UserDataReaderHandler {
    private final InitializerData initializerData;
    public UserDataReaderHandlerImpl(){
        initializerData = new InitializerDataImpl();
    }
    @Override
    public List<FlowDefinition> ReadUserFlowInput() {
        Scanner scanner = new Scanner(System.in);
        String filePathXML;
        List<FlowDefinition> flowDefinitions = null;
        //CHECK GIT
        while (flowDefinitions == null){
            System.out.println("Please Enter XML File Path :");
            filePathXML = scanner.nextLine();
            if (!checkFilePathXML(filePathXML)) {
                System.out.println("There was a problem in File Path, try again..");
            }else{
                flowDefinitions = initializerData.InitializeFlows(filePathXML);
            }
        }
        return flowDefinitions;
    }

    private static boolean checkFilePathXML(String filePath){
        File file = new File(filePath);
        if (!file.exists())
            return false;
        return file.getPath().lastIndexOf('.') != -1 &&
                filePath.substring(file.getPath().lastIndexOf('.')).equals(".xml");
    }
}
