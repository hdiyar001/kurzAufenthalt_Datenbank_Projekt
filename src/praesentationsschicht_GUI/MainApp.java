package praesentationsschicht_GUI;

import logikschicht.Modell;
import javafx.application.Application;
import javafx.stage.Stage;

/*

 *
 * @author Diyar
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Desgin Pattern
        Modell.getInstance().getViewFacotry().showLoginWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}
