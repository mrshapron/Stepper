package get;

import Flow.Definition.FlowDefinition;
import Flow.Execution.History.FlowHistoryData;
import Users.UserImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/get-free-inputs")
public class GetFreeInputsServlet extends HttpServlet {

    //Recieve the name of the flow, returns its free inputs in a json (TableViewModel)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String flowName = request.getParameter("flowName");
        FlowDefinition flow = null;
        synchronized(getServletContext()){
            List<FlowDefinition> flowDefinitions = (List<FlowDefinition>)getServletContext().getAttribute("flowDefinitions");
            //Finding user
            for (FlowDefinition flow1 : flowDefinitions){
                if (flow1.getName().equals(flowName)){
                    flow = flow1;
                }
            }

            //Have flow in hand


        }

    }






//    FlowHistoryData flowHistoryData = ...; // Obtain the FlowHistoryData object from somewhere
//    ObservableList<FreeInputsExecViewModel> freeInputsExecViewModels = FlowDefinitionToFreeInputsExecViewModelConverter.convert(flowHistoryData);

}
