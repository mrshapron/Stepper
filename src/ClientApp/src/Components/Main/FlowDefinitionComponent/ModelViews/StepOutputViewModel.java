package Components.Main.FlowDefinitionComponent.ModelViews;


public class StepOutputViewModel {
    private String name;
    private String connected;
    private String toInput;

    private static final String  freeOutputLabel = "Free Output";

    public StepOutputViewModel(String name, String connected, String toInput) {
        this.name = name;
        this.connected = connected;
        this.toInput = toInput;
    }


    public String getName() {
        return name;
    }

    public String getConnected() {
        return connected;
    }

    public String getToInput() {
        return toInput;
    }
}
