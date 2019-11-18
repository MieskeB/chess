package nl.michelbijnen;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;

public class LoginController implements IController {

    private final int columns = 25;
    private final int width = 512;
    private final int height = 512;

    private GridPane grid;

    private Text sceneTitle;

    private Label lbUsername;
    private TextField tbUsername;

    private Label lbPassword;
    private PasswordField tbPassword;

    private Button btLogin;
    private Button btForgotPassword;
    private Button btRegister;

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


        this.sceneTitle = new Text("Chess");
        this.sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.grid.add(this.sceneTitle, 0, 0, 2, 1);

        this.lbUsername = new Label("Username:");
        GridPane.setConstraints(this.lbUsername, 0, 1);
        this.grid.getChildren().add(this.lbUsername);
        this.tbUsername = new TextField();
        this.tbUsername.setPromptText("Username");
        this.tbUsername.setPrefColumnCount(5);
        GridPane.setConstraints(this.tbUsername, 1, 1);
        this.grid.getChildren().add(this.tbUsername);

        this.lbPassword = new Label("Password:");
        GridPane.setConstraints(this.lbPassword, 0, 2);
        this.grid.getChildren().add(this.lbPassword);
        this.tbPassword = new PasswordField();
        this.tbPassword.setPromptText("Password");
        this.tbPassword.setPrefColumnCount(5);
        GridPane.setConstraints(this.tbPassword, 1, 2);
        this.grid.getChildren().add(this.tbPassword);

        this.btLogin = new Button("Log in");
        this.btLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Factory.getIUserLogic(controller).login(tbUsername.getText(), tbPassword.getText());
            }
        });
        {
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.BOTTOM_RIGHT);
            hBox.getChildren().add(this.btLogin);
            this.grid.add(hBox, 1, 4);
        }
        this.btForgotPassword = new Button("Forgot");
        this.btForgotPassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO REDIRECT TO FORGOT PASSWORD
            }
        });
        {
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.BOTTOM_LEFT);
            hBox.getChildren().add(this.btForgotPassword);
            this.grid.add(hBox, 0, 4);
        }
        this.btRegister = new Button("Register");
        this.btRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new RegisterController().startScene(primaryStage);
            }
        });
        {
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.BOTTOM_RIGHT);
            hBox.getChildren().add(this.btRegister);
            this.grid.add(hBox, 1, 5);
        }

        primaryStage.setTitle("Chess - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void response(Object response) {
        if ((Boolean)response) {
            JOptionPane.showMessageDialog(null, "Successfully logged in!");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    new MainController().startScene(primaryStage);
                }
            });
        }
        else {
            JOptionPane.showMessageDialog(null, "Login credentials incorrect");
        }
    }

    @Override
    public void error() {
        JOptionPane.showMessageDialog(null, "Something went wrong");
    }
}
