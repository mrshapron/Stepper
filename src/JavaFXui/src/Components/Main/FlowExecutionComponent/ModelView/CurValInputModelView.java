package Components.Main.FlowExecutionComponent.ModelView;

public class CurValInputModelView {
    private String inputName;
    private String valueEntered;

    public CurValInputModelView(String inputName, String valueEntered){
        this.inputName = inputName;
        this.valueEntered = valueEntered;
    }

    public String getInputName() {
        return inputName;
    }

    public String getValueEntered() {
        return valueEntered;
    }
}
