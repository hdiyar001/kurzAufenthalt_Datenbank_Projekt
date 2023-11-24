package praesentationsschicht_GUI.Controllers;

import logikschicht.FilterBuchung;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import logikschicht.Buchungenverwaltung;
import logikschicht.Zahlenverwaltung;
import logikschicht.Zahlungen;
import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

public class BuchungenController implements Initializable {

    @FXML
    private Button btn_myZahlnungenReloadTable;
    @FXML
    private Button btn_myBuchungenReloadTable;
    @FXML
    private Button btn_bezahlen;
    @FXML
    private Button btn_loeschen;
    @FXML
    private Button btn_suchen;
    @FXML
    private DatePicker buchungsdatum;
    @FXML
    private Text ta_meldungen;
    @FXML
    private TextField tf_ort;
    @FXML
    private TextField tf_buchungsid;
    @FXML
    private TextField tf_buchungsid1;
    @FXML
    private TableView<FilterBuchung> tv_buchungen;
    @FXML
    private TableView<Zahlungen> tv_Zahlungen;
    @FXML
    private ComboBox cb_zahlungsart;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private String message = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_zahlungsart.getItems().addAll("VorOrt", "Lastschrift", "Visa");
        setupTableViewColumns();
        setupZahlungTableViewColumns();
        onActionEvents();
        btn_myBuchungenReloadTable.setOnAction(e -> refreshTableView());
        btn_myZahlnungenReloadTable.setOnAction(e -> refreshZahlungTableView());
        refreshTableView();
        refreshZahlungTableView();
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
        TableColumn<FilterBuchung, String> anzahlDerNaechte = new TableColumn<>("anzahlDerNaechte");
        anzahlDerNaechte.setCellValueFactory(new PropertyValueFactory<>("anzahlDerNaechte"));
        TableColumn<FilterBuchung, String> betrag = new TableColumn<>("betrag");
        betrag.setCellValueFactory(new PropertyValueFactory<>("betrag"));
        tv_buchungen.getColumns().addAll(buchungid, buchungsDatumColumn, startDatumColumn, endDatumColumn, anschriftColumn,
                preisProNachtColumn, anzahlDerNaechte, betrag);
    }

    private void setupZahlungTableViewColumns() {
        TableColumn<Zahlungen, String> zahlungsId = new TableColumn<>("zahlungsId");
        zahlungsId.setCellValueFactory(new PropertyValueFactory<>("zahlungsId"));
        TableColumn<Zahlungen, String> buchungId = new TableColumn<>("buchungId");
        buchungId.setCellValueFactory(new PropertyValueFactory<>("buchungId"));
        TableColumn<Zahlungen, String> betrag = new TableColumn<>("betrag");
        betrag.setCellValueFactory(new PropertyValueFactory<>("betrag"));
        TableColumn<Zahlungen, String> zahlungsdatum = new TableColumn<>("zahlungsdatum");
        zahlungsdatum.setCellValueFactory(new PropertyValueFactory<>("zahlungsdatum"));
        TableColumn<Zahlungen, String> zahlungsart = new TableColumn<>("zahlungsart");
        zahlungsart.setCellValueFactory(new PropertyValueFactory<>("zahlungsart"));
        tv_Zahlungen.getColumns().addAll(zahlungsId, buchungId, betrag, zahlungsdatum, zahlungsart);
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

    private List<Zahlungen> getZahlungen() throws Exception {
        return Zahlenverwaltung.getAllZahlungenen(LoginController.benutzerId);
    }

    private void onActionEvents() {
        btn_loeschen.setOnAction(e -> runTask(this::onLoeschen, "Die Buchung wurde erfolgreich gelöscht.", "Die Buchung konnte nicht gelöscht werden."));
        btn_suchen.setOnAction(e -> runTask(this::onSuchen, "Suche erfolgreich.", "Suche fehlgeschlagen."));
        btn_bezahlen.setOnAction(e -> runTask(this::onBezahlen, "Erfolgreich Bezahlt.", "Bezahlung fehlgeschlagen."));
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
                        ta_meldungen.setText(message);
                    }
                    refreshTableView();
                });
            }

            @Override
            protected void failed() {
                super.failed();
                Platform.runLater(() ->
                {
                    message += "> " + "Ein unerwarteter Fehler ist aufgetreten." + "\n";
                    ta_meldungen.setText(message);
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
                ObservableList<FilterBuchung> buchungen = FXCollections.observableArrayList(getBuchungen());
                tv_buchungen.setItems(buchungen);
                message += "> " + "Buchungstabelle wurde erflogreich aktuallisiert. " + "\n";
                ta_meldungen.setText(message);
            } catch (Exception e)
            {
                message += "> " + "Fehler beim Laden der Buchungsdaten: " + e.getMessage() + "\n";
                ta_meldungen.setText(message);
                Logger.getLogger(BuchungenController.class.getName()).log(Level.SEVERE, "Fehler beim Laden der Buchungsdaten", e);
            }
        });
    }

    private void refreshZahlungTableView() {
        Platform.runLater(() ->
        {
            try
            {
                ObservableList<Zahlungen> zahlungen = FXCollections.observableArrayList(getZahlungen());
                tv_Zahlungen.setItems(zahlungen);
                message += "> " + "Zahlungstabelle wurde erflogreich aktuallisiert. " + "\n";
                ta_meldungen.setText(message);
            } catch (Exception e)
            {
                message += "> " + "Fehler beim Laden der Zahlungssdaten: " + e.getMessage() + "\n";
                ta_meldungen.setText(message);
                Logger.getLogger(BuchungenController.class.getName()).log(Level.SEVERE, "Fehler beim Laden der Zahlungsdaten", e);
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

    private Boolean onBezahlen() throws Exception {
        String betrag = Buchungenverwaltung.getBetragByBuchungId(tf_buchungsid1.getText());
        String currentDate = formatDate(LocalDate.now());
        Zahlungen zahlung = new Zahlungen(null, tf_buchungsid1.getText(), betrag,
                currentDate,
                cb_zahlungsart.getValue() + "");
        boolean erfolg = Zahlenverwaltung.storeZahlungen(zahlung);
        if (erfolg)
        {
            refreshZahlungTableView();
        }
        return erfolg;
    }

    private String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
}
