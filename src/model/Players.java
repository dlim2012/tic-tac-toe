package model;

public enum Players {
    PLAYER1,
    PLAYER2;

    private static final Players[] vals = values();

    public Players next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }

    public Players prev() {
        return vals[(this.ordinal() + vals.length - 1) % vals.length];
    }
}
