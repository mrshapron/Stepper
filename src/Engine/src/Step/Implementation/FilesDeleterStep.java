package Step.Implementation;


import DataDefinitionPack.DataDefinitionRegistry;
import Flow.Execution.Context.StepExecutionContext;
import Step.AbstractStepDefinition;
import Step.DataNecessity;
import Step.Declaration.DataDefinitionDeclarationImpl;
import Step.StepResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilesDeleterStep extends AbstractStepDefinition {

    public FilesDeleterStep(){
        super("Files Deleter", false);

        addInput(new DataDefinitionDeclarationImpl("FILES_LIST", DataNecessity.MANDATORY, "Files to delete" , DataDefinitionRegistry.LIST));

        addOutput(new DataDefinitionDeclarationImpl("DELETED_LIST", DataNecessity.NA, "Files failed to be deleted", DataDefinitionRegistry.LIST));
        addOutput(new DataDefinitionDeclarationImpl("DELETION_STATS", DataNecessity.NA, "Deletion summary results", DataDefinitionRegistry.MAP));

    }
    @Override
    public StepResult invoke(StepExecutionContext context) {
        List<File> filesToDelete = context.getDataValue("FILES_LIST", List.class);
        context.addLog(String.format("About to start delete %d files\n", filesToDelete.size()));

        List<String> namesFilesFailedToDelete = new ArrayList<>();
        for (File file : filesToDelete) {
            if(!file.delete()){
                context.addLog(String.format("Failed to delete file %s", file.getName()));
                namesFilesFailedToDelete.add(file.getAbsolutePath());
            }
        }

        Map<Integer,Integer> deletionStats = new HashMap<>();
        deletionStats.put(filesToDelete.size() - namesFilesFailedToDelete.size(), namesFilesFailedToDelete.size());

        context.storeDataValue("DELETED_LIST", namesFilesFailedToDelete);
        context.storeDataValue("DELETION_STATS", deletionStats);

        if(namesFilesFailedToDelete.size() == 0){
            if(filesToDelete.size() == 0)
                context.addLog("The list of files to delete is empty");
            context.addSummaryLine("the step successfully deleted the files from the folder");
            return StepResult.SUCCESS;
        } else if (namesFilesFailedToDelete.size() < filesToDelete.size()) {
            context.addLog(String.format("%d/%d of the files failed to be deleted\n", namesFilesFailedToDelete.size(), namesFilesFailedToDelete.size()));
            context.addSummaryLine(String.format("the step couldn't delete %d/%d of the files\n", namesFilesFailedToDelete.size(), namesFilesFailedToDelete.size()));
            return StepResult.WARNING;
        }else{
            context.addLog("All the files failed to be deleted");
            context.addSummaryLine(String.format("The Step Failed to delete any files in the folder"));
            return StepResult.FAILURE;
        }
    }
}
