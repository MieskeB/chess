package nl.michelbijnen;

import nl.michelbijnen.ChessPieces.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChessBoard {

    private List<ChessPiece> chessPieces;
    private int totalValue;
    private PlayerColor playerColor;

    private boolean singleplayer;

    private List<Log> logs;

    private boolean gameFinished;
    private PlayerColor winner;

    public ChessBoard(PlayerColor playerColor) {

        this.logs = new ArrayList<>();

        this.playerColor = playerColor;

        this.chessPieces = new ArrayList<>();

        this.chessPieces.add(new Rook(playerColor, new Point(0, 7)));
        this.chessPieces.add(new Knight(playerColor, new Point(1, 7)));
        this.chessPieces.add(new Bishop(playerColor, new Point(2, 7)));
        this.chessPieces.add(new Queen(playerColor, new Point(3, 7)));
        this.chessPieces.add(new King(playerColor, new Point(4, 7)));
        this.chessPieces.add(new Bishop(playerColor, new Point(5, 7)));
        this.chessPieces.add(new Knight(playerColor, new Point(6, 7)));
        this.chessPieces.add(new Rook(playerColor, new Point(7, 7)));

        for (int i = 0; i <= 7; i++) {
            this.chessPieces.add(new Pawn(playerColor, new Point(i, 6), true));
        }

        PlayerColor opponentPlayerColor = playerColor == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;

        this.chessPieces.add(new Rook(opponentPlayerColor, new Point(0, 0)));
        this.chessPieces.add(new Knight(opponentPlayerColor, new Point(1, 0)));
        this.chessPieces.add(new Bishop(opponentPlayerColor, new Point(2, 0)));
        this.chessPieces.add(new Queen(opponentPlayerColor, new Point(3, 0)));
        this.chessPieces.add(new King(opponentPlayerColor, new Point(4, 0)));
        this.chessPieces.add(new Bishop(opponentPlayerColor, new Point(5, 0)));
        this.chessPieces.add(new Knight(opponentPlayerColor, new Point(6, 0)));
        this.chessPieces.add(new Rook(opponentPlayerColor, new Point(7, 0)));

        for (int i = 0; i <= 7; i++) {
            this.chessPieces.add(new Pawn(opponentPlayerColor, new Point(i, 1), false));
        }

        this.updateTotalValue();
    }

    public PlayerColor getPlayerColor() {
        return this.playerColor;
    }

    public int getTotalValue() {
        this.updateTotalValue();
        return this.totalValue;
    }

    public ChessPiece getChessPieceOfPoint(Point p) {
        for (ChessPiece chessPiece : this.chessPieces) {
            if (chessPiece.getCurrentLocation().x == p.x && chessPiece.getCurrentLocation().y == p.y) {
                return chessPiece;
            }
        }
        return null;
    }

    public List<ChessPiece> getAllChessPieces() {
        return new ArrayList<>(this.chessPieces);
    }

    public List<ChessPiece> getAllChessPieces(PlayerColor playerColor) {
        List<ChessPiece> chessPieces = new ArrayList<>();
        for (ChessPiece chessPiece : this.chessPieces) {
            if (chessPiece.playerColor == playerColor) {
                chessPieces.add(chessPiece);
            }
        }
        return chessPieces;
    }

    public boolean moveChessPiece(boolean testing, Point start, Point end) {
        ChessPiece chessPiece = this.getChessPieceOfPoint(start);
        return this.moveChessPiece(testing, chessPiece, end);
    }

    public boolean moveChessPiece(boolean testing, ChessPiece chessPiece, Point end) {
        boolean hasHit = false;

        this.logs.add(new Log(chessPiece, new Point(chessPiece.getCurrentLocation()), new Point(end)));

        ChessPiece chessPieceDestroyed = this.getChessPieceOfPoint(end);
        if (chessPieceDestroyed != null) {
            this.logs.add(new Log(chessPieceDestroyed, new Point(end)));
            this.destroyChessPiece(chessPieceDestroyed);
            if (!testing && chessPieceDestroyed instanceof King) {
                this.winner = chessPiece.playerColor;
                this.gameFinished = true;
            }
            hasHit = true;
        }
        chessPiece.setCurrentLocation(end);
        return hasHit;
    }

    public void destroyChessPiece(ChessPiece chessPiece) {
        this.chessPieces.remove(chessPiece);
    }

    private void updateTotalValue() {
        this.totalValue = 0;
        for (ChessPiece chessPiece : this.chessPieces) {
            this.totalValue += chessPiece.getValue();
        }
    }

    public ArrayList<Point> calculatePossibleLocations(ChessPiece chessPiece) {
        Calculate calculate = new Calculate(this);

        if (chessPiece instanceof Pawn) {
            return calculate.calculate((Pawn) chessPiece);
        } else if (chessPiece instanceof Rook) {
            return calculate.calculate((Rook) chessPiece);
        } else if (chessPiece instanceof Knight) {
            return calculate.calculate((Knight) chessPiece);
        } else if (chessPiece instanceof Bishop) {
            return calculate.calculate((Bishop) chessPiece);
        } else if (chessPiece instanceof Queen) {
            return calculate.calculate((Queen) chessPiece);
        } else if (chessPiece instanceof King) {
            return calculate.calculate((King) chessPiece);
        }

        return null;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void undo() {
        Log log = this.logs.get(this.logs.size() - 1);

        if (log.getLogType().equals("HIT")) {
            this.chessPieces.add(log.getChessPiece());
            this.logs.remove(log);
        }

        log = this.logs.get(this.logs.size() - 1);
        log.getChessPiece().setCurrentLocation(log.getPointFrom());
        this.logs.remove(log);
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public PlayerColor getWinner() {
        return winner;
    }

    public boolean isSingleplayer() {
        return singleplayer;
    }

    public void setSingleplayer(boolean singleplayer) {
        this.singleplayer = singleplayer;
    }
}
