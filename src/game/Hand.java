package game;

import java.util.ArrayList;

// Declarație a listei de cărți din mână
public class Hand {
    private final ArrayList<Card> hand;

    // Constructorul clasei Hand, inițializează lista de cărți
    public Hand() {
        hand = new ArrayList<>();
    }

    // Metodă care returnează lista de cărți din mână
    public ArrayList<Card> getCards() {
        return this.hand;
    }

    public int getPoints() {
        int total = 0;
        for (Card card : hand) { // Adună punctele tuturor cărților din mână
            total = total + card.getPoints();
        }

        if (total > 21) {
            total = 0;
            for (Card card : hand) {
                if (card.isAce())
                    total = total + 1;
                else
                    total = total + card.getPoints();
            }
        }
        return total;
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }

    // Metodă pentru verificarea dacă mâna este Blackjack
    public boolean isBlackjack() {
        return this.getPoints() == 21 && hand.size() == 2;
    }

    // Metodă pentru verificarea dacă mâna a depasit 21
    public boolean isBust() {
        return this.getPoints() > 21;
    }

    // elimina toate cartile
    public void clearHand() {
        this.hand.clear();
    }
}