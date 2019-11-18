package nl.michelbijnen;

import java.awt.*;
import java.util.HashMap;

public class GameDal implements IGameDal {
    public GameDal(IControllerLogic controllerLogic) {
        EndPoint.setLogic(controllerLogic);
        EndPoint.start();
    }

    @Override
    public void moveChessPiece(Point from, Point to) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("FromX", from.x);
        parameters.put("FromY", from.y);
        parameters.put("ToX", to.x);
        parameters.put("ToY", to.y);
        EndPoint.getInstance().sendMessage(true, "Chess", parameters);
    }

    @Override
    public void addScore(String userId, int score) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("UserId", userId);
        parameters.put("Score", score);
        EndPoint.getInstance().sendMessage(true, "AddScore", parameters);
    }
}
