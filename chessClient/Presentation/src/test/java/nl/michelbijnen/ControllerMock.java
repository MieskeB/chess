package nl.michelbijnen;

public class ControllerMock implements IController, IBoardController {

    private boolean gotResponse;
    private Object response;

    ControllerMock() {
        this.gotResponse = false;
    }

    @Override
    public void response(Object response) {
        this.gotResponse = true;
        this.response = response;
    }

    @Override
    public void error() {

    }

    public boolean isGotResponse() {
        return this.gotResponse;
    }

    public Object read() {
        if (this.gotResponse) {
            this.gotResponse = false;
            return response;
        }

        return null;
    }

    @Override
    public void updateBoard() {

    }

    @Override
    public void isLoading(boolean isLoading) {

    }

    @Override
    public void isFinished(PlayerColor playerColor) {

    }
}
