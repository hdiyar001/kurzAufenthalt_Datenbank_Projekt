package praesentationsschicht_GUI;

import logikschicht.Modell;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Diyar, Hussam und Ronida
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Verwendung des Singleton-Patterns für die Modell-Instanz.
        Modell.getInstance().getViewFacotry().showLoginWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}
