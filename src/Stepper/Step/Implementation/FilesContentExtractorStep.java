package Stepper.Step.Implementation;

import Stepper.DataDefinition.DataDefinitionRegistry;
import Stepper.DataDefinition.Implemantion.CustomType.RelationData;
import Stepper.Flow.Execution.Context.StepExecutionContext;
import Stepper.Step.AbstractStepDefinition;
import Stepper.Step.Declaration.DataDefinitionDeclarationImpl;
import Stepper.Step.DataNecessity;
import Stepper.Step.StepResult;

import java.io.*;
import java.util.*;

public class FilesContentExtractorStep extends AbstractStepDefinition {
    public FilesContentExtractorStep(){
        super("Files Content Extractor",true);

        addInput(new DataDefinitionDeclarationImpl("FILES_LIST", DataNecessity.MANDATORY,"Files to extract", DataDefinitionRegistry.LIST) ) ;
        addInput(new DataDefinitionDeclarationImpl("LINE", DataNecessity.MANDATORY,"Line number to extract",DataDefinitionRegistry.NUMBER));

        addOutput(new DataDefinitionDeclarationImpl("DATA",DataNecessity.NA,"Data extraction",DataDefinitionRegistry.RELATION));


    }

    @Override
    public StepResult invoke(StepExecutionContext context) {

        List<File> fileList = context.getDataValue("FILES_LIST", List.class);
        int numLineGetString = context.getDataValue("LINE", Integer.class);
        int indexFile = 0;
        List<String> col = Arrays.asList("ID", "fileName", "text");
        RelationData dt = new RelationData(col);

        for (File file : fileList) {
            System.out.format("About to start work on file %s\n", file.getName());
            indexFile++;
            String currentLine = tryGetLineFromFile(file,numLineGetString);
            Map<String, String> relationValuesRow = new HashMap<>();
            relationValuesRow.put("fileName", file.getName());
            relationValuesRow.put("ID", Integer.toString(indexFile));
            relationValuesRow.put("text", currentLine);
            dt.addRow(relationValuesRow);
        }
        context.storeDataValue("DATA", dt);
        return StepResult.SUCCESS;
    }
    private String tryGetLineFromFile(File file, int lineNumber){
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                count++;
                if (count == lineNumber) {
                    return line;
                }
            }
        } catch (FileNotFoundException e) {

            return "File not found";
        } catch (IOException e) {

        }finally {
            return "Not such line";
        }
    }
}
