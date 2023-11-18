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
import logikschicht.Nachrichten;
import logikschicht.Nachrichtenverwaltung;
import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

public class NachrichtenController implements Initializable {

    @FXML
    private TableView<Nachrichten> tv_nachrichten;

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
//        TableColumn<Nachrichten, String> nachrichtenIdColumn = new TableColumn<>("Nachrichten ID");
//        nachrichtenIdColumn.setCellValueFactory(new PropertyValueFactory<>("nachrichtenId"));

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

        tv_nachrichten.getColumns().addAll(benutzerNameColumn, senderIdColumn, empfaengerIdColumn, nachrichtenTextColumn, zeitStempelColumn);
    }

    private void fillTableView() throws Exception {
        System.out.println(getNachrichten());
        ObservableList<Nachrichten> data = FXCollections.observableArrayList(getNachrichten());
        tv_nachrichten.setItems(data);
    }

    private List<Nachrichten> getNachrichten() throws Exception {
        return Nachrichtenverwaltung.getAllNachrichten(LoginController.benutzerId);
    }
}
