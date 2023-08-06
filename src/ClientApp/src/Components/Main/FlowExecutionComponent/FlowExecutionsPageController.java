package Components.Main.FlowExecutionComponent;

import Components.Main.ClientMainController;
import Components.Main.FlowDefinitionComponent.ModelViews.*;
import Components.Main.deserializer.*;
import StepDTO.StepInput;
import StepDTO.StepOutput;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.concurrent.Task;
import javafx.embed.swt.FXCanvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import javafx.animation.Interpolator;
import Components.Main.FlowExecutionComponent.ModelViews.*;
import okhttp3.*;

import static com.sun.corba.se.impl.activation.ServerMain.logError;
import static configuration.Configuration.BASE_URL;
import static configuration.Configuration.HTTP_CLIENT;


public class FlowExecutionsPageController {
    private Map<String, String> freeInputsDatas;
    private String flowName;
    private ClientMainController clientMainController;
    private boolean canRun = false;
    private ObservableList<FlowExecutionModelView> flowExecutionModelViews;
    private StringProperty valueEnteredProperty;
    private ObservableList<CurValInputModelView> curValInputModelViews;
    private String username;

    @FXML
    private Label lblFlowName;

    @FXML
    private Label lblRunFlowContinue;

    @FXML
    private TableView<FreeInputsTableView> tableFreeInputs;

    @FXML
    private TableColumn<FreeInputsTableView, String> freeInputsInputNameColumn;

    @FXML
    private TableColumn<FreeInputsTableView, String> freeInputsNecessityColumn;

    @FXML
    private Label lblSelectedInput;

    @FXML
    private Label lblFreeInputUserString;

    @FXML
    private TextField txtValueInput;

    @FXML
    private Button btnEnterValueInput;

    @FXML
    private Button btnClearInputValue;

    @FXML
    private TableView<CurValInputModelView> tableCurrentValues;

    @FXML
    private TableColumn<?, ?> inputNameCurrentValCol;

    @FXML
    private TableColumn<?, ?> valueEnteredCurrentValCol;

    @FXML
    private Label lblStatusFreeInput;

    @FXML
    private Label lblProgressBar;

    @FXML
    private ProgressBar progressBarFlow;

    @FXML
    private ListView<?> listViewContinuations;

    @FXML
    private Button btnAddContinuation;

    @FXML
    private Button btnStartFlow;

    @FXML
    private HBox executionHBOX;

    @FXML
    private TableView<FlowExecutionModelView> tableAllFlowExecutions;

    @FXML
    private TableColumn<?, ?> allFlowExecFlowNameCol;

    @FXML
    private TableColumn<?, ?> allFlowExecIDExecCol;

    @FXML
    private TableColumn<?, ?> allFlowExecTimeRunCol;

    @FXML
    private TableView<FlowExecutionModelView> tableExecutionsDetails;

    @FXML
    private TableColumn<?, ?> execDetailsFlowNameCol;

    @FXML
    private TableColumn<?, ?> execDetailsIDExecCol;

    @FXML
    private TableColumn<?, ?> execDetailsResultCol;

    @FXML
    private TableColumn<?, ?> execDetailsTimeRunningCol;

    @FXML
    private TableView<FreeInputsExecViewModel> tableFreeInputsExecDetails;

    @FXML
    private TableColumn<?, ?> freeInputsExecInputNameCol;

    @FXML
    private TableColumn<?, ?> freeInputsExecTypeCol;

    @FXML
    private TableColumn<?, ?> freeInputsExecValueCol;

    @FXML
    private TableColumn<?, ?> freeInputsExecNeccCol;

    @FXML
    private TableView<OutputExecModelView> tableOutputExecution;

    @FXML
    private TableColumn<?, ?> outputExecFinalNameCol;

    @FXML
    private TableColumn<?, ?> outputExecTypeCol;

    @FXML
    private TableColumn<?, ?> outputExecValueCol;

    @FXML
    private Label lblStepExecutionResult;

    @FXML
    private TableView<StepExecModelView> stepExecutionResultTable;

    @FXML
    private TableColumn<?, ?> stepNameExecResCol;

    @FXML
    private TableColumn<?, ?> stepResultExecResultRow;

    @FXML
    private TableView<StepExecModelView> moreStepDetailsTable;

    @FXML
    private TableColumn<?, ?> stepNameStepDetailsCol;

    @FXML
    private TableColumn<?, ?> stepResStepDetailsCol;

    @FXML
    private TableColumn<?, ?> timeStartStepDetailsCol;

    @FXML
    private TableColumn<?, ?> runtimeMoreStepCol;

    @FXML
    private TableView<?> listInputStepTable;

    @FXML
    private TableView<?> listOutputStepTable;

    @FXML
    private ListView<String> stepLogsListView;


    //Adding from somewhere else:

    @FXML
    private void initialize() {
        // Other initialization code
        curValInputModelViews = FXCollections.observableArrayList();
        freeInputsDatas = new HashMap<>();
        freeInputsInputNameColumn.setCellValueFactory(new PropertyValueFactory<>("aliasName"));
        freeInputsNecessityColumn.setCellValueFactory(new PropertyValueFactory<>("necessity"));

        // Initialize other table columns similarly
        allFlowExecFlowNameCol.setCellValueFactory(new PropertyValueFactory<>("flowName"));
        allFlowExecIDExecCol.setCellValueFactory(new PropertyValueFactory<>("idExec"));
        allFlowExecTimeRunCol.setCellValueFactory(new PropertyValueFactory<>("timeRun"));

        flowExecutionModelViews = FXCollections.observableArrayList();
        tableCurrentValues.setItems(curValInputModelViews);

        execDetailsFlowNameCol.setCellValueFactory(new PropertyValueFactory<>("flowName"));
        execDetailsIDExecCol.setCellValueFactory(new PropertyValueFactory<>("idExec"));
        execDetailsResultCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        execDetailsTimeRunningCol.setCellValueFactory(new PropertyValueFactory<>("timeRunning"));

//        historyFlowNameCol.setCellValueFactory( new PropertyValueFactory<>("flowName"));
//        historyStartedTimeCol.setCellValueFactory(new PropertyValueFactory<>("timeRun"));
//        historyFlowResultCol.setCellValueFactory(new PropertyValueFactory<>("result"));

        freeInputsExecInputNameCol.setCellValueFactory(new PropertyValueFactory<>("inputFinalName"));
        freeInputsExecTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        freeInputsExecValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        freeInputsExecNeccCol.setCellValueFactory(new PropertyValueFactory<>("necessity"));

        inputNameCurrentValCol.setCellValueFactory(new PropertyValueFactory<>("inputName"));
        valueEnteredCurrentValCol.setCellValueFactory(new PropertyValueFactory<>("valueEntered"));


        valueEnteredProperty = new SimpleStringProperty("");

        outputExecFinalNameCol.setCellValueFactory(new PropertyValueFactory<>("finalName"));
        outputExecTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        outputExecValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        stepNameExecResCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stepResultExecResultRow.setCellValueFactory(new PropertyValueFactory<>("result"));

        stepNameStepDetailsCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stepResStepDetailsCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        timeStartStepDetailsCol.setCellValueFactory(new PropertyValueFactory<>("timeStarted"));
        runtimeMoreStepCol.setCellValueFactory(new PropertyValueFactory<>("runtime"));

        tableAllFlowExecutions.setItems(flowExecutionModelViews);


        txtValueInput.textProperty().bindBidirectional(valueEnteredProperty);
        valueEnteredProperty.set("");
    }


    @FXML
    void onClearInputClick(ActionEvent event) {
        FreeInputsTableView selectedInput = tableFreeInputs.getSelectionModel().getSelectedItem();
        Optional<CurValInputModelView> selected = curValInputModelViews.stream().filter(curValInputModelView -> curValInputModelView.getInputName().equals(selectedInput.getAliasName())).findFirst();
        if(selected.isPresent()){
            curValInputModelViews.remove(selected.get());
        }
        valueEnteredProperty.set("");
    }

    @FXML
    void onClickAddContinuation(ActionEvent event) {

    }

    @FXML
    void onClickEnterInputValue(ActionEvent event) {
        if(txtValueInput.getText().equals("") || tableFreeInputs.getSelectionModel().getSelectedItem() == null)
            return;
        FreeInputsTableView selectedInput = tableFreeInputs.getSelectionModel().getSelectedItem();
        freeInputsDatas.put(selectedInput.getAliasName(), txtValueInput.getText());
        btnStartFlow.disableProperty().set(false);
        enterValueToSelectedInput(selectedInput,valueEnteredProperty.getValue());
        txtValueInput.clear();
    }


    private void enterValueToSelectedInput(FreeInputsTableView selectedInput, String valueEntered) {
        CurValInputModelView valInputModelView = new CurValInputModelView(selectedInput.getAliasName(), valueEntered);
        Optional<CurValInputModelView> selected = curValInputModelViews.stream().filter(curValInputModelView -> curValInputModelView.getInputName().equals(selectedInput.getAliasName())).findFirst();
        if(selected.isPresent()){
            selected.get().setValueEntered(valueEntered);
            tableCurrentValues.refresh();
        }else{
            curValInputModelViews.add(valInputModelView);
        }
    }

    @FXML
    void onClickStartFlow(ActionEvent event) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(freeInputsDatas);

        // Send the JSON via HTTP POST request using OkHttp
        sendFreeInputsAndRunFlow(json);
    }

    private void sendFreeInputsAndRunFlow(String json) {
        String RESOURCE = "/run-flow";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        HttpUrl url = HttpUrl.parse(BASE_URL + RESOURCE)
                .newBuilder()
                .addQueryParameter("flowName", flowName) // Add the flowName parameter
                .addQueryParameter("username", username)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);

        try {
            Response response = call.execute();

            if (response.isSuccessful()) {
//                System.out.println("Request successful. Response:");
                String responseData = response.body().string();
                System.out.println(responseData);
                updateComponents(responseData);

            } else {
                System.out.println("Request failed. Response code: " + response.code());
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }


    void updateComponents(String data) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(FlowExecutionModelView.class, new FlowExecutionModelViewDeserializer())
                .registerTypeAdapter(FreeInputsExecViewModel.class, new FreeInputsExecViewModelDeserializer())
                .registerTypeAdapter(OutputExecModelView.class, new OutputExecModelViewDeserializer())
                .registerTypeAdapter(StepExecModelView.class, new StepExecModelViewDeserializer())
                .registerTypeAdapter(InputStepModelView.class, new InputStepModelViewDeserializer())
                .registerTypeAdapter(OutputStepModelView.class, new OutputStepModelViewDeserializer())
                // ... register other deserializers ...
                .create();

        FlowExecutionModelView modelView = gson.fromJson(data, FlowExecutionModelView.class);
        flowExecutionModelViews.clear();
        flowExecutionModelViews.add(modelView);
        // Populate UI components based on the deserialized modelView
        // Assuming you have labels, tables, or other UI components
        // that need to be updated based on the deserialized data
//        Platform.runLater(() -> {
//            lblFlowName.setText(modelView.getFlowName());
//        });

    }



    @FXML
    void onClickStepExecutionResultTable(MouseEvent event) {
        StepExecModelView selected = stepExecutionResultTable.getSelectionModel().getSelectedItem();
        stepLogsListView.setItems(selected.getLogsStep());
    }

    @FXML
    void onClickTableFreeInputs(MouseEvent event) {

    }

    @FXML
    void onKeyPressedValueInput(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            onClickEnterInputValue1();
        }
    }

    public void onClickEnterInputValue1() {
        if(valueEnteredProperty.getValue().equals(""))
            return;
        FreeInputsTableView selectedInput = tableFreeInputs.getSelectionModel().getSelectedItem();
        enterValueToSelectedInput(selectedInput,valueEnteredProperty.getValue());
    }

    @FXML
    void onTableAllFlowExecClick(MouseEvent event) {
        FlowExecutionModelView selected = tableAllFlowExecutions.getSelectionModel().getSelectedItem();
        stepExecutionResultTable.setItems(selected.getStepExecModelViews());
        moreStepDetailsTable.setItems(selected.getStepExecModelViews());
        tableExecutionsDetails.setItems(flowExecutionModelViews);
        tableFreeInputsExecDetails.setItems(selected.getFreeInputsExecViewModels());
        tableOutputExecution.setItems(selected.getOutputExecModelViews());
    }

    public void getFlowName(String flow) throws IOException {
        if (flow != null) {
            this.flowName = flow;
        }
        else
            this.flowName = flow;
        //Make an HTTP request to get Free Inputs;
        String RESOURCE = "/get-freeinputs";

        // Prepare the request body with the parameter
        RequestBody body = new FormBody.Builder()
                .add("flowName", flowName) // Add your parameter here
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .post(body)
                .build();

        Call call = HTTP_CLIENT.newCall(request);
        Response response = call.execute();
        String responseData = response.body().string();

        System.out.println(responseData);
        List<FreeInputsTableView> freeInputsList = processFreeInputs(responseData);
        tableFreeInputs.getItems().clear();
        for (FreeInputsTableView freeInput : freeInputsList){
            tableFreeInputs.getItems().add(freeInput);
        }
    }

    private List<FreeInputsTableView> processFreeInputs(String responseData) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(FreeInputsTableView.class, new FreeInputsTableViewModelDeserializer())
                .create();
        try {
            return gson.fromJson(responseData, new TypeToken<List<FreeInputsTableView>>(){}.getType());
        } catch (Exception e) {
            logError("Error occurred during JSON deserialization: " + e.getMessage());
            e.printStackTrace();
            return null; // Return null to indicate an error
        }
    }


    public void setClientMainController(ClientMainController clientMainController) {
        this.clientMainController = clientMainController;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
