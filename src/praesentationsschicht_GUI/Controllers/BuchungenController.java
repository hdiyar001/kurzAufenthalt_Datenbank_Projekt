package praesentationsschicht_GUI.Controllers;

import logikschicht.FilterBuchung;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import logikschicht.Buchungenverwaltung;
import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

public class BuchungenController implements Initializable {

    @FXML
    private Button btn_loeschen;
    @FXML
    private Button btn_suchen;
    @FXML
    private DatePicker buchungsdatum;
    @FXML
    private Text fehlerMeldungen;
    @FXML
    private TextField tf_ort;
    @FXML
    private TextField tf_buchungsid;
    @FXML
    private TableView<FilterBuchung> tv_buchungen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableViewColumns();
        onActionEvents();
        refreshTableView();
    }

    private void setupTableViewColumns() {
        TableColumn<FilterBuchung, String> buchungid = new TableColumn<>("buchungid");
        buchungid.setCellValueFactory(new PropertyValueFactory<>("buchungid"));
        TableColumn<FilterBuchung, String> buchungsDatumColumn = new TableColumn<>("Buchungsdatum");
        buchungsDatumColumn.setCellValueFactory(new PropertyValueFactory<>("buchungsDatum"));
        TableColumn<FilterBuchung, String> startDatumColumn = new TableColumn<>("Startdatum");
        startDatumColumn.setCellValueFactory(new PropertyValueFactory<>("startDatum"));
        TableColumn<FilterBuchung, String> endDatumColumn = new TableColumn<>("Enddatum");
        endDatumColumn.setCellValueFactory(new PropertyValueFactory<>("endDatum"));
        TableColumn<FilterBuchung, String> anschriftColumn = new TableColumn<>("Anschrift");
        anschriftColumn.setCellValueFactory(new PropertyValueFactory<>("anschrift"));
        TableColumn<FilterBuchung, String> preisProNachtColumn = new TableColumn<>("Preis Pro Nacht");
        preisProNachtColumn.setCellValueFactory(new PropertyValueFactory<>("preisProNacht"));
        TableColumn<FilterBuchung, String> bewertungstextColumn = new TableColumn<>("Bewertungstext");
        bewertungstextColumn.setCellValueFactory(new PropertyValueFactory<>("textBewertung"));
        TableColumn<FilterBuchung, String> sternBewertungColumn = new TableColumn<>("Sternbewertung");
        sternBewertungColumn.setCellValueFactory(new PropertyValueFactory<>("sternBewertung"));
        TableColumn<FilterBuchung, String> anzahlDerNaechte = new TableColumn<>("anzahlDerNaechte");
        anzahlDerNaechte.setCellValueFactory(new PropertyValueFactory<>("anzahlDerNaechte"));
        TableColumn<FilterBuchung, String> betrag = new TableColumn<>("betrag");
        betrag.setCellValueFactory(new PropertyValueFactory<>("betrag"));
        TableColumn<FilterBuchung, String> zahlungsdatum = new TableColumn<>("zahlungsdatum");
        zahlungsdatum.setCellValueFactory(new PropertyValueFactory<>("zahlungsdatum"));
        TableColumn<FilterBuchung, String> zahlungsart = new TableColumn<>("zahlungsart");
        zahlungsart.setCellValueFactory(new PropertyValueFactory<>("zahlungsart"));
        tv_buchungen.getColumns().addAll(buchungid, buchungsDatumColumn, startDatumColumn, endDatumColumn, anschriftColumn,
                preisProNachtColumn, bewertungstextColumn, sternBewertungColumn, anzahlDerNaechte,
                betrag, zahlungsdatum, zahlungsart);
    }

    private void fillTableView() throws Exception {
        ObservableList<FilterBuchung> data = FXCollections.observableArrayList(getBuchungen());
        tv_buchungen.setItems(data);
    }

    private List<FilterBuchung> getBuchungen() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = buchungsdatum.getValue() != null ? buchungsdatum.getValue().format(formatter) : "";
        return Buchungenverwaltung.getAllBuchungen(LoginController.benutzerId,
                tf_buchungsid.getText().isBlank() ? null : tf_buchungsid.getText(),
                tf_ort.getText().isBlank() ? null : tf_ort.getText(),
                formattedDate.isBlank() ? null : formattedDate);
    }

    private void onActionEvents() {
        btn_loeschen.setOnAction(e -> runTask(this::onLoeschen, "Die Buchung wurde erfolgreich gelöscht.", "Die Buchung konnte nicht gelöscht werden."));
        btn_suchen.setOnAction(e -> runTask(this::onSuchen, "Suche erfolgreich.", "Suche fehlgeschlagen."));
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
                Platform.runLater(() ->
                {
                    fehlerMeldungen.setText("Ein unerwarteter Fehler ist aufgetreten.");
                });
            }
        };
        new Thread(task).start();
    }

    private void refreshTableView() {
        Platform.runLater(() ->
        {
            try
            {
                tv_buchungen.getItems().clear();
                tv_buchungen.setItems(FXCollections.observableArrayList(getBuchungen()));
            } catch (Exception e)
            {
                fehlerMeldungen.setText("Fehler beim Laden der Buchungsdaten.");
                Logger.getLogger(BuchungenController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
    }

    private Boolean onLoeschen() throws Exception {
        return Buchungenverwaltung.deleteBuchung(tf_buchungsid.getText());
    }

    private Boolean onSuchen() throws Exception {
        List<FilterBuchung> gefilterteBuchungen = getBuchungen();
        Platform.runLater(() ->
        {
            tv_buchungen.setItems(FXCollections.observableArrayList(gefilterteBuchungen));
        });
        return gefilterteBuchungen != null && !gefilterteBuchungen.isEmpty();
    }
}
