package praesentationsschicht_GUI.Controllers;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import logikschicht.Nachrichten;
import logikschicht.Nachrichtenverwaltung;
import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

public class NachrichtenController implements Initializable {

    @FXML
    private Button btn_loeschen;

    @FXML
    private Button refreshTableView;

    @FXML
    private Button btn_senden;

    @FXML
    private DatePicker dp_nachrichtZeitraum;
    @FXML
    private Text ta_meldungen;
    @FXML
    private TextField tf_empfaengerId;
    @FXML
    private TextField tf_nachrichtId;
    @FXML
    private TextArea ta_nachricht;
    @FXML
    private TableView<Nachrichten> tv_nachrichten;
    private String message = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableViewColumns();
        onActionEvents();
        refreshTableView.setOnAction(e -> refreshTableView());
        refreshTableView();
    }

    private void setupTableViewColumns() {
        TableColumn<Nachrichten, String> nachrichtenIdColumn = new TableColumn<>("NachrichtID");
        nachrichtenIdColumn.setCellValueFactory(new PropertyValueFactory<>("nachrichtenId"));
        TableColumn<Nachrichten, String> benutzerNameColumn = new TableColumn<>("BenutzerName");
        benutzerNameColumn.setCellValueFactory(new PropertyValueFactory<>("benutzerName"));
        TableColumn<Nachrichten, String> senderIdColumn = new TableColumn<>("Sender ID");
        senderIdColumn.setCellValueFactory(new PropertyValueFactory<>("senderId"));
        TableColumn<Nachrichten, String> empfaengerIdColumn = new TableColumn<>("Empfänger ID");
        empfaengerIdColumn.setCellValueFactory(new PropertyValueFactory<>("empfaengerId"));
        TableColumn<Nachrichten, String> nachrichtenTextColumn = new TableColumn<>("Nachrichtentext");
        nachrichtenTextColumn.setCellValueFactory(new PropertyValueFactory<>("nachrichtenText"));
        TableColumn<Nachrichten, String> zeitStempelColumn = new TableColumn<>("Zeitstempel");
        zeitStempelColumn.setCellValueFactory(new PropertyValueFactory<>("zeitStempel"));
        TableColumn<Nachrichten, String> status = new TableColumn<>("status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tv_nachrichten.getColumns().addAll(nachrichtenIdColumn, benutzerNameColumn, senderIdColumn, empfaengerIdColumn, nachrichtenTextColumn, zeitStempelColumn, status);
    }

    private void onActionEvents() {
        btn_loeschen.setOnAction(e -> runTask(this::onLoeschen, "Die Nachricht wurde erfolgreich gelöscht.", "Die Nachricht konnte nicht gelöscht werden."));
        btn_senden.setOnAction(e -> runTask(this::onSenden, "Die Nachricht wurde erfolgreich gesendet.", "Die Nachricht konnte nicht gesendet werden."));
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
                List<Nachrichten> nachrichtenListe = getNachrichten();
                ObservableList<Nachrichten> data = FXCollections.observableArrayList(nachrichtenListe);
                tv_nachrichten.setItems(data);
                message += "> " + "Nachrichtentablle wurde erflogreich aktuallisiert. " + "\n";
                ta_meldungen.setText(message);
            } catch (Exception e)
            {
                message += "> " + "Fehler beim Laden der Nachrichten." + "\n";
                ta_meldungen.setText(message);
                Logger.getLogger(NachrichtenController.class.getName()).log(Level.SEVERE, null, e);
            }

        });
    }

    private Boolean onLoeschen() throws Exception {
        return Nachrichtenverwaltung.deleteNachricht(tf_nachrichtId.getText());
    }

    private Boolean onSenden() throws Exception {
        if (LoginController.benutzerId.equals(tf_empfaengerId.getText()))
        {
            return false;
        }
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        Nachrichten nachricht = new Nachrichten(null, LoginController.benutzerId, tf_empfaengerId.getText(), ta_nachricht.getText(), currentDate);
        System.out.println(nachricht);
        return Nachrichtenverwaltung.sendNachricht(nachricht);
    }

    private List<Nachrichten> getNachrichten() throws Exception {
        return Nachrichtenverwaltung.getAllNachrichten(LoginController.benutzerId);
    }
}
