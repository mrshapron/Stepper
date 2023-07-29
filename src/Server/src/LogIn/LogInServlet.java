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
            if (user.getUsername().equals("Sharon")){
                user.getRoles().get(0).addFlow("Step number 3");
                user.getRoles().get(0).addFlow("Rename Files");
            }
            usersList.add(user);
        }
        // Redirect the user to the main application page
        response.sendRedirect("Components.Main.ClientMain.fxml");
    }

    private static RoleImpl getDefaultRole(List<RoleImpl> rolesList) {
        for ( RoleImpl role : rolesList){
            if (role.name().equals("Read Only Flows"))
                return role;
        }
        //!!!!NEED TO CHANGE!!!!
        return new RoleImpl("Role3", "Description3");
//        return null;
    }
}
