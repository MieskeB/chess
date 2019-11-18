package nl.michelbijnen;

public class Session {

    //region singleton
    private Session() {
    }

    private static Session session = new Session();

    public static Session getInstance() {
        return session;
    }
    //endregion

    private ChessBoard chessBoard;
    private PlayerColor turn = PlayerColor.WHITE;
    private Difficulty difficulty;
    private String loggedInUserId;
    private User loggedInUser;

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public PlayerColor getTurn() {
        return turn;
    }

    public void setTurn(PlayerColor turn) {
        this.turn = turn;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }
}
