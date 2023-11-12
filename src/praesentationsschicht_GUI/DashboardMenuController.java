package praesentationsschicht;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import logikschicht.Modell;
import javafx.stage.Stage;

/**
 *
 * @author Diyar
 */
public class DashboardMenuController implements Initializable {

    @FXML
    private Button account_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button exercise_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button report_btn;

    @FXML
    private Button settings_btn;

    @FXML
    private Button statistics_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    private void addListener() {
        dashboard_btn.setOnAction((e) -> onDashboard());
//        account_btn.setOnAction((e) -> onAccount());
//        exercise_btn.setOnAction((e) -> onExercise());
//        logout_btn.setOnAction((e) -> onLogout());
//        settings_btn.setOnAction((e) -> onSettings());
//        statistics_btn.setOnAction((e) -> onStatistic());
    }

    private void onDashboard() {
        Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().set("Dashboard");
    }

//    private void onAccount() {
//        Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().set("Account");
//    }
//
//    private void onExercise() {
//        Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().set("Workout");
//    }
//
//    private void onSettings() {
//        Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().set("Settings");
//    }
//
//    private void onStatistic() {
//        Modell.getInstance().getViewFacotry().getClientSelectedMenuItem().set("Statistic");
//    }
    private void onLogout() {
        //We can get the stage of this window by any these avaliable controlls obove
        Stage stage = (Stage) logout_btn.getScene().getWindow();
        Modell.getInstance().getViewFacotry().closeStage(stage);
        Modell.getInstance().getViewFacotry().showLoginWindow();
    }
}
