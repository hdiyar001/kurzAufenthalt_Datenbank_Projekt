
import datenhaltungsschicht.DBZugriff;
import java.sql.SQLException;
import praesentationsschicht_KONSOLE.KonsolenMenue;

/**
 *
 * @author Diyar
 */
public class Main1 extends DBZugriff {

    public static void main(String[] args) throws SQLException {
        KonsolenMenue.startMenu();
    }
}
