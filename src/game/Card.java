package game;

public class Card {
    private final String suite;
    private final String rank;
    private final int points;

    // Constructorul clasei Card pentru a seta valorile suite, rank și points
    public Card(String suite, String rank, int points) {
        this.suite = suite;
        this.rank = rank;
        this.points = points;
    }

    public String getRank() {
        return this.rank;
    }

    public int getPoints() {
        return this.points;
    }

    // Metoda pentru verificarea dacă este o carte "Ace"
    public boolean isAce() {
        return this.getRank().equalsIgnoreCase("ace");
    }

    // Metoda pentru afișarea informațiilor despre carte
    public String display() {
        return this.rank + " of " + this.suite;
    }
}

