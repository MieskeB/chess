package nl.michelbijnen;

import javafx.concurrent.Task;

import java.awt.*;
import java.util.ArrayList;

public class Minimax extends Task {
    private ChessBoard chessBoard;
    private GameLogic gameLogic;
    private Difficulty difficulty;
    private PlayerColor aiColor;

    private boolean visualize = false;

    Minimax(GameLogic gameLogic, ChessBoard chessBoard, Difficulty difficulty, PlayerColor aiColor) {
        this.chessBoard = chessBoard;
        this.gameLogic = gameLogic;
        this.difficulty = difficulty;
        this.aiColor = aiColor;
    }

    @Override
    public Result call() {
        return this.calculateBestMove(this.difficulty.getValue(), this.aiColor);
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        gameLogic.minimaxCalcDone();
    }

    public Result calculateBestMove(int depth, PlayerColor playerColor) {
        if (depth == 0) {
            return new Result(this.chessBoard.getTotalValue());
        }

        // Get all possible moves of the ai
        ArrayList<Result> results = this.getAllPossibleMoves(playerColor);
        // Do not define the bestMove yet... until it is greater then bestValue
        Result bestMove = null;
        // Set the bestValue to an extremely high number or an extremely low number (-9999 * 1 || -9999 * -1)
        int bestValue = 0;

        // Get every result from the list
        for (Result result : results) {
            // Move the chessPiece to the new location to be tested
            this.chessBoard.moveChessPiece(true, result.getChessPiece(), result.getToPoint());

            // Visualize the steps it makes to calculate the best move
            if (this.visualize) {
                this.gameLogic.updateBoard();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }

            // Get the total value of the board after this move
            int value = calculateBestMove(depth - 1, playerColor.getOther()).getValueOfBoard();

            // Check if the value is greater or smaller then the result
            boolean newBest = false;
            switch (playerColor) {
                case WHITE:
                    newBest = value > bestValue;
                    break;
                case BLACK:
                    newBest = value < bestValue;
                    break;
            }
            if (newBest || bestValue == 0) {
                bestValue = value;
                bestMove = result;
            }

            // Undo the move
            this.chessBoard.undo();
        }

        return bestMove;
    }

    private ArrayList<Result> getAllPossibleMoves(PlayerColor playerColor) {
        ArrayList<Result> results = new ArrayList<>();
        for (ChessPiece chessPiece : this.chessBoard.getAllChessPieces(playerColor)) {
            for (Point p : this.chessBoard.calculatePossibleLocations(chessPiece)) {
                Result result = new Result(chessPiece, p, this.chessBoard.getTotalValue());
                results.add(result);
            }
        }

        return results;
    }
}
