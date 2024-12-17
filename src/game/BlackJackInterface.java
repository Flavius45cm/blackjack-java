package game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BlackJackInterface extends Application {
    private static BlackjackGame game;
    static String money;
    static Object saveMoney;

    // LABELS
    private final Label moneyLabel = new Label("Money : ");
    private final Label betLabel = new Label("Bet : ");
    private final Label dealerLabel = new Label("DEALER");
    private final Label dealerCardsLabel = new Label("Cards : ");
    private final Label dealerCardsPointsLabel = new Label("Points : ");
    private final Label playerLabel = new Label("YOU");
    private final Label playerCardsLabel = new Label("Cards : ");
    private final Label playerCardsPointsLabel = new Label("Points : ");
    private final Label resultGameLabel = new Label("RESULT : ");

    // TEXT FIELDS & LISTVIEWS
    private static final TextField moneyTextField = new TextField();
    private static final TextField betTextField = new TextField();
    private static final ListView<Object> dealerCardsListView = new ListView<>();
    private static final TextField dealerCardsPointsTextField = new TextField();
    private static final ListView<Object> playerCardsListView = new ListView<>();
    private static final TextField playerCardsPointsTextField = new TextField();
    private static final TextField resultGameTextField = new TextField();

    // BUTTONS
    static Button buttonHit = new Button("Hit");
    static Button buttonStand = new Button("Stand");
    static Button buttonPlay = new Button("Play");
    static Button buttonExit = new Button("Exit");

    private static void showHands() {
        dealerCardsListView.getItems().add(game.getDealerShowCard().display());
        showPlayerHand();
    }

    private static void showDealerHand() {
        for (Card card : game.getDealerHand().getCards()) {
            dealerCardsListView.getItems().add(card.display());
        }
    }

    private static void showPlayerHand() {
        for(Card card: game.getPlayerHand().getCards()) {
            playerCardsListView.getItems().add(card.display());
        }
    }

    private static void buttonHitClick() {
        if(game.isBlackjackOrBust()) {
            showWinner();
        } else {
            game.hit();
            showWinner();
        }
    }

    private void buttonStandClick() {
        if (game.isBlackjackOrBust()) {
            showWinner();
        } else {
            game.stand();
            showWinner();
        }
    }

    public static void showWinner() {
        playerCardsListView.getItems().clear();
        showPlayerHand();
        dealerCardsListView.getItems().clear();
        showDealerHand();

        String playerPoints = String.valueOf(game.getPlayerHand().getPoints());
        playerCardsPointsTextField.setText(playerPoints);

        String dealerPoints = String.valueOf(game.getDealerHand().getPoints());
        dealerCardsPointsTextField.setText(dealerPoints);

        if (game.isPush()) {
            resultGameTextField.setText("Push !");
            endGame();

        } else if (game.getPlayerHand().isBlackjack()) {
            saveMoney = game.addBlackjackToTotal();
            resultGameTextField.setText("BLACKJACK! You win!");
            money = String.valueOf(saveMoney);
            endGame();

        } else if (game.playerWins()) {
            saveMoney = game.addBetToTotal();
            resultGameTextField.setText("You win!");
            money = String.valueOf(saveMoney);
            endGame();

        } else {
            resultGameTextField.setText("You lose !");
            saveMoney = game.subtractBetFromTotal();
            money = String.valueOf(saveMoney);
            endGame();
        }
        showMoney();
    }

    private static void showMoney() {
        money = String.valueOf(saveMoney);
        moneyTextField.setText(money);
    }

    private void buttonPlayClick() {
        resetGame();
        outOfMoney();

        if (betTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid data !");
            alert.setContentText("Bet must be set before clicking on play button");
            alert.showAndWait();

        } else {
            double bet = Double.parseDouble(betTextField.getText());

            if (!game.isValidBet(bet)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Bet !");
                alert.setContentText("Bet must be between " + game.getMinBet() + " and " + game.getMaxBet() + ". You also cannot set your bet higher than your money.");
                alert.showAndWait();

            } else {
                game.setBet(bet);
                game.deal();

                showHands();
                hitOrStandButtons();
                noPlayOrExitButtons();
            }
        }
    }

    public static  void outOfMoney(){
        if(game.getTotalMoney() == 0) {
            saveMoney = game.resetMoney();
            moneyTextField.setText("100");
        } else {
            saveMoney = game.getTotalMoney();
        }
    }

    private static void resetGame() {
        game.resetGame();
        playerCardsListView.getItems().clear();
        dealerCardsListView.getItems().clear();
        resultGameTextField.clear();
        dealerCardsPointsTextField.clear();
        playerCardsPointsTextField.clear();
    }

    public static void endGame() {
        noHitOrStandButtons();
        PlayOrExitButtons();
    }

    private static void hitOrStandButtons() {
        buttonHit.setDisable(false);
        buttonStand.setDisable(false);
    }

    private static void noHitOrStandButtons() {
        buttonHit.setDisable(true);
        buttonStand.setDisable(true);
    }

    private static void noPlayOrExitButtons() {
        buttonPlay.setDisable(true);
        buttonExit.setDisable(true);
    }

    public static void PlayOrExitButtons() {
        buttonPlay.setDisable(false);
        buttonExit.setDisable(false);
    }

    private void buttonExitClick() {
        System.exit(0);
    }

    public static void startGame() {
        game = new BlackjackGame();
        saveMoney = game.loadMoney();

        if (moneyTextField.getText().isEmpty()) {
            money = String.valueOf(saveMoney);
            moneyTextField.setText(money);
            moneyTextField.setEditable(false);
        }
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Blackjack");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);

        grid.setPadding(new Insets(25,25,25,25));
        grid.setHgap(10);
        grid.setVgap(10);

        dealerCardsListView.setPrefHeight(3 * 24);
        playerCardsListView.setPrefHeight(3 * 24);

        VBox appContainer = getVBox();

        grid.add(appContainer, 0, 0, 2,3);

        noHitOrStandButtons();

        playerCardsPointsTextField.setEditable(false);
        dealerCardsPointsTextField.setEditable(false);

        buttonHit.setOnAction(actionEvent -> buttonHitClick());
        buttonStand.setOnAction(actionEvent -> buttonStandClick());
        buttonPlay.setOnAction(actionEvent -> buttonPlayClick());
        buttonExit.setOnAction(actionEvent -> buttonExitClick());

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox getVBox() {
        HBox moneyBox = new HBox(15, moneyLabel, moneyTextField);
        HBox betBox = new HBox(33, betLabel, betTextField);
        HBox dealerBox = new HBox(15, dealerCardsLabel, dealerCardsListView);
        HBox dealerPointsBox = new HBox(15, dealerCardsPointsLabel, dealerCardsPointsTextField);
        HBox playerBox = new HBox(15, playerCardsLabel, playerCardsListView);
        HBox playerPointsBox = new HBox(15, playerCardsPointsLabel, playerCardsPointsTextField);
        HBox hitOrStandButtons = new HBox(10, buttonHit, buttonStand);
        HBox resultGameBox = new HBox(5, resultGameLabel, resultGameTextField);
        HBox playOrExitButtons = new HBox(10, buttonPlay, buttonExit);

        return new VBox(10, moneyBox, betBox, dealerLabel, dealerBox, dealerPointsBox, playerLabel, playerBox, playerPointsBox, hitOrStandButtons, resultGameBox, playOrExitButtons);
    }

    public static void main(String[] args) {
        startGame();
        launch(args);
    }
}
