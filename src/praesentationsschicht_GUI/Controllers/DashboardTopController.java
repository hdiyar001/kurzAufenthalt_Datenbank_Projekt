package praesentationsschicht_GUI.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import logikschicht.Modell;
import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

/**
 *
 * @author Diyar
 */
public class DashboardTopController implements Initializable {

    @FXML
    private MenuBar menuBar;

    @FXML
    private Text tf_benutzername;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tf_benutzername.setText("Hallo, " + LoginController.benutzerName);
//        Menu menu = new Menu("Bearbeiten");
//        MenuItem testDatenItem = new MenuItem("Test_Daten Einfügen");
//
//        menu.getItems().add(testDatenItem);
//        menuBar.getMenus().add(menu);
    }

}
