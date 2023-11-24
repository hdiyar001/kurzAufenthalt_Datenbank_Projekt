
import datenhaltungsschicht.DBBenutzer;
import datenhaltungsschicht.DBBuchung;
import datenhaltungsschicht.DBNachrichten;
import datenhaltungsschicht.DBWohnung;
import datenhaltungsschicht.DBZugriff;
import java.sql.SQLException;
import logikschicht.Benutzer;
import logikschicht.Nachrichten;
import logikschicht.Wohnung;

/**
 *
 * @author Diyar
 */
public class Main1 {

    public static void main(String[] args) throws SQLException, Exception {
//        DBBuchung.getAllBuchung("1", null, "Teststadt", null).forEach(s -> System.out.println(s));
//        DBNachrichten.Insert(new Nachrichten(null, "1", "2", "TETSTESTE", "22.11.2023"));
        Benutzer benutzer = new Benutzer("2", "hasan", "Diyar", null, "hdiyar998",
                "hdiyar998@gmail.com", "hd", "bahnhangstr4", "Dortmund",
                "44289", "18.12.1998", null, null);
        System.out.println(DBBenutzer.update(benutzer));
    }
}
