package game;

import java.util.ArrayList;
public class Hand {
    private final ArrayList<Card> hand;

    public Hand() {
        hand = new ArrayList<>();
    }

    public ArrayList<Card> getCards() {
        return this.hand;
    }

    public int getPoints() {
        int total = 0;
        for (Card card : hand) {
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

    public boolean isBlackjack() {
        return this.getPoints() == 21 && hand.size() == 2;
    }

    public boolean isBust() {
        return this.getPoints() > 21;
    }

    public void clearHand() {
        this.hand.clear();
    }
}