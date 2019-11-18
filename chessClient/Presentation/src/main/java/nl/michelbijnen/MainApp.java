package nl.michelbijnen;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginController controller = new LoginController();
//        BoardController controller = new BoardController();
        controller.startScene(primaryStage/*, PlayerColor.WHITE, true, Difficulty.EASY*/);
    }
}
