package game;

public class Card {
    private final String suite;
    private final String rank;
    private final int points;

    public Card(String suite, String rank, int points) {
        this.suite = suite;
        this.rank = rank;
        this.points = points;
    }

    public String getRank() { return this.rank;}

    public int getPoints() {
        return this.points;
    }

    public boolean isAce() {
        return this.getRank().equalsIgnoreCase("ace");
    }

    public String display() {
        return this.rank + " of " + this.suite;
    }
}

