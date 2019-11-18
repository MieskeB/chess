package nl.michelbijnen;

import java.awt.*;

public interface IGameLogic {

    void initializeGame(PlayerColor playerColor, boolean singlePlayer, Difficulty difficulty);

    ChessBoard getChessBoard();

    boolean isYourTurn(PlayerColor playerColor);

    boolean moveChessPiece(ChessPiece chessPiece, Point to);

    void moveOpponentChessPiece(Point from, Point to);
}
