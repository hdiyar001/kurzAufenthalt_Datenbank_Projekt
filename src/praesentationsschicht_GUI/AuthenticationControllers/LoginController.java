package praesentationsschicht_GUI.AuthenticationControllers;

import logikschicht.Modell;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logikschicht.Benutzer;
import logikschicht.Benutzerverwaltung;

/**
 *
 * @author Diyar
 */
public class LoginController implements Initializable {

    @FXML
    private TextField tf_userNameLogin;

    @FXML
    private TextField tf_passwordLogin;

    @FXML
    private Label lbl_signup;

    @FXML
    private Button btn_login;

    @FXML
    private AnchorPane enterpressed;

    @FXML
    private Text text_errorMessage;

    @FXML
    private Label lbl_forgotPass;

    public static String benutzerName;
    public static String benutzerId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text_errorMessage.setText("");
        btn_login.setOnAction((event) -> onLogin());
        lbl_signup.setOnMouseClicked((event) -> onSignUp());
        lbl_forgotPass.setOnMouseClicked(e -> onPasswordForgot());
        enterpressed.setOnKeyPressed(e ->
        {
            if (e.getCode() == KeyCode.ENTER)
            {
                onLogin();
            }
        });
    }

    private void onLogin() {
        try
        {
            String BNameemail = tf_userNameLogin.getText();
            String passwort = tf_passwordLogin.getText();
            if (BNameemail.isBlank() || passwort.isBlank())
            {
                text_errorMessage.setText("Bitte kein leere Eingabefelder!");
            } else
            {
                Benutzer benutzer = Benutzerverwaltung.checkUserExists(BNameemail, passwort);

                if (benutzer != null)
                {
                    benutzerName = benutzer.getBenutzerName();
                    benutzerId = benutzer.getBenutzerId();
                    Stage stage = (Stage) btn_login.getScene().getWindow();
                    Modell.getInstance().getViewFacotry().closeStage(stage);
                    Modell.getInstance().getViewFacotry().showClientWindow();
                    Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().set("Wohnungen");
                } else
                {

                    text_errorMessage.setText("Konto nicht gefunden oder Passwort falsch!");
                }
            }
        } catch (Exception ex)
        {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void onSignUp() {
        Stage stage = (Stage) lbl_signup.getScene().getWindow();
        Modell.getInstance().getViewFacotry().closeStage(stage);
        Modell.getInstance().getViewFacotry().showSignUpWindow();

    }

    private void onPasswordForgot() {
        Stage stage = (Stage) lbl_forgotPass.getScene().getWindow();
        Modell.getInstance().getViewFacotry().closeStage(stage);
        Modell.getInstance().getViewFacotry().showNewPasswortView();

    }
}
