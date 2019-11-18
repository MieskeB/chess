package nl.michelbijnen;

import org.json.JSONObject;

import javax.websocket.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

@ClientEndpoint
public class EndPoint extends Observable {

    private static Session server;
    private static boolean started;
    private static IControllerLogic logic;

    //region singleton
    private EndPoint() {
    }

    public static void start() {
        try {
            if (!started) {
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                started = true;
                server = container.connectToServer(getInstance(), URI.create("ws://localhost:1337/ws/"));
            }
        } catch (Exception e) {
            started = false;
            e.printStackTrace();
        }
    }

    public static void setLogic(IControllerLogic newLogic) {
        logic = newLogic;
    }

    public static void stop() {
        try {
            started = false;
            server.close();
        } catch (Exception e) {
            started = true;
            e.printStackTrace();
        }
    }

    private static EndPoint instance = new EndPoint();

    public static EndPoint getInstance() {
        if (started) return instance;
        return null;
    }
    //endregion

    @OnOpen
    public void onOpen() {
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("[Received] " + message);
        JSONObject jsonObject = new JSONObject(message);
        if (jsonObject.getString("Method").equals("Login")) {
            PrivateKey.getInstance().setPrivateKey(jsonObject.getString("PrivateKey"));
        }
        logic.response(jsonObject);
    }

    @OnError
    public void onError(Throwable cause) {
    }

    @OnClose
    public void onClose(CloseReason reason) {
    }

    public static boolean isStarted() {
        return started;
    }

    public void sendMessage(boolean usesPrivateKey, String method, Map<String, Object> param) {
        if (usesPrivateKey) {
            param.put("PrivateKey", PrivateKey.getInstance().getPrivateKey());
        }
        param.put("Method", method);
        JSONObject jsonObject = new JSONObject(param);
        String message = jsonObject.toString();
        try {
            server.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
            started = false;
        }
    }
}
