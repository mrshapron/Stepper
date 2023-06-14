package Components.Main;

import BusinessLogic.StepperBusinessLogic;
import BusinessLogic.StepperBusinessLogicImpl;
import Components.Main.FlowDefinitionComponent.FlowDefinitionsPageController;
import Components.Main.FlowExecutionComponent.FlowExecutionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();

        StepperBusinessLogic businessLogic = new StepperBusinessLogicImpl();
        loader.setLocation(getClass().getResource("Main.fxml"));
        VBox root = loader.load();

        MainController mainController = loader.getController();
        mainController.setPrimaryStage(primaryStage);
        mainController.setBusinessLogic(businessLogic);
        primaryStage.setTitle("Stepper Menu");

        Scene scene = new Scene(root, 1200, 850);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(MainApplication.class);
    }
}
