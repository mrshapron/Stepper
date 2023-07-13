package BusinessLogic;

import Flow.Definition.FlowDefinition;
import Flow.Definition.InitialInputValue;
import Flow.Definition.StepperDefinition;
import Flow.Execution.FLowExecutor;
import Flow.Execution.FlowExecution;
import Flow.Execution.History.FlowHistoryData;
import Input.InitializerData;
import Input.InitializerDataImpl;
import Input.UserDataReader.UserDataReaderHandler;
import Input.UserDataReader.UserDataReaderHandlerImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class StepperBusinessLogicImpl implements StepperBusinessLogic {
    private static final int DEFAULT_NUMBER_THREADS = 6;
    private InitializerData initializerData;
    private FLowExecutor fLowExecutor;
    private UserDataReaderHandler userDataReaderHandler;
    private ExecutorService executorService;
    private StepperDefinition stepperDefinition;
    public StepperBusinessLogicImpl(){
        initializerData = new InitializerDataImpl();
        fLowExecutor = new FLowExecutor();

        userDataReaderHandler = new UserDataReaderHandlerImpl();
    }
    @Override
    public List<FlowDefinition> initializeStepper(String absolutePath) {
        this.stepperDefinition =  initializerData.InitializeStepper(absolutePath);
        if (this.stepperDefinition.getNumberOfThreads() != 0)
            executorService = Executors.newFixedThreadPool(this.stepperDefinition.getNumberOfThreads());
        else
            executorService = Executors.newFixedThreadPool(DEFAULT_NUMBER_THREADS);
        return this.stepperDefinition.getFlows();
    }

    public FlowHistoryData startFlow(FlowDefinition flowDefinition, Map<String,String> freeInputsValues, ProgressCallback progressCallback) {
        UUID uuid = UUID.randomUUID();
        List<InitialInputValue> initialInputValues = flowDefinition.getInitialInputValues();
        initialInputValues.forEach(initialInputValue -> freeInputsValues.put(initialInputValue.inputName(),initialInputValue.initialValue()));
        Map<String,Object> values =  userDataReaderHandler.ConvertDataInput(flowDefinition.getFlowFreeInputsIncludeInitializedValue(), freeInputsValues);
        FlowExecution execution = new FlowExecution(uuid.toString(), flowDefinition, values);
        fLowExecutor.executeFlow(execution, progressCallback);
        return fLowExecutor.getFlowsHistory().stream()
                .filter(flowHistoryData -> flowHistoryData.getFlowID().equals(uuid.toString()))
                .collect(Collectors.toList()).get(0);
    }

    public void executeTask(Runnable task){
        executorService.execute(task);
    }

    @Override
    public List<FlowDefinition> initializeStepperViaFile(InputStream fileContent) {
        this.stepperDefinition =  initializerData.InitializeStepperViaFile(fileContent);
        if (this.stepperDefinition.getNumberOfThreads() != 0)
            executorService = Executors.newFixedThreadPool(this.stepperDefinition.getNumberOfThreads());
        else
            executorService = Executors.newFixedThreadPool(DEFAULT_NUMBER_THREADS);
        return this.stepperDefinition.getFlows();
    }
}
