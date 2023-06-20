package Components.Main.FlowExecutionComponent;

import BusinessLogic.StepperBusinessLogic;
import Components.Main.FlowDefinitionComponent.ModelViews.FreeInputsViewModel;
import Components.Main.FlowDefinitionComponent.ModelViews.TableViewFlowModel;
import Components.Main.FlowExecutionComponent.ModelView.*;
import ContinuationPac.Continuation;
import ContinuationPac.ContinuationMapping;
import Flow.Definition.FlowDefinition;
import Flow.Execution.History.FlowHistoryData;
import Flow.Execution.History.OutputHistoryData;
import Step.DataNecessity;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.*;
import java.util.stream.Collectors;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import javafx.animation.Interpolator;
public class FlowExecutionController {
    @FXML private Label lblRunFlowContinue;
    @FXML private Button btnAddContinuation;
    @FXML private ListView<String> listViewContinuations;
    @FXML private Label lblFreeInputUserString;
    @FXML private HBox executionHBOX;
    @FXML private Button btnRunFlow;
    @FXML private Label lblStatusFreeInput;
    @FXML private Label lblSelectedInput;
    @FXML private TableView<FreeInputsViewModel> tableFreeInputs;
    @FXML
    private TableColumn<FreeInputsViewModel, String> freeInputsInputNameColumn;
    @FXML
    private TableColumn<FreeInputsViewModel, String> freeInputsNecessityColumn;
    @FXML
    private TextField txtValueInput;
    @FXML
    private Button btnEnterValueInput;
    @FXML
    private Button btnClearInputValue;
    @FXML
    private TableView<CurValInputModelView> tableCurrentValues;
    @FXML
    private TableColumn<CurValInputModelView, String> inputNameCurrentValCol;
    @FXML
    private TableColumn<CurValInputModelView, String> valueEnteredCurrentValCol;
    @FXML
    private Button btnStartFlow;
    @FXML
    private Label lblFlowName;


    @FXML private Label lblProgressBar;
    @FXML private ProgressBar progressBarFlow;
    @FXML private TableView<FlowExecutionModelView> tableAllFlowExecutions;
    @FXML private TableColumn<?, ?> allFlowExecFlowNameCol;
    @FXML private TableColumn<?, ?> allFlowExecIDExecCol;
    @FXML private TableColumn<?, ?> allFlowExecTimeRunCol;
    @FXML private TableView<FlowExecutionModelView> tableExecutionsDetails;
    @FXML private TableColumn<?, ?> execDetailsFlowNameCol;
    @FXML private TableColumn<?, ?> execDetailsIDExecCol;
    @FXML private TableColumn<?, ?> execDetailsResultCol;
    @FXML private TableColumn<?, ?> execDetailsTimeRunningCol;
    @FXML private TableView<FreeInputsExecViewModel> tableFreeInputsExecDetails;
    @FXML private TableColumn<?, ?> freeInputsExecInputNameCol;
    @FXML private TableColumn<?, ?> freeInputsExecTypeCol;
    @FXML private TableColumn<?, ?> freeInputsExecValueCol;
    @FXML private TableColumn<?, ?> freeInputsExecNeccCol;
    @FXML private TableView<OutputExecModelView> tableOutputExecution;
    @FXML private TableColumn<?, ?> outputExecFinalNameCol;
    @FXML private TableColumn<?, ?> outputExecTypeCol;
    @FXML private TableColumn<?, ?> outputExecValueCol;

    @FXML private Label lblStepExecutionResult;
    @FXML private TableColumn<?, ?> stepNameExecResCol;
    @FXML private TableColumn<?, ?> stepResultExecResultRow;
    @FXML private TableView<StepExecModelView> moreStepDetailsTable;
    @FXML private TableColumn<?, ?> stepNameStepDetailsCol;
    @FXML private TableColumn<?, ?> stepResStepDetailsCol;
    @FXML private TableColumn<?, ?> timeStartStepDetailsCol;
    @FXML private TableView<?> listInputStepTable;
    @FXML private TableColumn<?, ?> inputNameInputStepCol;
    @FXML private TableColumn<?, ?> valInputStepCol;
    @FXML private TableView<?> listOutputStepTable;
    @FXML private TableColumn<?, ?> OutputNameOutputStepCol;
    @FXML private TableColumn<?, ?> valOutputStepCol;
    @FXML private ListView<String> stepLogsListView;
    @FXML private TableColumn<?,?> runtimeMoreStepCol;
    @FXML private TableView<StepExecModelView> stepExecutionResultTable;



    private FlowDefinition selectedFlow;
    private TableViewFlowModel currentTableViewModel;
    private TableViewFlowModel primaryTableViewModel;
    private StepperBusinessLogic businessLogic;
    private BooleanProperty isAddValueEnable;

    private ObjectProperty<FlowDefinition> flowDefinitionObjectProperty;

    private StringProperty valueEnteredProperty;
    private ObservableList<CurValInputModelView> curValInputModelViews;
    private StringProperty currentSelectedFreeInput;
    private StringProperty statusRunFlowFreeInputs;
    private BooleanProperty isFlowCanRun;
    private ObservableList<FlowExecutionModelView> flowExecutionModelViews;
    private StringProperty userStringFreeInputProperty;
    private StringProperty flowNameOrContinuationProperty;
    private StringProperty flowNameProperty;
    private StringBinding flowNameBinding;
    private BooleanProperty isContinuationOn;
    private StringProperty lblRunFlowContinueProperty;
    private Queue<Map<String,String>> freeInputsValuesContinuationQueue;
    private Queue<FlowDefinition> flowOrderContinuationQueue;
    public FlowExecutionController(){
        flowDefinitionObjectProperty = new SimpleObjectProperty<>(null);
        freeInputsValuesContinuationQueue = new ArrayDeque<>();
        flowOrderContinuationQueue = new ArrayDeque<>();
    }

    public void initialize(){
        allFlowExecFlowNameCol.setCellValueFactory(new PropertyValueFactory<>("flowName"));
        allFlowExecIDExecCol.setCellValueFactory(new PropertyValueFactory<>("idExec"));
        allFlowExecTimeRunCol.setCellValueFactory(new PropertyValueFactory<>("timeRun"));

        flowExecutionModelViews = FXCollections.observableArrayList();

        execDetailsFlowNameCol.setCellValueFactory(new PropertyValueFactory<>("flowName"));
        execDetailsIDExecCol.setCellValueFactory(new PropertyValueFactory<>("idExec"));
        execDetailsResultCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        execDetailsTimeRunningCol.setCellValueFactory(new PropertyValueFactory<>("timeRunning"));

        freeInputsExecInputNameCol.setCellValueFactory(new PropertyValueFactory<>("inputFinalName"));
        freeInputsExecTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        freeInputsExecValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        freeInputsExecNeccCol.setCellValueFactory(new PropertyValueFactory<>("necessity"));

        outputExecFinalNameCol.setCellValueFactory(new PropertyValueFactory<>("finalName"));
        outputExecTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        outputExecValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        stepNameExecResCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stepResultExecResultRow.setCellValueFactory(new PropertyValueFactory<>("result"));

        stepNameStepDetailsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stepResStepDetailsCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        timeStartStepDetailsCol.setCellValueFactory(new PropertyValueFactory<>("timeStarted"));
        runtimeMoreStepCol.setCellValueFactory(new PropertyValueFactory<>("runtime"));
        lblRunFlowContinueProperty = new SimpleStringProperty("Run Flow");
        tableAllFlowExecutions.setItems(flowExecutionModelViews);
        isFlowCanRun = new SimpleBooleanProperty(false);
        isAddValueEnable = new SimpleBooleanProperty(false);
        valueEnteredProperty = new SimpleStringProperty("");
        curValInputModelViews = FXCollections.observableArrayList();
        curValInputModelViews.addListener((ListChangeListener<CurValInputModelView>) change -> onChangedCurValuesInputs(change));
        currentSelectedFreeInput = new SimpleStringProperty("Nothing");
        statusRunFlowFreeInputs = new SimpleStringProperty("Enter Mandatory Inputs To Run the Flow");
        btnEnterValueInput.setOnAction(event -> onClickEnterInputValue());
        btnClearInputValue.setOnAction(event -> onClearInputClick(event));
        freeInputsInputNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        freeInputsNecessityColumn.setCellValueFactory(new PropertyValueFactory<>("necessity"));
        lblSelectedInput.textProperty().bind(currentSelectedFreeInput);
        lblStatusFreeInput.textProperty().bind(statusRunFlowFreeInputs);
        inputNameCurrentValCol.setCellValueFactory(new PropertyValueFactory<>("inputName"));
        valueEnteredCurrentValCol.setCellValueFactory(new PropertyValueFactory<>("valueEntered"));
        txtValueInput.textProperty().bindBidirectional(valueEnteredProperty);
        tableCurrentValues.setItems(curValInputModelViews);
        flowNameProperty = new SimpleStringProperty("Choose Flow to Start");
        btnStartFlow.disableProperty().bind(isFlowCanRun.not());
        btnAddContinuation.disableProperty().bind(isFlowCanRun.not());
        flowNameBinding = Bindings.createStringBinding(() -> this.flowDefinitionObjectProperty.getName(), flowDefinitionObjectProperty);
        lblFlowName.textProperty().bind(flowNameProperty);
        executionHBOX.disableProperty().bind(Bindings.isEmpty(flowExecutionModelViews));
        userStringFreeInputProperty = new SimpleStringProperty("");
        lblFreeInputUserString.textProperty().bind(Bindings.concat(userStringFreeInputProperty, " : "));
        lblRunFlowContinue.textProperty().bind(lblRunFlowContinueProperty);
        isContinuationOn = new SimpleBooleanProperty(false);
        lblRunFlowContinueProperty.bind(Bindings.when(isContinuationOn.not())
                .then("Run Flow:").otherwise("Continue with Flow:"));
    }
    public void setPrimaryFlow(TableViewFlowModel flowDefinition){
        this.primaryTableViewModel = flowDefinition;
        setFlowDefinitionView(flowDefinition);
    }

    public void setFlowDefinitionView(TableViewFlowModel flowDefinition){
        curValInputModelViews.clear();
        valueEnteredProperty.set("");
        flowNameProperty.set(flowDefinition.getFlowName());
        flowDefinitionObjectProperty.setValue(flowDefinition.getFlowDefinition());
        tableFreeInputs.setItems(flowDefinition.getFreeInputsViewModels());
        listViewContinuations.setItems(flowDefinition.getContinuationFlows());
        this.currentTableViewModel = flowDefinition;
    }

    public void onClickEnterInputValue() {
        if(valueEnteredProperty.getValue().equals(""))
            return;
        FreeInputsViewModel selectedInput = tableFreeInputs.getSelectionModel().getSelectedItem();
        enterValueToSelectedInput(selectedInput,valueEnteredProperty.getValue());
    }

    private void enterValueToSelectedInput(FreeInputsViewModel selectedInput, String valueEntered) {
        CurValInputModelView valInputModelView = new CurValInputModelView(selectedInput.getName(), valueEntered);
        Optional<CurValInputModelView> selected = curValInputModelViews.stream().filter(curValInputModelView -> curValInputModelView.getInputName().equals(selectedInput.getName())).findFirst();
        if(selected.isPresent()){
            selected.get().setValueEntered(valueEnteredProperty.getValue());
            tableCurrentValues.refresh();
        }else{
            curValInputModelViews.add(valInputModelView);
        }
    }

    private void onChangedCurValuesInputs(ListChangeListener.Change<? extends CurValInputModelView> change) {
        while (change.next()) {
            if (isAllMandatoryInputsEntered()) {
                if(allInputsEntered())
                    statusRunFlowFreeInputs.set("You Can Start the flow. all inputs are set");
                else
                    statusRunFlowFreeInputs.set("You Can Start The flow or add more optional inputs");
                isFlowCanRun.set(true);
            } else {
                isFlowCanRun.set(false);
                statusRunFlowFreeInputs.set("Enter Mandatory Inputs To Run the Flow");
            }
        }
    }

    private boolean allInputsEntered() {
        List<FreeInputsViewModel> freeInputsViewModels = tableFreeInputs.getItems();
        List<CurValInputModelView> curValInputModelViews1 = tableCurrentValues.getItems();
        List<FreeInputsViewModel> enteredValueInputs = new ArrayList<>();
        freeInputsViewModels.forEach(freeInputs -> {
            if(curValInputModelViews1.stream().filter(curValInputModelView -> curValInputModelView.getInputName().equals(freeInputs.getName())).count() > 0){
                enteredValueInputs.add(freeInputs);
            }
        });
        List<FreeInputsViewModel> unenteredValueInputs = freeInputsViewModels.stream()
                .filter(freeInputsViewModel -> !enteredValueInputs
                        .contains(freeInputsViewModel)).collect(Collectors.toList());
        return unenteredValueInputs.size() == 0;
    }

    private boolean isAllMandatoryInputsEntered() {
        List<FreeInputsViewModel> freeInputsViewModels = tableFreeInputs.getItems();
        List<CurValInputModelView> curValInputModelViews1 = tableCurrentValues.getItems();
        List<FreeInputsViewModel> enteredValueInputs = new ArrayList<>();
        freeInputsViewModels.forEach(freeInputs -> {
            if(curValInputModelViews1.stream().filter(curValInputModelView -> curValInputModelView.getInputName().equals(freeInputs.getName())).count() > 0){
                enteredValueInputs.add(freeInputs);
            }
        });
        List<FreeInputsViewModel> unenteredValueInputs = freeInputsViewModels.stream()
                .filter(freeInputsViewModel -> !enteredValueInputs
                        .contains(freeInputsViewModel)).collect(Collectors.toList());
        if(unenteredValueInputs.stream().anyMatch(freeInputsViewModel -> freeInputsViewModel.getNecessity().equals(DataNecessity.MANDATORY.name())))
            return false;
        return true;

    }

    public void onClickTableFreeInputs(MouseEvent mouseEvent) {
        FreeInputsViewModel selectedInput = tableFreeInputs.getSelectionModel().getSelectedItem();
        currentSelectedFreeInput.set(selectedInput.getName());
        Optional<CurValInputModelView> selected = curValInputModelViews.stream().filter(curValInputModelView -> curValInputModelView.getInputName().equals(selectedInput.getName())).findFirst();
        userStringFreeInputProperty.set(selectedInput.getUserString());
        if(selected.isPresent()){
            valueEnteredProperty.set(selected.get().getValueEntered());
        }else{
            valueEnteredProperty.set("");
        }
    }

    public void onClearInputClick(ActionEvent actionEvent) {
        FreeInputsViewModel selectedInput = tableFreeInputs.getSelectionModel().getSelectedItem();
        Optional<CurValInputModelView> selected = curValInputModelViews.stream().filter(curValInputModelView -> curValInputModelView.getInputName().equals(selectedInput.getName())).findFirst();
        if(selected.isPresent()){
            curValInputModelViews.remove(selected.get());
        }
        valueEnteredProperty.set("");
    }

    public void onClickStartFlow(ActionEvent actionEvent) {
        if(isContinuationOn.get()){
            StartFlowContinuations();
        }
        else {
            DoubleProperty progressProperty = new SimpleDoubleProperty(0);
            Map<String, String> map = curValInputModelViews.stream()
                    .collect(Collectors.toMap(CurValInputModelView::getInputName, CurValInputModelView::getValueEntered));
            // Access the mappings in the map
            Task<FlowHistoryData> task = new Task<FlowHistoryData>() {
                @Override
                protected FlowHistoryData call() throws Exception {
                    FlowHistoryData flowHistoryData = businessLogic.startFlow(flowDefinitionObjectProperty.get(), map, progress -> {
                        updateProgress(progress, 100);
                        progressProperty.set(progress);
                    });
                    return flowHistoryData;
                }
            };

            progressBarFlow.progressProperty().bind(progressProperty);
            progressBarFlow.progressProperty().bind(task.progressProperty());
            businessLogic.executeTask(task);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(progressBarFlow.progressProperty(), progressBarFlow.getProgress())),
                    new KeyFrame(Duration.seconds(1), new KeyValue(progressProperty, 1, Interpolator.EASE_BOTH))
            );
            timeline.play();

            task.setOnSucceeded(event -> {
                Platform.runLater(() -> {
                    addHistoryFlow(task.getValue());
                });
            });
        }
        setFlowDefinitionView(this.primaryTableViewModel);
        //FlowHistoryData flowHistoryData =  businessLogic.startFlow(flowDefinitionObjectProperty.get(), map,));
    }

    private void StartFlowContinuations() {

        DoubleProperty progressProperty = new SimpleDoubleProperty(0);

        Map<String, String> map = curValInputModelViews.stream()
                .collect(Collectors.toMap(CurValInputModelView::getInputName, CurValInputModelView::getValueEntered));
        freeInputsValuesContinuationQueue.add(map);
        flowOrderContinuationQueue.add(currentTableViewModel.getFlowDefinition());
        // Access the mappings in the map
        Task<List<FlowHistoryData>> task = new Task<List<FlowHistoryData>>() {
            @Override
            protected List<FlowHistoryData> call() {
                List<FlowHistoryData> flowHistoryDataList = new ArrayList<>();
                boolean firstFlow = true;
                while (!flowOrderContinuationQueue.isEmpty() && !freeInputsValuesContinuationQueue.isEmpty()) {
                    FlowHistoryData flowHistoryData;
                    flowDefinitionObjectProperty.setValue(flowOrderContinuationQueue.remove());
                    Map<String, String> mapContinuationValues = freeInputsValuesContinuationQueue.remove();
                    if (firstFlow) {
                        flowHistoryData = businessLogic.startFlow(flowDefinitionObjectProperty.get(), mapContinuationValues, progress -> {
                            updateProgress(progress, 100);
                            progressProperty.set(progress);
                        });
                        flowHistoryDataList.add(flowHistoryData);
                        firstFlow = false;
                    } else {
                        for (Map.Entry<String, String> entry : mapContinuationValues.entrySet()) {
                            String inputName = entry.getKey();
                            String value = entry.getValue();
                            if (value.equals("#By Continuation#")) {
                                FlowHistoryData lastFlowHistoryData = flowHistoryDataList.get(flowHistoryDataList.size() - 1);
                                Optional<Continuation> lastFlowContinuationOptional = lastFlowHistoryData.flowDefinition().getContinuationFlows().stream()
                                        .filter(continuation -> continuation.flowDefinition()
                                                .equals(flowDefinitionObjectProperty.get())).findFirst();
                                if (lastFlowContinuationOptional.isPresent()) {
                                    Continuation conLastFlow = lastFlowContinuationOptional.get();
                                    Optional<ContinuationMapping> continuationMappingOptional = conLastFlow.continuationMappings()
                                            .stream().filter(continuationMapping -> continuationMapping.mappingFlowDataDefinition()
                                                    .getTargetData().getAliasName().equals(inputName)).findFirst();
                                    if (continuationMappingOptional.isPresent()) {
                                        ContinuationMapping continuationMapping = continuationMappingOptional.get();
                                        Optional<OutputHistoryData> outputHistoryDataContinuation = lastFlowHistoryData.getOutputsHistoryData().stream()
                                                .filter(outputHistoryData -> outputHistoryData.getFinalName().equals(continuationMapping.mappingFlowDataDefinition().getSourceData().getAliasName())).findFirst();
                                        if (outputHistoryDataContinuation.isPresent()) {
                                            entry.setValue(outputHistoryDataContinuation.get().getData().toString());
                                        }
                                    }
                                }
                            }
                        }
                        flowHistoryData = businessLogic.startFlow(flowDefinitionObjectProperty.get(), mapContinuationValues, progress -> {
                            updateProgress(progress, 100);
                            progressProperty.set(progress);
                        });
                        flowHistoryDataList.add(flowHistoryData);
                    }

                }
                return flowHistoryDataList;
            }
        };


        progressBarFlow.progressProperty().bind(progressProperty);
        progressBarFlow.progressProperty().bind(task.progressProperty());
        businessLogic.executeTask(task);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBarFlow.progressProperty(), progressBarFlow.getProgress())),
                new KeyFrame(Duration.seconds(1), new KeyValue(progressProperty, 1, Interpolator.EASE_BOTH))
        );
        timeline.play();

        task.setOnSucceeded(event -> {
            Platform.runLater(()->{
                task.getValue().forEach(flowHistoryData -> addHistoryFlow(flowHistoryData));
            });
        });

    }

    private void addHistoryFlow(FlowHistoryData flowHistoryData) {
        flowExecutionModelViews.add(new FlowExecutionModelView(flowHistoryData));
    }

    public void setBusinessLogic(StepperBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void onTableAllFlowExecClick(MouseEvent mouseEvent) {
        FlowExecutionModelView selected = tableAllFlowExecutions.getSelectionModel().getSelectedItem();
        stepExecutionResultTable.setItems(selected.getStepExecModelViews());
        moreStepDetailsTable.setItems(selected.getStepExecModelViews());
        tableExecutionsDetails.setItems(flowExecutionModelViews);
        tableFreeInputsExecDetails.setItems(selected.getFreeInputsExecViewModels());
        tableOutputExecution.setItems(selected.getOutputExecModelViews());

    }

    public void onKeyPressedValueInput(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            onClickEnterInputValue();
        }
    }

    public void onClickStepExecutionResultTable(MouseEvent mouseEvent) {
        StepExecModelView selected = stepExecutionResultTable.getSelectionModel().getSelectedItem();
        stepLogsListView.setItems(selected.getLogsStep());
    }

    public void onClickAddContinuation(ActionEvent actionEvent) {
        if(listViewContinuations.getSelectionModel().getSelectedItem() == null)
            return;
        FlowDefinition flowDefinition = currentTableViewModel.getFlowDefinition();
        String flowContinueStr= listViewContinuations.getSelectionModel().getSelectedItem();
        Optional<Continuation> continuationOptional =
                flowDefinition.getContinuationFlows().stream()
                        .filter(continuation -> continuation.flowDefinition().getName().equals(flowContinueStr))
                        .collect(Collectors.toList()).stream().findFirst();
        if(!continuationOptional.isPresent())
            return;
        Continuation continuation = continuationOptional.get();
        FlowDefinition flowDefinitionContinuation = continuation.flowDefinition();
        isContinuationOn.set(true);
        Map<String, String> map = curValInputModelViews.stream()
                .collect(Collectors.toMap(CurValInputModelView::getInputName, CurValInputModelView::getValueEntered));
        freeInputsValuesContinuationQueue.add(map);
        flowOrderContinuationQueue.add(flowDefinition);
        setFlowDefinitionView(new TableViewFlowModel(flowDefinitionContinuation));
        setContinuationToCurrentFlow(continuation);
    }

    private void setContinuationToCurrentFlow(Continuation continuation) {
        for (ContinuationMapping continuationMapping : continuation.continuationMappings()) {
            Optional<FreeInputsViewModel> freeInputsViewModelOptional = currentTableViewModel.getFreeInputsViewModels().stream()
                    .filter(freeInputsViewModel -> freeInputsViewModel.getNameProperty()
                            .equals(continuationMapping.mappingFlowDataDefinition().getTargetData().getAliasName()))
                    .findFirst();
            if(freeInputsViewModelOptional.isPresent()){
                enterValueToSelectedInput(freeInputsViewModelOptional.get(), "#By Continuation#");
            }
        }
    }
}
