package praesentationsschicht_GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
//import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logikschicht.Modell;

/**
 *
 * @author Diyar
 */
public class ViewFactory {

    //DashboardView
    private final StringProperty clientSelectedMenuItem;
    private AnchorPane WohnungView;
    private AnchorPane VermietenView;
    private AnchorPane NachrichtenView;
    private AnchorPane BuchungenView;
    private AnchorPane AccountView;

    public ViewFactory() {
        this.clientSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcen/UI/Authentication/LogInWindow.fxml"));
        createStage(loader);
    }

    public void showSignUpWindow() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcen/UI/Authentication/SignUpWindow.fxml"));
        createStage(loader);
    }

    public void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcen/UI/ScreenNavigation/ClientWindow.fxml"));
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
        stage.getIcons().add(new Image("/Bilder/App_Icon.png"));
        stage.setScene(scene);
        stage.setTitle("kurzAufenthaltApp ");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    public AnchorPane getWohnungView() {
        if (WohnungView == null)
        {
            try
            {
                WohnungView = new FXMLLoader(getClass().getResource("/resourcen/UI/ScreenNavigation/WohnungWindow.fxml")).load();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return WohnungView;
    }

    public AnchorPane getVermietenView() {
        if (VermietenView == null)
        {
            try
            {
                VermietenView = new FXMLLoader(getClass().getResource("/resourcen/UI/ScreenNavigation/VermietenWindow.fxml")).load();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return VermietenView;
    }

    public AnchorPane getBuchungenView() {
        if (BuchungenView == null)
        {
            try
            {
                BuchungenView = new FXMLLoader(getClass().getResource("/resourcen/UI/ScreenNavigation/BuchungenWindow.fxml")).load();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return BuchungenView;
    }

    public AnchorPane getNachrichtenView() {
        if (NachrichtenView == null)
        {
            try
            {
                NachrichtenView = new FXMLLoader(getClass().getResource("/resourcen/UI/ScreenNavigation/NachrichtenWindow.fxml")).load();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return NachrichtenView;
    }

    public AnchorPane getAccountView() {
        if (AccountView == null)
        {
            try
            {
                AccountView = new FXMLLoader(getClass().getResource("/resourcen/UI/ScreenNavigation/AccountWindow.fxml")).load();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return AccountView;
    }

}
