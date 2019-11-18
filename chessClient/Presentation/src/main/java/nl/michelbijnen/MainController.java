package nl.michelbijnen;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;

public class MainController implements IController {

    private final int columns = 25;
    private final int width = 512;
    private final int height = 512;

    private GridPane grid;

    private Text sceneTitle;


    private Label lbScoreText;
    private Label lbScore;


    private Label lbOnline;
    private CheckBox cbOnline;

    private Label lbDifficulty;
    private ComboBox cbDifficulty;


    private Button btStart;
    private Button btEditAccount;


    public void startScene(Stage primaryStage) {

        this.grid = new GridPane();
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setPadding(new Insets(this.columns, this.columns, this.columns, this.columns));
        this.grid.setVgap(10);
        this.grid.setHgap(10);

        Scene scene = new Scene(this.grid, this.width, this.height);


        this.sceneTitle = new Text("Chess");
        this.sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.sceneTitle, 0, 0, 2, 1);

        // Get the user and save it in the session
        Factory.getIUserLogic(this).getLoggedInUser();

        this.lbScoreText = new Label("Score:");
        GridPane.setConstraints(this.lbScoreText, 0, 1);
        this.grid.getChildren().add(this.lbScoreText);
        this.lbScore = new Label("Loading...");
        GridPane.setConstraints(this.lbScore, 1, 1);
        this.grid.getChildren().add(this.lbScore);

        this.lbOnline = new Label("Online:");
        GridPane.setConstraints(this.lbOnline, 0, 2);
        this.grid.getChildren().add(this.lbOnline);
        this.cbOnline = new CheckBox("");
        this.cbOnline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (cbOnline.isSelected()) {
                    lbDifficulty.setVisible(false);
                    cbDifficulty.setVisible(false);
                }
                else {
                    lbDifficulty.setVisible(true);
                    cbDifficulty.setVisible(true);
                }
            }
        });
        GridPane.setConstraints(this.cbOnline, 1, 2);
        this.grid.getChildren().add(this.cbOnline);

        this.lbDifficulty = new Label("Difficulty");
        GridPane.setConstraints(this.lbDifficulty, 0, 3);
        this.grid.getChildren().add(this.lbDifficulty);
        ObservableList<String> difficulties = FXCollections.observableArrayList("VERYEASY", "EASY", "MEDIUM", "HARD", "VERYHARD");
        this.cbDifficulty = new ComboBox(difficulties);
        GridPane.setConstraints(this.cbDifficulty, 1, 3);
        this.grid.getChildren().add(this.cbDifficulty);

        this.btStart = new Button("Start");
        this.btStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (cbOnline.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Searching for an opponent");
                    new MatchmakingController().startScene(primaryStage);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Starting game against ai.");
                    new BoardController().startScene(primaryStage, PlayerColor.WHITE, true, Difficulty.valueOf(cbDifficulty.getSelectionModel().getSelectedItem().toString()));
                }
            }
        });
        {
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.BOTTOM_RIGHT);
            hBox.getChildren().add(this.btStart);
            this.grid.add(hBox, 1, 4);
        }

        this.btEditAccount = new Button("Edit");
        this.btEditAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new EditAccountController().startScene(primaryStage);
            }
        });
        {
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.BOTTOM_LEFT);
            hBox.getChildren().add(this.btEditAccount);
            this.grid.add(hBox, 0, 4);
        }


        primaryStage.setTitle("Chess - Main menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void response(Object response) {
        User user = (User) response;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lbScore.setText("" + user.getScore());
            }
        });
    }

    @Override
    public void error() {
        JOptionPane.showMessageDialog(null, "Something went wrong");
    }
}
