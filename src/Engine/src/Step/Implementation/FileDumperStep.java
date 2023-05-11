package Step.Implementation;

import DataDefinitionPack.DataDefinitionRegistry;
import Flow.Execution.Context.StepExecutionContext;
import Step.AbstractStepDefinition;
import Step.DataNecessity;
import Step.Declaration.DataDefinitionDeclarationImpl;
import Step.StepResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileDumperStep extends AbstractStepDefinition {

    public FileDumperStep(){

        super("File Dumper",true);

        addInput(new DataDefinitionDeclarationImpl("CONTENT", DataNecessity.MANDATORY, "Content" , DataDefinitionRegistry.STRING));
        addInput(new DataDefinitionDeclarationImpl("FILE_NAME", DataNecessity.MANDATORY, "Target file path" , DataDefinitionRegistry.STRING));

        addOutput(new DataDefinitionDeclarationImpl("RESULT", DataNecessity.NA, " File Creation Result", DataDefinitionRegistry.STRING));

    }

    @Override
    public StepResult invoke(StepExecutionContext context) {

        String content = context.getDataValue("CONTENT" , String.class);
        String fileName = context.getDataValue("FILE_NAME", String.class);

        File newFile = new File(fileName);
        String res = "WARNING";
        context.addLog("About to create file named " + fileName);

        try {
            if(newFile.createNewFile()) {
                if(content.isEmpty()) {
                    context.addLog("No content found , empty file will be created!");
                    context.addSummaryLine("The step created a file with empty content");
                    context.storeDataValue("RESULT",res);
                    return StepResult.WARNING;
                }

                res = "SUCCESS";
                FileWriter writer = new FileWriter(fileName);
                writer.write(content);
                writer.close();
                context.storeDataValue("RESULT",res);
                context.addSummaryLine("");
                return StepResult.SUCCESS;
            }
            else {
                res = "FAILED - file already exists";
                context.storeDataValue("RESULT",res);
                return  StepResult.FAILURE;
            }

        } catch (IOException e) {
            context.addLog(e.getMessage());
            context.addSummaryLine("There step tried to create a new file but got an error during creation");
            return StepResult.FAILURE;
        }

    }
}