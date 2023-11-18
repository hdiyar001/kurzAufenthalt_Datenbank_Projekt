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
import logikschicht.FilterWohnung;
import logikschicht.Wohnung;
import logikschicht.Wohnungenverwaltung;

public class WohnungController implements Initializable {

    @FXML
    private TableView<FilterWohnung> tv_wohnungen;

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
        TableColumn<FilterWohnung, String> benutzerNameColumn = new TableColumn<>("Benutzername");
        benutzerNameColumn.setCellValueFactory(new PropertyValueFactory<>("benutzerName"));

        TableColumn<FilterWohnung, String> anschriftColumn = new TableColumn<>("Anschrift");
        anschriftColumn.setCellValueFactory(new PropertyValueFactory<>("anschrift"));

        TableColumn<FilterWohnung, String> verifiziertColumn = new TableColumn<>("Verifiziert");
        verifiziertColumn.setCellValueFactory(new PropertyValueFactory<>("verifiziert"));

        TableColumn<FilterWohnung, String> preisProNachtColumn = new TableColumn<>("Preis Pro Nacht");
        preisProNachtColumn.setCellValueFactory(new PropertyValueFactory<>("preisProNacht"));

        TableColumn<FilterWohnung, String> beschreibungColumn = new TableColumn<>("Beschreibung");
        beschreibungColumn.setCellValueFactory(new PropertyValueFactory<>("beschreibung"));

        TableColumn<FilterWohnung, String> verfuegbarkeitColumn = new TableColumn<>("Verfügbarkeit");
        verfuegbarkeitColumn.setCellValueFactory(new PropertyValueFactory<>("verfuegbarkeit"));

        TableColumn<FilterWohnung, String> bewertungstextColumn = new TableColumn<>("Bewertungstext");
        bewertungstextColumn.setCellValueFactory(new PropertyValueFactory<>("bewertungstext"));

        TableColumn<FilterWohnung, String> sternBewertungColumn = new TableColumn<>("SternBewertung");
        sternBewertungColumn.setCellValueFactory(new PropertyValueFactory<>("sternBewertung"));

        tv_wohnungen.getColumns().addAll(benutzerNameColumn, anschriftColumn, verifiziertColumn, preisProNachtColumn, beschreibungColumn, verfuegbarkeitColumn, bewertungstextColumn, sternBewertungColumn);
    }

    private void fillTableView() throws Exception {
        ObservableList<FilterWohnung> data = FXCollections.observableArrayList(getAllWohnungen());
        tv_wohnungen.setItems(data);
    }

    private List<FilterWohnung> getAllWohnungen() throws Exception {
        return Wohnungenverwaltung.getAllWohnungen();
    }
}
