package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import datenhaltungsschicht.Modell;

/**
 *
 * @author Diyar
 */
public class SignUpController implements Initializable {

    @FXML
    private Button btn_signup;

    @FXML
    private CheckBox cb_acceptTerms;

    @FXML
    private Label lbl_email;

    @FXML
    private Label lbl_phone;

    @FXML
    private Label lbl_signupTitle;

    @FXML
    private ImageView profileImage;

    @FXML
    private Text text_errorMessage;

    @FXML
    private PasswordField tf_confirmPassword;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_firstName;

    @FXML
    private TextField tf_lastName;

    @FXML
    private PasswordField tf_password;

    @FXML
    private TextField tf_username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    private void addListener() {
        btn_signup.setOnAction((e) -> onSignup());
    }

    private void onSignup() {
        Stage stage = (Stage) btn_signup.getScene().getWindow();
        Modell.getInstance().getViewFacotry().closeStage(stage);
        Modell.getInstance().getViewFacotry().showLoginWindow();
    }
}
