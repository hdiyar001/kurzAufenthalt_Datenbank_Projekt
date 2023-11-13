package praesentationsschicht_GUI;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logikschicht.Modell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import logikschicht.Benutzer;
import logikschicht.Benutzerverwaltung;

/**
 *
 * @author Diyar
 */
public class SignUpController implements Initializable {

    @FXML
    private ComboBox<String> anredeComboBox;

    @FXML
    private Button btn_signup;

    @FXML
    private CheckBox cb_acceptTerms;

    @FXML
    private DatePicker dp_gebDat;

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
    private TextField tf_hsNr;

    @FXML
    private TextField tf_lastName;

    @FXML
    private TextField tf_ort;

    @FXML
    private PasswordField tf_password;

    @FXML
    private TextField tf_plz;

    @FXML
    private TextField tf_refBenutzerId;

    @FXML
    private TextField tf_strasse;

    @FXML
    private TextField tf_username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text_errorMessage.setText("");
        anredeComboBox.getItems().addAll("HERR", "FRAU");

        addListener();

    }

    private void addListener() {
        btn_signup.setOnAction((e) -> onSignup());
    }

    private void onSignup() {

        try
        {
            if (checkInputs())
            {
                Benutzer neueBenutzer = getBenutzer();
                System.out.println(neueBenutzer);
                if (Benutzerverwaltung.storeBenutzer(neueBenutzer))
                {
                    System.out.println("Der Benutzer wurde hinzugefügt.");

                    Stage stage = (Stage) btn_signup.getScene().getWindow();
                    Modell.getInstance().getViewFacotry().closeStage(stage);
                    Modell.getInstance().getViewFacotry().showLoginWindow();
                }
            } else
            {
                text_errorMessage.setText("Bitte füllen Sie alle Notwendige Eingabefelder!.");
            }
        } catch (Exception ex)
        {
            text_errorMessage.setText("Bitte füllen Sie alle Notwendige Eingabefelder!.");
        }
    }

    private Benutzer getBenutzer() {
        String formattedDate = formatDate(dp_gebDat.getValue());

        return new Benutzer("7",
                tf_lastName.getText(),
                tf_firstName.getText(),
                anredeComboBox.getValue(),
                tf_username.getText(),
                tf_email.getText(),
                tf_password.getText(),
                tf_strasse.getText(),
                tf_ort.getText(),
                tf_plz.getText(),
                formattedDate,
                tf_refBenutzerId.getText().isBlank() ? "null" : tf_refBenutzerId.getText(),
                "N");
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    private boolean checkInputs() {
        String formattedDate = formatDate(dp_gebDat.getValue());
        return !(tf_lastName.getText().isBlank()
                && tf_firstName.getText().isBlank()
                && anredeComboBox.getValue().isBlank()
                && tf_username.getText().isBlank()
                && tf_password.getText().isBlank()
                && tf_strasse.getText().isBlank()
                && tf_ort.getText().isBlank()
                && tf_plz.getText().isBlank()
                && formattedDate.isBlank()) && emailValdator();

    }

    private boolean emailValdator() {
        return tf_email.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}
