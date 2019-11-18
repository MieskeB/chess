package nl.michelbijnen;

import com.sun.org.apache.bcel.internal.generic.JsrInstruction;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.json.JSONObject;

import java.awt.*;

public class ControllerLogic implements IControllerLogic {
    private IController controller;

    public ControllerLogic(IController controller) {
        this.controller = controller;
    }

    @Override
    public void response(JSONObject response) {
        switch (response.getString("Method")) {
            case "Error":
                this.error();
                break;
            case "Login":
                this.login(response);
                break;
            case "Register":
                this.register(response);
                break;
            case "GetUser":
                this.getUser(response);
                break;
            case "FindMatch":
                this.findMatch(response);
                break;
            case "Chess":
                this.moveOpponentChessPiece(response);
        }
    }

    private void error() {
        this.controller.error();
    }

    private void login(JSONObject response) {
        String publicKey = response.getString("PublicKey");
        if (!publicKey.equals("0")) {
            Session.getInstance().setLoggedInUserId(publicKey);
            this.controller.response(true);
        }
        else {
            this.controller.response(false);
        }
    }

    private void register(JSONObject response) {
        this.controller.response(Boolean.parseBoolean(response.getString("Register")));
    }

    private void getUser(JSONObject response) {
        User user = new User();
        user.setUsername(response.getString("Username"));
        user.setPassword(response.getString("Password"));
        user.setFirstName(response.getString("FirstName"));
        user.setLastName(response.getString("LastName"));
        user.setEmail(response.getString("Email"));
        user.setGender(response.getString("Gender"));
        user.setScore(response.getInt("Score"));
        Session.getInstance().setLoggedInUser(user);
        this.controller.response(user);
    }

    private void findMatch(JSONObject response) {
        String username = response.getString("Username");
        String playerColor = response.getString("PlayerColor");
        this.controller.response(username + ":" + playerColor);
    }

    private void moveOpponentChessPiece(JSONObject response) {
        Point from = new Point(response.getInt("FromX"), response.getInt("FromY"));
        Point to = new Point(response.getInt("ToX"), response.getInt("ToY"));
        OpponentPoints opponentPoints = new OpponentPoints();
        opponentPoints.setFrom(from);
        opponentPoints.setTo(to);
        this.controller.response(opponentPoints);
    }
}
