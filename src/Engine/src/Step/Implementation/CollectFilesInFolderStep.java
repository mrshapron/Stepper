package Step.Implementation;


import DataDefinitionPack.DataDefinitionRegistry;
import Flow.Execution.Context.StepExecutionContext;
import Step.AbstractStepDefinition;
import Step.DataNecessity;
import Step.Declaration.DataDefinitionDeclarationImpl;
import Step.StepResult;

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
        context.addLog(String.format("Reading folder %s content with filter %s\n", folderName, suffixFilter));

        File folder = new File(folderName);
        if(!folder.exists() || !folder.isDirectory()){
            context.addLog("The Path of " + folderName + "isn't exist or isn't a directory");
            context.addSummaryLine(String.format("The Path %s isn't exist or isn't a directory", folderName));
            return StepResult.FAILURE;
        }
        List<File> listFiles = Arrays.asList(folder.listFiles());
        if (suffixFilter != null) {
            List<File> filteredFiles = Arrays.asList(folder.listFiles()).stream()
                    .filter(file -> file.getName().substring(file.getName().lastIndexOf('.') + 1).equals(suffixFilter))
                    .collect(Collectors.toList());
            listFiles = filteredFiles;
        }

        int countFiles = listFiles.size();

        context.storeDataValue("FILES_LIST", listFiles);
        context.storeDataValue("TOTAL_FOUND", countFiles);

        if(countFiles == 0){
            context.addLog("The folder exists but there are no files/no files with the filter");
            context.addSummaryLine("The step couldn't find files in the folder");
            return StepResult.WARNING;
        }else{
            context.addLog(String.format("Found %d files in folder matching the filter", countFiles));
            context.addSummaryLine(String.format("The step collected %d files from the folder", countFiles));
            return StepResult.SUCCESS;
        }

    }
}
