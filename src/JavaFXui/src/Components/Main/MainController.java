package Components.Main;

import BusinessLogic.StepperBusinessLogic;
import Components.Main.ExecutionsHistoryComponent.ExecutionsHistoryController;
import Components.Main.FlowDefinitionComponent.FlowDefinitionsPageController;
import Components.Main.FlowExecutionComponent.FlowExecutionController;
import Components.Main.FlowExecutionComponent.ModelView.FlowExecutionModelView;
import Flow.Definition.FlowDefinition;
import Components.Main.FlowDefinitionComponent.ModelViews.TableViewFlowModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class MainController {



    private StepperBusinessLogic businessLogic;
    private Stage primaryStage;

    @FXML private HBox hBoxMain;
    @FXML private TabPane tabPane;
    @FXML private Button btnLoadFile;
    @FXML private TextField txtFiledFileChosen;
    @FXML private AnchorPane flowDefinitionContainer;
    @FXML private ExecutionsHistoryController executionsHistoryChildController;
    @FXML private FlowDefinitionsPageController flowDefinitionsViewChildController;
    @FXML private FlowExecutionController flowExecutionViewChildController;
    @FXML private TableView<FlowDefinition> tableViewFlows;

    private List<FlowDefinition> flowDefinitions;
    private ObservableList<TableViewFlowModel> tableViewFlowModels;
    private SimpleIntegerProperty opacityProperty;
    private SimpleStringProperty selectedFileProperty;


    public MainController() {
        selectedFileProperty = new SimpleStringProperty();
        opacityProperty = new SimpleIntegerProperty(100);
    }

    public void initialize() {
        txtFiledFileChosen.textProperty().bind(selectedFileProperty);
        txtFiledFileChosen.opacityProperty().bind(opacityProperty);
        tableViewFlowModels = FXCollections.observableArrayList();
        flowDefinitionsViewChildController.bindFlowList(tableViewFlowModels);
        flowDefinitionsViewChildController.setMainController(this);
        executionsHistoryChildController.setMainController(this);
        hBoxMain.disableProperty().bind(txtFiledFileChosen.textProperty().isEmpty());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void onClickLoadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Flows XML File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null)
            return;

        String absolutePath = selectedFile.getAbsolutePath();
        selectedFileProperty.set(absolutePath);
        opacityProperty.set(50);
        flowDefinitions = businessLogic.initializeStepper(absolutePath);
        initializeFlowsList(flowDefinitions);
    }

    private void initializeFlowsList(List<FlowDefinition> flowDefinitions) {
        tableViewFlowModels.clear();
        flowDefinitions.forEach(flowDefinition -> tableViewFlowModels.add(new TableViewFlowModel(flowDefinition)));
    }

    public void switchToFlowExecutionTab(FlowExecutionModelView flowExecutionModelView){
        switchToFlowExecutionTab(new TableViewFlowModel(flowExecutionModelView.getFlowDefinition()));
        flowExecutionViewChildController.setValuesFreeInputs(flowExecutionModelView.getFreeInputsExecViewModels());
    }

    public void switchToFlowExecutionTab(TableViewFlowModel tableViewFlowModel) {
        tabPane.getSelectionModel().select(1); // Switch to the "Flow Executions" tab
        flowExecutionViewChildController.setPrimaryFlow(tableViewFlowModel);
        flowExecutionViewChildController.setExecutionsHistoryController(executionsHistoryChildController);
        flowExecutionViewChildController.setBusinessLogic(this.businessLogic);
    }

    public void setBusinessLogic(StepperBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }
}
