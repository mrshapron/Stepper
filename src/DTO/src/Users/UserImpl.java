package Users;

import Users.Role.Role;
import Users.Role.RoleImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserImpl implements User {
    private String username;
    private List<RoleImpl> roles;
    private Boolean manager;

    public UserImpl(String username, List<RoleImpl> roles) {
        this.username = username;
        this.roles = new ArrayList<>();
        this.roles.addAll(roles);
        manager = false;
    }

    public UserImpl(String username, RoleImpl role){
        this.username = username;
        this.roles = new ArrayList<>();
        roles.add(role);
        manager = false;
    }

    public UserImpl(String username, List<RoleImpl> roles, Boolean isManager){
        this.username = username;
        this.roles = new ArrayList<>();
        this.roles.addAll(roles);
        this.manager = isManager;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public List<RoleImpl> getRoles() {
        return roles;
    }

    @Override
    public Boolean isManager() {
        return manager;
    }

    public void setUsername(String username){this.username = username;}
    public void setRoles(List<RoleImpl> roles){this.roles = roles;}

    public void setManager(Boolean isManager){
        manager = isManager;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        UserImpl otherUser = (UserImpl) obj;
        return this.getUsername().equals(otherUser.getUsername()) &&
                this.getRoles().equals(otherUser.getRoles()) &&
                this.isManager().equals(otherUser.isManager());
    }




}