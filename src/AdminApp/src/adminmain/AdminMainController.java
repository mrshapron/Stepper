package adminmain;

import Users.Role.RoleImpl;
import Users.UserImpl;
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
import usersManagementTab.UsersManagementTabController;

import static configuration.Configuration.HTTP_CLIENT;
import static configuration.Configuration.BASE_URL;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdminMainController {

    private Stage primaryStage;

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
    @FXML
    private BorderPane UsersManagementTab;
    @FXML
    private UsersManagementTabController UsersManagementTabController;


    public AdminMainController() {
        selectedFileProperty = new SimpleStringProperty();
        opacityProperty = new SimpleIntegerProperty(100);
    }

    public void initialize() {
        txtFiledFileChosen.textProperty().bind(selectedFileProperty);
        txtFiledFileChosen.opacityProperty().bind(opacityProperty);
        hBoxMain.disableProperty().bind(txtFiledFileChosen.textProperty().isEmpty());
        UsersManagementTabController.setMainController(this);
        RolesManagementTabController.setMainController(this);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
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

        //System.out.println(response.body().string());
    }

    public void updateUsers(List<UserImpl> updatedUsers){
        UsersManagementTabController.updateUsers(updatedUsers);
    }


    public void updateRoles(List<RoleImpl> updatedRoles) { RolesManagementTabController.updateRoles(updatedRoles);}

    public void setHisRoles(List<RoleImpl> updatedRoles) {
        UsersManagementTabController.UpdateMyRoles(updatedRoles);
    }

    public void updateUsers1(List<UserImpl> mylist) {
        RolesManagementTabController.updateUsers(mylist);
    }
}
