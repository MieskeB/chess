package nl.michelbijnen;

import java.awt.*;

public class Result {
    private ChessPiece chessPiece;
    private Point toPoint;
    private int valueOfBoard;

    public Result() {
        this.valueOfBoard = 0;
    }

    public Result(int valueOfBoard) {
        this.valueOfBoard = valueOfBoard;
    }

    public Result(ChessPiece chessPiece, Point toPoint, int valueOfBoard) {
        this.chessPiece = chessPiece;
        this.toPoint = toPoint;
        this.valueOfBoard = valueOfBoard;
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    public Point getToPoint() {
        return toPoint;
    }

    public void setToPoint(Point toPoint) {
        this.toPoint = toPoint;
    }

    public int getValueOfBoard() {
        return valueOfBoard;
    }

    public void setValueOfBoard(int valueOfBoard) {
        this.valueOfBoard = valueOfBoard;
    }

    @Override
    public String toString() {
        return "Result: " + this.getChessPiece() + " to " + this.getToPoint() + " curr value of board " + this.getValueOfBoard();
    }
}
