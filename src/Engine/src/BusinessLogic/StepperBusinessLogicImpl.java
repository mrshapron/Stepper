package BusinessLogic;

import Flow.Defenition.FlowDefinition;
import Input.InitializerData;
import Input.InitializerDataImpl;

import java.util.List;

public class StepperBusinessLogicImpl implements StepperBusinessLogic {
    private InitializerData initializerData;
    public StepperBusinessLogicImpl(){
        initializerData = new InitializerDataImpl();
    }
    @Override
    public List<FlowDefinition> initializeFlowsList(String absolutePath) {
        List<FlowDefinition> flows =  initializerData.InitializeFlows(absolutePath);
        return flows;
    }
}
