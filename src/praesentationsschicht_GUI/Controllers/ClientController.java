package praesentationsschicht_GUI.Controllers;

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
                case "Wohnung" ->
                    client_parent.setCenter(Modell.getInstance().getViewFacotry().getWohnungView());
                case "Vermieten" ->
                    client_parent.setCenter(Modell.getInstance().getViewFacotry().getVermietenView());
                case "Buchungen" ->
                    client_parent.setCenter(Modell.getInstance().getViewFacotry().getBuchungenView());
                case "Nachrichten" ->
                    client_parent.setCenter(Modell.getInstance().getViewFacotry().getNachrichtenView());
                case "Account" ->
                    client_parent.setCenter(Modell.getInstance().getViewFacotry().getAccountView());
//                default ->
//                    client_parent.setCenter(Modell.getInstance().getViewFacotry().getView("Auslogen"));
            }
        });

    }

}
