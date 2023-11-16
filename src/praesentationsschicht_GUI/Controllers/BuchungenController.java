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
import logikschicht.Buchung;
import logikschicht.Buchungenverwaltung;

public class BuchungenController implements Initializable {

    @FXML
    private TableView<Buchung> tv_buchungen;

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
        TableColumn<Buchung, String> buchungIdColumn = new TableColumn<>("Buchung ID");
        buchungIdColumn.setCellValueFactory(new PropertyValueFactory<>("buchungId"));

        TableColumn<Buchung, String> mieterIdColumn = new TableColumn<>("Mieter ID");
        mieterIdColumn.setCellValueFactory(new PropertyValueFactory<>("mieterId"));

        TableColumn<Buchung, String> wohnungIdColumn = new TableColumn<>("Wohnung ID");
        wohnungIdColumn.setCellValueFactory(new PropertyValueFactory<>("wohnungId"));

        TableColumn<Buchung, String> buchungsDatumColumn = new TableColumn<>("Buchungsdatum");
        buchungsDatumColumn.setCellValueFactory(new PropertyValueFactory<>("buchungsDatum"));

        TableColumn<Buchung, String> startDatumColumn = new TableColumn<>("Startdatum");
        startDatumColumn.setCellValueFactory(new PropertyValueFactory<>("startDatum"));

        TableColumn<Buchung, String> endDatumColumn = new TableColumn<>("Enddatum");
        endDatumColumn.setCellValueFactory(new PropertyValueFactory<>("endDatum"));

        tv_buchungen.getColumns().addAll(buchungIdColumn, mieterIdColumn, wohnungIdColumn, buchungsDatumColumn, startDatumColumn, endDatumColumn);
    }

    private void fillTableView() throws Exception {
        ObservableList<Buchung> data = FXCollections.observableArrayList(getBuchungen());
        tv_buchungen.setItems(data);
    }

    private List<Buchung> getBuchungen() throws Exception {
        return Buchungenverwaltung.getAllBuchungen();
    }
}
