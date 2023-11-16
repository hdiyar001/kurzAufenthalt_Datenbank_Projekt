package praesentationsschicht_GUI.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.text.Text;
import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

/**
 *
 * @author Diyar
 */
public class DashboardTopController implements Initializable {

    @FXML
    private Text tf_benutzername;
    @FXML
    private MenuBar menuBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tf_benutzername.setText("Hallo, " + LoginController.benutzerName);

    }

}
