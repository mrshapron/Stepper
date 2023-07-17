package Flow.Definition;

public class InitialInputValueImpl implements InitialInputValue {
    private String inputName;
    private String initialValue;

    public InitialInputValueImpl(String inputName, String initialValue) {
        this.inputName = inputName;
        this.initialValue = initialValue;
    }

    @Override
    public String inputName() {return inputName; }

    @Override
    public String initialValue() {
        return initialValue;
    }
}
