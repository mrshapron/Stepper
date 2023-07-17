package Users;

import Users.Role.Role;

public interface User {
    String username();
    Role role();
}