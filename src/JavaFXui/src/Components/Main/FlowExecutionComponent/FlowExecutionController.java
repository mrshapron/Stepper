package Components.Main.FlowExecutionComponent;

import BusinessLogic.StepperBusinessLogic;
import Components.Main.FlowDefinitionComponent.ModelViews.FreeInputsViewModel;
import Components.Main.FlowDefinitionComponent.ModelViews.TableViewFlowModel;
import Components.Main.FlowExecutionComponent.ModelView.CurValInputModelView;
import Components.Main.FlowExecutionComponent.ModelView.FlowExecutionModelView;
import Components.Main.FlowExecutionComponent.ModelView.FreeInputsExecViewModel;
import Components.Main.FlowExecutionComponent.ModelView.OutputExecModelView;
import Flow.Defenition.FlowDefinition;
import Flow.Defenition.FreeInputsDefinitionImpl;
import Flow.Execution.History.FlowHistoryData;
import Step.DataNecessity;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlowExecutionController {

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



    private FlowDefinition selectedFlow;
    private TableViewFlowModel currentTableViewModel;
    private StepperBusinessLogic businessLogic;
    private BooleanProperty isAddValueEnable;

    private ObjectProperty<FlowDefinition> flowDefinitionObjectProperty;

    private StringProperty valueEnteredProperty;
    private ObservableList<CurValInputModelView> curValInputModelViews;
    private StringProperty currentSelectedFreeInput;
    private StringProperty statusRunFlowFreeInputs;
    private BooleanProperty isFlowCanRun;
    private ObservableList<FlowExecutionModelView> flowExecutionModelViews;
    public FlowExecutionController(){
        flowDefinitionObjectProperty = new SimpleObjectProperty<>(null);
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
        btnStartFlow.disableProperty().bind(isFlowCanRun.not());
        StringBinding flowNameBinding = Bindings.createStringBinding(() -> this.flowDefinitionObjectProperty.getName(), flowDefinitionObjectProperty);
        lblFlowName.textProperty().bind(flowNameBinding);
    }

    public void setFlowDefinition(TableViewFlowModel flowDefinition){
        curValInputModelViews.clear();
        valueEnteredProperty.set("");
        flowDefinitionObjectProperty.setValue(flowDefinition.getFlowDefinition());
        tableFreeInputs.setItems(flowDefinition.getFreeInputsViewModels());
        this.currentTableViewModel = flowDefinition;
    }

    public void onClickEnterInputValue() {
        FreeInputsViewModel selectedInput = tableFreeInputs.getSelectionModel().getSelectedItem();
        CurValInputModelView valInputModelView = new CurValInputModelView(selectedInput.getName(), valueEnteredProperty.getValue());
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
        Map<String, String> map = curValInputModelViews.stream()
                .collect(Collectors.toMap(CurValInputModelView::getInputName, CurValInputModelView::getValueEntered));
        // Access the mappings in the map
        FlowHistoryData flowHistoryData =  businessLogic.startFlow(flowDefinitionObjectProperty.get(), map);
        addHistoryFlow(flowHistoryData);
    }

    private void addHistoryFlow(FlowHistoryData flowHistoryData) {
        flowExecutionModelViews.add(new FlowExecutionModelView(flowHistoryData));
    }

    public void setBusinessLogic(StepperBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void onTableAllFlowExecClick(MouseEvent mouseEvent) {
        FlowExecutionModelView selected = tableAllFlowExecutions.getSelectionModel().getSelectedItem();
        tableExecutionsDetails.setItems(flowExecutionModelViews);
        tableFreeInputsExecDetails.setItems(selected.getFreeInputsExecViewModels());
        tableOutputExecution.setItems(selected.getOutputExecModelViews());
    }

    public void onKeyPressedValueInput(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            onClickEnterInputValue();
        }
    }
}
