package praesentationsschicht_GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import logikschicht.Modell;

/**
 *
 * @author Diyar
 */
public class ClientController implements Initializable {

    @FXML
    public BorderPane client_parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) ->
        {
            switch (newVal)
            {
//                case "Account" ->
//                    client_parent.setCenter(Modell.getInstance().getViewFacotry().getAccountView());
                case "Dashboard" ->
                    client_parent.setCenter(Modell.getInstance().getViewFacotry().getDashBoardView());
//                case "Workout" ->
//                    client_parent.setCenter(Modell.getInstance().getViewFacotry().getWorkoutView());
//                case "Settings" ->
//                    client_parent.setCenter(Modell.getInstance().getViewFacotry().getSettingsView());
//                case "Statistic" ->
//                    client_parent.setCenter(Modell.getInstance().getViewFacotry().getStatisticView());

            }
        });

    }

}
