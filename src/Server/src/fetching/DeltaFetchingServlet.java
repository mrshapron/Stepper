package fetching;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Users.User;
import Users.UserImpl;
import Users.Role.Role;
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
import jakarta.servlet.http.Part;

@WebServlet("/delta-fetching")
public class DeltaFetchingServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize your userList with some sample data
        RoleImpl role1 = new RoleImpl("role1", "Description 1");
        RoleImpl role2 = new RoleImpl("role2", "Description 2");
        UserImpl user1 = new UserImpl("user1", role1);
        UserImpl user2 = new UserImpl("user2", role2);


        ServletContext servletContext = getServletContext();
        List<UserImpl> usersList = (List<UserImpl>) servletContext.getAttribute("usersList");
        if (usersList == null) {
            usersList = new ArrayList<>();
            servletContext.setAttribute("usersList", usersList);
            //Also need to return roles
        }
        usersList.add(user1);
        usersList.add(user2);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();
        String lastFetchedId = request.getParameter("lastFetchedId");

        // Fetch the updated users from your data source based on the last fetched ID
        List<UserImpl> updatedUsers = fetchUpdatedUsers(lastFetchedId);

        // Convert the updated users to JSON
        String responseData = gson.toJson(updatedUsers);

        // Set the response data
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseData);

        // Set the latest fetched ID in the response header
        String latestFetchedId = getLatestFetchedId(updatedUsers);
        response.setHeader("Latest-Fetched-ID", latestFetchedId);
    }

    private List<UserImpl> fetchUpdatedUsers(String lastFetchedId) {
        // Fetch the updated users from your data source based on lastFetchedId
        // You can use the lastFetchedId to determine which users have been updated or added since the last fetch

        // Example implementation: Assuming userList is your source of truth
        List<UserImpl> updatedUsers = new ArrayList<>();
        List<UserImpl> userList = (List<UserImpl>) getServletContext().getAttribute("usersList");
        if (userList != null) {
            for (UserImpl user : userList) {
                // Add users whose IDs are greater than lastFetchedId
                if (user.username().compareTo(lastFetchedId) > 0) {
                    updatedUsers.add(user);
                }
            }
        }
        return updatedUsers;
    }

    private String getLatestFetchedId(List<UserImpl> updatedUsers) {
        // Determine the latest fetched ID based on the updated users
        // You can sort the updatedUsers list based on the ID and return the highest ID as the latest fetched ID
        // Here's a simple example assuming User objects have an "id" property:
        String latestFetchedId = "0"; // Assuming usernames are alphanumeric
        for (User user : updatedUsers) {
            String username = user.username();
            if (username.compareTo(latestFetchedId) > 0) {
                latestFetchedId = username;
            }
        }
        return latestFetchedId;
    }
}
