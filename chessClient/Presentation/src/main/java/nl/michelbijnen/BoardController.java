package nl.michelbijnen;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;


public class BoardController implements IBoardController, IController {

    private final int borderSize = 10;
    private final int squareWidth = 72;
    private final int squareHeight = 72;
    private final int buttonWidth = 180;
    private final int buttonHeight = 36;

    private final int squaresHorizontal = 8;
    private final int squaresVertical = 8;

    private Rectangle[][] board;
    private Rectangle[][] movementBoard;

    private Button btStartNewGame;

    private ChessPiece selectedChessPiece = null;

    private Group root;

    private IGameLogic gameLogic = Factory.getIGameLogic(this, this);

    private PlayerColor playerColor;
    private Difficulty difficulty;

    private boolean singleplayer;

    private Stage primaryStage;

    public void startScene(Stage primaryStage, PlayerColor playerColor, boolean singleplayer, Difficulty difficulty) {

        this.primaryStage = primaryStage;

        this.difficulty = difficulty;
        this.playerColor = playerColor;
        this.singleplayer = singleplayer;

        GridPane grid = new GridPane();
        grid.setHgap(this.borderSize);
        grid.setVgap(this.borderSize);
        grid.setPadding(new Insets(this.borderSize, this.borderSize, this.borderSize, this.borderSize));

        this.root = new Group();
        Scene scene = new Scene(this.root, this.squareWidth * this.squaresHorizontal, this.squareHeight * this.squaresVertical + this.buttonHeight);
        this.root.getChildren().add(grid);

        this.board = new Rectangle[this.squaresHorizontal][this.squaresVertical];
        {
            Color horizontalColor = Color.DARKGRAY;
            for (int i = 0; i < this.squaresHorizontal; i++) {
                horizontalColor = horizontalColor == Color.WHITE ? Color.DARKGRAY : Color.WHITE;
                Color verticalColor = horizontalColor;
                for (int j = 0; j < this.squaresVertical; j++) {
                    int x = this.squareWidth * i;
                    int y = this.squareHeight * j;

                    Rectangle rectangle = new Rectangle(x, y, this.squareWidth, this.squareHeight);
                    rectangle.setFill(verticalColor);
                    rectangle.setVisible(true);

                    final int hor = i;
                    final int ver = j;

                    rectangle.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            onClickChessPiece(new Point(hor, ver));
                        }
                    });

                    this.board[i][j] = rectangle;

                    verticalColor = verticalColor == Color.WHITE ? Color.DARKGRAY : Color.WHITE;
                }
            }
        }

        /*
        //TODO start new game button
        this.btStartNewGame = new Button("Start new game");
        this.btStartNewGame.setMinWidth(this.buttonWidth);
        this.btStartNewGame.setMinHeight(this.buttonHeight);
        Tooltip tooltipStartNewGame = new Tooltip("Press this button to start a new game");
        this.btStartNewGame.setTooltip(tooltipStartNewGame);
        this.btStartNewGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        //grid.add(this.btStartNewGame, 1, 2, 3, 4);
        */

        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();

        this.gameLogic.initializeGame(this.playerColor, this.singleplayer, difficulty);
    }

    @Override
    public void updateBoard() {
        root.getChildren().clear();

        for (Rectangle[] rectangles : board) {
            for (Rectangle rectangle : rectangles) {
                root.getChildren().add(rectangle);
            }
        }

        movementBoard = new Rectangle[squaresHorizontal][squaresVertical];
        for (int i = 0; i < squaresHorizontal; i++) {
            for (int j = 0; j < squaresVertical; j++) {
                final int colx = i;
                final int coly = j;
                final int x = i * squareWidth;
                final int y = j * squareHeight;

                Rectangle rectangleMovement = new Rectangle(x, y, squareWidth, squareHeight);
                ChessPiece chessPiece = gameLogic.getChessBoard().getChessPieceOfPoint(new Point(i, j));
                if (chessPiece != null) {
                    Image image = new Image("Images/" + chessPiece.playerColor.toString() + "/" + chessPiece.toString() + ".png");
                    rectangleMovement.setFill(new ImagePattern(image));
                    rectangleMovement.setVisible(true);
                    rectangleMovement.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            onClickChessPiece(new Point(colx, coly));
                        }
                    });
                } else {
                    rectangleMovement.setVisible(false);
                }
                movementBoard[i][j] = rectangleMovement;
                root.getChildren().add(rectangleMovement);
            }
        }
    }

    @Override
    public void isLoading(boolean isLoading) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = (Stage) root.getScene().getWindow();
                if (isLoading) {
                    stage.setTitle("Chess - Loading");
                } else {
                    stage.setTitle("Chess");
                }
            }
        });
    }

    @Override
    public void isFinished(PlayerColor playerColor) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "Player " + playerColor + " has won!");
                new MainController().startScene(primaryStage);
            }
        });
    }

    private void onClickChessPiece(Point p) {
        ChessPiece chessPiece = gameLogic.getChessBoard().getChessPieceOfPoint(p);

        if (this.selectedChessPiece == null) {
            if (chessPiece != null) {
                if (!gameLogic.isYourTurn(chessPiece.getPlayerColor())) {
                    JOptionPane.showMessageDialog(null, "Not your turn!");
                    return;
                }
            }

            this.selectedChessPiece = chessPiece;
        } else if (this.selectedChessPiece.getPlayerColor() != this.playerColor) {
            JOptionPane.showMessageDialog(null, "Wrong player");
        } else {
            if (!gameLogic.moveChessPiece(this.selectedChessPiece, p)) {
                JOptionPane.showMessageDialog(null, "You cannot place that here!");
            }

            this.selectedChessPiece = null;
        }
    }

    @Override
    public void response(Object response) {
        OpponentPoints opponentPoints = (OpponentPoints) response;
        this.gameLogic.moveOpponentChessPiece(opponentPoints.getFrom(), opponentPoints.getTo());
    }

    @Override
    public void error() {

    }
}
