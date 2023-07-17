package adminmain;

import BusinessLogic.StepperBusinessLogic;
import Flow.Definition.FlowDefinition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.*;
import rolesManagementTab.RolesManagementTabController;

import static configuration.Configuration.HTTP_CLIENT;
import static configuration.Configuration.BASE_URL;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdminMainController {

    private StepperBusinessLogic businessLogic;
    private Stage primaryStage;

    private List<FlowDefinition> flowDefinitions;
    private SimpleIntegerProperty opacityProperty;
    private SimpleStringProperty selectedFileProperty;

    @FXML
    private Button btnLoadFile;

    @FXML
    private TextField txtFiledFileChosen;

    @FXML
    private HBox hBoxMain;

    @FXML
    private TabPane tabPane;

    @FXML
    private AnchorPane RolesManagementContainer;

    @FXML
    private AnchorPane executionHistoryContainer;

    @FXML
    private AnchorPane StatisticsContainer;

    @FXML
    private BorderPane RolesManagementTab;
    @FXML
    private RolesManagementTabController RolesManagementTabController;



    public AdminMainController() {
        selectedFileProperty = new SimpleStringProperty();
        opacityProperty = new SimpleIntegerProperty(100);
    }

    public void initialize() {
        txtFiledFileChosen.textProperty().bind(selectedFileProperty);
        txtFiledFileChosen.opacityProperty().bind(opacityProperty);
        hBoxMain.disableProperty().bind(txtFiledFileChosen.textProperty().isEmpty());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setBusinessLogic(StepperBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }




    //Events
    @FXML
    void LoadFileButtonPressed(Event event) throws IOException {
        String RESOURCE = "/upload-file";

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Flows XML File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null)
            return;

        String absolutePath = selectedFile.getAbsolutePath();
        selectedFileProperty.set(absolutePath);
        opacityProperty.set(50);

        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("file", selectedFile.getName(), RequestBody.create(selectedFile, MediaType.parse("text/plain")))
                        //.addFormDataPart("key1", "value1") // you can add multiple, different parts as needed
                        .build();

        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .post(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();

        System.out.println(response.body().string());

        RolesManagementTabController.initiateRoles();
    }



}
