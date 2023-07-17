package Components.Main.ExecutionsHistoryComponent;

import Components.Main.FlowDefinitionComponent.ModelViews.TableViewFlowModel;
import Components.Main.FlowExecutionComponent.ModelView.FlowExecutionModelView;
import Components.Main.FlowExecutionComponent.ModelView.FreeInputsExecViewModel;
import Components.Main.FlowExecutionComponent.ModelView.OutputExecModelView;
import Components.Main.FlowExecutionComponent.ModelView.StepExecModelView;
import Components.Main.MainController;
import Step.StepResult;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ExecutionsHistoryController {


    @FXML private ComboBox<String> ddlChooseResult;
    @FXML private TableView<FlowExecutionModelView> tableViewFlowHistory;
    @FXML private BorderPane borderPaneHistory;
    @FXML
    private ListView stepLogsListView;
    @FXML
    private TableView<FlowExecutionModelView> tableAllFlowExecutions;

    @FXML
    private TableColumn<?, ?> allFlowExecFlowNameCol;

    @FXML
    private TableColumn<?, ?> allFlowExecIDExecCol;

    @FXML
    private TableColumn<?, ?> allFlowExecTimeRunCol;

    @FXML
    private TableView<FlowExecutionModelView> tableExecutionsDetails;

    @FXML
    private TableColumn<?, ?> execDetailsFlowNameCol;

    @FXML
    private TableColumn<?, ?> execDetailsIDExecCol;

    @FXML
    private TableColumn<?, ?> execDetailsResultCol;

    @FXML
    private TableColumn<?, ?> execDetailsTimeRunningCol;

    @FXML
    private TableView<FreeInputsExecViewModel> tableFreeInputsExecDetails;

    @FXML
    private TableColumn<?, ?> freeInputsExecInputNameCol;

    @FXML
    private TableColumn<?, ?> freeInputsExecTypeCol;

    @FXML
    private TableColumn<?, ?> freeInputsExecValueCol;

    @FXML
    private TableColumn<?, ?> freeInputsExecNeccCol;

    @FXML
    private TableView<OutputExecModelView> tableOutputExecution;

    @FXML
    private TableColumn<?, ?> outputExecFinalNameCol;

    @FXML
    private TableColumn<?, ?> outputExecTypeCol;

    @FXML
    private TableColumn<?, ?> outputExecValueCol;

    @FXML
    private Label lblStepExecutionResult;

    @FXML
    private TableView<StepExecModelView> stepExecutionResultTable;

    @FXML
    private TableColumn<?, ?> stepNameExecResCol;

    @FXML
    private TableColumn<?, ?> stepResultExecResultRow;

    @FXML
    private TableView<StepExecModelView> moreStepDetailsTable;

    @FXML
    private TableColumn<?, ?> stepNameStepDetailsCol;

    @FXML
    private TableColumn<?, ?> stepResStepDetailsCol;

    @FXML
    private TableColumn<?, ?> timeStartStepDetailsCol;

    @FXML
    private TableColumn<?, ?> runtimeMoreStepCol;

    @FXML
    private TableView<?> listInputStepTable;

    @FXML private TableColumn<? ,?> historyFlowNameCol;
    @FXML private TableColumn<? ,?> historyStartedTimeCol;
    @FXML private TableColumn<? ,?> historyFlowResultCol;

    @FXML
    private TableView<?> listOutputStepTable;
    private ObservableList<FlowExecutionModelView> flowExecutionModelViews;
    private SimpleStringProperty lblRunFlowContinueProperty;
    private MainController mainController;

    @FXML
    public void onClickStepExecutionResultTable(MouseEvent event) {
        StepExecModelView selected = stepExecutionResultTable.getSelectionModel().getSelectedItem();
        stepLogsListView.setItems(selected.getLogsStep());
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

        historyFlowNameCol.setCellValueFactory( new PropertyValueFactory<>("flowName"));
        historyStartedTimeCol.setCellValueFactory(new PropertyValueFactory<>("timeRun"));
        historyFlowResultCol.setCellValueFactory(new PropertyValueFactory<>("result"));

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
        borderPaneHistory.disableProperty().bind(Bindings.isEmpty(flowExecutionModelViews));
        tableAllFlowExecutions.setItems(flowExecutionModelViews);
        tableViewFlowHistory.setItems(flowExecutionModelViews);
        ObservableList<String> resultOptions = FXCollections.observableArrayList();
        resultOptions.add("SUCCESS");
        resultOptions.add("WARNING");
        resultOptions.add("FAILURE");
        ddlChooseResult.setItems(resultOptions);

        ddlChooseResult.setOnAction(event -> {onClickChooseResultDDL();});

    }

    public void setFlowExecutionModelViews(ObservableList<FlowExecutionModelView> flowExecutionModelViews){
        this.flowExecutionModelViews.clear();
        this.flowExecutionModelViews.addAll(flowExecutionModelViews);
    }

    @FXML
    public void onTableAllFlowExecClick(MouseEvent mouseEvent) {
        FlowExecutionModelView selected = tableAllFlowExecutions.getSelectionModel().getSelectedItem();
        stepExecutionResultTable.setItems(selected.getStepExecModelViews());
        moreStepDetailsTable.setItems(selected.getStepExecModelViews());
        tableExecutionsDetails.setItems(flowExecutionModelViews);
        tableFreeInputsExecDetails.setItems(selected.getFreeInputsExecViewModels());
        tableOutputExecution.setItems(selected.getOutputExecModelViews());
    }

    public void onClickChooseResultDDL() {
        String resultChoice = ddlChooseResult.getSelectionModel().getSelectedItem();
        tableViewFlowHistory.setItems(flowExecutionModelViews
                .filtered(flowExecutionModelView -> flowExecutionModelView.getResult().equals(resultChoice)));
    }

    public void onClickRunFlowAgain(ActionEvent actionEvent) {
        FlowExecutionModelView selectedItemFlowExec =  tableViewFlowHistory.getSelectionModel().getSelectedItem();
        mainController.switchToFlowExecutionTab(selectedItemFlowExec);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
