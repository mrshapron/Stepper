package Stepper.Input;

import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Convert.FlowConverter;
import Stepper.Convert.FlowConverterImpl;
import Stepper.Input.Read.FlowReaderXML;
import Stepper.JAXB.Generated.STFlow;

import java.util.List;
import java.util.stream.Collectors;

public class InitializerDataImpl implements InitializerData {
    private FlowReaderXML _flowReaderXML;
    private FlowConverter _flowConverter;
    public InitializerDataImpl(){
        // can be changed easily to dependency injection design pattern for easier testing structure
        _flowReaderXML = new FlowReaderXML();
        _flowConverter = new FlowConverterImpl();
    }
    @Override
    public List<FlowDefinition> InitializeFlows(String filePath) {
        List<STFlow> stFlows = _flowReaderXML.read(filePath);
        if (stFlows == null){
            System.out.println("There was a problem in XML File..");
            return null;
        }

        List<FlowDefinition> flowDefinitions = stFlows.stream()
                .map(stFlow ->  _flowConverter.Convert(stFlow))
                .collect(Collectors.toList());
        if(flowDefinitions.isEmpty())
            return null;
        for (FlowDefinition flowDefinition : flowDefinitions) {
            flowDefinition.automaticMapping();
            flowDefinition.customMapping();
            if (!flowDefinition.validateFlowStructure())
                return null;
        }
        return flowDefinitions;
    }
}
