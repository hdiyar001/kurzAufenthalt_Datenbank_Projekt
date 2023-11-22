package praesentationsschicht_GUI.Controllers;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logikschicht.Benutzer;
import logikschicht.Benutzerverwaltung;
import logikschicht.Modell;
import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

public class AccountController implements Initializable {

    @FXML
    private Button btn_kontoDatenAendern;
    @FXML
    private Button btn_kontoloeschen;
    @FXML
    private DatePicker dp_gebDat;
    @FXML
    private Text fehlerMeldungen;
    @FXML
    private TextField tf_benutzername;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_nachname;
    @FXML
    private TextField tf_ort;
    @FXML
    private TextField tf_passwort;
    @FXML
    private TextField tf_plz;
    @FXML
    private TextField tf_strasse;
    @FXML
    private TextField tf_vorname;
    @FXML
    private TableView<Benutzer> tv_account;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableViewColumns();
        onActionEvents();
        refreshTableView();
    }

    private void setupTableViewColumns() {
        TableColumn<Benutzer, String> benutzerIdColumn = new TableColumn<>("Benutzer ID");
        benutzerIdColumn.setCellValueFactory(new PropertyValueFactory<>("benutzerId"));
        TableColumn<Benutzer, String> nachnameColumn = new TableColumn<>("Nachname");
        nachnameColumn.setCellValueFactory(new PropertyValueFactory<>("nachname"));
        TableColumn<Benutzer, String> vornameColumn = new TableColumn<>("Vorname");
        vornameColumn.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        TableColumn<Benutzer, String> anredeColumn = new TableColumn<>("Anrede");
        anredeColumn.setCellValueFactory(new PropertyValueFactory<>("anrede"));
        TableColumn<Benutzer, String> benutzerNameColumn = new TableColumn<>("Benutzername");
        benutzerNameColumn.setCellValueFactory(new PropertyValueFactory<>("benutzerName"));
        TableColumn<Benutzer, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Benutzer, String> passwortColumn = new TableColumn<>("Passwort");
        passwortColumn.setCellValueFactory(new PropertyValueFactory<>("passwort"));
        TableColumn<Benutzer, String> strasseColumn = new TableColumn<>("Straße");
        strasseColumn.setCellValueFactory(new PropertyValueFactory<>("strasse"));
        TableColumn<Benutzer, String> ortColumn = new TableColumn<>("Ort");
        ortColumn.setCellValueFactory(new PropertyValueFactory<>("ort"));
        TableColumn<Benutzer, String> plzColumn = new TableColumn<>("PLZ");
        plzColumn.setCellValueFactory(new PropertyValueFactory<>("plz"));
        TableColumn<Benutzer, String> geburtsdatumColumn = new TableColumn<>("Geburtsdatum");
        geburtsdatumColumn.setCellValueFactory(new PropertyValueFactory<>("geburtsdatum"));
        tv_account.getColumns().addAll(benutzerIdColumn, nachnameColumn, vornameColumn, anredeColumn, benutzerNameColumn, emailColumn, passwortColumn, strasseColumn, ortColumn, plzColumn, geburtsdatumColumn);
    }

    private void onActionEvents() {
        btn_kontoDatenAendern.setOnAction(e -> runTask(this::onKontoDatenAendern, "Konto-Daten erfolgreich geändert.", "Konto-Daten konnten nicht geändert werden."));
        btn_kontoloeschen.setOnAction(e ->
        {
            runTask(this::onKontoLoeschen, "Konto erfolgreich gelöscht.", "Konto konnte nicht gelöscht werden.");
            Stage stage = (Stage) btn_kontoloeschen.getScene().getWindow();
            Modell.getInstance().getViewFacotry().closeStage(stage);
            Modell.getInstance().getViewFacotry().showLoginWindow();

        }
        );
    }

    private void runTask(Callable<Boolean> action, String successMessage, String failureMessage) {
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return action.call();
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                Platform.runLater(() ->
                {
                    if (getValue())
                    {
                        fehlerMeldungen.setText(successMessage);
                    } else
                    {
                        fehlerMeldungen.setText(failureMessage);
                    }
                    refreshTableView();
                });
            }

            @Override
            protected void failed() {
                super.failed();
                Platform.runLater(() -> fehlerMeldungen.setText("Ein unerwarteter Fehler ist aufgetreten."));
            }
        };
        new Thread(task).start();
    }

    private void refreshTableView() {
        Platform.runLater(() ->
        {
            try
            {
                Benutzer benutzer = getBenutzer();
                tv_account.setItems(FXCollections.observableArrayList(benutzer));
            } catch (Exception e)
            {
                fehlerMeldungen.setText("Fehler beim Laden der Konto-Daten.");
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
    }

    private Boolean onKontoDatenAendern() throws Exception {
        Benutzer benutzer = new Benutzer(LoginController.benutzerId, tf_nachname.getText(), tf_vorname.getText(), null, tf_benutzername.getText(),
                tf_email.getText(), tf_passwort.getText(), tf_strasse.getText(), tf_ort.getText(), tf_plz.getText(),
                dp_gebDat.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), null, null);
        return Benutzerverwaltung.updateBenutzer(benutzer);
    }

    private Boolean onKontoLoeschen() throws Exception {
        return Benutzerverwaltung.deleteBenutzer(LoginController.benutzerId);
    }

    private Benutzer getBenutzer() throws Exception {
        return Benutzerverwaltung.getBenutzer(LoginController.benutzerId);
    }
}
