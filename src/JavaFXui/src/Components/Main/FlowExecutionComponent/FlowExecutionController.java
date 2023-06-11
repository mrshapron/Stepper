package Components.Main.FlowExecutionComponent;

import Components.Main.FlowDefinitionComponent.ModelViews.FreeInputsViewModel;
import Components.Main.FlowDefinitionComponent.ModelViews.TableViewFlowModel;
import Components.Main.FlowExecutionComponent.ModelView.CurValInputModelView;
import Components.Main.FlowExecutionComponent.ModelView.FreeInputsExecViewModel;
import Flow.Defenition.FlowDefinition;
import Flow.Defenition.FreeInputsDefinitionImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FlowExecutionController {

    @FXML
    private TableView<FreeInputsViewModel> tableFreeInputs;

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

    private BooleanProperty isAddValueEnable;

    private ObjectProperty<FlowDefinition> flowDefinitionObjectProperty;

    private StringProperty valueEnteredProperty;
    private ObservableList<CurValInputModelView> curValInputModelViews;

    public FlowExecutionController(){
        flowDefinitionObjectProperty = new SimpleObjectProperty<>(null);
    }

    public void initialize(){
        isAddValueEnable = new SimpleBooleanProperty(false);
        valueEnteredProperty = new SimpleStringProperty("");
        curValInputModelViews = FXCollections.observableArrayList();

        btnEnterValueInput.setOnAction(event -> onClickEnterInputValue(event));

        freeInputsInputNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        freeInputsNecessityColumn.setCellValueFactory(new PropertyValueFactory<>("necessity"));

        inputNameCurrentValCol.setCellValueFactory(new PropertyValueFactory<>("inputName"));
        valueEnteredCurrentValCol.setCellValueFactory(new PropertyValueFactory<>("valueEntered"));

        txtValueInput.textProperty().bindBidirectional(valueEnteredProperty);
        tableCurrentValues.setItems(curValInputModelViews);
    }

    public void setFlowDefinition(TableViewFlowModel flowDefinition){
        tableFreeInputs.setItems(flowDefinition.getFreeInputsViewModels());
    }


    public void onClickEnterInputValue(ActionEvent actionEvent) {
        FreeInputsViewModel selectedInput = tableFreeInputs.getSelectionModel().getSelectedItem();
        CurValInputModelView valInputModelView = new CurValInputModelView(selectedInput.getName(), valueEnteredProperty.getValue());
        curValInputModelViews.add(valInputModelView);
    }
}
