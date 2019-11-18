package nl.michelbijnen;

import org.json.JSONObject;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;

public class UserRest implements IUserRest {
    @Override
    public Authentication login(String username, String password) {
        HashMap<String, String> bodyParameters = new HashMap<>();
        bodyParameters.put("username", username);
        bodyParameters.put("password", password);

        try {
            JSONObject jsonObject = new JSONObject(new RestEndPoint().sendRequest("", RestMethod.POST, "user", "login", null, null, bodyParameters));
            Authentication authentication = new Authentication(jsonObject.getString("publicKey"), jsonObject.getString("privateKey"));
            return authentication;
        } catch (RequestException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean logout(String privatekey, String userId) {
        HashMap<String, String> bodyParameters = new HashMap<>();
        bodyParameters.put("userId", userId);

        try {
            new RestEndPoint().sendRequest(privatekey, RestMethod.POST, "user", "logout", null, null, bodyParameters);
            return true;
        } catch (RequestException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean register(User user) {
        HashMap<String, String> bodyParameters = new HashMap<>();
        bodyParameters.put("email", user.getEmail());
        bodyParameters.put("firstName", user.getFirstName());
        bodyParameters.put("gender", user.getGender());
        bodyParameters.put("lastName", user.getLastName());
        bodyParameters.put("password", user.getPassword());
        bodyParameters.put("username", user.getUsername());

        try {
            new RestEndPoint().sendRequest("", RestMethod.POST, "user", "register", null, null, bodyParameters);
            return true;
        } catch (RequestException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(String privateKey, User user, String oldPassword) {
        HashMap<String, String> bodyParameters = new HashMap<>();
        bodyParameters.put("email", user.getEmail());
        bodyParameters.put("firstName", user.getFirstName());
        bodyParameters.put("gender", user.getGender());
        bodyParameters.put("lastName", user.getLastName());
        bodyParameters.put("oldPassword", oldPassword);
        bodyParameters.put("password", user.getPassword());
        bodyParameters.put("userId", user.getId());
        bodyParameters.put("username", user.getUsername());

        try {
            new RestEndPoint().sendRequest(privateKey, RestMethod.PUT, "user", "update", null, null, bodyParameters);
            return true;
        } catch (RequestException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addScore(String privateKey, String userId, int score) {
        HashMap<String, String> bodyParameters = new HashMap<>();
        bodyParameters.put("score", score + "");
        bodyParameters.put("userId", userId);

        try {
            new RestEndPoint().sendRequest(privateKey, RestMethod.POST, "user", "addScore", null, null, bodyParameters);
            return true;
        } catch (RequestException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean forgotPassword(String email) {
        HashMap<String, String> bodyParameters = new HashMap<>();
        bodyParameters.put("email", email);

        try {
            new RestEndPoint().sendRequest("", RestMethod.PUT, "user", "forgotpassword", null, null, bodyParameters);
            return true;
        } catch (RequestException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUser(String privateKey, String userId) {
        ArrayList<String> inlineParameters = new ArrayList<>();
        inlineParameters.add(userId);

        try {
            JSONObject jsonObject = new JSONObject(new RestEndPoint().sendRequest(privateKey, RestMethod.GET, "user", "getuser", inlineParameters, null, null));
            User user = new User();
            user.setId(jsonObject.getString("id"));
            user.setUsername(jsonObject.getString("username"));
            user.setPassword("");
            user.setFirstName(jsonObject.getString("firstName"));
            user.setLastName(jsonObject.getString("lastName"));
            user.setEmail(jsonObject.getString("email"));
            user.setScore(jsonObject.getInt("score"));
            user.setGender(jsonObject.getString("gender"));
            return user;
        } catch (RequestException e) {
            e.printStackTrace();
            return null;
        }
    }
}
