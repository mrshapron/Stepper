package Components.Main;
import Components.Main.FlowExecutionComponent.ModelViews.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Components.Main.FlowDefinitionComponent.ModelViews.*;
import Components.Main.deserializer.*;
import LogIn.LogInController;
import StepDTO.StepInput;
import StepDTO.StepOutput;
import Users.Role.Role;
import Users.Role.RoleImpl;
import Users.User;
import Users.UserImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.lang.reflect.Type;
import java.util.*;

import static com.sun.corba.se.impl.activation.ServerMain.logError;
import static configuration.Configuration.BASE_URL;
import static configuration.Configuration.HTTP_CLIENT;

public class ClientApp extends Application {
    private ClientMainController clientMainController;
    private LogInController loginController;
    private String username;
    private boolean ok = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the initial login scene (assuming it's in the "ClientApp" folder)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../LogIn/LogIn.fxml"));
        Parent loginRoot = loader.load();
        loginController = loader.getController();

        // Set the primary stage in the controller
        loginController.setPrimaryStage(primaryStage);

        Scene loginScene = new Scene(loginRoot);

        // Set the initial scene and show the stage
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Client LogIn");
        primaryStage.show();
    }

    // Method to open a new stage with a different scene (e.g., main application)
    public void openNewStage(String fxmlPath, String title) throws Exception {
        try {
            if (fxmlPath == null) {
                throw new IllegalArgumentException("FXML path cannot be null.");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox root = loader.load();
            Scene scene = new Scene(root);
            clientMainController = loader.getController();

            // Create a new stage for the new scene
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle(title);

            // Show the new stage
            newStage.show();

            // Start the DeltaFetchingTask after the new stage is shown
            DeltaFetchingTask deltaFetchingTask = new DeltaFetchingTask();
            // Delay the first execution by 2 seconds (2000 milliseconds)
            long initialDelay = 2000;
            // Execute the task every 2 seconds (2000 milliseconds)
            long period = 2000;
            // Schedule the task
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(deltaFetchingTask, initialDelay, period);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception if FXML file loading fails
        }


    }





        private class DeltaFetchingTask extends TimerTask {
            @Override
            public void run() {
//                System.out.println("Executing DeltaFetchingTask...");

                RequestBody body = new FormBody.Builder()
                        .add("username", username) // Add your parameter here
                        .build();

                Request request = new Request.Builder()
                        .url(BASE_URL + "/fetch-flows")
                        .post(body)
                        .build();

                try (Response response = HTTP_CLIENT.newCall(request).execute()) {
//                    System.out.println("Received server response: " + response.code() + " " + response.message());
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
//                        System.out.println("Response data: " + responseData);
                        // Process the fetched data

                        List<TableViewFlowModel> updatedFlows = processFetchedData(responseData);

                        // Pass the fetched users to your controller via a method
                        clientMainController.updateFlows(updatedFlows);
                        if (ok) {
                            Platform.runLater(() -> {
                                clientMainController.setUsername(username);
                            });
                            ok = false;
                        }
                    } else {
                        System.out.println("There's a problem with fetch-flows");
                    }
                } catch (Exception e) {
                    logError("Error occurred during fetching data: " + e.getMessage());
                    e.printStackTrace();
                }

                RequestBody requestBody = new FormBody.Builder()
                        .add("username", username) // Add your parameter here
                        .build();

                Request request2 = new Request.Builder()
                        .url(BASE_URL + "/fetch-history")
                        .post(requestBody) // Use .put(requestBody) for PUT requests or .patch(requestBody) for PATCH requests
                        .build();

                try (Response historyResponse = HTTP_CLIENT.newCall(request2).execute()) {
                    if (historyResponse.isSuccessful()) {
                        String historyResponseData = historyResponse.body().string();
//                        System.out.println(historyResponseData);
                        // Pass the fetched roles to your controller via a method
//                        System.out.println(historyResponseData);
                        if (!historyResponseData.equals("[]")) {
                            Gson gson = new GsonBuilder()
                                    .registerTypeAdapter(FlowExecutionModelView.class, new FlowExecutionModelViewDeserializer())
                                    .registerTypeAdapter(FreeInputsExecViewModel.class, new FreeInputsExecViewModelDeserializer())
                                    .registerTypeAdapter(OutputExecModelView.class, new OutputExecModelViewDeserializer())
                                    .registerTypeAdapter(StepExecModelView.class, new StepExecModelViewDeserializer())
                                    .registerTypeAdapter(InputStepModelView.class, new InputStepModelViewDeserializer())
                                    .registerTypeAdapter(OutputStepModelView.class, new OutputStepModelViewDeserializer())
                                    // ... register other deserializers ...
                                    .create();
                            Type listType = new TypeToken<List<FlowExecutionModelView>>() {
                            }.getType();
                            List<FlowExecutionModelView> modelViews = gson.fromJson(historyResponseData, listType);
//                        FlowExecutionModelView modelView1 = gson.fromJson(historyResponseData, FlowExecutionModelView.class);
//                            System.out.println("Flow Name: " + modelView1.getFlowName());
//                            System.out.println("Free Inputs: " + modelView1.getFreeInputsExecViewModels()); // Print freeInputsExecViewModels here
//                        ObservableList<FlowExecutionModelView> observableList = FXCollections.observableList(modelViews);
                            clientMainController.updateHistory(modelViews);
                        }
                        else {
                            clientMainController.updateHistory(null);
                        }
                    } else{
                        System.out.println("Problem in request");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

            private List<TableViewFlowModel> processFetchedData(String responseData) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(TableViewFlowModel.class, new TableViewFlowModelDeserializer())
                        .registerTypeAdapter(new TypeToken<List<TableViewFlowModel>>() {}.getType(), new TableViewFlowModelListDeserializer())
                        .registerTypeAdapter(new TypeToken<Map<String, List<StepInputsViewModel>>>() {}.getType(), new InputsMapDeserializer())
                        .registerTypeAdapter(new TypeToken<Map<String, List<StepOutputViewModel>>>() {}.getType(), new OutputsMapDeserializer())
                        .registerTypeAdapter(new TypeToken<List<StepsTableViewModel>>() {}.getType(), new ObservableListStepsTableViewModelDeserializer())
                        .registerTypeAdapter(new TypeToken<List<FreeInputsViewModel>>() {}.getType(), new ObservableListFreeInputsViewModelDeserializer())
                        .registerTypeAdapter(new TypeToken<List<String>>() {}.getType(), new ObservableListStringDeserializer())
                        .registerTypeAdapter(new TypeToken<ObservableList<StepInputsViewModel>>() {}.getType(), new ObservableListStepInputsViewModelDeserializer())
                        .registerTypeAdapter(new TypeToken<ObservableList<AllOutputModelView>>() {}.getType(), new ObservableListAllOutputModelViewDeserializer())
                        .registerTypeAdapter(new TypeToken<ObservableList<StepOutputViewModel>>() {}.getType(), new ObservableListStepOutputViewModelDeserializer())
                        .registerTypeAdapter(new TypeToken<ReadOnlyStringWrapper>() {}.getType(), new ReadOnlyStringWrapperDeserializer())
                        .registerTypeAdapter(StepInput.class, new StepInputDeserializer()) // Custom deserializer for StepInput
                        .registerTypeAdapter(StepOutput.class, new StepOutputDeserializer()) // Custom deserializer for StepOutput
                        .create();


                try {
                    return gson.fromJson(responseData, new TypeToken<List<TableViewFlowModel>>(){}.getType());
                } catch (Exception e) {
                    logError("Error occurred during JSON deserialization: " + e.getMessage());
                    e.printStackTrace();
                    return null; // Return null to indicate an error
                }            }
        }

        public void setUsername(String text){
            username = text;
        }

    public static void main(String[] args) {
        launch(args);
    }
}

