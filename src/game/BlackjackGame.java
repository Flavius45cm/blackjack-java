package game;

public class BlackjackGame {
    private final Hand playerHand;
    private final Hand dealerHand;
    private final Deck deck;
    private double betAmount;
    private final double minBet;
    private final double maxBet;
    private double totalMoney;

    public BlackjackGame() {
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.minBet = 5.0;
        this.maxBet = 1000.0;
    }

    public Object loadMoney() {
        return totalMoney = 100.0;
    }

    public boolean isOutOfMoney() {
        return this.totalMoney < this.minBet;
    }

    public Object resetMoney() {
        return this.totalMoney = 100.0;
    }

    public boolean isValidBet(double localBetAmt) {
        return localBetAmt < this.minBet || localBetAmt > this.maxBet || localBetAmt > this.totalMoney;
    }

    // Retourne minBet
    public double getMinBet() {
        return this.minBet;
    }

    public double getMaxBet() { return this.maxBet; }

    public double getTotalMoney() { return this.totalMoney; }

    public void setBet(double amt) {
        this.betAmount = amt;
    }

    public void deal() {
        this.playerHand.addCard(deck.drawCard());
        this.playerHand.addCard(deck.drawCard());
        this.dealerHand.addCard(deck.drawCard());
        this.dealerHand.addCard(deck.drawCard());
    }

    public void hit() {
        playerHand.addCard(deck.drawCard());
    }

    public void stand() {
        while (dealerHand.getPoints() < 17)
            dealerHand.addCard(deck.drawCard());
    }


    public Card getDealerShowCard() {
        return this.dealerHand.getCards().get(1);
    }

    public Hand getDealerHand() {
        return this.dealerHand;
    }

    public Hand getPlayerHand() {
        return this.playerHand;
    }

    // Inghetata
    public boolean isBlackjackOrBust() {
        return playerHand.isBlackjack() || playerHand.isBust() || dealerHand.isBlackjack() || dealerHand.isBust();
    }

    public boolean isPush() {
        return playerHand.getPoints() <= 21 && playerHand.getPoints() == dealerHand.getPoints();
    }

    public boolean playerWins() {
        return !playerHand.isBust() && playerHand.getPoints() > dealerHand.getPoints() || playerHand.getPoints() == 21 ||  playerHand.isBlackjack() || dealerHand.isBust();
    }

    public Object addBetToTotal() {
        return totalMoney += betAmount;
    }

    public Object addBlackjackToTotal() {
        return totalMoney += betAmount * 1.5;
    }

    public Object subtractBetFromTotal() { return totalMoney -= betAmount; }

    public void resetGame() {
        this.playerHand.clearHand();
        this.dealerHand.clearHand();
    }
}

