
package Components.Main.FlowDefinitionComponent;

import Components.Main.ClientMainController;
import Components.Main.FlowDefinitionComponent.ModelViews.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlowDefinitionsPageController {
    private List<TableViewFlowModel> currFlows;
    private StringProperty flowNameProperty;
    private StringProperty descriptionProperty;
    private BooleanProperty isReadOnlyProperty;
    private ClientMainController clientMainController;
    private String username;

    @FXML
    private BorderPane root;

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


    private BooleanProperty isRunFlowDisable;
    private StringProperty isReadOnlyStatusProperty;



    public FlowDefinitionsPageController(){
        isRunFlowDisable = new SimpleBooleanProperty(true);
        flowNameProperty = new SimpleStringProperty("Flow Name");
        descriptionProperty = new SimpleStringProperty("Flow Description");
        isReadOnlyProperty = new SimpleBooleanProperty(false);
        isReadOnlyStatusProperty = new SimpleStringProperty("Is Read Only");
    }


    public void initialize(){
        currFlows = new ArrayList<>();
        btnRunFlow.disableProperty().bind(isRunFlowDisable);
        comboBoxStepsInfo.setOnAction(this::onClickStepInfoComboBox);
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
        lblIsReadOnly.textProperty().bind(isReadOnlyStatusProperty);
    }

    public void setClientMainController(ClientMainController clientMainController) {
        this.clientMainController = clientMainController;
    }


    @FXML
    void onClickRunFlowAction(ActionEvent event) throws IOException {
        TableViewFlowModel selectedFlowModel = tableViewFlows.getSelectionModel().getSelectedItem();
        String flowName = selectedFlowModel.getFlowName();
        clientMainController.switchToFlowExecutionTab(flowName);
    }

    public void bindFlowList(ObservableList<TableViewFlowModel> flowDefinitions){
        tableViewFlows.setItems(flowDefinitions);
    }

    public void onClickFlowTable(MouseEvent mouseEvent) {
        TableViewFlowModel selectedFlowModel = tableViewFlows.getSelectionModel().getSelectedItem();
        if (selectedFlowModel != null) {
            comboBoxStepsInfo.setItems(selectedFlowModel.getStepsNameBind());
            listViewFormalOutputs.setItems(selectedFlowModel.getFormalOutputs());
            tableViewSteps.setItems(selectedFlowModel.getStepsTableViewModels());
            tableViewFreeInputs.setItems(selectedFlowModel.getFreeInputsViewModels());
            tableViewInputsStep.setItems(selectedFlowModel.getStepInputs());
            tableViewAllOutputs.setItems(selectedFlowModel.getAllOutputModelViews());
            tableViewOutputs.setItems(selectedFlowModel.getStepOutputs());
            // Update the label text
            flowNameProperty.set(selectedFlowModel.getFlowName());
            descriptionProperty.set(selectedFlowModel.getDescription());
            isReadOnlyStatusProperty.set("true");
            isRunFlowDisable.set(false);

        } else {
            // Reset the label text when no flow is selected
            flowNameProperty.set("Flow Name");
            descriptionProperty.set("Flow Description");
            isReadOnlyStatusProperty.set("Is Read Only");
        }
    }


    public void onClickStepInfoComboBox(ActionEvent mouseEvent) {
        String nameStep;
        nameStep = comboBoxStepsInfo.getSelectionModel().getSelectedItem();
        System.out.println(nameStep);
        if (comboBoxStepsInfo.getSelectionModel() == null)
            nameStep = comboBoxStepsInfo.getItems().get(0);
        tableViewFlows.getSelectionModel().getSelectedItem().setStepInputs(nameStep);
        tableViewFlows.getSelectionModel().getSelectedItem().setStepOutputs(nameStep);
    }

    public void updateFlows(List<TableViewFlowModel> updatedFlows) {
        if (!updatedFlows.equals(currFlows)) {
            //Need to CHANGE!
            tableViewFlows.getItems().clear();
            for (TableViewFlowModel model : updatedFlows) {
                tableViewFlows.getItems().add(model);
            }
            currFlows = updatedFlows;
        }
    }


    public void setUsername(String username) {
        this.username = username;
    }
}
