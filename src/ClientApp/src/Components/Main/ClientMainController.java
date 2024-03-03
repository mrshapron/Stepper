package Components.Main;

import Components.Main.FlowDefinitionComponent.FlowDefinitionsPageController;
import Components.Main.FlowDefinitionComponent.ModelViews.TableViewFlowModel;
import Components.Main.FlowExecutionComponent.FlowExecutionsPageController;
import Components.Main.FlowExecutionComponent.ModelViews.FlowExecutionModelView;
import Components.Main.FlowHistoryComponent.FlowHistoryPageController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class ClientMainController {
    private String username;

    @FXML
    private Label ClientNameLabel;

    @FXML
    private Label IsManagerLabel;

    @FXML
    private HBox AssignedRolesHBox;
    @FXML
    private TabPane tabPane;

    @FXML
    private BorderPane FlowDefinitionsTab;
    @FXML
    private FlowDefinitionsPageController FlowDefinitionsTabController;
    @FXML
    private BorderPane FlowExecutionsTab;
    @FXML
    private FlowExecutionsPageController FlowExecutionsTabController;

    @FXML
    private BorderPane FlowHistoryTab;
    @FXML
    private FlowHistoryPageController FlowHistoryTabController;

    @FXML
    public void switchToFlowExecutionTab(String flowName) throws IOException {
        tabPane.getSelectionModel().select(1); // Switch to the "Flow Executions" tab
        FlowExecutionsTabController.getFlowName(flowName);
        setControllerUsername(username);
    }

    public void updateUser(boolean isManager){
        if (isManager)
            IsManagerLabel.setText(" is a manager");
        else
            IsManagerLabel.setText(" is NOT a manager");
    }

    public void initialize() {
        FlowDefinitionsTabController.setClientMainController(this);
        FlowExecutionsTabController.setClientMainController(this);
        FlowHistoryTabController.setClientMainController(this);

    }

    public FlowDefinitionsPageController getFlowDefinitionsTabController() {
        return FlowDefinitionsTabController;
    }


    public void updateFlows(List<TableViewFlowModel> updatedFlows) {
        FlowDefinitionsTabController.updateFlows(updatedFlows);
    }

    public void updateHistory(List<FlowExecutionModelView> modelViews) {
        FlowHistoryTabController.updateHistory(modelViews);
    }

    public void setUsername(String username) {
        this.username = username;
        ClientNameLabel.setText("Name: " +username);
        IsManagerLabel.setText(" is NOT a manager");
    }

    public void setControllerUsername(String text){
        FlowExecutionsTabController.setUsername(username);
        FlowDefinitionsTabController.setUsername(username);
    }
}
