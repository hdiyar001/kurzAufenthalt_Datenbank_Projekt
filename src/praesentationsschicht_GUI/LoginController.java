package praesentationsschicht_GUI;

import logikschicht.Modell;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Diyar
 */
public class LoginController implements Initializable {
//Dependency Injection (DI)

    @FXML
    private TextField tf_userNameLogin;

    @FXML
    private TextField tf_passwordLogin;

    @FXML
    private Label lbl_signup;

    @FXML
    private Button btn_login;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btn_login.setOnAction((event) -> onLogin());
        lbl_signup.setOnMouseClicked((event) -> onSignUp());
    }

    private void onLogin() {
        //We can get the stage of this window by any these avaliable controlls obove
        Stage stage = (Stage) btn_login.getScene().getWindow();
        Modell.getInstance().getViewFacotry().closeStage(stage);
        Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().set("Dashboard");
    }

    private void onSignUp() {
        Stage stage = (Stage) lbl_signup.getScene().getWindow();
        Modell.getInstance().getViewFacotry().closeStage(stage);
        Modell.getInstance().getViewFacotry().showSignUpWindow();

    }
}

//    @FXML
//    public void login(ActionEvent e) {
//        Login loginDatabase = new Login();
////        Button b = (Button) e.getSource();
//        System.out.println(e.getSource() == btn_login);
////        b.getScene().getWindow().hide();
//        String loginNameOrEmail = tf_userNameLogin.getText();
//        String password = tf_passwordLogin.getText();
//        System.out.println(loginNameOrEmail + " " + password);
//
//        if (loginDatabase.emailExists(loginNameOrEmail) && loginDatabase.passwordExists(password))
//        {
//            System.out.println("Login was Seccsussful");
//            Modell.getInstance().getViewFacotry().showClientWindow();
//        }
//    }
