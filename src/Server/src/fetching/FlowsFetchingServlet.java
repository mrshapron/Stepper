package fetching;

import Flow.Definition.FlowDefinition;
import FlowDefinitionConverter.FlowDefinitionToTableViewFlowModelConverter;
import Users.Role.Role;
import Users.Role.RoleImpl;
import Users.UserImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.swing.text.TableView;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/fetch-flows")
public class FlowsFetchingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();
        PrintWriter out = response.getWriter();
        Cookie[] cookies = request.getCookies();
        String username = null;
    // Fetch the updated users from your data source based on the last fetched ID
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }
        username = request.getParameter("username"); //Check, might leave it like that
        List<FlowDefinition> updatedFlows;
        UserImpl user = getUserViaUsername(username);
        if (user != null)
            updatedFlows = fetchUpdatedFlows(user);
        else{
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/plain");
            response.getWriter().write("An error occurred: looking for username that doesn't exist!");
            return;
        }




        List<String> responseData = new ArrayList<>();

        // Convert the updated users to JSON
        for (FlowDefinition flow : updatedFlows) {
            responseData.add(FlowDefinitionToTableViewFlowModelConverter.convert(flow));
        }

        String jsonResponse = "[" + String.join(",", responseData) + "]";//?
        System.out.println(jsonResponse);

        // Set the response data
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);

//        // Set the latest fetched ID in the response header
//        String latestFetchedId = getLatestFetchedId(updatedUsers);
//        response.setHeader("Latest-Fetched-ID", latestFetchedId);
}

    private List<FlowDefinition> fetchUpdatedFlows(UserImpl user) {
        // Fetch the entire list of users from your data source
        synchronized (getServletContext()) {
            List<FlowDefinition> flowDefinitions = (List<FlowDefinition>) getServletContext().getAttribute("flowDefinitions");
            List<FlowDefinition> usersFlowDefinitions = new ArrayList<>();
            if (flowDefinitions == null) {
                return usersFlowDefinitions;
            }
            for (Role userRole : user.getRoles()) {
                for (String userFlow : userRole.availableFlows()) {
                    for (FlowDefinition flow : flowDefinitions) {
                        if (userFlow.equals(flow.getName()) && !usersFlowDefinitions.contains(flow)) {
                            usersFlowDefinitions.add(flow);
                        }
                    }
                }
            }
            return usersFlowDefinitions; // Return a new copy of the user list
        }
    }

    private UserImpl getUserViaUsername (String username){
        synchronized(getServletContext()) {
            List<UserImpl> usersList = (List<UserImpl>) getServletContext().getAttribute("usersList");
            for (UserImpl user : usersList) {
                if (username.equals(user.getUsername()))
                    return user;
            }
            return null;
        }
    }
}
