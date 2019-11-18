package nl.michelbijnen.ChessPieces;

import nl.michelbijnen.ChessPiece;
import nl.michelbijnen.Direction;
import nl.michelbijnen.PlayerColor;

import java.awt.*;
import java.util.ArrayList;

public class Rook extends ChessPiece {

    public Rook(PlayerColor playerColor, Point startingPoint) {
        super(playerColor, startingPoint);
    }

    @Override
    public ArrayList<Point> getAllPossibleMoves() {
        ArrayList<Point> possibleLocations = new ArrayList<>();
        possibleLocations.addAll(this.getHorizontalMovesIntoDirection(Direction.UP));
        possibleLocations.addAll(this.getHorizontalMovesIntoDirection(Direction.DOWN));
        possibleLocations.addAll(this.getHorizontalMovesIntoDirection(Direction.LEFT));
        possibleLocations.addAll(this.getHorizontalMovesIntoDirection(Direction.RIGHT));
        return possibleLocations;
    }

    public ArrayList<Point> getHorizontalMovesIntoDirection(Direction direction) {
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
        return this.playerColor == PlayerColor.WHITE ? 50 : -50;
    }

    @Override
    public String toString() {
        return "R";
    }
}
