package fetching;


import Flow.Definition.FlowDefinition;
import Flow.Execution.History.FlowHistoryData;
import Users.Role.Role;
import Users.UserImpl;
import convertion3.FlowHistoryDataToFlowExecutionModelViewConvertor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.SyncFailedException;
import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/fetch-history")
public class HistoryFetchingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); //Check, might leave it like that
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
        List<FlowHistoryData> userHistoryList = new ArrayList<>();
        if (user.isManager()) {
            synchronized (getServletContext()) {
                List<FlowHistoryData> historyList = (List<FlowHistoryData>) getServletContext().getAttribute("history");
                for (FlowDefinition flow : updatedFlows) {
                    for (FlowHistoryData historyData : historyList) {
                        if (flow.getName().equals(historyData.getFlowName()))
                            userHistoryList.add(historyData);
                    }
                }
            }
        }
        else{
            Map<String, List<FlowHistoryData>> historyMap;
            synchronized (getServletContext()){
                historyMap = (Map<String, List<FlowHistoryData>>) getServletContext().getAttribute("userToHistory");
                if (historyMap != null) {
                    List<FlowHistoryData> historyMapList = historyMap.get(username);
//                    for (FlowDefinition flow : updatedFlows) {
//                        for(FlowHistoryData historyData : historyMapList){
//                            if (flow.getName().equals(historyData.getFlowName()))
//                                userHistoryList.add(historyData);
//                        }
//                    }
                    for (FlowHistoryData flowHistoryData : historyMapList){
                        userHistoryList.add(flowHistoryData);
                    }
                }
            }
        }

        List<String> responseData = new ArrayList<>();
        for (FlowHistoryData flowHistoryData : userHistoryList){
            responseData.add(FlowHistoryDataToFlowExecutionModelViewConvertor.convert(flowHistoryData));
        }

        String jsonResponse = "[" + String.join(",", responseData) + "]";//?

        // Set the response data
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
//        response.getWriter().write(responseData.get(0));
    }

    private List<FlowDefinition> fetchUpdatedFlows(UserImpl user) {
        // Fetch the entire list of users from your data source
        synchronized (getServletContext()) {
            List<FlowDefinition> flowDefinitions = (List<FlowDefinition>) getServletContext().getAttribute("flowDefinitions");
            List<FlowDefinition> usersFlowDefinitions = new ArrayList<>();
            if (flowDefinitions == null) {
                return usersFlowDefinitions;
            }
            if (user.isManager()){
                for (FlowDefinition flow : flowDefinitions) {
                    usersFlowDefinitions.add(flow);
                }
                return usersFlowDefinitions;
            }
            else {
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
