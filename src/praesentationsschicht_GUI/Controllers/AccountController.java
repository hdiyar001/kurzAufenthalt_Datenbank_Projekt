package praesentationsschicht_GUI.Controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logikschicht.Benutzer;
import datenhaltungsschicht.DBBenutzer;
import logikschicht.Benutzerverwaltung;
import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

public class AccountController implements Initializable {

    @FXML
    private TableView<Benutzer> tv_account;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            setupTableViewColumns();
            fillTableView();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
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

        TableColumn<Benutzer, String> refBenutzerColumn = new TableColumn<>("Referenz Benutzer");
        refBenutzerColumn.setCellValueFactory(new PropertyValueFactory<>("refBenutzer"));

        TableColumn<Benutzer, String> verftColumn = new TableColumn<>("Verifiziert");
        verftColumn.setCellValueFactory(new PropertyValueFactory<>("verft"));

        tv_account.getColumns().addAll(benutzerIdColumn, nachnameColumn, vornameColumn, anredeColumn, benutzerNameColumn, emailColumn, passwortColumn, strasseColumn, ortColumn, plzColumn, geburtsdatumColumn, refBenutzerColumn, verftColumn);
    }

    private void fillTableView() throws Exception {
        ObservableList<Benutzer> data = FXCollections.observableArrayList(getBenutzer());
        tv_account.setItems(data);
    }

    private Benutzer getBenutzer() throws Exception {
        return Benutzerverwaltung.getBenutzer(LoginController.benutzerId);
    }

    // Eventuell weitere Methoden für das Hinzufügen, Aktualisieren und Löschen von Benutzern
}
