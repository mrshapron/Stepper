package Components.Main.ExecutionsHistoryComponent;

import Components.Main.FlowExecutionComponent.ModelView.StepExecModelView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;


public class ExecutionsHistoryController {

    @FXML
    private ListView stepLogsListView;
    @FXML
    private TableView<?> tableAllFlowExecutions;

    @FXML
    private TableColumn<?, ?> allFlowExecFlowNameCol;

    @FXML
    private TableColumn<?, ?> allFlowExecIDExecCol;

    @FXML
    private TableColumn<?, ?> allFlowExecTimeRunCol;

    @FXML
    private TableView<?> tableExecutionsDetails;

    @FXML
    private TableColumn<?, ?> execDetailsFlowNameCol;

    @FXML
    private TableColumn<?, ?> execDetailsIDExecCol;

    @FXML
    private TableColumn<?, ?> execDetailsResultCol;

    @FXML
    private TableColumn<?, ?> execDetailsTimeRunningCol;

    @FXML
    private TableView<?> tableFreeInputsExecDetails;

    @FXML
    private TableColumn<?, ?> freeInputsExecInputNameCol;

    @FXML
    private TableColumn<?, ?> freeInputsExecTypeCol;

    @FXML
    private TableColumn<?, ?> freeInputsExecValueCol;

    @FXML
    private TableColumn<?, ?> freeInputsExecNeccCol;

    @FXML
    private TableView<?> tableOutputExecution;

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
    private TableView<?> moreStepDetailsTable;

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

    @FXML
    private TableView<?> listOutputStepTable;

    @FXML
    public void onClickStepExecutionResultTable(MouseEvent event) {
        StepExecModelView selected = stepExecutionResultTable.getSelectionModel().getSelectedItem();
        stepLogsListView.setItems(selected.getLogsStep());
    }

    @FXML
    public void onTableAllFlowExecClick(MouseEvent mouseEvent) {
    }
}
