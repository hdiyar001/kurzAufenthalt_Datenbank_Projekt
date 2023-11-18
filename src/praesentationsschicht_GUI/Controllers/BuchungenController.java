package praesentationsschicht_GUI.Controllers;

import logikschicht.FilterBuchung;
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
import logikschicht.Buchung;
import logikschicht.Buchungenverwaltung;
import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

public class BuchungenController implements Initializable {

    @FXML
    private TableView<FilterBuchung> tv_buchungen;

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

        tv_buchungen.getColumns().addAll(buchungsDatumColumn, startDatumColumn, endDatumColumn, anschriftColumn, preisProNachtColumn, bewertungstextColumn, sternBewertungColumn);
    }

    private void fillTableView() throws Exception {
        ObservableList<FilterBuchung> data = FXCollections.observableArrayList(getBuchungen());
        tv_buchungen.setItems(data);
    }

    private List<FilterBuchung> getBuchungen() throws Exception {
        return Buchungenverwaltung.getAllBuchungen(LoginController.benutzerId);
    }
}
