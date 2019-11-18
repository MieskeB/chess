package nl.michelbijnen;

public enum Difficulty {
    VERYEASY(2),
    EASY(4),
    MEDIUM(6),
    HARD(8),
    VERYHARD(10);

    private int value;

    Difficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
