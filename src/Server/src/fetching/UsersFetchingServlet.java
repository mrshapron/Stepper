package fetching;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Users.UserImpl;
import Users.Role.RoleImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/fetch-users")
public class UsersFetchingServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize your userList with some sample data
        RoleImpl role1 = new RoleImpl("role1", "Description 1");
        RoleImpl role2 = new RoleImpl("role2", "Description 2");
        UserImpl user1 = new UserImpl("user1", role1);
        UserImpl user2 = new UserImpl("user2", role2);

        synchronized (getServletContext()) {
            ServletContext servletContext = getServletContext();
            List<UserImpl> usersList = (List<UserImpl>) servletContext.getAttribute("usersList");
            List<RoleImpl> rolesList = (List<RoleImpl>) servletContext.getAttribute("rolesList");
            if (usersList == null) {
                usersList = new ArrayList<>();
                servletContext.setAttribute("usersList", usersList);
            }
            if (rolesList == null) {
                rolesList = new ArrayList<>();
                servletContext.setAttribute("rolesList", rolesList);
            }
            usersList.add(user1);
            usersList.add(user2);
            rolesList.add(role1);
            rolesList.add(role2);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();

        // Fetch the updated users from your data source based on the last fetched ID
        List<UserImpl> updatedUsers = fetchUpdatedUsers();

        // Convert the updated users to JSON
        String responseData = gson.toJson(updatedUsers);

        // Set the response data
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseData);

//        // Set the latest fetched ID in the response header
//        String latestFetchedId = getLatestFetchedId(updatedUsers);
//        response.setHeader("Latest-Fetched-ID", latestFetchedId);
    }

    private List<UserImpl> fetchUpdatedUsers() {
        // Fetch the entire list of users from your data source
        synchronized (getServletContext()) {
            List<UserImpl> userList = (List<UserImpl>) getServletContext().getAttribute("usersList");
            if (userList == null) {
                return new ArrayList<>(); // Return an empty list if the user list is not available
            }
            return new ArrayList<>(userList); // Return a new copy of the user list
        }
    }
}
