package game;

public class BlackjackGame {
    // Obiectele principale: mana jucatorului, mana dealer-ului si pachetul de carti
    private final Hand playerHand;
    private final Hand dealerHand;
    private final Deck deck;
    private double betAmount; // Suma pariata pentru o runda
    private final double minBet; // Pariul minim permis
    private final double maxBet; // Pariul maxim permis
    private double totalMoney; // Fondurile totale ale jucatorului

    // Constructorul initializeaza pachetul de carti, mainile si limitele de pariere
    public BlackjackGame() {
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.minBet = 5.0;
        this.maxBet = 1000.0;
    }

    // Incarca suma initiala de bani pentru joc
    public double loadMoney() {
        return totalMoney = 100.0;
    }

    // Verifica daca jucatorul a ramas fara bani
    public boolean isOutOfMoney() {
        return this.totalMoney < this.minBet;
    }

    // Reseteaza fondurile jucatorului la suma initiala
    public double resetMoney() {
        return this.totalMoney = 100.0;
    }

    // Verifica daca suma pariata este valida
    public boolean isValidBet(double localBetAmt) {
        return localBetAmt >= this.minBet || localBetAmt <= this.maxBet || localBetAmt <= this.totalMoney;
    }

    // Returneaza pariul minim
    public double getMinBet() {
        return this.minBet;
    }

    // Returneaza pariul maxim
    public double getMaxBet() {
        return this.maxBet;
    }

    // Returneaza suma totala de bani a jucatorului
    public double getTotalMoney() {
        return this.totalMoney;
    }

    // Seteaza suma pariata pentru runda curenta
    public void setBet(double amt) {
        this.betAmount = amt;
    }

    // Imparte doua carti jucatorului si dealer-ului
    public void deal() {
        this.playerHand.addCard(deck.drawCard());
        this.playerHand.addCard(deck.drawCard());
        this.dealerHand.addCard(deck.drawCard());
        this.dealerHand.addCard(deck.drawCard());
    }

    // Adauga o carte la mana jucatorului (Hit)
    public void hit() {
        playerHand.addCard(deck.drawCard());
    }

    // Dealer-ul continua sa traga carti pana cand totalul este cel putin 17
    public void stand() {
        while (dealerHand.getPoints() < 17)
            dealerHand.addCard(deck.drawCard());
    }

    // Returneaza a doua carte a dealer-ului (cartea vizibila)
    public Card getDealerShowCard() {
        return this.dealerHand.getCards().get(1);
    }

    // Returneaza mana dealer-ului
    public Hand getDealerHand() {
        return this.dealerHand;
    }

    // Returneaza mana jucatorului
    public Hand getPlayerHand() {
        return this.playerHand;
    }

    // Verifica daca jocul este terminat prin Blackjack sau Bust
    public boolean isBlackjackOrBust() {
        return playerHand.isBlackjack() || playerHand.isBust() || dealerHand.isBlackjack() || dealerHand.isBust();
    }

    // Verifica daca este remiza (Push)
    public boolean isPush() {
        return playerHand.getPoints() <= 21 && playerHand.getPoints() == dealerHand.getPoints();
    }

    // Verifica daca jucatorul a castigat
    public boolean playerWins() {
        return !playerHand.isBust() && playerHand.getPoints() > dealerHand.getPoints() || playerHand.getPoints() == 21 ||  playerHand.isBlackjack() || dealerHand.isBust();
    }

    // Adauga suma pariata la totalul de bani al jucatorului daca acesta castiga
    public double addBetToTotal() {
        return totalMoney += betAmount;
    }

    // Adauga castigul special pentru Blackjack (de 1.5 ori pariul)
    public double addBlackjackToTotal() {
        totalMoney += betAmount * 1.5;
        return totalMoney;
    }

    // Scade suma pariata din totalul de bani al jucatorului daca acesta pierde
    public double subtractBetFromTotal() {
        totalMoney -= betAmount;
        return totalMoney;
    }

    // Reseteaza mainile jucatorului si dealer-ului pentru o noua runda
    public void resetGame() {
        this.playerHand.clearHand();
        this.dealerHand.clearHand();
    }
}
