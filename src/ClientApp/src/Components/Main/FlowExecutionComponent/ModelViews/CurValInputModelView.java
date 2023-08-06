package Components.Main.FlowExecutionComponent.ModelViews;

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

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public void setValueEntered(String valueEntered) {
        this.valueEntered = valueEntered;
    }
}

