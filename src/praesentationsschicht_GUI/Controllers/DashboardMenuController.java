package praesentationsschicht_GUI.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import logikschicht.Modell;
import javafx.stage.Stage;

/**
 *
 * @author Diyar
 */
public class DashboardMenuController implements Initializable {

    @FXML
    private Button wohnungen_btn;

    @FXML
    private Button vermieten_btn;

    @FXML
    private Button buchungen_btn;

    @FXML
    private Button nachrrichten_btn;

    @FXML
    private Button account_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button report_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    private void addListener() {
        wohnungen_btn.setOnAction((e) -> onWohnung());
        vermieten_btn.setOnAction((e) -> onVermieten());
        buchungen_btn.setOnAction((e) -> onBuchungen());
        nachrrichten_btn.setOnAction((e) -> onNachrichten());
        account_btn.setOnAction((e) -> onAccount());
        logout_btn.setOnAction((e) -> onLogout());
    }

    private void onWohnung() {
        Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().set("Wohnung");
    }

    private void onVermieten() {
        Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().set("Vermieten");
    }

    private void onBuchungen() {
        Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().set("Buchungen");
    }

    private void onNachrichten() {
        Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().set("Nachrichten");
    }

    private void onAccount() {
        Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().set("Account");
    }

    private void onLogout() {
        //We can get the stage of this window by any these avaliable controlls obove
        Stage stage = (Stage) logout_btn.getScene().getWindow();
        Modell.getInstance().getViewFacotry().closeStage(stage);
        Modell.getInstance().getViewFacotry().showLoginWindow();
    }
}
