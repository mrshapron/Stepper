package Components.Main.FlowExecutionComponent;

import Components.Main.FlowDefinitionComponent.ModelViews.FreeInputsViewModel;
import Components.Main.FlowDefinitionComponent.ModelViews.TableViewFlowModel;
import Components.Main.FlowExecutionComponent.ModelView.CurValInputModelView;
import Components.Main.FlowExecutionComponent.ModelView.FreeInputsExecViewModel;
import Flow.Defenition.FlowDefinition;
import Flow.Defenition.FreeInputsDefinitionImpl;
import Step.DataNecessity;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlowExecutionController {

    @FXML private Label lblStatusFreeInput;
    @FXML private Label lblSelectedInput;
    @FXML private TableView<FreeInputsViewModel> tableFreeInputs;

    @FXML
    private TableColumn<FreeInputsViewModel, String> freeInputsInputNameColumn;

    @FXML
    private TableColumn<FreeInputsViewModel, String> freeInputsNecessityColumn;

    @FXML
    private TextField txtValueInput;

    @FXML
    private Button btnEnterValueInput;

    @FXML
    private Button btnClearInputValue;

    @FXML
    private TableView<CurValInputModelView> tableCurrentValues;

    @FXML
    private TableColumn<CurValInputModelView, String> inputNameCurrentValCol;

    @FXML
    private TableColumn<CurValInputModelView, String> valueEnteredCurrentValCol;

    @FXML
    private Button btnStartFlow;

    @FXML
    private Label lblFlowName;

    private FlowDefinition selectedFlow;
    private TableViewFlowModel currentTableViewModel;

    private BooleanProperty isAddValueEnable;

    private ObjectProperty<FlowDefinition> flowDefinitionObjectProperty;

    private StringProperty valueEnteredProperty;
    private ObservableList<CurValInputModelView> curValInputModelViews;
    private StringProperty currentSelectedFreeInput;
    private StringProperty statusRunFlowFreeInputs;
    private BooleanProperty isFlowCanRun;
    public FlowExecutionController(){
        flowDefinitionObjectProperty = new SimpleObjectProperty<>(null);
    }



    public void initialize(){
        isFlowCanRun = new SimpleBooleanProperty(false);
        isAddValueEnable = new SimpleBooleanProperty(false);
        valueEnteredProperty = new SimpleStringProperty("");
        curValInputModelViews = FXCollections.observableArrayList();
        curValInputModelViews.addListener((ListChangeListener<CurValInputModelView>) change -> onChangedCurValuesInputs(change));
        currentSelectedFreeInput = new SimpleStringProperty("Nothing");
        statusRunFlowFreeInputs = new SimpleStringProperty("Enter Mandatory Inputs To Run the Flow");
        btnEnterValueInput.setOnAction(event -> onClickEnterInputValue(event));
        btnClearInputValue.setOnAction(event -> onClearInputClick(event));
        freeInputsInputNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        freeInputsNecessityColumn.setCellValueFactory(new PropertyValueFactory<>("necessity"));
        lblSelectedInput.textProperty().bind(currentSelectedFreeInput);
        lblStatusFreeInput.textProperty().bind(statusRunFlowFreeInputs);
        inputNameCurrentValCol.setCellValueFactory(new PropertyValueFactory<>("inputName"));
        valueEnteredCurrentValCol.setCellValueFactory(new PropertyValueFactory<>("valueEntered"));
        txtValueInput.textProperty().bindBidirectional(valueEnteredProperty);
        tableCurrentValues.setItems(curValInputModelViews);
        btnStartFlow.disableProperty().bind(isFlowCanRun.not());
    }

    public void setFlowDefinition(TableViewFlowModel flowDefinition){
        tableFreeInputs.setItems(flowDefinition.getFreeInputsViewModels());
        this.currentTableViewModel = flowDefinition;
    }


    public void onClickEnterInputValue(ActionEvent actionEvent) {
        FreeInputsViewModel selectedInput = tableFreeInputs.getSelectionModel().getSelectedItem();
        CurValInputModelView valInputModelView = new CurValInputModelView(selectedInput.getName(), valueEnteredProperty.getValue());
        Optional<CurValInputModelView> selected = curValInputModelViews.stream().filter(curValInputModelView -> curValInputModelView.getInputName().equals(selectedInput.getName())).findFirst();
        if(selected.isPresent()){
            selected.get().setValueEntered(valueEnteredProperty.getValue());
            tableCurrentValues.refresh();
        }else{
            curValInputModelViews.add(valInputModelView);
        }
    }

    private void onChangedCurValuesInputs(ListChangeListener.Change<? extends CurValInputModelView> change) {
        while (change.next()) {
            if (isAllMandatoryInputsEntered()) {
                if(allInputsEntered())
                    statusRunFlowFreeInputs.set("You Can Start the flow. all inputs are set");
                else
                    statusRunFlowFreeInputs.set("You Can Start The flow or add more optional inputs");
                isFlowCanRun.set(true);
            } else {
                isFlowCanRun.set(false);
                statusRunFlowFreeInputs.set("Enter Mandatory Inputs To Run the Flow");
            }
        }
    }

    private boolean allInputsEntered() {
        List<FreeInputsViewModel> freeInputsViewModels = tableFreeInputs.getItems();
        List<CurValInputModelView> curValInputModelViews1 = tableCurrentValues.getItems();
        List<FreeInputsViewModel> enteredValueInputs = new ArrayList<>();
        freeInputsViewModels.forEach(freeInputs -> {
            if(curValInputModelViews1.stream().filter(curValInputModelView -> curValInputModelView.getInputName().equals(freeInputs.getName())).count() > 0){
                enteredValueInputs.add(freeInputs);
            }
        });
        List<FreeInputsViewModel> unenteredValueInputs = freeInputsViewModels.stream()
                .filter(freeInputsViewModel -> !enteredValueInputs
                        .contains(freeInputsViewModel)).collect(Collectors.toList());
        return unenteredValueInputs.size() == 0;
    }

    private boolean isAllMandatoryInputsEntered() {
        List<FreeInputsViewModel> freeInputsViewModels = tableFreeInputs.getItems();
        List<CurValInputModelView> curValInputModelViews1 = tableCurrentValues.getItems();
        List<FreeInputsViewModel> enteredValueInputs = new ArrayList<>();
        freeInputsViewModels.forEach(freeInputs -> {
            if(curValInputModelViews1.stream().filter(curValInputModelView -> curValInputModelView.getInputName().equals(freeInputs.getName())).count() > 0){
                enteredValueInputs.add(freeInputs);
            }
        });
        List<FreeInputsViewModel> unenteredValueInputs = freeInputsViewModels.stream()
                .filter(freeInputsViewModel -> !enteredValueInputs
                        .contains(freeInputsViewModel)).collect(Collectors.toList());
        if(unenteredValueInputs.stream().anyMatch(freeInputsViewModel -> freeInputsViewModel.getNecessity().equals(DataNecessity.MANDATORY.name())))
            return false;
        return true;

    }

    public void onClickTableFreeInputs(MouseEvent mouseEvent) {
        FreeInputsViewModel selectedInput = tableFreeInputs.getSelectionModel().getSelectedItem();
        currentSelectedFreeInput.set(selectedInput.getName());
        Optional<CurValInputModelView> selected = curValInputModelViews.stream().filter(curValInputModelView -> curValInputModelView.getInputName().equals(selectedInput.getName())).findFirst();
        if(selected.isPresent()){
            valueEnteredProperty.set(selected.get().getValueEntered());
        }else{
            valueEnteredProperty.set("");
        }
    }

    public void onClearInputClick(ActionEvent actionEvent) {
        FreeInputsViewModel selectedInput = tableFreeInputs.getSelectionModel().getSelectedItem();
        Optional<CurValInputModelView> selected = curValInputModelViews.stream().filter(curValInputModelView -> curValInputModelView.getInputName().equals(selectedInput.getName())).findFirst();
        if(selected.isPresent()){
            curValInputModelViews.remove(selected.get());
        }
        valueEnteredProperty.set("");
    }
}
