package nl.michelbijnen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditAccountController {

    private final int columns = 25;
    private final int width = 512;
    private final int height = 512;

    private GridPane grid;

    private Text sceneTitle;


    private Label lbUsername;
    private TextField tbUsername;

    private Label lbPassword;
    private PasswordField tbPassword;

    private Label lbFirstName;
    private TextField tbFirstName;

    private Label lbLastName;
    private TextField tbLastName;

    private Label lbEmail;
    private TextField tbEmail;

    private Label lbGender;
    private ComboBox cbGender;

    private Button btEdit;
    private Button btMainMenu;

    private Label lbOldPassword;
    private PasswordField tbOldPassword;

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

        this.lbFirstName = new Label("First name:");
        GridPane.setConstraints(this.lbFirstName, 0, 3);
        this.grid.getChildren().add(this.lbFirstName);
        this.tbFirstName = new TextField();
        this.tbFirstName.setPromptText("First name");
        this.tbFirstName.setPrefColumnCount(5);
        GridPane.setConstraints(this.tbFirstName, 1, 3);
        this.grid.getChildren().add(this.tbFirstName);

        this.lbLastName = new Label("Last name:");
        GridPane.setConstraints(this.lbLastName, 0, 4);
        this.grid.getChildren().add(this.lbLastName);
        this.tbLastName = new TextField();
        this.tbLastName.setPromptText("Last name");
        this.tbLastName.setPrefColumnCount(5);
        GridPane.setConstraints(this.tbLastName, 1, 4);
        this.grid.getChildren().add(this.tbLastName);

        this.lbEmail = new Label("Email:");
        GridPane.setConstraints(this.lbEmail, 0, 5);
        this.grid.getChildren().add(this.lbEmail);
        this.tbEmail = new TextField();
        this.tbEmail.setPromptText("Email");
        this.tbEmail.setPrefColumnCount(5);
        GridPane.setConstraints(this.tbEmail, 1, 5);
        this.grid.getChildren().add(this.tbEmail);

        this.lbGender = new Label("Gender:");
        GridPane.setConstraints(this.lbGender, 0, 6);
        this.grid.getChildren().add(this.lbGender);
        ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
        this.cbGender = new ComboBox(genders);
        GridPane.setConstraints(this.cbGender, 1, 6);
        this.grid.getChildren().add(this.cbGender);

        this.lbOldPassword = new Label("Old password:");
        GridPane.setConstraints(this.lbOldPassword, 0, 7);
        this.grid.getChildren().add(this.lbOldPassword);
        this.tbOldPassword = new PasswordField();
        this.tbOldPassword.setPromptText("Password");
        this.tbOldPassword.setPrefColumnCount(5);
        GridPane.setConstraints(this.tbOldPassword, 1, 7);
        this.grid.getChildren().add(this.tbOldPassword);


        this.btMainMenu = new Button("Main menu");
        this.btMainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new MainController().startScene(primaryStage);
            }
        });
        {
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.BOTTOM_LEFT);
            hBox.getChildren().add(this.btMainMenu);
            this.grid.add(hBox, 0, 8);
        }

        this.btEdit = new Button("Edit");
        this.btEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO HIER AANPASSEN
            }
        });
        {
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.BOTTOM_RIGHT);
            hBox.getChildren().add(this.btEdit);
            this.grid.add(hBox, 1, 8);
        }

        primaryStage.setTitle("Chess - Edit");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
