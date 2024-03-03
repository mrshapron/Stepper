package run;

import BusinessLogic.ProgressCallback;
import BusinessLogic.StepperBusinessLogic;
import Flow.Definition.FlowDefinition;
import Flow.Execution.History.FlowHistoryData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import convertion3.FlowHistoryDataToFlowExecutionModelViewConvertor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.security.cert.PKIXCertPathBuilderResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@WebServlet("/run-flow")
public class RunFlowServlet extends HttpServlet {

    StepperBusinessLogic businessLogic;
    FlowDefinition flow;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            BufferedReader reader = request.getReader();
            PrintWriter out = response.getWriter();

            // Parse the JSON response into a Map<String, String>
            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> inputsMap = new Gson().fromJson(reader, mapType);

            String flowName = request.getParameter("flowName");
            String username = request.getParameter("username");
            if (username==null)
                username = "There's a problem";
            flow = null;
            synchronized (getServletContext()) {
                businessLogic = (StepperBusinessLogic) getServletContext().getAttribute("businessLogic");
                List<FlowDefinition> flowsList = (List<FlowDefinition>) getServletContext().getAttribute("flowDefinitions");
                for (FlowDefinition flow1 : flowsList) {
                    if (flowName.equals(flow1.getName())) {
                        flow = flow1;
                    }
                }
            }

//            if (flow != null) {
//                Task<FlowHistoryData> task = new Task<FlowHistoryData>() {
//                    @Override
//                    protected FlowHistoryData call() throws Exception {
//                        flowHistoryData = businessLogic.startFlow(flow, inputsMap, progress -> {
//                            updateProgress(progress, 100);
//                        });
//                        return flowHistoryData;
//                    }
//                };


//                task.setOnSucceeded(event -> {
//                    FlowHistoryData result = task.getValue();
//                    String flowNameFromTask = result.getFlowName();
//
//                    // Perform UI-related updates on the JavaFX Application Thread
//                    Platform.runLater(() -> {
//                    System.out.println(flowNameFromTask);
//                    // Update your JavaFX UI components here
//                    // For example, set a label's text, update a progress bar, etc.
//                });
//            });


            FlowHistoryData result = null;
            if (flow != null) {
                Callable<FlowHistoryData> callable = () -> {
                    FlowHistoryData flowHistoryData = businessLogic.startFlow(flow, inputsMap, progress -> {
                        // Simulate progress updates (replace with your actual logic)
                        System.out.println("Progress: " + progress);
                    });
                    return flowHistoryData;
                };

                // Create an ExecutorService
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<FlowHistoryData> future = executor.submit(callable);

                // Continue with other servlet operations
                // Retrieve the result of the task when it's available
                try {
                    result = future.get();
//                    out.println("Task result: " + result);
                    List<FlowHistoryData> historyList;
                    Map<String, List<FlowHistoryData>> userFlowHistoryDataMap;
                    List<FlowHistoryData> newList;
                    //Also need to know who run this flow!
                    //Map<String (username), List<FlowHistoryData> (user's FlowHistoryData)!

                    //populate the tableviews somehow!
                    synchronized (getServletContext()){
                        historyList = (List<FlowHistoryData>) getServletContext().getAttribute("history");
                        userFlowHistoryDataMap = (Map<String, List<FlowHistoryData>>) getServletContext().getAttribute("userToHistory");
                        if (historyList == null) {
                            historyList = new ArrayList<>();
                            getServletContext().setAttribute("history", historyList);
                        }
                        historyList.add(result);
                        if (userFlowHistoryDataMap == null) {
                            //There's no map..
                            userFlowHistoryDataMap = new HashMap<>();
                            newList = new ArrayList<>();
                            getServletContext().setAttribute("userToHistory", userFlowHistoryDataMap);
                            newList.add(result);
                            userFlowHistoryDataMap.put(username, newList);
                        }
                        else{
                            getServletContext().setAttribute("userToHistory", userFlowHistoryDataMap);
                            newList = userFlowHistoryDataMap.get(username);
                            if (newList != null) {
                                //not first time this user is using this map
                                newList.add(result);
                                userFlowHistoryDataMap.put(username, newList);
                            }
                            else {
                                //First time this user is using the map
                                newList = new ArrayList<>();
                                newList.add(result);
                                userFlowHistoryDataMap.put(username, newList);
                            }
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                // Shut down the executor
                executor.shutdown();


            // Create a thread to run the FutureTask
//                Thread thread = new Thread(futureTask);
//                thread.start();
//            businessLogic.executeTask(task);

//            System.out.println("BREAK");
            } else {
                System.out.println("Problem");
                return;
            }

//            System.out.println(flowHistoryData.getFlowName());

            // Set an attribute on the request to indicate the process is finished
//            request.setAttribute("finish", "finish");

            //Populate information to send back to user!
            String responseData;
            if (result != null)
                responseData = FlowHistoryDataToFlowExecutionModelViewConvertor.convert(result);
            else
                responseData = "";

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(responseData);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            out.println("Error processing JSON response");
            out.flush();
        }
    }
}
