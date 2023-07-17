package roles;

import BusinessLogic.StepperBusinessLogic;
import BusinessLogic.StepperBusinessLogicImpl;
import Flow.Definition.FlowDefinition;
import Flow.Definition.StepperDefinition;
import Flow.Definition.StepperDefinitionImpl;
import Users.Role.Role;
import Users.Role.RoleImpl;
import Users.User;
import Users.UserImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

@WebServlet("/add-role")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class AddRoleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("Hello world from upload file");
    }

    @Override //Synchronize?
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        BufferedReader reader = request.getReader();
        RoleImpl role = gson.fromJson(reader, RoleImpl.class);
        out.println(reader);
        reader.close();

        ServletContext servletContext = getServletContext();
        List<Role> rolesList = (List<Role>) servletContext.getAttribute("rolesList");
        if (rolesList == null){
            rolesList = new ArrayList<Role>();
            servletContext.setAttribute("rolesList", rolesList);
        }
        rolesList.add(role);
        out.println(role.name());
        out.println(role.description());
    }
}
