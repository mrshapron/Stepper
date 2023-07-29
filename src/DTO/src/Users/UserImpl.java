package Users;

import Users.Role.Role;
import Users.Role.RoleImpl;

import java.util.ArrayList;
import java.util.List;

public class UserImpl implements User {
    private String username;
    private List<Role> roles;

    public UserImpl(String username, List<RoleImpl> roles) {
        this.username = username;
        this.roles = new ArrayList<>();
        for (RoleImpl role : roles){
            roles.add(role);
        }
    }

    public UserImpl(String username, RoleImpl role){
        this.username = username;
        this.roles = new ArrayList<>();
        roles.add(role);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        UserImpl otherUser = (UserImpl) obj;
        return this.getUsername().equals(otherUser.getUsername());
    }
}