package nl.michelbijnen;

import org.json.JSONObject;

import javax.websocket.Session;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebsocketLogic {

    public static HashMap<Session, String> sessions = new HashMap<>();
    private ExecutorService pool = Executors.newFixedThreadPool(100);

    void addSession(Session session) {
        sessions.put(session, "");
    }

    void removeSession(Session session) {
        sessions.remove(session);
    }

    HashMap<Session, String> getSessions() {
        return sessions;
    }

    void setPrivateKey(String privateKey, Session session) {
        sessions.put(session,privateKey);
    }

    void jsonHandler(Session session, String json) {
        JSONObject jsonObject = new JSONObject(json);
        MethodLogic methodLogic = new MethodLogic(this, session, jsonObject, sessions.get(session));
        Thread thread = new Thread(methodLogic);
        this.pool.submit(thread);
    }
}
