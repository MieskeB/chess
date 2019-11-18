package nl.michelbijnen;

import nl.michelbijnen.ChessPieces.*;

import java.awt.*;
import java.util.ArrayList;

class Calculate {
    private ChessBoard chessBoard;

    Calculate(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    ArrayList<Point> calculate(Pawn pon) {
        ArrayList<Point> possibleMoves = pon.getAllPossibleMoves();

        for (Point p : pon.getAllHitMoves()) {
            ChessPiece chessPiece = this.chessBoard.getChessPieceOfPoint(p);
            if (chessPiece == null || chessPiece.playerColor == pon.playerColor) {
                possibleMoves.remove(p);
            }
        }

        int i = 0;
        for (Point p : pon.getAllMoveMoves()) {
            ChessPiece chessPiece = this.chessBoard.getChessPieceOfPoint(p);
            if (chessPiece != null) {
                possibleMoves.remove(p);
                if (i == 0) {
                    break;
                }
            }
            i++;
        }

        return possibleMoves;
    }

    ArrayList<Point> calculate(Rook tower) {
        ArrayList<Point> possibleMoves = new ArrayList<>();
        Direction direction = Direction.UP;
        boolean done = false;

        while (!done) {
            ArrayList<Point> points = tower.getHorizontalMovesIntoDirection(direction);
            for (Point p : points) {
                ChessPiece chessPiece = this.chessBoard.getChessPieceOfPoint(p);
                if (chessPiece == null) {
                    possibleMoves.add(p);
                }
                else {
                    if (chessPiece.playerColor != tower.playerColor) {
                        possibleMoves.add(p);
                    }
                    break;
                }
            }

            switch (direction) {
                case UP:
                    direction = Direction.LEFT;
                    break;
                case LEFT:
                    direction = Direction.DOWN;
                    break;
                case DOWN:
                    direction = Direction.RIGHT;
                    break;
                case RIGHT:
                    done = true;
                    break;
            }
        }

        return possibleMoves;
    }

    ArrayList<Point> calculate(Knight knight) {
        ArrayList<Point> possibleMoves = knight.getAllPossibleMoves();

        for (Point p : new ArrayList<>(possibleMoves)) {
            ChessPiece chessPiece = this.chessBoard.getChessPieceOfPoint(p);
            if (chessPiece != null) {
                if (chessPiece.playerColor == knight.playerColor){
                    possibleMoves.remove(p);
                }
            }
        }

        return possibleMoves;
    }

    ArrayList<Point> calculate(Bishop bishop) {
        ArrayList<Point> possibleMoves = new ArrayList<>();
        Direction direction = Direction.LEFTUP;
        boolean done = false;

        while (!done) {
            ArrayList<Point> points = bishop.getDiagonalMovesIntoDirection(direction);
            for (Point p : points) {
                ChessPiece chessPiece = this.chessBoard.getChessPieceOfPoint(p);
                if (chessPiece == null) {
                    possibleMoves.add(p);
                }
                else {
                    if (chessPiece.playerColor != bishop.playerColor) {
                        possibleMoves.add(p);
                    }
                    break;
                }
            }

            switch (direction) {
                case LEFTUP:
                    direction = Direction.LEFTDOWN;
                    break;
                case LEFTDOWN:
                    direction = Direction.RIGHTUP;
                    break;
                case RIGHTUP:
                    direction = Direction.RIGHTDOWN;
                    break;
                case RIGHTDOWN:
                    done = true;
                    break;
            }
        }

        return possibleMoves;
    }

    ArrayList<Point> calculate(Queen queen) {
        ArrayList<Point> possibleMoves = new ArrayList<>();
        Direction direction = Direction.LEFTUP;
        boolean done = false;

        while (!done) {
            ArrayList<Point> points = queen.getMovesIntoDirection(direction);
            for (Point p : points) {
                ChessPiece chessPiece = this.chessBoard.getChessPieceOfPoint(p);
                if (chessPiece == null) {
                    possibleMoves.add(p);
                }
                else {
                    if (chessPiece.playerColor != queen.playerColor) {
                        possibleMoves.add(p);
                    }
                    break;
                }
            }

            switch (direction) {
                case LEFTUP:
                    direction = Direction.LEFTDOWN;
                    break;
                case LEFTDOWN:
                    direction = Direction.RIGHTUP;
                    break;
                case RIGHTUP:
                    direction = Direction.RIGHTDOWN;
                    break;
                case RIGHTDOWN:
                    direction = Direction.UP;
                    break;
                case UP:
                    direction = Direction.LEFT;
                    break;
                case LEFT:
                    direction = Direction.DOWN;
                    break;
                case DOWN:
                    direction = Direction.RIGHT;
                    break;
                case RIGHT:
                    done = true;
                    break;
            }
        }

        return possibleMoves;
    }

    ArrayList<Point> calculate(King king) {
        ArrayList<Point> possibleMoves = king.getAllPossibleMoves();

        for (Point p : new ArrayList<>(possibleMoves)) {
            ChessPiece chessPiece = this.chessBoard.getChessPieceOfPoint(p);
            if (chessPiece != null) {
                if (chessPiece.playerColor == king.playerColor){
                    possibleMoves.remove(p);
                }
            }
        }

        return possibleMoves;
    }
}
