package praesentationsschicht_GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
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

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcen/UI/Authentication/LogInWindow.fxml"));
        createStage(loader);
    }

    public void showSignUpWindow() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcen/UI/Authentication/SignUpWindow.fxml"));
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
//        stage.getIcons().add(new Image("/resources/Images"));
        stage.setScene(scene);
        stage.setTitle("kurzAufenthalt ");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
