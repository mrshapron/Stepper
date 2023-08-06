package LogIn;

import Components.Main.ClientApp;
import Components.Main.ClientMainController;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.IOException;

import static configuration.Configuration.BASE_URL;
import static configuration.Configuration.HTTP_CLIENT;

public class LogInController {

    private ClientMainController clientMainController;


    private Stage primaryStage;

    @FXML
    private TextField usernameField;

    @FXML
    private Button LogInButton;


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @FXML
    void LogInButtonPressed(ActionEvent event) throws Exception {
        String RESOURCE = "/log-in";

        String username = usernameField.getText();
        //Start http request
        // Prepare the request body with the parameter
        RequestBody body = new FormBody.Builder()
                .add("username", username) // Add your parameter here
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .post(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        Response response = call.execute();

        System.out.println(response.body().string());


        // Simulating a redirection response
            String redirectionURL = "/Components/Main/ClientMain.fxml";
            String redirectionTitle = "Main Application";

            // Call the openNewStage() method to load the new scene in a new stage
            ClientApp mainApp = new ClientApp();
            mainApp.setUsername(username);
            mainApp.openNewStage(redirectionURL, redirectionTitle);
            primaryStage.close();
            // Close the current stage (login stage)
        /* IF name already exists!
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Validation Error");
            alert.setContentText("Username already exists!");

            alert.showAndWait();
*/




    }
}
