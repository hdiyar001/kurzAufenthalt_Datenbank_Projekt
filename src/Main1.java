
import datenhaltungsschicht.DBZugriff;
import java.sql.SQLException;
import logikschicht.Benutzerverwaltung;
import logikschicht.Buchungenverwaltung;
import logikschicht.Nachrichtenverwaltung;
import logikschicht.Wohnungenverwaltung;

/**
 *
 * @author Diyar
 */
public class Main1 extends DBZugriff {

    public static void main(String[] args) throws SQLException, Exception {

//        KonsolenMenue.startMenu();
//        System.out.println(storeBenutzer(new Benutzer("6", "Mueller", "Max", "HERR", "max.mueller", "max.mueller@example.com", "max12345", "Beispielstr. 1", "Beispielstadt", "12345", "01.01.1990", "2", "J")));
        Nachrichtenverwaltung.getAllNachrichten().forEach(s -> System.out.println(s));

    }

}
