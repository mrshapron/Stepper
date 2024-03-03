package Components.Main.FlowExecutionComponent.ModelViews;

public class FreeInputsTableView {
    private String aliasName;
    private String necessity;
    private Class<?> type;

    // Getter and Setter for AliasName
    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    // Getter and Setter for Neccessity (typo in the field name, should be "necessity")
    public String getNecessity() {
        return necessity;
    }

    public void setNecessity(String necessity) {
        this.necessity = necessity;
    }

    // Getter and Setter for type
    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
