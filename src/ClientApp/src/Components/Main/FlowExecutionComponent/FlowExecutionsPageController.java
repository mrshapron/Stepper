package Components.Main.FlowExecutionComponent;

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
import Components.Main.FlowExecutionComponent.ModelViews.*;

public class FlowExecutionsPageController {

    @FXML
    private Label lblFlowName;

    @FXML
    private Label lblRunFlowContinue;

    @FXML
    private TableView<?> tableFreeInputs;

    @FXML
    private TableColumn<?, ?> freeInputsInputNameColumn;

    @FXML
    private TableColumn<?, ?> freeInputsNecessityColumn;

    @FXML
    private Label lblSelectedInput;

    @FXML
    private Label lblFreeInputUserString;

    @FXML
    private TextField txtValueInput;

    @FXML
    private Button btnEnterValueInput;

    @FXML
    private Button btnClearInputValue;

    @FXML
    private TableView<?> tableCurrentValues;

    @FXML
    private TableColumn<?, ?> inputNameCurrentValCol;

    @FXML
    private TableColumn<?, ?> valueEnteredCurrentValCol;

    @FXML
    private Label lblStatusFreeInput;

    @FXML
    private Label lblProgressBar;

    @FXML
    private ProgressBar progressBarFlow;

    @FXML
    private ListView<?> listViewContinuations;

    @FXML
    private Button btnAddContinuation;

    @FXML
    private Button btnStartFlow;

    @FXML
    private HBox executionHBOX;

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
    private TableView<?> stepExecutionResultTable;

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
    private ListView<?> stepLogsListView;

    @FXML
    void onClearInputClick(ActionEvent event) {

    }

    @FXML
    void onClickAddContinuation(ActionEvent event) {

    }

    @FXML
    void onClickEnterInputValue(ActionEvent event) {

    }

    @FXML
    void onClickStartFlow(ActionEvent event) {

    }

    @FXML
    void onClickStepExecutionResultTable(MouseEvent event) {

    }

    @FXML
    void onClickTableFreeInputs(MouseEvent event) {

    }

    @FXML
    void onKeyPressedValueInput(KeyEvent event) {

    }

    @FXML
    void onTableAllFlowExecClick(MouseEvent event) {

    }

}
