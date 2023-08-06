//package get;
//
//import Flow.Definition.FlowDefinition;
//import Flow.Definition.FreeInputsDefinition;
//import Flow.Execution.History.FlowHistoryData;
//import Users.UserImpl;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static convertion2.FlowDefinitionToFreeInputsTableView.*;
//
//@WebServlet("/get-freeinputs")
//public class GetFreeInputsServlet extends HttpServlet {
//
//    //Recieve the name of the flow, returns its free inputs in a json (TableViewModel)
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String flowName = request.getParameter("flowName");
//        FlowDefinition flow = null;
//        synchronized(getServletContext()){
//            List<FlowDefinition> flowDefinitions = (List<FlowDefinition>)getServletContext().getAttribute("flowDefinitions");
//            //Finding user
//            for (FlowDefinition flow1 : flowDefinitions){
//                if (flow1.getName().equals(flowName)){
//                    flow = flow1;
//                }
//            }
//            if (flow == null)
//                return;
//
//            //Have flow in hand
//
//
//            List<String> responseData = new ArrayList<>();
//            // Convert the updated users to JSON
//            for (FreeInputsDefinition freeInput : flow.getFlowFreeInputs()) {
//                responseData.add(convert(freeInput));
//            }
//
//            String jsonResponse = "[" + String.join(",", responseData) + "]";//?
//            System.out.println(jsonResponse);
//
//            // Set the response data
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            response.getWriter().write(jsonResponse);
//        }
//
//    }
//
//
//
//
//
//
////    FlowHistoryData flowHistoryData = ...; // Obtain the FlowHistoryData object from somewhere
////    ObservableList<FreeInputsExecViewModel> freeInputsExecViewModels = FlowDefinitionToFreeInputsExecViewModelConverter.convert(flowHistoryData);
//
//}
//


package get;

        import Flow.Definition.FlowDefinition;
        import Flow.Definition.FreeInputsDefinition;
        import convertion2.FlowDefinitionToFreeInputsTableView;
        import jakarta.servlet.ServletException;
        import jakarta.servlet.annotation.WebServlet;
        import jakarta.servlet.http.HttpServlet;
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpServletResponse;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

        import static convertion2.FlowDefinitionToFreeInputsTableView.convertToJSON;


@WebServlet("/get-freeinputs")
public class GetFreeInputsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String flowName = request.getParameter("flowName");
        FlowDefinition flow = null;
        synchronized (getServletContext()) {
            List<FlowDefinition> flowDefinitions = (List<FlowDefinition>) getServletContext().getAttribute("flowDefinitions");
            for (FlowDefinition flow1 : flowDefinitions) {
                if (flow1.getName().equals(flowName)) {
                    flow = flow1;
                    break; // Exit loop since we found the flow
                }
            }
            if (flow == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            // Inside GetFreeInputsServlet doPost method
            List<FreeInputsDefinition> freeInputsList = flow.getFlowFreeInputs();
            String jsonResponse = convertToJSON(freeInputsList);
//            System.out.println(jsonResponse);

            // Set the response data
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        }
    }
}

