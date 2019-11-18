package nl.michelbijnen.ChessPieces;

import nl.michelbijnen.ChessPiece;
import nl.michelbijnen.PlayerColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Knight extends ChessPiece {

    public Knight(PlayerColor playerColor, Point startingPoint) {
        super(playerColor, startingPoint);
    }

    @Override
    public ArrayList<Point> getAllPossibleMoves() {
        ArrayList<Point> possibleLocations = new ArrayList<>();

        possibleLocations.add(new Point(this.currentLocation.x + 2, this.currentLocation.y + 1));
        possibleLocations.add(new Point(this.currentLocation.x + 2, this.currentLocation.y - 1));
        possibleLocations.add(new Point(this.currentLocation.x - 2, this.currentLocation.y + 1));
        possibleLocations.add(new Point(this.currentLocation.x - 2, this.currentLocation.y - 1));
        possibleLocations.add(new Point(this.currentLocation.x + 1, this.currentLocation.y + 2));
        possibleLocations.add(new Point(this.currentLocation.x - 1, this.currentLocation.y + 2));
        possibleLocations.add(new Point(this.currentLocation.x + 1, this.currentLocation.y - 2));
        possibleLocations.add(new Point(this.currentLocation.x - 1, this.currentLocation.y - 2));

        for (Point p : new ArrayList<>(possibleLocations)) {
            if (p.x > 7 || p.x < 0 || p.y > 7 || p.y < 0) {
                possibleLocations.remove(p);
            }
        }

        return possibleLocations;
    }

    @Override
    public int getValue() {
        return this.playerColor == PlayerColor.WHITE ? 30 : -30;
    }

    @Override
    public String toString() {
        return "H";
    }
}
