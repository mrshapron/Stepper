package Components.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientMainController {

    @FXML private Label lblClientName;
    @FXML private Label lblIsManager;
    @FXML private AnchorPane lblNameClient;
    @FXML private ClientMainController flowDefinitionTabController;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
