package game;

public class Deck {
    private final Card[] deck;  // Declarația unui array de Card-uri care reprezintă pachetul de cărți.
    private int currentCardIndex; // Indexul curent al cărții trase din pachet.


    public Deck() {
        deck = new Card[52];
        String[] allSuites = {"Spades", "Hearts", "Clubs", "Diamonds"};
        String[] allRanks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "King", "Queen", "Jack", "Ace"};
        int counter = 0;

        // Generarea fiecărei combinații de Suit și Rank.
        for (String suite: allSuites) {
            for (String rank : allRanks) {
                int points = switch (rank) {  // Calcularea punctajului în funcție de rang
                    case "King", "Queen", "Jack" -> 10;
                    case "Ace" -> 11;
                    default -> Integer.parseInt(rank); // Convertire la întreg pentru rangurile numerice
                };
                deck[counter] = new Card(suite, rank, points);
                counter++;
            }
        }
        shuffleDeck();
    }

    private void shuffleDeck() {
        for (int i = 51; i > 0; i--) {
            int j = (int)(Math.random()* 52); // Generarea unui index random pentru amestecarea cărților
            Card swapCard = deck[i];
            deck[i] = deck[j];
            deck[j] = swapCard;
        }
    }

    public Card drawCard() {
        if (currentCardIndex == 51) { // Dacă am ajuns la ultima carte, amestecăm din nou pachetul
            Card currCard = deck[currentCardIndex];
            shuffleDeck();
            return currCard;
        }
        else {
            return deck[currentCardIndex++]; // Tragem cartea curentă și incrementăm indexul
        }
    }
}
