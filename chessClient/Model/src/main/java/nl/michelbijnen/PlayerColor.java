package nl.michelbijnen;

public enum PlayerColor {
    BLACK(-1),
    WHITE(1);

    private int value;

    PlayerColor(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    private static final PlayerColor[] VALUES = values();

    public PlayerColor getOther() {
        return VALUES[(ordinal() + 1) % 2];
    }
}
