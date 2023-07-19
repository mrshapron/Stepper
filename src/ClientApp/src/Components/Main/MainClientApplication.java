package Components.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainClientApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("ClientMain.fxml"));
        VBox root = loader.load();

        ClientMainController clientMainController = loader.getController();
        clientMainController.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Stepper Menu");

        Scene scene = new Scene(root, 1200, 850);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
