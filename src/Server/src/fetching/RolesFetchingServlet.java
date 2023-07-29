package fetching;

import Users.Role.RoleImpl;
import Users.UserImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/fetch-roles")
public class RolesFetchingServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize your userList with some sample data
        RoleImpl role1 = new RoleImpl("Read Only Flows", "Have access to all Read-only flows");
        RoleImpl role2 = new RoleImpl("All Flows", "Have access to all of the flows");
        synchronized(getServletContext()) {
            ServletContext servletContext = getServletContext();
            List<RoleImpl> rolesList = (List<RoleImpl>) servletContext.getAttribute("rolesList");
            if (rolesList == null) {
                rolesList = new ArrayList<>();
                servletContext.setAttribute("rolesList", rolesList);
                //Also need to return roles
            }
            rolesList.add(role1);
            rolesList.add(role2);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();

        // Fetch the updated users from your data source based on the last fetched ID
        List<RoleImpl> updatedRoles = fetchUpdatedRoles();

        // Convert the updated users to JSON
        String responseData = gson.toJson(updatedRoles);

        // Set the response data
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseData);

//        // Set the latest fetched ID in the response header
//        String latestFetchedId = getLatestFetchedId(updatedUsers);
//        response.setHeader("Latest-Fetched-ID", latestFetchedId);
    }

    private List<RoleImpl> fetchUpdatedRoles() {
        // Fetch the entire list of users from your data source
        synchronized(getServletContext()) {
            List<RoleImpl> rolesList = (List<RoleImpl>) getServletContext().getAttribute("rolesList");
            if (rolesList == null) {
                return new ArrayList<>(); // Return an empty list if the user list is not available
            }
            return new ArrayList<>(rolesList); // Return a new copy of the user list
        }
    }
}
