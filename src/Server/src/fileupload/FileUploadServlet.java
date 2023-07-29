package fileupload;

import BusinessLogic.StepperBusinessLogic;
import BusinessLogic.StepperBusinessLogicImpl;
import Flow.Definition.FlowDefinition;
import Flow.Definition.FlowDefinitionImpl;
import Flow.Definition.StepperDefinition;
import Flow.Definition.StepperDefinitionImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import javax.swing.text.TableView;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

@WebServlet("/upload-file")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("Hello world from upload file");
    }

    @Override //Synchronize?
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        Part filePart = request.getPart("file");

        StepperBusinessLogic stepperBusinessLogic = new StepperBusinessLogicImpl();
        List<FlowDefinition> my_list = stepperBusinessLogic.initializeStepperViaFile(filePart.getInputStream());

        //Adding the flows to servletContext
        synchronized(getServletContext()) {
            ServletContext servletContext = getServletContext();
            List<FlowDefinition> flowDefinitions = (List<FlowDefinition>) servletContext.getAttribute("flowDefinitions");

            // If the list doesn't exist, create a new one
            if (flowDefinitions == null) {
                servletContext.setAttribute("flowDefinitions", my_list);
                //Also need to return roles
            } else
                flowDefinitions.addAll(my_list);
        }
    }

}
