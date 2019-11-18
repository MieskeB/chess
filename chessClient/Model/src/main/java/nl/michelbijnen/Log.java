package nl.michelbijnen;

import java.awt.*;

public class Log {
    enum LogType {
        HIT,
        MOVE
    }

    private LogType logType;
    private ChessPiece chessPiece;
    private Point pointFrom;
    private Point pointTo;

    public Log(ChessPiece chessPiece, Point pointTo) {
        this.logType = LogType.HIT;
        this.chessPiece = chessPiece;
        this.pointTo = new Point(pointTo);
    }

    public Log(ChessPiece chessPiece, Point pointFrom, Point pointTo) {
        this.logType = LogType.MOVE;
        this.chessPiece = chessPiece;
        this.pointFrom = new Point(pointFrom);
        this.pointTo = new Point(pointTo);
    }

    public ChessPiece getChessPiece() {
        return this.chessPiece;
    }

    public Point getPointFrom() {
        return new Point(this.pointFrom);
    }

    public Point getPointTo() {
        return new Point(this.pointTo);
    }

    public String getLogType() {
        return logType.name();
    }

    @Override
    public String toString() {
        if (this.logType == LogType.MOVE) {
            return this.getChessPiece() + " moved from " + this.getPointFrom() + " to " + this.getPointTo();
        }
        else {
            return this.getChessPiece() + " just got slain at " + this.getPointTo();
        }
    }
}