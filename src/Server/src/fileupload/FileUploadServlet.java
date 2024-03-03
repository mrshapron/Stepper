package fileupload;

import BusinessLogic.StepperBusinessLogic;
import BusinessLogic.StepperBusinessLogicImpl;
import Flow.Definition.FlowDefinition;
import Flow.Definition.FlowDefinitionImpl;
import Flow.Definition.StepperDefinition;
import Flow.Definition.StepperDefinitionImpl;
import Users.Role.RoleImpl;
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


        //Get stepper business from servlet context:
        StepperBusinessLogic stepperBusinessLogic;
        List<FlowDefinition> my_list;
        synchronized(getServletContext()){
            stepperBusinessLogic = (StepperBusinessLogic) getServletContext().getAttribute("businessLogic");
            if (stepperBusinessLogic == null){
                stepperBusinessLogic = new StepperBusinessLogicImpl();
            }
            getServletContext().setAttribute("businessLogic", stepperBusinessLogic);
            my_list = stepperBusinessLogic.initializeStepperViaFile(filePart.getInputStream());
            List<FlowDefinition> flowDefinitions = (List<FlowDefinition>) getServletContext().getAttribute("flowDefinitions");
            if (flowDefinitions == null){
                flowDefinitions = new ArrayList<>();
            }
            getServletContext().setAttribute("flowDefinitions", flowDefinitions);
            flowDefinitions.addAll(my_list);
        }

        //Adding the flows to servletContext

    }

}
