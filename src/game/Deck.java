package game;

public class Deck {
    private final Card[] deck;
    private int currentCardIndex;

    public Deck() {
        deck = new Card[52];
        String[] allSuites = {"Spades", "Hearts", "Clubs", "Diamonds"};
        String[] allRanks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "King", "Queen", "Jack", "Ace"};
        int counter = 0;

        for (String suite: allSuites) {
            for (String rank : allRanks) {
                int points = switch (rank) {
                    case "King", "Queen", "Jack" -> 10;
                    case "Ace" -> 11;
                    default -> Integer.parseInt(rank);
                };
                deck[counter] = new Card(suite, rank, points);
                counter++;
            }
        }
        shuffleDeck();
    }

    private void shuffleDeck() {
        for (int i = 51; i > 0; i--) {
            int j = (int)(Math.random()* 52);
            Card swapCard = deck[i];
            deck[i] = deck[j];
            deck[j] = swapCard;
        }
    }

    public Card drawCard() {
        if (currentCardIndex == 51) {
            Card currCard = deck[currentCardIndex];
            shuffleDeck();
            return currCard;
        }
        else {
            return deck[currentCardIndex++];
        }
    }
}
