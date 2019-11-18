package nl.michelbijnen.ChessPieces;

import nl.michelbijnen.ChessPiece;
import nl.michelbijnen.Direction;
import nl.michelbijnen.PlayerColor;

import java.awt.*;
import java.util.ArrayList;

public class Bishop extends ChessPiece {

    public Bishop(PlayerColor playerColor, Point startingPoint) {
        super(playerColor, startingPoint);
    }

    @Override
    public ArrayList<Point> getAllPossibleMoves() {
        ArrayList<Point> possibleLocations = new ArrayList<>();
        possibleLocations.addAll(this.getDiagonalMovesIntoDirection(Direction.LEFTUP));
        possibleLocations.addAll(this.getDiagonalMovesIntoDirection(Direction.LEFTDOWN));
        possibleLocations.addAll(this.getDiagonalMovesIntoDirection(Direction.RIGHTUP));
        possibleLocations.addAll(this.getDiagonalMovesIntoDirection(Direction.RIGHTDOWN));
        return possibleLocations;
    }

    public ArrayList<Point> getDiagonalMovesIntoDirection(Direction direction) {
        ArrayList<Point> possibleLocations = new ArrayList<>();

        switch (direction) {
            case LEFTUP:
                int closestLU = this.currentLocation.x < this.currentLocation.y ? this.currentLocation.x : this.currentLocation.y;
                for (int i = 1; i <= closestLU; i++) {
                    possibleLocations.add(new Point(this.currentLocation.x - i, this.currentLocation.y - i));
                }
                return possibleLocations;
            case LEFTDOWN:
                int closestLD = this.currentLocation.x < 7 - this.currentLocation.y ? this.currentLocation.x : 7 - this.currentLocation.y;
                for (int i = 1; i <= closestLD; i++) {
                    possibleLocations.add(new Point(this.currentLocation.x - i, this.currentLocation.y + i));
                }
                return possibleLocations;
            case RIGHTUP:
                int closestRU = 7 - this.currentLocation.x < this.currentLocation.y ? 7 - this.currentLocation.x : this.currentLocation.y;
                for (int i = 1; i <= closestRU; i++) {
                    possibleLocations.add(new Point(this.currentLocation.x + i, this.currentLocation.y - i));
                }
                return possibleLocations;
            case RIGHTDOWN:
                int closestRD = 7 - this.currentLocation.x < 7 - this.currentLocation.y ? 7 - this.currentLocation.x : 7 - this.currentLocation.y;
                for (int i = 1; i <= closestRD; i++) {
                    possibleLocations.add(new Point(this.currentLocation.x + i, this.currentLocation.y + i));
                }
                return possibleLocations;
        }

        return null;
    }

    @Override
    public int getValue() {
        return this.playerColor == PlayerColor.WHITE ? 30 : -30;
    }

    @Override
    public String toString() {
        return "B";
    }
}
