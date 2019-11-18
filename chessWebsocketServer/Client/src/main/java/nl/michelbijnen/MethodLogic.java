package nl.michelbijnen;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import javafx.concurrent.Task;
import org.json.JSONObject;

import javax.websocket.Session;
import java.util.ArrayList;

public class MethodLogic implements Runnable {

    private WebsocketLogic websocketLogic;
    private Session session;
    private JSONObject message;
    private JSONObject response = null;
    private String authKey;

    public MethodLogic(WebsocketLogic websocketLogic, Session session, JSONObject message, String authKey) {
        this.websocketLogic = websocketLogic;
        this.session = session;
        this.message = message;
        this.authKey = authKey;
    }

    @Override
    public void run() {
        String method = this.message.getString("Method");

        // All methods without private key
        boolean done = false;
        switch (method) {
            case "Login":
                this.response = login();
                done = true;
                break;
            case "Register":
                this.response = register();
                done = true;
                break;
        }

        if (done) {
            this.session.getAsyncRemote().sendText(this.response.toString());
            return;
        }

        if (this.message.getString("PrivateKey").equals(this.authKey)) {
            // All methods with private key
            switch (method) {
                case "GetUser":
                    this.response = getUser();
                    break;
                case "FindMatch":
                    this.findMatch();
                    return;
                case "CancelFindMatch":
                    this.cancelFindMatch();
                    return;
                case "Chess":
                    this.chess();
                    return;
                case "AddScore":
                    this.addScore();
                    return;
            }
        }
        else {
            // Private key is wrong :S (Shouldn't be possible unless someone is hacking in the mainframe XD)
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method", "Error");
            this.response = jsonObject;
        }

        this.session.getAsyncRemote().sendText(this.response.toString());
    }

    private JSONObject login() {
        String username = this.message.getString("Username");
        String password = this.message.getString("Password");
        Authentication authentication = Factory.getUserRest().login(username, password);
        if (authentication != null) {
            this.websocketLogic.setPrivateKey(authentication.getPrivateKey(), this.session);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method", "Login");
            jsonObject.put("PublicKey", authentication.getPublicKey());
            jsonObject.put("PrivateKey", authentication.getPrivateKey());
            return jsonObject;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Method", "Login");
        jsonObject.put("PublicKey", "0");
        jsonObject.put("PrivateKey", "0");
        return jsonObject;
    }

    private JSONObject register() {
        User user = new User();
        user.setUsername(this.message.getString("Username"));
        user.setPassword(this.message.getString("Password"));
        user.setFirstName(this.message.getString("FirstName"));
        user.setLastName(this.message.getString("LastName"));
        user.setEmail(this.message.getString("Email"));
        user.setGender(this.message.getString("Gender"));
        boolean response = Factory.getUserRest().register(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Method", "Register");
        jsonObject.put("Register", response ? "true" : "false");
        return jsonObject;
    }

    private JSONObject getUser() {
        String userId = this.message.getString("UserId");
        User user = Factory.getUserRest().getUser(this.authKey, userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Method", "GetUser");
        jsonObject.put("Username", user.getUsername());
        jsonObject.put("Password", user.getPassword());
        jsonObject.put("FirstName", user.getFirstName());
        jsonObject.put("LastName", user.getLastName());
        jsonObject.put("Email", user.getEmail());
        jsonObject.put("Gender", user.getGender());
        jsonObject.put("Score", user.getScore());
        return jsonObject;
    }

    private void findMatch() {
        int score = this.message.getInt("Score");
        String username = this.message.getString("Username");
        Matches.getInstance().addOngoingSearch(this.session, username, score);

        for (Searching searching : new ArrayList<>(Matches.getInstance().getOngoingSearches())) {
            if (WebsocketLogic.sessions.get(searching.getSession()) == null) {
                Matches.getInstance().getOngoingSearches().remove(searching);
            }

            if (searching.getScore() < (score + 25) && searching.getScore() > (score - 25) && searching.getSession() != this.session) {
                Matches.getInstance().addOngoingMatch(searching.getSession(), this.session);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Method", "FindMatch");
                jsonObject.put("Username", searching.getUsername());
                jsonObject.put("PlayerColor", "WHITE");
                this.session.getAsyncRemote().sendText(jsonObject.toString());
                jsonObject.put("Username", username);
                jsonObject.put("PlayerColor", "BLACK");
                searching.getSession().getAsyncRemote().sendText(jsonObject.toString());

                break;
            }
        }
    }

    private void cancelFindMatch() {
        Matches.getInstance().removeSessionFromOngoingSearches(this.session);
    }

    private void chess() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Method", "Chess");
        jsonObject.put("FromX", this.message.getInt("FromX"));
        jsonObject.put("FromY", 7 - this.message.getInt("FromY"));
        jsonObject.put("ToX", this.message.getInt("ToX"));
        jsonObject.put("ToY", 7 - this.message.getInt("ToY"));
        Matches.getInstance().getOpponent(this.session).getAsyncRemote().sendText(jsonObject.toString());
    }

    private void addScore() {
        Factory.getUserRest().addScore(this.authKey, this.message.getString("UserId"), this.message.getInt("Score"));
        Matches.getInstance().removeSessionsFromOngoingMatches(this.session);
    }
}
