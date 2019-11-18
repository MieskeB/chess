package nl.michelbijnen;

import java.awt.*;

public interface IGameDal {
    void moveChessPiece(Point from, Point to);
    void addScore(String userId, int score);
}
