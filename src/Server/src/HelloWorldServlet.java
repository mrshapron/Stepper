

import Flow.Definition.FlowDefinition;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletContext;

public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        ServletContext servletContext = getServletContext();
        for (FlowDefinition flowDefinition : (List<FlowDefinition>) servletContext.getAttribute("flowDefinitions")){
            out.println(flowDefinition.getName());
        }
    }
}
