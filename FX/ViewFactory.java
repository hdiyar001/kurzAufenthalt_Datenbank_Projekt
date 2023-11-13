package praesentationsschicht;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Diyar
 */
public class ViewFactory {

    //DashboardView
    private final StringProperty clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane AccountView;
    private AnchorPane WorkoutView;
    private AnchorPane SettingsView;
    private AnchorPane StatisticView;

    public ViewFactory() {
        this.clientSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashBoardView() {
        if (dashboardView == null)
        {
            try
            {
                dashboardView = new FXMLLoader(getClass().getResource("/resources/UI/ScreenNavigation/DashboardWindow.fxml")).load();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return dashboardView;
    }

    public AnchorPane getWorkoutView() {
        if (WorkoutView == null)
        {
            try
            {
                WorkoutView = new FXMLLoader(getClass().getResource("/resources/UI/Views/WorkoutWindow.fxml")).load();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return WorkoutView;
    }

    public AnchorPane getSettingsView() {
        if (SettingsView == null)
        {
            try
            {
                SettingsView = new FXMLLoader(getClass().getResource("/resources/UI/Views/SettingsWindow.fxml")).load();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return SettingsView;
    }

    public AnchorPane getStatisticView() {
        if (StatisticView == null)
        {
            try
            {
                StatisticView = new FXMLLoader(getClass().getResource("/resources/UI/Views/StatisticWindow.fxml")).load();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return StatisticView;
    }

    public AnchorPane getAccountView() {
        if (AccountView == null)
        {
            try
            {
                AccountView = new FXMLLoader(getClass().getResource("/resources/UI/Views/AccountWindow.fxml")).load();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return AccountView;
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/UI/Authentication/LogInWindow.fxml"));
        createStage(loader);
    }

    public void showSignUpWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/UI/Authentication/SignUpWindow.fxml"));
        createStage(loader);
    }


    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try
        {
            scene = new Scene(loader.load());

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        Stage stage = new Stage();

//        stage.setResizable(false);
//        if (loader.getLocation().toString().endsWith("LogInWindow.fxml"))
//        {
//        }
        stage.getIcons().add(new Image("/resources/Images/pulseAppLogo2.png"));
        stage.setScene(scene);
        stage.setTitle("ActiviWave Workouts");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
