package nl.michelbijnen;

import javax.xml.ws.Endpoint;
import java.util.HashMap;

public class UserDal implements IUserDal {
    public UserDal(IControllerLogic controllerLogic) {
        EndPoint.setLogic(controllerLogic);
        EndPoint.start();
    }

    @Override
    public void login(String username, String password) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("Username", username);
        parameters.put("Password", password);
        EndPoint.getInstance().sendMessage(false,"Login", parameters);
    }

    @Override
    public void register(User user) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("Username", user.getUsername());
        parameters.put("Password", user.getPassword());
        parameters.put("FirstName", user.getFirstName());
        parameters.put("LastName", user.getLastName());
        parameters.put("Email", user.getEmail());
        parameters.put("Gender", user.getGender());
        EndPoint.getInstance().sendMessage(false,"Register", parameters);
    }

    @Override
    public void update(User user, String currentPassword) {

    }

    @Override
    public void resetPassword(String username) {

    }

    @Override
    public void getUser(String userId) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("UserId", userId);
        EndPoint.getInstance().sendMessage(true, "GetUser", parameters);
    }

    @Override
    public void findMatch(String username, int score) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("Username", username);
        parameters.put("Score", score);
        EndPoint.getInstance().sendMessage(true, "FindMatch", parameters);
    }

    @Override
    public void cancelFindMatch() {
        HashMap<String, Object> parameters = new HashMap<>();
        EndPoint.getInstance().sendMessage(true, "CancelFindMatch", parameters);
    }
}
