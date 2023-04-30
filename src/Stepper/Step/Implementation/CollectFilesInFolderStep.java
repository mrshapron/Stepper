package Stepper.Step.Implementation;

import Stepper.DataDefinition.DataDefinitionRegistry;
import Stepper.Flow.Execution.Context.StepExecutionContext;
import Stepper.Step.*;
import Stepper.Step.Declaration.DataDefinitionDeclarationImpl;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectFilesInFolderStep extends AbstractStepDefinition {

    public CollectFilesInFolderStep(){
        super("Collect Files In Folder", true);

        addInput(new DataDefinitionDeclarationImpl("FOLDER_NAME", DataNecessity.MANDATORY, "Folder name to scan" , DataDefinitionRegistry.STRING));
        addInput(new DataDefinitionDeclarationImpl("FILTER", DataNecessity.OPTIONAL, "Filter only these files", DataDefinitionRegistry.STRING));

        addOutput(new DataDefinitionDeclarationImpl("FILES_LIST", DataNecessity.NA, "Files list", DataDefinitionRegistry.LIST));
        addOutput(new DataDefinitionDeclarationImpl("TOTAL_FOUND", DataNecessity.NA, "Total files found", DataDefinitionRegistry.NUMBER));
    }


    @Override
    public StepResult invoke(StepExecutionContext context) {
        String folderName = context.getDataValue("FOLDER_NAME", String.class);
        String suffixFilter =  context.getDataValue("FILTER", String.class);
        logger.addLog(String.format("Reading folder %s content with filter %s\n", folderName, suffixFilter));

        File folder = new File(folderName);
        if(!folder.exists() || !folder.isDirectory()){
            logger.addLog("The Path of " + folderName + "isn't exist or isn't a directory");
            return StepResult.FAILURE;
        }
        List<File> filteredFiles = Arrays.asList(folder.listFiles()).stream()
                .filter(file -> file.getName().substring(file.getName().lastIndexOf('.') + 1).equals(suffixFilter))
                .collect(Collectors.toList());

        int countFiles = filteredFiles.size();

        context.storeDataValue("FILES_LIST", filteredFiles);
        context.storeDataValue("TOTAL_FOUND", countFiles);

        if(countFiles == 0){
            logger.addLog("The folder exists but there are no files in it");
            return StepResult.WARNING;
        }else{
            logger.addLog(String.format("Found %d files in folder matching the filter", countFiles));
            return StepResult.SUCCESS;
        }

    }
}
