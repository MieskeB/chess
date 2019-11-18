package nl.michelbijnen;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;

public class MatchmakingController implements IController {

    private final int columns = 25;
    private final int width = 512;
    private final int height = 512;

    private GridPane grid;

    private Text sceneTitle;

    private Label lbMatchmaking;
    private Button btCancel;

    private Stage primaryStage;

    public void startScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        IController controller = this;

        this.grid = new GridPane();
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setPadding(new Insets(this.columns, this.columns, this.columns, this.columns));
        this.grid.setVgap(10);
        this.grid.setHgap(10);

        Scene scene = new Scene(this.grid, this.width, this.height);

        // Tell the server that you want to start searching for a match
        Factory.getIUserLogic(this).findMatch();

        this.sceneTitle = new Text("Chess");
        this.sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.sceneTitle, 0, 0, 2, 1);

        this.lbMatchmaking = new Label("Searching...");
        GridPane.setConstraints(this.lbMatchmaking, 0, 1);
        this.grid.getChildren().add(this.lbMatchmaking);

        this.btCancel = new Button("Cancel");
        this.btCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Factory.getIUserLogic(controller).cancelFindMatch();
                JOptionPane.showMessageDialog(null, "Matchmaking cancelled");
                new MainController().startScene(primaryStage);
            }
        });
        {
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.BOTTOM_RIGHT);
            hBox.getChildren().add(this.btCancel);
            this.grid.add(hBox, 1, 2);
        }

        primaryStage.setTitle("Chess - Searching");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void response(Object response) {
        String opponent = ((String)response).split(":")[0];
        PlayerColor playerColor = PlayerColor.valueOf(((String)response).split(":")[1]);
        JOptionPane.showMessageDialog(null, "Found opponent! You are playing against " + opponent);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                new BoardController().startScene(primaryStage, playerColor, false, Difficulty.MEDIUM);
            }
        });
    }

    @Override
    public void error() {
        JOptionPane.showMessageDialog(null, "Something went wrong");
    }
}
