package rolesManagementTab.modelView;

public class RoleModelView {
    private String name;
    private String number_of_flows;

    public RoleModelView(String name, String number_of_flows){
        this.name = name;
        this.number_of_flows = number_of_flows;
    }



    public String getName() {
        return name;
    }

    public String getNumberOfFlows() {
        return number_of_flows;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfFlows(String valueEntered) {
        this.number_of_flows = number_of_flows;
    }


}