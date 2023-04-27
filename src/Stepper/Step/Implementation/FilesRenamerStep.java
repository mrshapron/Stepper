package Stepper.Step.Implementation;

import Stepper.DataDefinition.DataDefinitionRegistry;
import Stepper.DataDefinition.Implemantion.CustomType.RelationData;
import Stepper.Flow.Execution.Context.StepExecutionContext;
import Stepper.Step.AbstractStepDefinition;
import Stepper.Step.Declaration.DataDefinitionDeclarationImpl;
import Stepper.Step.DataNecessity;
import Stepper.Step.StepResult;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        boolean prefixNullOrEmpty = true, suffixNullOrEmpty = true;
        if(prefix != null && !prefix.isEmpty()){
            prefixNullOrEmpty = true;
            filesToRename.stream().forEach(file -> {
                File fileRename = new File(prefix + file.getName());
                file.renameTo(fileRename);
            });
        }
        List<String> mr =  Arrays.asList("ID", "FileNameSource", "FileNameTarget");
        RelationData rl = new RelationData(mr);
        rl.addRow(new HashMap<>());

        if(suffix != null && !suffix.isEmpty()){
            suffixNullOrEmpty = true;
            filesToRename.stream().forEach(file -> {
                int lastIndexOfDot = file.getName().lastIndexOf('.');
                File fileRename = new File(file.getName().substring
                        (0,lastIndexOfDot) + suffix + file.getName().substring(lastIndexOfDot + 1));
                file.renameTo(fileRename);
            });
        }
        return null;
    }
}
