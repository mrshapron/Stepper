package Stepper.Input;

import Stepper.Flow.Defenition.FlowDefinition;
import Stepper.Input.Convert.FlowConverter;
import Stepper.Input.Convert.FlowConverterImpl;
import Stepper.Input.InitializerData;
import Stepper.Input.Read.FlowReaderXML;
import Stepper.JAXB.Generated.STFlow;

import java.util.List;
import java.util.stream.Collectors;

public class InitializerDataImpl implements InitializerData {
    private FlowReaderXML _flowReaderXML;
    private FlowConverter _flowConverter;
    public InitializerDataImpl(){
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
        return flowDefinitions;
    }
}
