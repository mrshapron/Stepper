package Components.Main.FlowDefinitionComponent;

import Components.Main.FlowDefinitionComponent.ModelViews.*;
import Components.Main.MainController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
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
import sun.applet.Main;

public class FlowDefinitionsPageController {

    private StringProperty flowNameProperty;
    private StringProperty descriptionProperty;
    private BooleanProperty isReadOnlyProperty;

    @FXML
    private TableColumn<AllOutputModelView, String> allOutputsTableName;

    @FXML
    private TableColumn<AllOutputModelView, String> allOutputsTableType;

    @FXML
    private TableColumn<AllOutputModelView, String> allOutputsTableStep;

    @FXML
    private TableColumn<FreeInputsViewModel, String> freeInputsTableName;

    @FXML
    private TableColumn<FreeInputsViewModel, String> freeInputsTableType;

    @FXML
    private TableColumn<FreeInputsViewModel, String> freeInputsTableNecessity;

    @FXML
    private TableColumn<FreeInputsViewModel, String> freeInputsTableSteps;

    @FXML
    private TableView<TableViewFlowModel> tableViewFlows;

    @FXML
    private TableColumn<TableViewFlowModel, String> flowsFlowNameCloumn;

    @FXML
    private TableColumn<TableViewFlowModel, String> flowsFreeInputsColumn;

    @FXML
    private TableColumn<TableViewFlowModel, String> flowsStepsColumn;

    @FXML
    private Label lblFlowName;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblIsReadOnly;

    @FXML
    private ListView<String> listViewFormalOutputs;

    @FXML
    private TableView<AllOutputModelView> tableViewAllOutputs;

    @FXML
    private TableView<FreeInputsViewModel> tableViewFreeInputs;

    @FXML
    private ComboBox<String> comboBoxStepsInfo;

    @FXML
    private TableView<StepInputsViewModel> tableViewInputsStep;

    @FXML
    private TableView<StepOutputViewModel> tableViewOutputs;

    @FXML
    private Button btnRunFlow;

    @FXML
    private TableColumn<StepsTableViewModel, String> stepNameColumn;

    @FXML
    private TableColumn<StepsTableViewModel, String> readOnlyStepColumn;

    @FXML
    private TableView<StepsTableViewModel> tableViewSteps;

    @FXML
    private TableColumn<StepInputsViewModel, String> inputNameColumn;

    @FXML
    private TableColumn<StepInputsViewModel, String> inputNecessityColumn;

    @FXML
    private TableColumn<StepInputsViewModel, String> inputConnectedColumn;

    @FXML
    private TableColumn<StepInputsViewModel, String> inputFromOutputColumn;

    @FXML
    private TableColumn<StepOutputViewModel, String> outputNameColumn;

    @FXML
    private TableColumn<StepOutputViewModel, String> outputConnectedColumn;

    @FXML
    private TableColumn<StepOutputViewModel, String> outputToInputColumn;

    private MainController mainController;

    private BooleanProperty isRunFlowDisable;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public FlowDefinitionsPageController(){
        isRunFlowDisable = new SimpleBooleanProperty(true);
        flowNameProperty = new SimpleStringProperty("Flow Name");
        descriptionProperty = new SimpleStringProperty("Flow Description");
        isReadOnlyProperty = new SimpleBooleanProperty(false);
    }


    public void initialize(){
        btnRunFlow.disableProperty().bind(isRunFlowDisable);
        comboBoxStepsInfo.setOnAction(actionEvent-> onClickStepInfoComboBox(actionEvent));
        flowsFlowNameCloumn.setCellValueFactory(new PropertyValueFactory<>("flowName"));
        flowsFreeInputsColumn.setCellValueFactory(new PropertyValueFactory<>("freeInputs"));
        flowsStepsColumn.setCellValueFactory(new PropertyValueFactory<>("steps"));
        stepNameColumn.setCellValueFactory(new PropertyValueFactory<>("stepName"));
        readOnlyStepColumn.setCellValueFactory(new PropertyValueFactory<>("isReadOnly"));

        allOutputsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allOutputsTableType.setCellValueFactory(new PropertyValueFactory<>("type"));
        allOutputsTableStep.setCellValueFactory(new PropertyValueFactory<>("step"));

        outputNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        outputConnectedColumn.setCellValueFactory(new PropertyValueFactory<>("connected"));
        outputToInputColumn.setCellValueFactory(new PropertyValueFactory<>("toInput"));

        inputNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inputNecessityColumn.setCellValueFactory(new PropertyValueFactory<>("necessity"));
        inputConnectedColumn.setCellValueFactory(new PropertyValueFactory<>("connected"));
        inputFromOutputColumn.setCellValueFactory(new PropertyValueFactory<>("fromOutput"));

        comboBoxStepsInfo.setValue(null);

        freeInputsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        freeInputsTableType.setCellValueFactory(new PropertyValueFactory<>("type"));
        freeInputsTableNecessity.setCellValueFactory(new PropertyValueFactory<>("necessity"));
        freeInputsTableSteps.setCellValueFactory(new PropertyValueFactory<>("stepsName"));

        lblFlowName.textProperty().bind(flowNameProperty);
        lblDescription.textProperty().bind(descriptionProperty);
        lblIsReadOnly.textProperty().bind(Bindings.format(isReadOnlyProperty.get() + ""));

    }

    @FXML
    void onClickRunFlowAction(ActionEvent event) {
        TableViewFlowModel selectedFlowModel = tableViewFlows.getSelectionModel().getSelectedItem();
        mainController.switchToFlowExecutionTab(selectedFlowModel);
    }

    public void bindFlowList(ObservableList<TableViewFlowModel> flowDefinitions){
        tableViewFlows.setItems(flowDefinitions);
    }

    public void onClickFlowTable(MouseEvent mouseEvent) {
        comboBoxStepsInfo.setItems(tableViewFlows.getSelectionModel().getSelectedItem().getStepsNameBind());
        listViewFormalOutputs.setItems(tableViewFlows.getSelectionModel().getSelectedItem().getFormalOutputs());
        tableViewSteps.setItems(tableViewFlows.getSelectionModel().getSelectedItem().getStepsTableViewModels());
        tableViewFreeInputs.setItems(tableViewFlows.getSelectionModel().getSelectedItem().getFreeInputsViewModels());
        tableViewInputsStep.setItems(tableViewFlows.getSelectionModel().getSelectedItem().getStepInputs());
        flowNameProperty.bind(tableViewFlows.selectionModelProperty().get().getSelectedItem().getFlowNameProperty());/**/
        descriptionProperty.bind(tableViewFlows.selectionModelProperty().get().getSelectedItem().getDescriptionProperty());/**/
        tableViewAllOutputs.setItems(tableViewFlows.getSelectionModel().getSelectedItem().getAllOutputModelViews());
        tableViewOutputs.setItems(tableViewFlows.getSelectionModel().getSelectedItem().getStepOutputs());
        isRunFlowDisable.set(false);
    }


    public void onClickStepInfoComboBox(ActionEvent mouseEvent) {
        String nameStep = comboBoxStepsInfo.getSelectionModel().getSelectedItem();
        tableViewFlows.getSelectionModel().getSelectedItem().setStepInputs(nameStep);
        tableViewFlows.getSelectionModel().getSelectedItem().setStepOutputs(nameStep);
    }
}
