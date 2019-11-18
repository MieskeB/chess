package nl.michelbijnen;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class ChessPiece {

    protected PlayerColor playerColor;
    protected Point currentLocation;

    public ChessPiece(PlayerColor playerColor, Point startingPoint) {
        this.playerColor = playerColor;
        this.currentLocation = startingPoint;
    }


    public PlayerColor getPlayerColor() {
        return this.playerColor;
    }

    public abstract ArrayList<Point> getAllPossibleMoves();

    public Point getCurrentLocation() {
        return this.currentLocation;
    }

    public abstract int getValue();

    public void setCurrentLocation(Point p) {
        this.currentLocation = p;
    }
}
