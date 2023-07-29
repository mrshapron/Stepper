package rolesManagementTab;

import Users.Role.RoleImpl;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import okhttp3.*;

import java.io.IOException;

import static configuration.Configuration.BASE_URL;
import static configuration.Configuration.HTTP_CLIENT;

public class RolesManagementTabController {

    @FXML
    private TableView<?> RolesTableView;

    @FXML
    private TableColumn<?, ?> RoleNameColumn;

    @FXML
    private TableColumn<?, ?> NumberOfFlowsColumn;

    @FXML
    private Button AddNewRoleButton;

    @FXML
    private Label RoleNameLabel;

    @FXML
    private ListView<?> RoleDetailsListView;

    @FXML
    private TableView<?> AssignedFlowsTableView;

    @FXML
    private TableColumn<?, ?> stepNameColumn;

    @FXML
    private TableView<?> AssignedUsersTableView;

    @FXML
    private TableColumn<?, ?> inputNameColumn;

    @FXML
    void AddNewRoleBtnPressed(ActionEvent event) {

    }

    public void initiateRoles() throws IOException {
        RoleImpl roleAllFlows = new RoleImpl("All Flows", "Can accesss all of the flows available");
        RoleImpl roleReadOnly = new RoleImpl("Read Only Flows", "Can access read-only flows");
        addRole(roleReadOnly);
        addRole(roleAllFlows);
    }

    private void addRole(RoleImpl role) throws IOException{
//        Gson gson = new Gson();
//        String roleAsJson = gson.toJson(role);
//
//        //Start http request
//        RequestBody body = RequestBody.create(MediaType.parse("application/json"), roleAsJson);
//
//        Request request = new Request.Builder()
//                .url(BASE_URL + "/add-role")
//                .post(body)
//                .build();
//
//        Call call = HTTP_CLIENT.newCall(request);
//
//        Response response = call.execute();
//
//        System.out.println(response.body().string());
    }
}
