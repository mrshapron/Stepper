package Stepper.Step.Implementation;

import Stepper.DataDefinition.DataDefinitionRegistry;
import Stepper.DataDefinition.Implemantion.CustomType.RelationData;
import Stepper.Flow.Execution.Context.StepExecutionContext;
import Stepper.Step.AbstractStepDefinition;
import Stepper.Step.Declaration.DataDefinitionDeclarationImpl;
import Stepper.Step.DataNecessity;
import Stepper.Step.StepResult;

import java.util.List;

public class PropertiesExporterStep extends AbstractStepDefinition {

    public PropertiesExporterStep(){
        super("Properties Exporter",true);

        addInput(new DataDefinitionDeclarationImpl("SOURCE", DataNecessity.MANDATORY,"Source data", DataDefinitionRegistry.RELATION));

        addOutput(new DataDefinitionDeclarationImpl("RESULT",DataNecessity.NA,"Properties export result",DataDefinitionRegistry.STRING));

    }

    @Override
    public StepResult invoke(StepExecutionContext context) {

        RelationData data = context.getDataValue("SOURCE", RelationData.class);
        StringBuilder propertiesBuilder = new StringBuilder();
        int rowsNum = data.getNumOfLines();
        int colsNum = data.getNumOfColumns();
        List<String> columns = data.getColumns();

        if(rowsNum == 0)
        {
            logger.addLog("No content in table..");
            context.storeDataValue("RESULT", propertiesBuilder.toString());
            return StepResult.WARNING;
        }
        logger.addLog(String.format("About to process %d lines of data", rowsNum));

        for(int i = 0; i < rowsNum; i++)
        {
            List<String> rowData = data.getRowDataByColumnsOrder(i);

            for(int j=0; j<colsNum; j++)
            {
                propertiesBuilder.append("row-");
                propertiesBuilder.append((i+1));
                propertiesBuilder.append(".");
                propertiesBuilder.append(columns.get(j));
                propertiesBuilder.append("=");
                propertiesBuilder.append(rowData.get(j));
            }
            propertiesBuilder.append("\n");

        }
        context.storeDataValue("RESULT", propertiesBuilder.toString());
        logger.addLog(String.format("Extracted total of %d" , colsNum*rowsNum));
        return StepResult.SUCCESS;
    }
}