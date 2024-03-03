package fetching;

import Flow.Definition.FlowDefinition;
import Users.Role.Role;
import Users.Role.RoleImpl;
import Users.UserImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import convertion.FlowDefinitionToTableViewFlowModelConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static convertion.FlowDefinitionToTableViewFlowModelConverter.*;

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
        if (user != null) {
            user = updateUsers(user);
            updatedFlows = fetchUpdatedFlows(user);
        }
        else{
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/plain");
            response.getWriter().write("An error occurred: looking for username that doesn't exist!");
            return;
        }




        List<String> responseData = new ArrayList<>();

        // Convert the updated users to JSON
        for (FlowDefinition flow : updatedFlows) {
            responseData.add(convert(flow));
        }

        String jsonResponse = "[" + String.join(",", responseData) + "]";//?=

        // Set the response data
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);

//        // Set the latest fetched ID in the response header
//        String latestFetchedId = getLatestFetchedId(updatedUsers);
//        response.setHeader("Latest-Fetched-ID", latestFetchedId);
}

    private UserImpl updateUsers(UserImpl user) {
        UserImpl userToReturn = null;
        synchronized (getServletContext()){
            List<UserImpl> usersList = (List<UserImpl>) getServletContext().getAttribute("usersList");
            List<RoleImpl> rolesList = (List<RoleImpl>) getServletContext().getAttribute("rolesList");
            getServletContext().setAttribute("usersList", usersList);
            //not good for changing role
            for (UserImpl userFromList : usersList){
                for(RoleImpl role : rolesList){
                    for (RoleImpl userRole : userFromList.getRoles()) {
                        if (role.name().equals(userRole.name())){
                            for(String flow : role.availableFlows()){
                                if (!userRole.availableFlows().contains(flow))
                                    userRole.addFlow(flow);
                                //Need to remove flow from role aswell..
                            }
                        }
                    }
                }
                if (user.getUsername().equals(userFromList.getUsername()))
                    userToReturn = userFromList;
            }
        }
        return userToReturn;
    }


    private List<FlowDefinition> fetchUpdatedFlows(UserImpl user) {
        // Fetch the entire list of users from your data source
        synchronized (getServletContext()) {
            List<FlowDefinition> flowDefinitions = (List<FlowDefinition>) getServletContext().getAttribute("flowDefinitions");
            List<FlowDefinition> usersFlowDefinitions = new ArrayList<>();
            if (flowDefinitions == null) {
                return usersFlowDefinitions;
            }
            if (!user.isManager()) {
                for (Role userRole : user.getRoles()) {
                    for (String userFlow : userRole.availableFlows()) {
                        for (FlowDefinition flow : flowDefinitions) {
                            if ((userFlow.equals(flow.getName()) && !usersFlowDefinitions.contains(flow)) || (user.isManager() && !usersFlowDefinitions.contains(flow))) {
                                usersFlowDefinitions.add(flow); //Here is the problem
                            }
                        }
                    }
                }
            }
            else {
                for (FlowDefinition flow : flowDefinitions) {
                    usersFlowDefinitions.add(flow);
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
