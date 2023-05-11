package Step.Implementation;

import DataDefinitionPack.DataDefinitionRegistry;
import DataDefinitionPack.Implemantion.CustomType.RelationData;
import Flow.Execution.Context.StepExecutionContext;
import Step.AbstractStepDefinition;
import Step.DataNecessity;
import Step.Declaration.DataDefinitionDeclarationImpl;
import Step.StepResult;

import java.io.File;
import java.util.*;

public class FilesRenamerStep extends AbstractStepDefinition {
    public FilesRenamerStep(){
        super("Files Renamer", false);

        addInput(new DataDefinitionDeclarationImpl("FILES_TO_RENAME", DataNecessity.MANDATORY, "Files to rename" , DataDefinitionRegistry.LIST));
        addInput(new DataDefinitionDeclarationImpl("PREFIX", DataNecessity.OPTIONAL, "Add this prefix" , DataDefinitionRegistry.STRING));
        addInput(new DataDefinitionDeclarationImpl("SUFFIX", DataNecessity.OPTIONAL, "Append this suffix" , DataDefinitionRegistry.STRING));

        addOutput(new DataDefinitionDeclarationImpl("RENAME_RESULT", DataNecessity.NA, "Rename operation summary", DataDefinitionRegistry.RELATION));
    }
    @Override
    public StepResult invoke(StepExecutionContext context) {
        List<File> filesToRename = context.getDataValue("FILES_TO_RENAME", List.class);
        String prefix = context.getDataValue("PREFIX", String.class);
        String suffix = context.getDataValue("SUFFIX", String.class);
        if(filesToRename.isEmpty()){
            context.addSummaryLine("The list of files to rename was empty.");
            return StepResult.WARNING;
        }
        context.addLog(String.format("About to start rename %d files. Adding prefix: %s; adding suffix: %s\n", filesToRename.size(), prefix,suffix));

        List<String> mr =  Arrays.asList("ID", "FileNameSource", "FileNameTarget");
        RelationData rl = new RelationData(mr);

        if(prefix == null || prefix.isEmpty()){
            prefix = "";
        }
        if(suffix == null || suffix.isEmpty()){
            suffix = "";
        }
        Integer indexFile = 1;
        List<String> failedToRename = new ArrayList<>();
        for (File fileToRename: filesToRename) {
            Map<String,String> row = new HashMap<>();
            row.put("ID", (indexFile++).toString());
            row.put("FileNameSource", fileToRename.getName());
            String extension = fileToRename.getName().substring(fileToRename.getName().lastIndexOf('.') + 1);
            String nameWithOutExtension = fileToRename.getName().substring(0,fileToRename.getName().lastIndexOf('.'));
            String fullRename = prefix + nameWithOutExtension + suffix + '.' + extension;
            File fileRenamer = new File(fullRename);
            boolean success = fileToRename.renameTo(fileRenamer);
            if (!success){
                context.addLog(String.format("Problem renaming file %s", fileToRename.getName()));
                failedToRename.add(fileToRename.getName());
            }
            row.put("FileNameTarget", fileToRename.getName());
            rl.addRow(row);
        }
        if (!failedToRename.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder("There was a problem renaming those files : ");
            failedToRename.forEach(failedFileRename -> stringBuilder.append(failedFileRename + " "));
            context.addSummaryLine(stringBuilder.toString());
            return StepResult.WARNING;
        }
        if(rl.getNumOfLines() == 0)
            context.addSummaryLine(String.format("The Step haven't changed name to any file"));
        else{
            context.addSummaryLine(String.format("The Step changed name for %d files in the folder", rl.getNumOfLines()));
        }
        context.storeDataValue("RENAME_RESULT", rl);

        return StepResult.SUCCESS;
    }
}
