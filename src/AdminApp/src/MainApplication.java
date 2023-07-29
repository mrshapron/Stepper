import Users.Role.Role;
import Users.UserImpl;
import adminmain.AdminMainController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import deserializer.RoleDeserializer;
import deserializer.UserDeserializer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Users.*;
import okhttp3.Request;
import okhttp3.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import static configuration.Configuration.BASE_URL;
import static configuration.Configuration.HTTP_CLIENT;

public class MainApplication extends Application {
    private static List<UserImpl> fetchedUsers = new ArrayList<>();
    private Timer timer;
    private AdminMainController mainController;
    private Gson gson = new Gson();

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/adminmain/AdminMain.fxml"));
        VBox root = loader.load();

        mainController = loader.getController();
        mainController.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Stepper Menu");

        Scene scene = new Scene(root, 1200, 850);
        primaryStage.setScene(scene);
        primaryStage.show();


        timer = new Timer();
        timer.schedule(new DeltaFetchingTask(), 0, 2000);
    }


    @Override
    public void stop() throws Exception {
        super.stop();

        // Cancel the timer when the JavaFX application is stopped
        if (timer != null) {
            timer.cancel();
        }
    }


    private class DeltaFetchingTask extends TimerTask {
        @Override
        public void run() {
//            System.out.println("Executing DeltaFetchingTask...");
            Request request = new Request.Builder()
                    .url(BASE_URL + "/fetch-users")
                    .get()
                    .build();

            try (Response response = HTTP_CLIENT.newCall(request).execute()) {
//                System.out.println("Received server response: " + response.code() + " " + response.message());
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
//                    System.out.println("Response data: " + responseData);
                    // Process the fetched data
                    List<UserImpl> updatedUsers = processFetchedData(responseData);

                    // Pass the fetched users to your controller via a method
                    mainController.updateUsers(updatedUsers);
                } else {
                    // Handle the error response
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private List<UserImpl> processFetchedData(String responseData) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
            gsonBuilder.registerTypeAdapter(Role.class, new RoleDeserializer());
            Gson gson = gsonBuilder.create();

            Type userListType = new TypeToken<List<UserImpl>>() {}.getType();
            return gson.fromJson(responseData, userListType);
        }
    }



    public static void main(String[] args) {
        launch(MainApplication.class);
    }
}
