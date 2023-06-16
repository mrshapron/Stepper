package BusinessLogic;

import Flow.Defenition.FlowDefinition;
import Flow.Execution.FLowExecutor;
import Flow.Execution.FlowExecution;
import Flow.Execution.History.FlowHistoryData;
import Input.InitializerData;
import Input.InitializerDataImpl;
import Input.UserDataReader.UserDataReaderHandler;
import Input.UserDataReader.UserDataReaderHandlerImpl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class StepperBusinessLogicImpl implements StepperBusinessLogic {
    private InitializerData initializerData;
    private FLowExecutor fLowExecutor;
    private UserDataReaderHandler userDataReaderHandler;
    private ExecutorService executorService;
    public StepperBusinessLogicImpl(){
        initializerData = new InitializerDataImpl();
        fLowExecutor = new FLowExecutor();
        userDataReaderHandler = new UserDataReaderHandlerImpl();
        executorService = Executors.newFixedThreadPool(5);
    }
    @Override
    public List<FlowDefinition> initializeFlowsList(String absolutePath) {
        List<FlowDefinition> flows =  initializerData.InitializeFlows(absolutePath);
        return flows;
    }

    public FlowHistoryData startFlow(FlowDefinition flowDefinition, Map<String,String> freeInputsValues, ProgressCallback progressCallback) {
        UUID uuid = UUID.randomUUID();
        Map<String,Object> values =  userDataReaderHandler.ConvertDataInput(flowDefinition.getFlowFreeInputs(), freeInputsValues);
        FlowExecution execution = new FlowExecution(uuid.toString(), flowDefinition, values);
        fLowExecutor.executeFlow(execution, progressCallback);
        return fLowExecutor.getFlowsHistory().stream()
                .filter(flowHistoryData -> flowHistoryData.getFlowID().equals(uuid.toString()))
                .collect(Collectors.toList()).get(0);
    }

    public void executeTask(Runnable task){
        executorService.execute(task);
    }
}
