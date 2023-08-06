package usersManagementTab;

import Users.Role.RoleImpl;
import Users.User;
import Users.UserImpl;
import adminmain.AdminMainController;
import com.google.gson.Gson;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import okhttp3.*;
import rolesManagementTab.RolesManagementTabController;
import usersManagementTab.ViewModel.UserModelView;

import java.io.IOException;
import java.util.*;

import static Users.UserUtil.areUsersListsIdentical;
import static configuration.Configuration.BASE_URL;
import static configuration.Configuration.HTTP_CLIENT;

public class UsersManagementTabController {
    private boolean Check = false;
    private List<String> flowsList;
    private List<UserImpl> currUsers;
    private List<RoleImpl> currRoles;
    private AdminMainController mainController;
    private Map<RoleImpl, CheckBox> roleCheckBoxMap;
    private UserImpl myUser; // Instance variable to store the selected UserImpl



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
    private ListView<String> UserDetailsListView;

    @FXML
    private TableView<RoleImpl> AssignedRolesTableView;

    @FXML
    private TableColumn<RoleImpl, String> AssignedRoleNameColumn;

    @FXML
    private TableColumn<RoleImpl, String> AssignedRolesDescriptionColumn;

    @FXML
    private Label AddRolesLabel;

    @FXML
    private VBox updateUserVBox;

    @FXML
    private Label updateLabel;

    @FXML
    private Label rolesLabel;

    @FXML
    private VBox RolesVBox;

    @FXML
    private CheckBox isManagerCheckBox;

    @FXML
    private Button updateUserButton;

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");


    public void initialize() {
        currUsers = new ArrayList<>();
        flowsList = new ArrayList<>();
        currRoles = new ArrayList<>();
        roleCheckBoxMap = new HashMap<>();
        myUser = null;
        // Set up cell value factories for the table columns
        UserNameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        NumberOfRolesColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfRolesProperty());
        ManagerColumn.setCellValueFactory(cellData -> cellData.getValue().managerProperty());
        UsersTableView.setOnMouseClicked(this::handleUserRowClicked);

        AssignedRoleNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssignedRolesDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

    public void setMainController(AdminMainController mainController){ this.mainController = mainController;}
        private void handleUserRowClicked(MouseEvent event) {
            // Check if a row was actually clicked and not an empty area
            if (UsersTableView.getSelectionModel().getSelectedItem() != null) {
                updateUserComponents();
                // Get the selected user from the TableView
                UserModelView selectedUser = UsersTableView.getSelectionModel().getSelectedItem();
                UserImpl user = null;
                synchronized (currUsers) {
                    for (UserImpl user1 : currUsers) {
                        if (user1.getUsername().equals(selectedUser.getUsername()))
                            user = user1;
                    }
                }
                RoleImpl role1 = new RoleImpl("A", "B");
                if (user == null)
                    user = new UserImpl("PROBLEM", role1);

                myUser = user;


                // Add information about the selected user to the updateUserVBox
                updateLabel.setVisible(true);
                updateLabel.setText("Update " +myUser.getUsername() +"'s information:");
                rolesLabel.setVisible(true);
                RolesVBox.setVisible(true);
                updateUserButton.setVisible(true);
                isManagerCheckBox.setVisible(true);
                isManagerCheckBox.setSelected(Objects.equals(selectedUser.getManager(), "true"));

                RolesVBox.getChildren().clear();
                synchronized (roleCheckBoxMap){
                    roleCheckBoxMap.clear();
                }


                synchronized (currRoles) {
                    for (RoleImpl role : currRoles){
                        addNewRole(role, myUser);
                    }
                }
            }
        }

    private void addNewRole(RoleImpl role, UserImpl user) {
        HBox newRoleHBox = new HBox();
        Label newRoleLabel = new Label();
        CheckBox newRoleCB = new CheckBox();
        boolean checkBox = false;
        newRoleLabel.setText(role.name());
        for (RoleImpl role1: user.getRoles()){
            if (role1.name().equals(role.name()))
                checkBox = true;
        }
        newRoleCB.setSelected(checkBox);
        newRoleHBox.getChildren().addAll(newRoleLabel, newRoleCB);

        RolesVBox.getChildren().add(newRoleHBox);
        // Add the RoleImpl and its corresponding CheckBox to the map
        synchronized (roleCheckBoxMap) {
            roleCheckBoxMap.put(role, newRoleCB);
        }
    }

    @FXML
    void UpdateUser(ActionEvent event) throws IOException {
        UsersTableView.getSelectionModel().clearSelection();
        if (myUser == null) {
            // Show an error message or return from the method
            System.out.println("Please select a user before updating.");
            return;
        }

        //Create new User
        UserImpl user = createNewUser(myUser.getUsername());
        System.out.println(user.getRoles().size());
        //Send HTTP request

        Gson gson = new Gson();

        // Convert the Gson object to a JSON string
        String jsonBody = gson.toJson(user);
        // Build the request body
        RequestBody requestBody = RequestBody.create(jsonBody, JSON_MEDIA_TYPE);


        Request request = new Request.Builder()
                .url(BASE_URL + "/change-user")
                .post(requestBody) // Use .put(requestBody) for PUT requests or .patch(requestBody) for PATCH requests
                .addHeader("Content-Type", "application/json")
                .build();


        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();
        System.out.println(response);
        myUser = null;
    }

    private UserImpl createNewUser(String username) {
        List<RoleImpl> roles = new ArrayList<>();
        synchronized (roleCheckBoxMap) {
            for (Map.Entry<RoleImpl, CheckBox> role : roleCheckBoxMap.entrySet()) {
                if (role.getValue().isSelected())
                    roles.add(role.getKey());
            }
        }

        return new UserImpl(username, roles, isManagerCheckBox.isSelected());
    }

    public void updateUsers(List<UserImpl> updatedUsers) {
        // Convert the UserImpl objects to UserModel and update the table
        List<UserModelView> usersList = convertToUserModelList(updatedUsers);
        synchronized (currUsers) {
            if (!areUsersListsIdentical(updatedUsers, currUsers)) {
                UsersTableView.getItems().setAll(usersList);
                currUsers = updatedUsers;
                mainController.updateUsers1(updatedUsers);
            }
        }
    }

    private List<UserModelView> convertToUserModelList(List<UserImpl> users) {
        // Convert UserImpl objects to UserModel objects
        List<UserModelView> userModelList = new ArrayList<>();
        for (UserImpl user : users) {
            String username = user.getUsername();
            String numberOfRoles = String.valueOf(user.getRoles().size()); // Assuming you have a getRoles() method
            String manager = user.isManager().toString(); // Replace this with the actual getter for manager

            UserModelView userModel = new UserModelView(username, numberOfRoles, manager);
            userModelList.add(userModel);
        }
        return userModelList;
    }


    public void UpdateMyRoles (List<RoleImpl> rolesList){
        synchronized (currRoles) {
            currRoles = rolesList;
        }
    }


    private void updateUserComponents() {
        UserModelView user = UsersTableView.getSelectionModel().getSelectedItem();
        UserImpl myUser = null;
        synchronized (currUsers){
            for (UserImpl user1 : currUsers){
                if (user1.getUsername().equals(user.getUsername()))
                    myUser = user1;
            }
        }

        AssignedRolesTableView.getItems().clear();
        AssignedRolesTableView.getItems().addAll(myUser.getRoles());

        UserDetailsListView.getItems().clear();
        UserDetailsListView.getItems().add("The user's name is: " +myUser.getUsername() +".\n" +
                "It has " +myUser.getRoles().size() +" number of roles." +"\n and manager: " +myUser.isManager().toString());

    }
}

