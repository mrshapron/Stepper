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
    }

    private List<UserImpl> fetchUpdatedUsers() {
        // Fetch the entire list of users from your data source
        synchronized (getServletContext()) {
            List<UserImpl> userList = (List<UserImpl>) getServletContext().getAttribute("usersList");
            if (userList == null) {
                return new ArrayList<>(); // Return an empty list if the user list is not available
            }
            // Create a new list and copy all the users to avoid synchronization issues
            List<UserImpl> updatedUsers = new ArrayList<>(userList);
            return updatedUsers;
        }
    }
}
