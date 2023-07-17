package Input;


import ContinuationPac.*;
import Convert.FlowConverter;
import Convert.FlowConverterImpl;
import Flow.Definition.FlowDefinition;
import Flow.Definition.StepperDefinition;
import Flow.Definition.StepperDefinitionImpl;
import Input.Read.FlowReaderXML;
import JAXB.Generated.STStepper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InitializerDataImpl implements InitializerData {
    private FlowReaderXML _flowReaderXML;
    private FlowConverter _flowConverter;

    public InitializerDataImpl() {
        // can be changed easily to dependency injection design pattern for easier testing structure
        _flowReaderXML = new FlowReaderXML();
        _flowConverter = new FlowConverterImpl();
    }

    @Override
    public StepperDefinition InitializeStepper(String filePath) {
        STStepper stStepper = _flowReaderXML.readXMLFile(filePath);
        if (stStepper.getSTFlows() == null) {
            System.out.println("There was a problem in XML File..");
            return null;
        }
        List<FlowDefinition> flowDefinitions = stStepper.getSTFlows().getSTFlow().stream()
                .map(stFlow -> _flowConverter.Convert(stFlow))
                .collect(Collectors.toList());
        if (flowDefinitions.isEmpty())
            return null;
        for (int i = 0; i < flowDefinitions.size(); i++) {
            if (flowDefinitions.get(i) == null)
                return null;
        }
        for (FlowDefinition flowDefinition : flowDefinitions) {
            flowDefinition.automaticMapping();
            flowDefinition.customMapping();
            if (!flowDefinition.validateFlowStructure())
                return null;
        }

        initializeContinuations(stStepper, flowDefinitions);
        return new StepperDefinitionImpl(flowDefinitions, stStepper.getSTThreadPool());
    }

    @Override
    public StepperDefinition InitializeStepperViaFile(InputStream fileContent) {
        STStepper stStepper = _flowReaderXML.readXMLFileViaFile(fileContent);
        if (stStepper.getSTFlows() == null) {
            System.out.println("There was a problem in XML File..");
            return null;
        }
        List<FlowDefinition> flowDefinitions = stStepper.getSTFlows().getSTFlow().stream()
                .map(stFlow -> _flowConverter.Convert(stFlow))
                .collect(Collectors.toList());
        if (flowDefinitions.isEmpty())
            return null;
        for (int i = 0; i < flowDefinitions.size(); i++) {
            if (flowDefinitions.get(i) == null)
                return null;
        }
        for (FlowDefinition flowDefinition : flowDefinitions) {
            flowDefinition.automaticMapping();
            flowDefinition.customMapping();
            if (!flowDefinition.validateFlowStructure())
                return null;
        }

        initializeContinuations(stStepper, flowDefinitions);
        return new StepperDefinitionImpl(flowDefinitions, stStepper.getSTThreadPool());
    }

    private void initializeContinuations(STStepper stStepper, List<FlowDefinition> flowDefinitions) {
        List<ContinuationMetaData> continuationsMetaDataMap = _flowConverter.ConvertContinuations(stStepper);
        for (ContinuationMetaData continuationMetaData : continuationsMetaDataMap) {
            Optional<FlowDefinition> optionalFlowDefinitionSource = flowDefinitions.stream()
                    .filter(flowDefinition -> flowDefinition.getName().equals(continuationMetaData.flowDefinitionSource())).findFirst();
            Optional<FlowDefinition> optionalFlowDefinitionTarget = flowDefinitions.stream()
                    .filter(flowDefinition -> flowDefinition.getName().equals(continuationMetaData.flowDefinitionTarget())).findFirst();

            if (optionalFlowDefinitionSource.isPresent() && optionalFlowDefinitionTarget.isPresent()) {
                ContinuationMetaDataFlowDef continuationMetaDataFlowDef = new ContinuationMetaDataFlowDefImpl(optionalFlowDefinitionTarget.get());
                for (CustomContinuationMapping continuationMapping : continuationMetaData.customContinuationMapping()) {
                    continuationMetaDataFlowDef.addCustomContinuationMapping(continuationMapping);
                }
                optionalFlowDefinitionSource.get().addContinuationFlow(continuationMetaDataFlowDef);
            }
        }
    }
}

