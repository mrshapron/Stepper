package rolesManagementTab;

import Users.Role.RoleImpl;
import Users.UserImpl;
import adminmain.AdminMainController;
import com.google.gson.Gson;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import okhttp3.*;
import usersManagementTab.UsersManagementTabController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static configuration.Configuration.BASE_URL;
import static configuration.Configuration.HTTP_CLIENT;

public class RolesManagementTabController {

    private List<String> flowsList;
    private List<UserImpl> usersList;
    private Boolean first;
    private AdminMainController mainController;
    private Map<String, CheckBox> flowsCheckBoxMap;

    @FXML
    private TableView<RoleImpl> RolesTableView;

    @FXML
    private TableColumn<RoleImpl, String> RoleNameColumn;

    @FXML
    private TableColumn<RoleImpl, Integer> NumberOfFlowsColumn;

    @FXML
    private Button AddNewRoleButton;

    @FXML
    private VBox switchVbox;

    @FXML
    private Label RoleNameLabel;

    @FXML
    private TextArea RoleDetailsTextArea;

    @FXML
    private ListView<String> AssignedFlowsListView;

    @FXML
    private ListView<String> AssignedUsersListView;

    @FXML
    private GridPane AddingFlowGridPane;

    @FXML
    private Button roleButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField DescriptionTextField;
    @FXML
    private GridPane RolesGridPane;
    @FXML
    private VBox flowsListVBox;



    public void initialize() {
        usersList = new ArrayList<>();
        first = true;
        flowsList = new ArrayList<>();
        flowsCheckBoxMap = new HashMap<>();
        // Define cell value factories for the table columns
        RoleNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));
        NumberOfFlowsColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().availableFlows().size()).asObject());

    }

    public void setMainController(AdminMainController adminMainController) {
        mainController = adminMainController;

    }

    @FXML
    void AddNewRoleBtnPressed(ActionEvent event) {
        RoleNameLabel.setText("Adding new Role");
        RolesGridPane.setVisible(false);
        addFlowsToVBox();
        AddingFlowGridPane.setVisible(true);
    }

    private void addFlowsToVBox() {
        flowsListVBox.getChildren().clear();
        synchronized(flowsList) {
            for (String flow : flowsList) {
                addFlowToBox(flow);
            }
        }
    }

    public void updateUsers(List<UserImpl> users){
        usersList.clear();
        usersList.addAll(users);
    }

    private void addFlowToBox(String flow) {
        HBox newFlowHBox = new HBox();
        CheckBox newFlowCheckBox = new CheckBox();
        Label newFlowLabel = new Label();
        newFlowLabel.setText(flow);
        newFlowCheckBox.setSelected(false);
        newFlowHBox.getChildren().addAll(newFlowLabel, newFlowCheckBox);
        flowsCheckBoxMap.put(flow, newFlowCheckBox);
        flowsListVBox.getChildren().add(newFlowHBox);
    }

    private void addRole(RoleImpl role) throws IOException{
        Gson gson = new Gson();
        String roleAsJson = gson.toJson(role);
        //Start http request
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), roleAsJson);

        Request request = new Request.Builder()
                .url(BASE_URL + "/add-role")
                .post(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();

        System.out.println(response.body().string());
    }

    public void updateRoles(List<RoleImpl> updatedRoles) {
        // Check if the new data is different from the current data in the table
        if (!isDataChanged(updatedRoles)) {
            first = false;
            return; // If there are no changes, do nothing and return
        }

        synchronized (flowsList) {
            for (RoleImpl role : updatedRoles) {
                for (String flow : role.availableFlows()) {
                    if (!flowsList.contains(flow))
                        flowsList.add(flow);
                }
            }
        }

        mainController.setHisRoles(updatedRoles);
        //Through main controller update the roles in users managament tab;
        // Clear the table before adding new data
        RolesTableView.getItems().clear();
        // Add the updatedRoles list to the table
        RolesTableView.getItems().addAll(updatedRoles);
    }

    // Helper method to check if the new data is different from the current data in the table
    private boolean isDataChanged(List<RoleImpl> updatedRoles) {
        ObservableList<RoleImpl> currentData = RolesTableView.getItems();

        // Check if the size of the lists is different
        if (updatedRoles.size() != currentData.size()) {
            return true;
        }

        // Check if any role in the updatedRoles list is different from the corresponding role in the table
        for (int i = 0; i < updatedRoles.size(); i++) {
            RoleImpl updatedRole = updatedRoles.get(i);
            RoleImpl currentRole = currentData.get(i);

            // Compare the name, description, and availableFlows
            if (!updatedRole.name().equals(currentRole.name()) ||
                    !updatedRole.description().equals(currentRole.description()) ||
                    !updatedRole.availableFlows().equals(currentRole.availableFlows())) {
                return true;
            }
        }
        return false; // If no differences are found, return false
    }

    @FXML
    void dispatchRole(ActionEvent event) throws IOException {
        String name = nameTextField.getText();
        String description = DescriptionTextField.getText();
        RoleImpl role;
        if (name!= null && description != null) {
            role = new RoleImpl(name, description);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            if (name == null)
                alert.setContentText("need to insert a name to the role...");
            else if (description == null)
                alert.setContentText("need to insert a description to the role...");
            else
                alert.setContentText("There was a problem");
            // Show and wait for the user to close the error box
            alert.showAndWait();
            return;
        }
        //Adding flows to the role..
        for (Map.Entry<String, CheckBox> map : flowsCheckBoxMap.entrySet())
        {
            if (map.getValue().isSelected())
                role.addFlow(map.getKey());
        }
        //Check something with the flows and add them to role somehow...
        addRole(role);
        switchBack();
    }
    public void switchBack (){
        RoleNameLabel.setText("Role Name");
        AddingFlowGridPane.setVisible(false);
        RolesGridPane.setVisible(true);
    }

    @FXML
    public void onMouseClickedRolesTableView(MouseEvent mouseEvent) {
        RoleImpl role =  RolesTableView.getSelectionModel().getSelectedItem();
        RoleDetailsTextArea.setText("The name of the role is: " +role.name() +"\nThe description of the role is: "+
                role.description() +"\nThe role has " +role.availableFlows().size() +" flows");

        AssignedFlowsListView.getItems().clear();
        AssignedFlowsListView.getItems().addAll(role.availableFlows());

        List<String> nameList = new ArrayList<>();
        for (UserImpl user1 : usersList){
            if (user1.getRoles().contains(role))
                nameList.add(user1.getUsername());
        }
        AssignedUsersListView.getItems().clear();
        AssignedUsersListView.getItems().addAll(nameList);
    }
}
