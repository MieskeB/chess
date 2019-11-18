package nl.michelbijnen.ChessPieces;

import nl.michelbijnen.ChessPiece;
import nl.michelbijnen.PlayerColor;

import java.awt.*;
import java.util.ArrayList;

public class King extends ChessPiece {

    public King(PlayerColor playerColor, Point startingPoint) {
        super(playerColor, startingPoint);
    }

    @Override
    public ArrayList<Point> getAllPossibleMoves() {
        ArrayList<Point> possibleLocations = new ArrayList<>();
        possibleLocations.add(new Point(this.currentLocation.x + 1, this.currentLocation.y + 1));
        possibleLocations.add(new Point(this.currentLocation.x, this.currentLocation.y + 1));
        possibleLocations.add(new Point(this.currentLocation.x + 1, this.currentLocation.y));
        possibleLocations.add(new Point(this.currentLocation.x - 1, this.currentLocation.y + 1));
        possibleLocations.add(new Point(this.currentLocation.x + 1, this.currentLocation.y - 1));
        possibleLocations.add(new Point(this.currentLocation.x - 1, this.currentLocation.y));
        possibleLocations.add(new Point(this.currentLocation.x, this.currentLocation.y - 1));
        possibleLocations.add(new Point(this.currentLocation.x - 1, this.currentLocation.y - 1));

        for (Point p : new ArrayList<>(possibleLocations)) {
            if (p.x < 0 || p.y < 0 || p.x > 7 || p.y > 7) {
                possibleLocations.remove(p);
            }
        }

        return possibleLocations;
    }

    @Override
    public int getValue() {
        return this.playerColor == PlayerColor.WHITE ? 900 : -900;
    }

    @Override
    public String toString() {
        return "K";
    }
}
