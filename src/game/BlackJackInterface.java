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

// Clasa principală care definește interfața grafică a jocului Blackjack
public class BlackJackInterface extends Application {
    private BlackjackGame game; // Instanță a jocului de Blackjack
    String money; // Variabilă pentru afișarea banilor
    double saveMoney; // Variabilă pentru salvarea banilor actuali

    // Etichete pentru interfață
    private final Label moneyLabel;
    private final Label betLabel;
    private final Label dealerLabel;
    private final Label dealerCardsLabel;
    private final Label dealerCardsPointsLabel;
    private final Label playerLabel;
    private final Label playerCardsLabel;
    private final Label playerCardsPointsLabel;
    private final Label resultGameLabel;

    //Câmpuri pentru introducere date și afișare
    private final TextField moneyTextField;
    private final TextField betTextField;
    private final ListView<Object> dealerCardsListView;
    private final TextField dealerCardsPointsTextField;
    private final ListView<Object> playerCardsListView;
    private final TextField playerCardsPointsTextField;
    private final TextField resultGameTextField;

    // Butoane interfata
    private final Button buttonHit;
    private final Button buttonStand;
    private final Button buttonPlay;
    private final Button buttonExit;

    // Constructorul clasei
    public BlackJackInterface() {
        moneyLabel = new Label("Money : "); // Etichetă pentru bani
        betLabel = new Label("Bet : "); // Etichetă pentru pariu
        dealerLabel = new Label("DEALER"); // Titlu pentru dealer
        dealerCardsLabel = new Label("Cards : "); // Etichetă pentru cărțile dealerului
        dealerCardsPointsLabel = new Label("Points : "); // Etichetă pentru punctele dealerului
        playerLabel = new Label("YOU"); // Titlu pentru jucător
        playerCardsLabel = new Label("Cards : "); // Etichetă pentru cărțile jucătorului
        playerCardsPointsLabel = new Label("Points : "); // Etichetă pentru punctele jucătorului
        resultGameLabel = new Label("RESULT : "); // Etichetă pentru rezultat

        //Câmpuri pentru introducere date și afișare
        moneyTextField = new TextField();
        betTextField = new TextField();
        dealerCardsListView = new ListView<>();
        dealerCardsPointsTextField = new TextField();
        playerCardsListView = new ListView<>();
        playerCardsPointsTextField = new TextField();
        resultGameTextField = new TextField();

        // Inițializarea jocului și încărcarea sumei curente
        game = new BlackjackGame();
        saveMoney = game.loadMoney();
        money = String.valueOf(saveMoney);
        moneyTextField.setText(money);
        moneyTextField.setEditable(false);

        // Butoane interfata
        buttonHit = new Button("Hit");
        buttonStand = new Button("Stand");
        buttonPlay = new Button("Play");
        buttonExit = new Button("Exit");
    }

    // Metodă care afișează cărțile dealerului și ale jucătorului
    private void showHands() {
        dealerCardsListView.getItems().add(game.getDealerShowCard().display());
        showPlayerHand();
    }

    // Afișează toate cărțile dealerului
    private void showDealerHand() {
        for (Card card : game.getDealerHand().getCards()) {
            dealerCardsListView.getItems().add(card.display());
        }
    }

    // Afișează cărțile jucătorului
    private void showPlayerHand() {
        for (Card card : game.getPlayerHand().getCards()) {
            playerCardsListView.getItems().add(card.display());
        }
    }

    // jucătorul ia o carte
    private void buttonHitOrStandClick() {
        if (!game.isBlackjackOrBust()) {
            game.hit();
        }
        showWinner();
    }

    // Afișează câștigătorul și actualizează interfața
    public void showWinner() {
        playerCardsListView.getItems().clear();
        showPlayerHand();
        dealerCardsListView.getItems().clear();
        showDealerHand();

        // Actualizează punctele jucătorului și dealerului
        String playerPoints = String.valueOf(game.getPlayerHand().getPoints());
        playerCardsPointsTextField.setText(playerPoints);

        String dealerPoints = String.valueOf(game.getDealerHand().getPoints());
        dealerCardsPointsTextField.setText(dealerPoints);

        // Determină rezultatul rundei
        if (game.isPush()) {
            resultGameTextField.setText("Push !");
            endGame();

        } else if (game.getPlayerHand().isBlackjack() || game.playerWins()) {
            saveMoney = game.addBlackjackToTotal();
            resultGameTextField.setText(game.getPlayerHand().isBlackjack() ? "BLACKJACK! " : " " + "You win!");
            money = String.valueOf(saveMoney);
            endGame();
        } else {
            saveMoney = game.subtractBetFromTotal();
            resultGameTextField.setText("You lose !");
            money = String.valueOf(saveMoney);
            endGame();
        }
        showMoney();
    }

    // Afișează banii actuali ai jucătorului
    private void showMoney() {
        money = String.valueOf(saveMoney);
        this.moneyTextField.setText(money);
    }

    //începe o nouă rundă
    private void buttonPlayClick() {
        resetGame();
        outOfMoney();

        // Verifică dacă pariul a fost setat
        if (betTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid data !");
            alert.setContentText("Bet must be set before clicking on play button");
            alert.showAndWait();

        } else {
            double bet = Double.parseDouble(betTextField.getText());

            System.out.println(bet);

            // Verifică validitatea pariului
            if (!game.isValidBet(bet)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Bet !");
                alert.setContentText("Bet must be between " + game.getMinBet() + " and " + game.getMaxBet() + ". You also cannot set your bet higher than your money.");
                alert.showAndWait();

            } else {
                game.setBet(bet); // Setează suma pariului
                game.deal(); // Împarte cărțile

                showHands(); // Afișează cărțile jucătorului și dealerului
                hitOrStandButtons(); // Activează butoanele "Hit" și "Stand"
                noPlayOrExitButtons(); // Dezactivează butoanele "Play" și "Exit"
            }
        }
    }

    public void outOfMoney() {
        // Verifică dacă jucătorul a rămas fără bani
        if (game.getTotalMoney() == 0) {
            saveMoney = game.resetMoney(); // Resetează suma de bani la valoarea inițială
            moneyTextField.setText("100"); // Afișează suma resetată în câmpul pentru bani
        } else {
            saveMoney = game.getTotalMoney(); // Salvează suma curentă de bani
        }
    }

    private void resetGame() {
        game.resetGame(); // Resetează starea jocului pentru o nouă rundă
        playerCardsListView.getItems().clear();
        dealerCardsListView.getItems().clear();
        resultGameTextField.clear();
        dealerCardsPointsTextField.clear();
        playerCardsPointsTextField.clear();
    }

    // Dezactivează butoanele "Hit" și "Stand" și activează "Play" și "Exit"
    public void endGame() {
        noHitOrStandButtons();
        PlayOrExitButtons();
    }

    // Activează butoanele "Hit" și "Stand"
    private void hitOrStandButtons() {
        buttonHit.setDisable(false);
        buttonStand.setDisable(false);
    }

    // Dezactivează butoanele "Hit" și "Stand"
    private void noHitOrStandButtons() {
        buttonHit.setDisable(true);
        buttonStand.setDisable(true);
    }

    // Dezactivează butoanele "Play" și "Exit"
    private void noPlayOrExitButtons() {
        buttonPlay.setDisable(true);
        buttonExit.setDisable(true);
    }

    // Activează butoanele "Play" și "Exit"
    public void PlayOrExitButtons() {
        buttonPlay.setDisable(false);
        buttonExit.setDisable(false);
    }

    private void buttonExitClick() {
        System.exit(0); // Închide aplicația
    }

    public void startGame() {
        // Inițializează jocul Blackjack și încarcă suma de bani a jucătorului
        game = new BlackjackGame(); // Creează o nouă instanță a jocului
        saveMoney = game.loadMoney(); // Încarcă banii salvați anterior

        money = String.valueOf(saveMoney);
        moneyTextField.setText(money);
        moneyTextField.setEditable(false);
    }

    @Override
    public void start(Stage primaryStage) {
        // Inițializează interfața grafică a aplicației
        primaryStage.setTitle("Blackjack"); // Titlul ferestrei

        // Configurarea layout-ului principal folosind GridPane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER); // Aliniere la centru
        grid.setPadding(new Insets(25, 25, 25, 25)); // Marginile exterioare
        grid.setHgap(10); // Spațiu orizontal între elemente
        grid.setVgap(10); // Spațiu vertical între elemente

        // Setează înălțimea listelor pentru cărți
        dealerCardsListView.setPrefHeight(3 * 24);
        playerCardsListView.setPrefHeight(3 * 24);

        VBox appContainer = getVBox(); // Obține containerul principal vertical

        grid.add(appContainer, 0, 0, 2, 3);

        noHitOrStandButtons();

        // Setează câmpurile punctelor ca non-editabile
        playerCardsPointsTextField.setEditable(false);
        dealerCardsPointsTextField.setEditable(false);

        // Asociază acțiunile butoanelor
        buttonHit.setOnAction(actionEvent -> buttonHitOrStandClick());
        buttonStand.setOnAction(actionEvent -> buttonHitOrStandClick());
        buttonPlay.setOnAction(actionEvent -> buttonPlayClick());
        buttonExit.setOnAction(actionEvent -> buttonExitClick());

        // Creează scena și afișează fereastra
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Creează layout-ul vertical cu secțiunile și butoanele jocului
    private VBox getVBox() {
        // Organizare elemente în layout-uri orizontale
        HBox moneyBox = new HBox(15, moneyLabel, moneyTextField);
        HBox betBox = new HBox(33, betLabel, betTextField);
        HBox dealerBox = new HBox(15, dealerCardsLabel, dealerCardsListView);
        HBox dealerPointsBox = new HBox(15, dealerCardsPointsLabel, dealerCardsPointsTextField);
        HBox playerBox = new HBox(15, playerCardsLabel, playerCardsListView);
        HBox playerPointsBox = new HBox(15, playerCardsPointsLabel, playerCardsPointsTextField);
        HBox hitOrStandButtons = new HBox(10, buttonHit, buttonStand);
        HBox resultGameBox = new HBox(5, resultGameLabel, resultGameTextField);
        HBox playOrExitButtons = new HBox(10, buttonPlay, buttonExit);

        // Returnează un container vertical ce conține toate elementele interfeței
        return new VBox(10, moneyBox, betBox, dealerLabel, dealerBox, dealerPointsBox, playerLabel, playerBox, playerPointsBox, hitOrStandButtons, resultGameBox, playOrExitButtons);
    }

    // Punctul de intrare în aplicație
    public static void main(String[] args) {
        BlackJackInterface app = new BlackJackInterface(); // Creează o instanță a aplicației
        app.startGame(); // Inițializează jocul
        launch(args); // Pornește aplicația grafică
    }
}
