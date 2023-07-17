package Users;

import Users.Role.Role;

public class UserImpl implements User {
    private String username;
    private Role role;

    public UserImpl(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public Role role() {
        return role;
    }
}