package Users;

import Users.Role.Role;
import Users.Role.RoleImpl;

import java.util.List;

public interface User {
    String getUsername();
    List<RoleImpl> getRoles();
    Boolean isManager();
}