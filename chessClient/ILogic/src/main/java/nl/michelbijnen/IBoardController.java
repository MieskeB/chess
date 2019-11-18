package nl.michelbijnen;

public interface IBoardController {
    void updateBoard();
    void isLoading(boolean isLoading);
    void isFinished(PlayerColor playerColor);
}
