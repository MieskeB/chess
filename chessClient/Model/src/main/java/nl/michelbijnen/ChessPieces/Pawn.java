package nl.michelbijnen.ChessPieces;

import nl.michelbijnen.PlayerColor;
import nl.michelbijnen.ChessPiece;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Pawn extends ChessPiece {

    private boolean goesUp;

    public Pawn(PlayerColor playerColor, Point startingPoint, boolean goesUp) {
        super(playerColor, startingPoint);
        this.goesUp = goesUp;
    }

    @Override
    public ArrayList<Point> getAllPossibleMoves() {
        ArrayList<Point> possibleLocations = new ArrayList<>();

        possibleLocations.addAll(this.getAllMoveMoves());
        possibleLocations.addAll(this.getAllHitMoves());

        return possibleLocations;
    }

    public ArrayList<Point> getAllMoveMoves() {
        ArrayList<Point> possibleLocations = new ArrayList<>();

        if (goesUp) {
            if (this.currentLocation.y == 0) {
                return possibleLocations;
            }

            possibleLocations.add(new Point(this.currentLocation.x, this.currentLocation.y - 1));

            if (this.currentLocation.y == 6) {
                possibleLocations.add(new Point(this.currentLocation.x, this.currentLocation.y - 2));
            }
        } else {
            if (this.currentLocation.y == 7) {
                return null;
            }

            possibleLocations.add(new Point(this.currentLocation.x, this.currentLocation.y + 1));

            if (this.currentLocation.y == 1) {
                possibleLocations.add(new Point(this.currentLocation.x, this.currentLocation.y + 2));
            }
        }

        return possibleLocations;
    }

    public ArrayList<Point> getAllHitMoves() {
        ArrayList<Point> possibleLocations = new ArrayList<>();

        if (goesUp) {
            if (this.currentLocation.y == 0) {
                return possibleLocations;
            }

            possibleLocations.add(new Point(this.currentLocation.x - 1, this.currentLocation.y - 1));
            possibleLocations.add(new Point(this.currentLocation.x + 1, this.currentLocation.y - 1));
        } else {
            if (this.currentLocation.y == 7) {
                return null;
            }

            possibleLocations.add(new Point(this.currentLocation.x - 1, this.currentLocation.y + 1));
            possibleLocations.add(new Point(this.currentLocation.x + 1, this.currentLocation.y + 1));
        }

        return possibleLocations;
    }

    @Override
    public int getValue() {
        return this.playerColor == PlayerColor.WHITE ? 10 : -10;
    }

    @Override
    public String toString() {
        return "P";
    }
}
