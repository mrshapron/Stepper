package Users;

import Users.Role.Role;

import java.util.List;

public interface User {
    String getUsername();
    List<Role> getRoles();
}