package nl.michelbijnen;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

@ServerEndpoint(value = "/ws/")
public class ChessWebsocket {

    private static WebsocketLogic websocketLogic = new WebsocketLogic();

    @OnOpen
    public void onConnect(Session session) {
        websocketLogic.addSession(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        websocketLogic.jsonHandler(session, message);
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        cause.printStackTrace();
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        websocketLogic.removeSession(session);
    }
}
