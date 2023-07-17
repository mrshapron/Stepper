package Flow.Execution.Context;


import Convert.DataConverter;
import Convert.DataConverterImpl;
import DataDefinitionPack.DataDefinition;
import Flow.Definition.FlowDefinition;
import Flow.Definition.StepUsageDeclaration;
import Flow.Execution.History.FlowHistoryData;
import Flow.Execution.History.OutputHistoryDataImpl;
import Flow.Execution.History.StepHistoryData;
import Flow.Logger.FlowLog;
import Mapping.MappingDataDefinition;
import Step.Declaration.DataDefinitionDeclaration;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class StepExecutionContextImpl implements StepExecutionContext {
    private StepUsageDeclaration currentStep;
    private StepHistoryData currentHistoryStep;
    private Map<String, Object> dataValues;
    private final List<FlowLog> logs;
    private final List<FlowLog> summaries;
    private final FlowDefinition flowDefinitionRunning;
    private final DataConverter converter;
    private FlowHistoryData flowHistoryData;
    public StepExecutionContextImpl(FlowDefinition flowDefinition, Map<String,Object> values) {
        logs = new ArrayList<>();
        summaries = new ArrayList<>();
        converter = new DataConverterImpl();
        if (values != null && !values.isEmpty())
            dataValues = new HashMap<>(values);
        else
            dataValues = new HashMap<>();
        flowDefinitionRunning = flowDefinition;
    }

    @Override
    public <T> T getDataValue(String dataName, Class<T> expectedDataType) {
        // assuming that from the data name we can get to its data definition
        DataDefinition theExpectedDataDefinition = converter.dataTypeToDefinition(expectedDataType);
        String aliasName = getNameToGetTheValue(dataName);
        if (aliasName == null)
            return null;
        if (expectedDataType.isAssignableFrom(theExpectedDataDefinition.getType())) {
            Object aValue = dataValues.get(aliasName);
            if (aValue == null)
                return null;
            return expectedDataType.cast(aValue);
        } else {
            return null;
        }
    }

    private String getAliasNameByDataName(String dataName){
        List<DataDefinitionDeclaration> dataDefDecsInputs = currentStep.getStepDefinition().inputs().stream()
                .filter(dataDefinitionDeclaration -> dataDefinitionDeclaration.getName().equals(dataName))
                .collect(Collectors.toList());
        List<DataDefinitionDeclaration> dataDefDecOutputs = currentStep.getStepDefinition().outputs().stream()
                .filter(dataDefinitionDeclaration -> dataDefinitionDeclaration.getName().equals(dataName))
                .collect(Collectors.toList());
        if(dataDefDecsInputs.isEmpty() && dataDefDecOutputs.isEmpty())
            return null;
        DataDefinitionDeclaration dataDefDec;
        if(!dataDefDecsInputs.isEmpty())
            dataDefDec = dataDefDecsInputs.get(0);
        else
            dataDefDec = dataDefDecOutputs.get(0);
        return dataDefDec.getAliasName();
    }

    private String getNameToGetTheValue(String dataName){
        List<MappingDataDefinition> customMappingDefinition = flowDefinitionRunning.getCustomMappingData().stream().filter(mappingDataDefinition ->
                mappingDataDefinition.getTargetStep() == currentStep && mappingDataDefinition.getTargetData().getName().equals(dataName)).collect(Collectors.toList());
        if (customMappingDefinition.isEmpty())
            return getAliasNameByDataName(dataName);
        else
            return customMappingDefinition.get(0).getSourceData().getAliasName();
    }




    @Override
    public boolean storeDataValue(String dataName, Object value) {
        // assuming that from the data name we can get to its data definition

        DataDefinitionDeclaration theData = flowDefinitionRunning.getDataDefinitionByName(dataName);
        String aliasName = getAliasNameByDataName(dataName);
        if (aliasName == null)
            return false;
        // we have the DD type so we can make sure that its from the same type
        if (theData.dataDefinition().getType().isAssignableFrom(value.getClass())) {
            dataValues.put(aliasName, value);
            flowHistoryData.addOutputHistory(new OutputHistoryDataImpl(aliasName, value.getClass(), value, currentStep));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setHistoryData(FlowHistoryData historyData) {
        flowHistoryData = historyData;
    }



    public void setCurrentStep(StepUsageDeclaration stepUsageDeclaration, StepHistoryData historyDataStep)
    {
        currentStep = stepUsageDeclaration;
        currentHistoryStep = historyDataStep;
    }

    @Override
    public void addLog(String log) {
        FlowLog flowLog = new FlowLog(LocalDateTime.now(), log);
        logs.add(flowLog);
        currentHistoryStep.addLog(flowLog);
    }

    @Override
    public void addSummaryLine(String summary) {
        FlowLog summaryLog = new FlowLog(LocalDateTime.now(), summary);
        summaries.add(new FlowLog(LocalDateTime.now(), summary));
        currentHistoryStep.addSummary(summaryLog);
    }
}
