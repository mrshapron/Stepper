package change;

import Users.Role.RoleImpl;
import Users.UserImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import deserializer.RoleDeserializer;
import deserializer.UserDeserializer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/change-user")
public class ChangeUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read the JSON data from the request
        String jsonData = request.getReader().lines().collect(Collectors.joining());
        // Deserialize the JSON data to MyObject using Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RoleImpl.class, new RoleDeserializer());
        gsonBuilder.registerTypeAdapter(UserImpl.class, new UserDeserializer());

        // Create the Gson object
        Gson gson = gsonBuilder.create();

        // Now you can use Gson to deserialize the JSON data into UserImpl objects
        UserImpl user = gson.fromJson(jsonData, UserImpl.class);

        synchronized (getServletContext()){
            List<UserImpl> usersList = (List<UserImpl>)getServletContext().getAttribute("usersList");
            for (UserImpl user_from_list : usersList){
                if (user.getUsername().equals(user_from_list.getUsername()))
                {
                    user_from_list.setManager(user.isManager());
                    user_from_list.setRoles(user.getRoles());
                    //Still need to check if works properly
                }
            }
        }
    }


//    private UserImpl processFetcheduserData(String responseData) {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(UserImpl.class, new UserDeserializer());
//        Gson gson = gsonBuilder.create();
//
//        Type userType = new TypeToken<UserImpl>() {}.getType();
//        return gson.fromJson(responseData, userType);
//    }
}
