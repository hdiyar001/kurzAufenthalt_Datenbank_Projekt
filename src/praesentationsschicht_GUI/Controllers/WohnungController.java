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
import logikschicht.Wohnung;
import logikschicht.Wohnungenverwaltung;

public class WohnungController implements Initializable {

    @FXML
    private TableView<Wohnung> tv_wohnungen;

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
        TableColumn<Wohnung, String> wohnungIdColumn = new TableColumn<>("Wohnung ID");
        wohnungIdColumn.setCellValueFactory(new PropertyValueFactory<>("wohnungId"));

        TableColumn<Wohnung, String> eigentuemerIdColumn = new TableColumn<>("Eigentümer ID");
        eigentuemerIdColumn.setCellValueFactory(new PropertyValueFactory<>("eigentuemerId"));

        TableColumn<Wohnung, String> strasseColumn = new TableColumn<>("Straße");
        strasseColumn.setCellValueFactory(new PropertyValueFactory<>("strasse"));

        TableColumn<Wohnung, String> ortColumn = new TableColumn<>("Ort");
        ortColumn.setCellValueFactory(new PropertyValueFactory<>("ort"));

        TableColumn<Wohnung, String> plzColumn = new TableColumn<>("PLZ");
        plzColumn.setCellValueFactory(new PropertyValueFactory<>("plz"));

        TableColumn<Wohnung, String> preisProNachtColumn = new TableColumn<>("Preis Pro Nacht");
        preisProNachtColumn.setCellValueFactory(new PropertyValueFactory<>("preisProNacht"));

        TableColumn<Wohnung, String> beschreibungColumn = new TableColumn<>("Beschreibung");
        beschreibungColumn.setCellValueFactory(new PropertyValueFactory<>("beschreibung"));

        TableColumn<Wohnung, String> verfuegbarkeitColumn = new TableColumn<>("Verfügbarkeit");
        verfuegbarkeitColumn.setCellValueFactory(new PropertyValueFactory<>("verfuegbarkeit"));

        tv_wohnungen.getColumns().addAll(wohnungIdColumn, eigentuemerIdColumn, strasseColumn, ortColumn, plzColumn, preisProNachtColumn, beschreibungColumn, verfuegbarkeitColumn);
    }

    private void fillTableView() throws Exception {
        ObservableList<Wohnung> data = FXCollections.observableArrayList(getWohnungen());
        tv_wohnungen.setItems(data);
    }

    private List<Wohnung> getWohnungen() throws Exception {
        return Wohnungenverwaltung.getAllWohnungn();
    }
}
