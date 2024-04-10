package datenhaltungsschicht;

import static datenhaltungsschicht.DBBenutzer.getAnrede;
import static datenhaltungsschicht.DBBenutzer.getBenutzerName;
import static datenhaltungsschicht.DBBenutzer.getEmail;
import static datenhaltungsschicht.DBBenutzer.getGeburtsdatum;
import static datenhaltungsschicht.DBBenutzer.getNachname;
import static datenhaltungsschicht.DBBenutzer.getOrt;
import static datenhaltungsschicht.DBBenutzer.getPLZ;
import static datenhaltungsschicht.DBBenutzer.getPasswort;
import static datenhaltungsschicht.DBBenutzer.getStrasse;
import static datenhaltungsschicht.DBBenutzer.getVorname;
import static datenhaltungsschicht.DBBenutzer.getrefBenutzer;
import static datenhaltungsschicht.DBBenutzer.getverft;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainTest {

    public static void main(String[] args) {
        // Informationen für die Verbindung
        String url = "jdbc:oracle:thin:@dihas001.fritz.box:1521:xe";
        String user = "system";
        String password = "system"; // Ersetzen Sie "IhrPasswort" durch Ihr tatsächliches Passwort
        ResultSet datenmenge;
        Connection connection = null;
        try {
            // Oracle JDBC Driver laden
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Verbindung herstellen
            connection = DriverManager.getConnection(url, user, password);

            // Eine Testabfrage ausführen
            String query = "SELECT * FROM T_Benutzer";

            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                datenmenge = pstmt.executeQuery();
                while (datenmenge.next()) {
                    String benutzerId = datenmenge.getString("benutzerId");
                    String nachname = datenmenge.getString("Nachname");
                    System.out.println(nachname);
                }
            } catch (SQLException e) {
                System.err.println("Fehler beim Abrufen der Benutzerliste: " + e.getMessage());
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Verbindung schließen
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
