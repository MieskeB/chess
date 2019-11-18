package nl.michelbijnen;

import javafx.application.Platform;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameLogic implements IGameLogic {

    private ExecutorService pool = Executors.newFixedThreadPool(1);
    private IBoardController boardController;
    private Minimax minimax;
    private IGameDal gameDal;

    public GameLogic(IGameDal iGameDal, IBoardController boardController) {
        this.boardController = boardController;
        this.gameDal = iGameDal;
    }

    @Override
    public void initializeGame(PlayerColor playerColor, boolean singlePlayer, Difficulty difficulty) {
        Session.getInstance().setChessBoard(new ChessBoard(playerColor));
        Session.getInstance().setTurn(PlayerColor.WHITE);
        this.boardController.updateBoard();
        this.getChessBoard().setSingleplayer(singlePlayer);
        this.boardController.isLoading(playerColor == PlayerColor.BLACK);

        if (singlePlayer) {
            Session.getInstance().setDifficulty(difficulty);
            if (this.getChessBoard().getPlayerColor() == PlayerColor.BLACK) {
                this.moveAi();
            }
        }
    }

    @Override
    public void moveOpponentChessPiece(Point from, Point to) {
        this.boardController.isLoading(false);
        this.getChessBoard().moveChessPiece(false, from, to);
        this.switchTurn();
        this.updateBoard();
        this.checkWin();
    }

    @Override
    public ChessBoard getChessBoard() {
        return Session.getInstance().getChessBoard();
    }

    @Override
    public boolean isYourTurn(PlayerColor playerColor) {
        PlayerColor color = Session.getInstance().getTurn();
        return color == playerColor;
    }

    @Override
    public boolean moveChessPiece(ChessPiece chessPiece, Point to) {
        if (!isYourTurn(chessPiece.playerColor)) {
            return false;
        }

        if (this.canMove(chessPiece, to)) {
            Point from = chessPiece.getCurrentLocation();

            this.getChessBoard().moveChessPiece(false, chessPiece, to);

            if (!this.getChessBoard().isSingleplayer()) {
                this.gameDal.moveChessPiece(from, to);
            }

            this.switchTurn();

            this.boardController.isLoading(true);
            this.boardController.updateBoard();

            if (this.checkWin()) {
                return true;
            }

            if (this.getChessBoard().isSingleplayer()) {
                if (this.getChessBoard().getPlayerColor().getOther() == Session.getInstance().getTurn()) {
                    this.moveAi();
                }
            }

            return true;
        }
        return false;
    }

    private boolean canMove(ChessPiece chessPiece, Point to) {
        for (Point p : Objects.requireNonNull(this.getChessBoard().calculatePossibleLocations(chessPiece))) {
            if (p.x == to.x && p.y == to.y) {
                return true;
            }
        }

        return false;
    }

    private void switchTurn() {
        Session.getInstance().setTurn(Session.getInstance().getTurn().getOther());
    }

    private void moveAi() {
        this.minimax = new Minimax(this, this.getChessBoard(), Session.getInstance().getDifficulty(), this.getChessBoard().getPlayerColor().getOther());
        Thread thread = new Thread(this.minimax);
        pool.submit(thread);
    }

    private boolean checkWin() {
        if (this.getChessBoard().isGameFinished()) {
            // Send the score to the database
            if (this.getChessBoard().getPlayerColor() == this.getChessBoard().getWinner()) {
                this.gameDal.addScore(Session.getInstance().getLoggedInUserId(), 3);
            } else {
                this.gameDal.addScore(Session.getInstance().getLoggedInUserId(), 1);
            }
            
            this.boardController.isFinished(this.getChessBoard().getWinner());

            return true;
        }

        return false;
    }

    void updateBoard() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                boardController.updateBoard();
            }
        });
    }

    void minimaxCalcDone() {
        try {
            Result result = (Result) this.minimax.get();
            this.moveOpponentChessPiece(result.getChessPiece().getCurrentLocation(), result.getToPoint());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
