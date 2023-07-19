package usersManagementTab.ViewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserModelView {
    private final StringProperty username;
    private final StringProperty numberOfRoles;
    private final StringProperty manager;

    public UserModelView(String username, String numberOfRoles, String manager) {
        this.username = new SimpleStringProperty(username);
        this.numberOfRoles = new SimpleStringProperty(numberOfRoles);
        this.manager = new SimpleStringProperty(manager);
    }

    // Getters and Setters for properties
    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getNumberOfRoles() {
        return numberOfRoles.get();
    }

    public StringProperty numberOfRolesProperty() {
        return numberOfRoles;
    }

    public void setNumberOfRoles(String numberOfRoles) {
        this.numberOfRoles.set(numberOfRoles);
    }

    public String getManager() {
        return manager.get();
    }

    public StringProperty managerProperty() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager.set(manager);
    }
}