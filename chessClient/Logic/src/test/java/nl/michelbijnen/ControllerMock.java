package nl.michelbijnen;

public class ControllerMock implements IBoardController {

    private int timesUpdated;
    private boolean isLoading;
    private PlayerColor winner;

    ControllerMock() {
        this.timesUpdated = 0;
        this.isLoading = true;
    }

    @Override
    public void updateBoard() {
        this.timesUpdated++;
    }

    @Override
    public void isLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public void isFinished(PlayerColor playerColor) {
        this.winner = playerColor;
    }

    public int getTimesUpdated() {
        return timesUpdated;
    }

    public void resetTimesUpdated() {
        this.timesUpdated = 0;
    }

    public boolean isLoading() {
        return isLoading;
    }
}
