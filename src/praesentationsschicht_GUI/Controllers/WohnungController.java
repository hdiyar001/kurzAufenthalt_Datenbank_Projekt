package praesentationsschicht_GUI.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import logikschicht.Buchung;
import logikschicht.Buchungenverwaltung;
import logikschicht.FilterWohnung;
import logikschicht.Wohnungenverwaltung;
import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

public class WohnungController implements Initializable {

    @FXML
    private TableView<FilterWohnung> tv_wohnungen;

    @FXML
    private TextField tf_ort;

    @FXML
    private Slider slider_mindPreis;

    @FXML
    private Button btn_filter;

    @FXML
    private TextField tf_wohnungId;

    @FXML
    private Button btn_ReloadTable;

    @FXML
    private Button btn_buchung;

    @FXML
    private Label lbl_preisValue;

    @FXML
    private DatePicker dp_startdatum;

    @FXML
    private DatePicker dp_enddatum;

    @FXML
    private Text ta_meldungen;
    private String message = "";

    private static final Logger LOGGER = Logger.getLogger(WohnungController.class.getName());
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_ReloadTable.setOnAction(e -> reloadTableView());
        configureSlider();
        configureBuchungButton();
        configureFilterButton();
        initializeTableView();
    }

    private void configureSlider() {
        slider_mindPreis.valueProperty().addListener((obs, oldVal, newVal)
                -> lbl_preisValue.setText(String.format("%.2f €", newVal.doubleValue())));
    }

    private void configureBuchungButton() {
        btn_buchung.setOnAction(e -> handleBuchung());
    }

    private void configureFilterButton() {
        btn_filter.setOnAction(e -> reloadTableView());
    }

    private void initializeTableView() {
        setupTableViewColumns();
        reloadTableView();
    }

    private void handleBuchung() {
        try
        {
            if (validateBuchung())
            {
                Buchung buchung = createBuchung();
                if (Buchungenverwaltung.storeBuchung(buchung))
                {
                    Wohnungenverwaltung.updateWohnungStatus(buchung.getWohnungId(), "N");
                    message += "> " + "Die Wohnung wurde erfolgreich gebucht :)" + "\n";

                    ta_meldungen.setText(message);
                    reloadTableView();
                } else
                {
                    message += "> " + "Buchung fehlgeschlagen. Überprüfen Sie Ihre Eingaben." + "\n";
                    ta_meldungen.setText(message);
                }
            }
        } catch (Exception ex)
        {
            message += "> " + "Ein Fehler ist aufgetreten." + "\n";
            ta_meldungen.setText(message);
            LOGGER.log(Level.SEVERE, "Buchung fehlgeschlagen", ex);
        }
    }

    private boolean validateBuchung() {
        if (dp_startdatum.getValue().isBefore(dp_enddatum.getValue()))
        {
            return true;
        } else
        {
            message += "> " + "Das Enddatum muss nach dem Startdatum liegen." + "\n";
            ta_meldungen.setText(message);
            return false;
        }
    }

    private Buchung createBuchung() {
        String start = formatDate(dp_startdatum.getValue());
        String end = formatDate(dp_enddatum.getValue());
        String currentDate = formatDate(LocalDate.now());
        return new Buchung(null, LoginController.benutzerId, tf_wohnungId.getText(), currentDate, start, end);
    }

    private void reloadTableView() {
        try
        {
            ObservableList<FilterWohnung> data = FXCollections.observableArrayList(getAllWohnungen());
            tv_wohnungen.setItems(data);
        } catch (Exception ex)
        {
            LOGGER.log(Level.SEVERE, "Fehler beim Laden der Wohnungen", ex);
        }
    }

    private List<FilterWohnung> getAllWohnungen() throws Exception {
        double minPreis = slider_mindPreis.getValue();
        String ort = tf_ort.getText();
        return Wohnungenverwaltung.getAllGefWohnungen(LoginController.benutzerId, String.valueOf(minPreis), ort);
    }

    private void setupTableViewColumns() {
        tv_wohnungen.getColumns().clear();
        tv_wohnungen.getColumns().addAll(
                createColumn("WohnungID", "wohnungId"),
                createColumn("Benutzername", "benutzerName"),
                createColumn("Anschrift", "anschrift"),
                createColumn("Verifiziert", "verifiziert"),
                createColumn("Preis Pro Nacht", "preisProNacht"),
                createColumn("Beschreibung", "beschreibung"),
                createColumn("Verfügbarkeit", "verfuegbarkeit"),
                createColumn("Bewertungstext", "bewertungstext"),
                createColumn("SternBewertung", "sternBewertung")
        );
    }

    private TableColumn<FilterWohnung, String> createColumn(String header, String property) {
        TableColumn<FilterWohnung, String> column = new TableColumn<>(header);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    private String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
}
