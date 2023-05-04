package Stepper.Flow.Execution.Context;

import Stepper.Convert.DataConverter;
import Stepper.Convert.DataConverterImpl;
import Stepper.DataDefinition.DataDefinition;
import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Flow.Logger.FlowLog;
import Stepper.Mapping.CustomMappingDefinition;
import Stepper.Mapping.CustomMappingDefinitionImpl;
import Stepper.Step.Declaration.DataDefinitionDeclaration;
import Stepper.Step.StepDefinition;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class StepExecutionContextImpl implements StepExecutionContext {
    private Map<String, Object> dataValues;
    private final List<FlowLog> logs;
    private final List<FlowLog> summaries;
    private final FlowDefinition flowDefinitionRunning;
    private final DataConverter converter;
    public StepExecutionContextImpl(FlowDefinition flowDefinition, Map<String,Object> values) {
        dataValues = new HashMap<>();
        logs = new ArrayList<>();
        summaries = new ArrayList<>();
        converter = new DataConverterImpl();
        dataValues = values;
        flowDefinitionRunning = flowDefinition;
    }

    @Override
    public <T> T getDataValue(String dataName, Class<T> expectedDataType) {
        // assuming that from the data name we can get to its data definition
        DataDefinition theExpectedDataDefinition = converter.dataTypeToDefinition(expectedDataType);

        if (expectedDataType.isAssignableFrom(theExpectedDataDefinition.getType())) {
            Object aValue = dataValues.get(dataName);
            if (aValue == null)
                return null;
            return expectedDataType.cast(aValue);
        } else {
            return null;
        }
    }

    @Override
    public boolean storeDataValue(String dataName, Object value) {
        // assuming that from the data name we can get to its data definition
        DataDefinition theData = flowDefinitionRunning.getDataDefinitionByName(dataName);
        // we have the DD type so we can make sure that its from the same type
        if (theData.getType().isAssignableFrom(value.getClass())) {
            dataValues.put(dataName, value);
            return true;
        } else {
            // error handling of some sort...
        }

        return false;
    }

    @Override
    public void addLog(String log) {
        logs.add(new FlowLog(LocalDateTime.now(), log));
    }

    @Override
    public void addSummaryLine(String summary) {
        summaries.add(new FlowLog(LocalDateTime.now(), summary));
    }
}
