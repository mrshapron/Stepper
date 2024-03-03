package LogIn;
import Users.Role.RoleImpl;
import Users.User;
import Users.UserImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/log-in")
public class LogInServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the "username" parameter from the HTTP request
        String username = request.getParameter("username");
        boolean ok = true;
        if (username == null)
            username = "There's a problem";
        synchronized(getServletContext()) {
            ServletContext servletContext = getServletContext();
            List<RoleImpl> rolesList = (List<RoleImpl>) servletContext.getAttribute("rolesList");
            RoleImpl defaultRole = getDefaultRole(rolesList);

            // Create a new User object with the retrieved username
            UserImpl user = new UserImpl(username, defaultRole);
            List<UserImpl> usersList = (List<UserImpl>) servletContext.getAttribute("usersList");
            if (usersList == null) {
                usersList = new ArrayList<>();
                servletContext.setAttribute("usersList", usersList);
            }
            else {
                for (UserImpl user1 : usersList){
                    if (user1.getUsername().equals(username))
                        ok = false;
                }
            }
            if (ok)
                usersList.add(user);
        }
        // Redirect the user to the main application page
        if (ok) {
            response.getWriter().print("Components/Main/ClientMain.fxml");
        }
        else
            response.getWriter().print("a");
    }

    private static RoleImpl getDefaultRole(List<RoleImpl> rolesList) {
        for ( RoleImpl role : rolesList){
            if (role.name().equals("Read Only Flows"))
                return role;
        }
        //!!!!NEED TO CHANGE!!!!
        return new RoleImpl("Problem Role", "Problem!");
    }
}
