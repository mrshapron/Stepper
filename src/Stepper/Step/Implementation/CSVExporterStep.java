package Stepper.Step.Implementation;

import Stepper.DataDefinition.DataDefinitionRegistry;
import Stepper.DataDefinition.Implemantion.CustomType.RelationData;
import Stepper.Flow.Execution.Context.StepExecutionContext;
import Stepper.Step.AbstractStepDefinition;
import Stepper.Step.Declaration.DataDefinitionDeclarationImpl;
import Stepper.Step.DataNecessity;
import Stepper.Step.StepResult;

import java.util.List;


public class CSVExporterStep extends AbstractStepDefinition {
    public CSVExporterStep(){
        super("CSV Exporter",true);

        addInput(new DataDefinitionDeclarationImpl("SOURCE", DataNecessity.MANDATORY,"Source data", DataDefinitionRegistry.RELATION));

        addOutput(new DataDefinitionDeclarationImpl("RESULT",DataNecessity.NA,"CSV export result",DataDefinitionRegistry.STRING));
    }

    @Override
    public StepResult invoke(StepExecutionContext context) {
        RelationData data = context.getDataValue("SOURCE", RelationData.class);
        String CSVFile = "";
        int rowNum = data.getNumOfLines();
        int colNum = data.getNumOfColumns();

        context.addLog(String.format("About to process %d lines of data", rowNum));

        for (int i=0; i<rowNum; i++)
        {
            List<String> rowData = data.getRowDataByColumnsOrder(i);
            int curr = 0;
            while(curr!=colNum)
            {
                CSVFile += rowData.get(curr);
                CSVFile+=",";
                curr++;
            }
            CSVFile+="\n";
        }
        context.storeDataValue("RESULT",CSVFile);
        if(rowNum == 0)
        {
            context.addLog("No content in table..");
            context.addSummaryLine("There was no content in the table");
            return StepResult.WARNING;
        }
        context.addSummaryLine(String.format("Step Succeeded "));
        return StepResult.SUCCESS;

    }
}