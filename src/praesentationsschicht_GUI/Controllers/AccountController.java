package praesentationsschicht_GUI.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logikschicht.Benutzer;
import logikschicht.Benutzerverwaltung;
import logikschicht.Bewertung;
import logikschicht.Bewertungverwaltung;
import logikschicht.Modell;
import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

public class AccountController implements Initializable {

    @FXML
    private ComboBox<String> comb_buchungid;

    @FXML
    private ComboBox<String> comb_sterne;
    @FXML
    private TextArea ta_bewertungstext;

    @FXML
    private Button btn_bewerten;
    @FXML
    private Button btn_bewloeschen;
    @FXML
    private Button btn_kontoDatenAendern;
    @FXML
    private Button btn_kontoloeschen;
    @FXML
    private DatePicker dp_gebDat;
    @FXML
    private Text ta_meldungen;
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

    @FXML
    private TableView<Bewertung> tv_history;
    private String message = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comb_sterne.getItems().setAll("*", "**", "***", "****", "*****");
//        comb_buchungid.getItems().setAll("")
        setupTableViewColumns();
        setupBewertungTableViewColumns();
        onActionEvents();
        refreshTableView();
        refreshBewertungTableView();
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

    private void setupBewertungTableViewColumns() {
        TableColumn<Bewertung, String> bewertungId = new TableColumn<>("bewertungId");
        bewertungId.setCellValueFactory(new PropertyValueFactory<>("bewertungId"));

        TableColumn<Bewertung, String> buchungId = new TableColumn<>("buchungId");
        buchungId.setCellValueFactory(new PropertyValueFactory<>("buchungId"));

        TableColumn<Bewertung, String> bewertungText = new TableColumn<>("bewertungText");
        bewertungText.setCellValueFactory(new PropertyValueFactory<>("bewertungText"));

        TableColumn<Bewertung, String> sternBewertung = new TableColumn<>("sternBewertung");
        sternBewertung.setCellValueFactory(new PropertyValueFactory<>("sternBewertung"));

        tv_history.getColumns().addAll(bewertungId, buchungId, bewertungText, sternBewertung);
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
//        btn_bewerten.setOnAction(e -> runTask(this::onBewerten "Bewrtung ist erfolgreich veröffentlicht worden.", "Bewrtung könnte leider nicht veröffentlicht werden."));
//        btn_bewloeschen.setOnAction(e -> runTask(this::onBewLoeschen, "Bewrtung ist erfolgreich gelöscht worden.", "Bewrtung könnte leider nicht gelöscht werden."));
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
                        message += "> " + successMessage + "\n";
                        ta_meldungen.setText(message);
                    } else
                    {
                        message += "> " + failureMessage + "\n";
                        ta_meldungen.setText(failureMessage);
                    }
                    refreshTableView();
                    refreshBewertungTableView();
                });
            }

            @Override
            protected void failed() {
                super.failed();
                Platform.runLater(()
                        ->
                {
                    message += "> " + "Ein unerwarteter Fehler ist aufgetreten." + "\n";
                    ta_meldungen.setText(message);
                }
                );
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
                message += "> " + "Fehler beim Laden der Konto-Daten." + "\n";
                ta_meldungen.setText(message);
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
    }

    private Boolean onKontoDatenAendern() throws Exception {

        LocalDate geburtsdatum = dp_gebDat.getValue();
        if (geburtsdatum != null)
        {
            int alter = Period.between(geburtsdatum, LocalDate.now()).getYears();
            if (alter < 18)
            {
                message += "> " + "Sie müssen mindestens 18 Jahre alt alt sein." + "\n";
                ta_meldungen.setText(message);
                return false;
            }
        }
        Benutzer benutzer = new Benutzer(LoginController.benutzerId,
                !tf_nachname.getText().isBlank() ? tf_nachname.getText() : null,
                !tf_vorname.getText().isBlank() ? tf_vorname.getText() : null,
                null,
                !tf_benutzername.getText().isBlank() ? tf_benutzername.getText() : null,
                !tf_email.getText().isBlank() ? tf_email.getText() : null,
                !tf_passwort.getText().isBlank() ? tf_passwort.getText() : null,
                !tf_strasse.getText().isBlank() ? tf_strasse.getText() : null,
                !tf_ort.getText().isBlank() ? tf_ort.getText() : null,
                !tf_plz.getText().isBlank() ? tf_plz.getText() : null,
                geburtsdatum != null ? geburtsdatum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : null,
                null, null);
        return Benutzerverwaltung.updateBenutzer(benutzer);
    }

    private Boolean onKontoLoeschen() throws Exception {
        return Benutzerverwaltung.deleteBenutzer(LoginController.benutzerId);
    }

    private Benutzer getBenutzer() throws Exception {
        return Benutzerverwaltung.getBenutzer(LoginController.benutzerId);
    }

    private void refreshBewertungTableView() {
        Platform.runLater(() ->
        {
            try
            {
                Bewertung bewertung = getBewertung();
                tv_history.setItems(FXCollections.observableArrayList(bewertung));
            } catch (Exception e)
            {
                ta_meldungen.setText("Fehler beim Laden der Bewertungen.");
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
    }

    private Bewertung getBewertung() throws Exception {
        return Bewertungverwaltung.getBewertungBybewertungId(LoginController.benutzerId);
    }

    private Boolean onBewerten() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Boolean onBewLoeschen() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
