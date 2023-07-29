package Components.Main;

import Components.Main.FlowDefinitionComponent.FlowDefinitionsPageController;
import Components.Main.FlowDefinitionComponent.ModelViews.TableViewFlowModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
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
    private BorderPane FlowDefinitionsTab;
    @FXML
    private FlowDefinitionsPageController FlowDefinitionsTabController;

//    public void initialize() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("Components/Main/FlowDefinitionComponent/FlowDefinitionsTab.fxml"));
//            FlowDefinitionsTabController = loader.load();
//            FlowDefinitionsTab.getChildren().add(FlowDefinitionsTabController.getRoot());
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle the exception if FXML file loading fails
//        }
//    }

    public FlowDefinitionsPageController getFlowDefinitionsTabController() {
        return FlowDefinitionsTabController;
    }


    public void updateFlows(List<TableViewFlowModel> updatedFlows) {
        FlowDefinitionsTabController.updateFlows(updatedFlows);
    }

}
