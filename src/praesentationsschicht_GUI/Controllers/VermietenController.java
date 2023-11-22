package praesentationsschicht_GUI.Controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import logikschicht.FilterWohnung;
import logikschicht.Wohnung;
import logikschicht.Wohnungenverwaltung;
import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

public class VermietenController implements Initializable {

    @FXML
    private Button btn_aendern;
    @FXML
    private Button btn_loeschen;
    @FXML
    private Button btn_vermieten;
    @FXML
    private Button btn_hinzufuegen;
    @FXML
    private Text fehlerMeldungen;
    @FXML
    private TextArea ta_beschreibungAendern;
    @FXML
    private TextField tf_ortAendern;
    @FXML
    private TextField tf_plzAendern;
    @FXML
    private TextField tf_preisAendern;
    @FXML
    private TextField tf_strasseAendern;
    @FXML
    private ComboBox<String> comb_wohnId;
    @FXML
    private ComboBox<String> comb_verfuegbarkeit;
    @FXML
    private TableView<FilterWohnung> tv_wohnungen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableViewColumns();
        onActionEvents();
        comb_verfuegbarkeit.getItems().setAll("JA", "NEIN");
        refreshTableView();
    }

    private void setupTableViewColumns() {
        TableColumn<FilterWohnung, String> wohnungId = new TableColumn<>("wohnungId");
        wohnungId.setCellValueFactory(new PropertyValueFactory<>("wohnungId"));
        TableColumn<FilterWohnung, String> anschriftColumn = new TableColumn<>("Anschrift");
        anschriftColumn.setCellValueFactory(new PropertyValueFactory<>("anschrift"));
        TableColumn<FilterWohnung, String> preisProNachtColumn = new TableColumn<>("Preis Pro Nacht");
        preisProNachtColumn.setCellValueFactory(new PropertyValueFactory<>("preisProNacht"));
        TableColumn<FilterWohnung, String> beschreibungColumn = new TableColumn<>("Beschreibung");
        beschreibungColumn.setCellValueFactory(new PropertyValueFactory<>("beschreibung"));
        TableColumn<FilterWohnung, String> verfuegbarkeitColumn = new TableColumn<>("Verfügbarkeit");
        verfuegbarkeitColumn.setCellValueFactory(new PropertyValueFactory<>("verfuegbarkeit"));
        tv_wohnungen.getColumns().addAll(wohnungId, anschriftColumn, preisProNachtColumn, beschreibungColumn, verfuegbarkeitColumn);
    }

    private void fillTableView() throws Exception {
        ObservableList<FilterWohnung> data = FXCollections.observableArrayList(getAllWohnungen());
        comb_wohnId.getItems().clear();
        comb_wohnId.getItems().addAll(
                data.stream()
                        .map(FilterWohnung::getWohnungId)
                        .collect(Collectors.toList())
        );
        tv_wohnungen.setItems(data);
    }

    private List<FilterWohnung> getAllWohnungen() throws Exception {
        return Wohnungenverwaltung.getVermieteteWohnungen(LoginController.benutzerId);
    }

    private boolean onVermieten() throws Exception {
        boolean vermietet = Wohnungenverwaltung.updateWohnungStatus(comb_wohnId.getValue(), "J");
        refreshTableView();
        return vermietet;
    }

    private boolean onLoeschen() throws Exception {
        boolean geloescht = Wohnungenverwaltung.deleteWohnung(comb_wohnId.getValue());
        refreshTableView();
        return geloescht;
    }

    private boolean onAktuallisieren() throws Exception {

        boolean aktuallisert = Wohnungenverwaltung.updateWohnung(getWohnung(comb_wohnId.getValue()));
        refreshTableView();
        return aktuallisert;
    }

    private Boolean btn_hinzufuegen() throws Exception {
        boolean hinzugefuegt = Wohnungenverwaltung.storeWohnung(getWohnung(null));
        refreshTableView();
        return hinzugefuegt;
    }

    private void onActionEvents() {
        btn_loeschen.setOnAction(e -> runTask(this::onLoeschen, "Die Wohnung wurde erfolgreich gelöscht.", "Die Wohnung konnte nicht gelöscht werden."));
        btn_vermieten.setOnAction(e -> runTask(this::onVermieten, "Ihre Wohnung ist nun veröffentlicht worden.", "Die Wohnung konnte nicht veröffentlicht werden."));
        btn_aendern.setOnAction(e -> runTask(this::onAktuallisieren, "Die Wohnungsdetails wurden erfolgreich aktualisiert.", "Die Aktualisierung der Wohnungsdetails ist fehlgeschlagen."));
        btn_hinzufuegen.setOnAction(e -> runTask(this::btn_hinzufuegen, "Neue Wohnung wurde Erfolgreich hinzugefügt.", "Neue Wohnung hinzugefügen fehlgeschlagen."));
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
                tv_wohnungen.getColumns().clear();
                setupTableViewColumns();
                tv_wohnungen.getItems().clear();
                fillTableView();
            } catch (Exception e)
            {
                fehlerMeldungen.setText("Fehler beim Laden der Tabellendaten.");
                Logger.getLogger(VermietenController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
    }

    private Wohnung getWohnung(String id) {
        return new Wohnung(
                id,
                LoginController.benutzerId,
                tf_strasseAendern.getText(),
                tf_ortAendern.getText(),
                tf_plzAendern.getText(),
                tf_preisAendern.getText(),
                ta_beschreibungAendern.getText(),
                comb_verfuegbarkeit.getValue().equals("JA") ? "J" : "N");
    }
}
