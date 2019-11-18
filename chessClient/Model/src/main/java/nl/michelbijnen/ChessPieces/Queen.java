package nl.michelbijnen.ChessPieces;

import nl.michelbijnen.ChessPiece;
import nl.michelbijnen.Direction;
import nl.michelbijnen.PlayerColor;

import java.awt.*;
import java.util.ArrayList;

public class Queen extends ChessPiece {

    public Queen(PlayerColor playerColor, Point startingPoint) {
        super(playerColor, startingPoint);
    }

    @Override
    public ArrayList<Point> getAllPossibleMoves() {
        ArrayList<Point> possibleLocations = new ArrayList<>();
        possibleLocations.addAll(this.getMovesIntoDirection(Direction.UP));
        possibleLocations.addAll(this.getMovesIntoDirection(Direction.DOWN));
        possibleLocations.addAll(this.getMovesIntoDirection(Direction.LEFT));
        possibleLocations.addAll(this.getMovesIntoDirection(Direction.RIGHT));
        possibleLocations.addAll(this.getMovesIntoDirection(Direction.LEFTUP));
        possibleLocations.addAll(this.getMovesIntoDirection(Direction.LEFTDOWN));
        possibleLocations.addAll(this.getMovesIntoDirection(Direction.RIGHTUP));
        possibleLocations.addAll(this.getMovesIntoDirection(Direction.RIGHTDOWN));
        return possibleLocations;
    }

    public ArrayList<Point> getMovesIntoDirection(Direction direction) {
        if (direction == Direction.UP || direction == Direction.LEFT || direction == Direction.DOWN || direction == Direction.RIGHT) {
            return this.getHorizontalMovesIntoDirection(direction);
        }
        return this.getDiagonalMovesIntoDirection(direction);
    }

    private ArrayList<Point> getDiagonalMovesIntoDirection(Direction direction) {
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

    private ArrayList<Point> getHorizontalMovesIntoDirection(Direction direction) {
        ArrayList<Point> possibleLocations = new ArrayList<>();

        switch (direction) {
            case UP:
                for (int i = this.currentLocation.y - 1; i >= 0; i--) {
                    possibleLocations.add(new Point(this.currentLocation.x, i));
                }
                return possibleLocations;

            case DOWN:
                for (int i = this.currentLocation.y + 1; i <= 7; i++) {
                    possibleLocations.add(new Point(this.currentLocation.x, i));
                }
                return possibleLocations;

            case LEFT:
                for (int i = this.currentLocation.x - 1; i >= 0; i--) {
                    possibleLocations.add(new Point(i, this.currentLocation.y));
                }
                return possibleLocations;

            case RIGHT:
                for (int i = this.currentLocation.x + 1; i <= 7; i++) {
                    possibleLocations.add(new Point(i, this.currentLocation.y));
                }
                return possibleLocations;
        }
        return null;
    }

    @Override
    public int getValue() {
        return this.playerColor == PlayerColor.WHITE ? 90 : -90;
    }

    @Override
    public String toString() {
        return "Q";
    }
}
