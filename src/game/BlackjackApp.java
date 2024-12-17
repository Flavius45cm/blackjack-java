package game;

public class BlackjackApp {
    private static BlackjackGame game;

    public static void main(String[] args) {
        System.out.println("BLACKJACK!");
        System.out.println("Blackjack payout is 3:2");
        System.out.println();

        game = new BlackjackGame();
        game.loadMoney();

        String playAgain = "y";
        while (playAgain.equalsIgnoreCase("y")) {

            game.resetGame();

            if(game.isOutOfMoney()) {
                if(!buyMoreChips())
                    break;
            }

            showMoney();

            getBetAmount();

            game.deal();

            while(!game.isBlackjackOrBust()) {
                showHands();
                String choice = getHitOrStand();

                if (choice.equalsIgnoreCase("h")) {
                    game.hit();
                } else {
                    game.stand();
                    break;
                }
            }
            showWinner();

            String[] message = {"y", "n"};
            playAgain = Console.getString("\nPlay again ? (y/n) : ", message);
        }
        System.out.println("\nBye!");
    }

    private static boolean buyMoreChips() {
        String[] outOfMoneyMessage = {"y", "n"};
        String choice = Console.getString("\nOut of money! Would you like to add more? (y/n) : ", outOfMoneyMessage);

        if (choice.equalsIgnoreCase("y")) {
            game.resetMoney();
            return true;
        }
        return false;
    }

    private static void getBetAmount() {
        while (true) {
            double localAmount = Console.getDouble("\nBet amount : ");
            if (game.isValidBet(localAmount)) {
                game.setBet(localAmount);
                break;
            } else {
                System.out.printf("\nBet must be between $%,.2f  and $%,.2f \n", game.getMinBet(), game.getMaxBet());
            }
        }
    }

    private static String getHitOrStand() {
        String[] message = {"h", "s"};
        return Console.getString("\nHit or Stand ? (h/s) : ", message);
    }

    private static void showHands() {
        showDealerShowCard();
        showPlayerHand();
    }

    private static void showDealerShowCard() {
        System.out.println("\nDEALER'S SHOW CARD ");
        System.out.println(game.getDealerShowCard().display());
    }

    private static void showDealerHand() {
        System.out.println("\nDEALER'S CARDS : ");

        for (Card card : game.getDealerHand().getCards()) {
            System.out.println(card.display());
        }
    }

    private static void showPlayerHand() {
        System.out.println("\nYOUR CARDS ");

        for (Card card : game.getPlayerHand().getCards()) {
            System.out.println(card.display());
        }
    }

    private static void showMoney() {
        System.out.printf("Total Money : %.2f %n", game.getTotalMoney());
    }

    private static void showWinner() {
        showPlayerHand();
        System.out.printf("YOUR POINTS: %d%n", game.getPlayerHand().getPoints());

        showDealerHand();
        System.out.printf("DEALER'S POINTS: %d%n%n", game.getDealerHand().getPoints());

        if (game.isPush()) {
            System.out.println("Push!");
        } else if (game.getPlayerHand().isBlackjack()) {
            System.out.println("BLACKJACK! You win!");
            game.addBlackjackToTotal();
        } else if (game.playerWins()) {
            System.out.println("You win!");
            game.addBetToTotal();
        } else {
            System.out.println("Sorry, you lose.");
            game.subtractBetFromTotal();
        }
        showMoney();
    }
}

