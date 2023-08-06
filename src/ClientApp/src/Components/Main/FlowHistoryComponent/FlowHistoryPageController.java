package Components.Main.FlowHistoryComponent;


import Components.Main.ClientMainController;
import Components.Main.FlowExecutionComponent.ModelViews.FlowExecutionModelView;
import Components.Main.FlowExecutionComponent.ModelViews.FreeInputsExecViewModel;
import Components.Main.FlowExecutionComponent.ModelViews.OutputExecModelView;
import Components.Main.FlowExecutionComponent.ModelViews.StepExecModelView;
import convertion3.FlowHistoryDataToFlowExecutionModelViewConvertor;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class FlowHistoryPageController {

    private ClientMainController clientMainController;
    private ObservableList<FlowExecutionModelView> flowExecutionModelViews;
    private ObservableList<FlowExecutionModelView> currHistory;
    private SimpleStringProperty lblRunFlowContinueProperty;


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




    public void initialize(){
        allFlowExecFlowNameCol.setCellValueFactory(new PropertyValueFactory<>("flowName"));
        allFlowExecIDExecCol.setCellValueFactory(new PropertyValueFactory<>("idExec"));
        allFlowExecTimeRunCol.setCellValueFactory(new PropertyValueFactory<>("timeRun"));

        flowExecutionModelViews = FXCollections.observableArrayList();
        currHistory = FXCollections.observableArrayList();

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
    public void setClientMainController(ClientMainController clientMainController) {
        this.clientMainController = clientMainController;
    }
    @FXML
    void onClickChooseResultDDL(MouseEvent event) {

    }

    @FXML
    void onClickRunFlowAgain(ActionEvent event) {

    }

    @FXML
    void onClickStepExecutionResultTable(MouseEvent event) {

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

    public void updateHistory(List<FlowExecutionModelView> modelViews) {
        //update the FlowExecutionModelView table!
        //convert into a ObservableList
//            ObservableList<FlowExecutionModelView> newItems = FXCollections.observableList(modelViews);
//            if (!newItems.equals(currHistory))
//                setFlowExecutionModelViews(newItems);
            //^^ That worked before
            if (modelViews != null) {
                ObservableList<FlowExecutionModelView> newItems = FXCollections.observableList(modelViews);

                if (!newItems.equals(currHistory))
                    setFlowExecutionModelViews(newItems);
            } else {
                setFlowExecutionModelViews(null);  // Clear the tables (assuming setFlowExecutionModelViews(null) handles table clearing)
            }
    }

    public void setFlowExecutionModelViews(ObservableList<FlowExecutionModelView> flowExecutionModelViews){
        if (flowExecutionModelViews == null) {
            this.flowExecutionModelViews.clear();
        }
        else{
            this.flowExecutionModelViews.setAll(flowExecutionModelViews);
        }
        currHistory = flowExecutionModelViews;
    }
}
