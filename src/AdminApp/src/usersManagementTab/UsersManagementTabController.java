package usersManagementTab;

import Users.UserImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import usersManagementTab.ViewModel.UserModelView;

import java.util.ArrayList;
import java.util.List;

public class UsersManagementTabController {
    List<UserImpl> currUsers;

    @FXML
    private TableView<UserModelView> UsersTableView;

    @FXML
    private TableColumn<UserModelView, String> UserNameColumn;

    @FXML
    private TableColumn<UserModelView, String> NumberOfRolesColumn;

    @FXML
    private TableColumn<UserModelView, String> ManagerColumn;

    @FXML
    private Label UserNameLabel;

    @FXML
    private ListView<?> UserDetailsListView;

    @FXML
    private TableView<?> AssignedFlowsTableView;

    @FXML
    private TableColumn<?, ?> AssignedRoleNameColumn;

    @FXML
    private TableColumn<?, ?> AssignedRolesDescriptionColumn;

    public void initialize() {
        currUsers = new ArrayList<>();
        // Set up cell value factories for the table columns
        UserNameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        NumberOfRolesColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfRolesProperty());
        ManagerColumn.setCellValueFactory(cellData -> cellData.getValue().managerProperty());
    }

    public void updateUsers(List<UserImpl> updatedUsers) {
        // Convert the UserImpl objects to UserModel and update the table
        List<UserModelView> usersList = convertToUserModelList(updatedUsers);
        if (!updatedUsers.equals(currUsers)) {
            UsersTableView.getItems().setAll(usersList);
            currUsers = updatedUsers;
        }
    }

    private List<UserModelView> convertToUserModelList(List<UserImpl> users) {
        // Convert UserImpl objects to UserModel objects
        List<UserModelView> userModelList = new ArrayList<>();
        for (UserImpl user : users) {
            String username = user.getUsername();
            String numberOfRoles = String.valueOf(user.getRoles().size()); // Assuming you have a getRoles() method
            String manager = "true"; // Replace this with the actual getter for manager

            UserModelView userModel = new UserModelView(username, numberOfRoles, manager);
            userModelList.add(userModel);
        }
        return userModelList;
    }
}

