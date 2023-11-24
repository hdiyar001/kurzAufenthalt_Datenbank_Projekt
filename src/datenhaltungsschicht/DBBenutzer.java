package datenhaltungsschicht;

import static datenhaltungsschicht.DBZugriff.close;
import logikschicht.Benutzer;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse DBBenutzer verwaltet die Benutzerdaten in der Datenbank. Sie
 * bietet Methoden zum Einfügen, Aktualisieren, Löschen und Abfragen von
 * Benutzerdaten. Diese Klasse ist Teil der Datenhaltungsschicht in der
 * 3-Schichten-Architektur.
 *
 * @author Diyar, Hussam und Ronida
 */
public class DBBenutzer {

    private static ResultSet datenmenge;
    private static DBZugriff dbZugriff = DBZugriff.getInstance();

    /**
     * Fügt einen neuen Benutzer in die Datenbank ein.
     *
     * @param benutzer Das Benutzerobjekt, das in die Datenbank eingefügt werden
     * soll.
     * @return true, wenn der Benutzer erfolgreich hinzugefügt wurde, sonst
     * false.
     */
    public static boolean Insert(Benutzer benutzer) {
        String insertCommand = "INSERT INTO T_Benutzer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(insertCommand))
        {
            pstmt.setString(1, benutzer.getBenutzerId());
            pstmt.setString(2, benutzer.getNachname());
            pstmt.setString(3, benutzer.getVorname());
            pstmt.setString(4, benutzer.getAnrede());
            pstmt.setString(5, benutzer.getBenutzerName());
            pstmt.setString(6, benutzer.getEmail());
            pstmt.setString(7, benutzer.getPasswort());
            pstmt.setString(8, benutzer.getStrasse());
            pstmt.setString(9, benutzer.getOrt());
            pstmt.setString(10, benutzer.getPlz());
            pstmt.setString(11, benutzer.getGeburtsdatum());
            pstmt.setString(12, benutzer.getRefBenutzer());
            pstmt.setString(13, benutzer.getVerft());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex)
        {
            System.err.println("Fehler beim Hinzufügen des Benutzers: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Aktualisiert die Daten eines existierenden Benutzers in der Datenbank.
     *
     * @param benutzer Das Benutzerobjekt mit aktualisierten Daten.
     * @return true, wenn die Aktualisierung erfolgreich war, sonst false.
     */
    public static boolean update(Benutzer benutzer) {
        String dataToUpdate = getDataToUpdate(benutzer);

        String updateCommand = "UPDATE T_Benutzer SET " + dataToUpdate + " WHERE benutzerId = " + benutzer.getBenutzerId();
        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(updateCommand))
        {
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex)
        {
            System.err.println("Fehler beim Aktualisieren des Benutzers: " + ex.getMessage());
            return false;
        }
    }

    private static String getDataToUpdate(Benutzer benutzer) {
        String sql = "";

        sql += benutzer.getAnrede() != null ? " Anrede = '" + benutzer.getAnrede() + "', " : "";
        sql += benutzer.getVorname() != null ? " Vorname = '" + benutzer.getVorname() + "', " : "";
        sql += benutzer.getNachname() != null ? " Nachname = '" + benutzer.getNachname() + "', " : "";
        sql += benutzer.getGeburtsdatum() != null ? " Geburtsdatum = '" + benutzer.getGeburtsdatum() + "', " : "";
        sql += benutzer.getEmail() != null ? " Email = '" + benutzer.getEmail() + "', " : "";
        sql += benutzer.getPlz() != null ? " PLZ = " + benutzer.getPlz() + ", " : "";
        sql += benutzer.getOrt() != null ? " Ort = '" + benutzer.getOrt() + "', " : "";
        sql += benutzer.getBenutzerName() != null ? " benutzerName= '" + benutzer.getBenutzerName() + "', " : "";
        sql += benutzer.getPasswort() != null ? " passwort= '" + benutzer.getPasswort() + "', " : "";
        sql += benutzer.getStrasse() != null ? " Strasse = '" + benutzer.getStrasse() + "', " : "";
        sql += benutzer.getRefBenutzer() != null ? " referenzbenutzerid = " + benutzer.getRefBenutzer() + ", " : "";
        sql += benutzer.getVerft() != null ? " verifiziert= '" + benutzer.getVerft() + "', " : "";
        return sql.substring(0, sql.length() - 2);
    }

    /**
     * Aktualisiert das Passwort eines Benutzers in der Datenbank.
     *
     * @param BNameemail Der Benutzername oder die E-Mail-Adresse des Benutzers,
     * dessen Passwort aktualisiert werden soll.
     * @param passwort Das neue Passwort des Benutzers.
     * @return true, wenn das Passwort erfolgreich aktualisiert wurde, sonst
     * false.
     * @throws SQLException Wirft eine Ausnahme bei Fehlern während der
     * Datenbankaktualisierung.
     */
    public static boolean updatePasswort(String BNameemail, String passwort) throws SQLException {
        String updateCommand = "UPDATE T_Benutzer SET passwort = ? WHERE benutzername = ? OR Email = ?";

        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(updateCommand))
        {
            pstmt.setString(1, passwort);
            pstmt.setString(2, BNameemail);
            pstmt.setString(3, BNameemail);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex)
        {
            System.err.println("Fehler beim Aktualisieren des Passworts für Benutzer " + BNameemail + ": " + ex.getMessage());
            throw ex;
        }
    }

    /**
     * Löscht einen Benutzer aus der Datenbank anhand seiner Benutzer-ID.
     *
     * @param benutzerId Die ID des zu löschenden Benutzers.
     * @return true, wenn der Benutzer erfolgreich gelöscht wurde, sonst false.
     * @throws SQLException Wirft eine SQLException bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean Delete(String benutzerId) throws SQLException {
        String deleteCommand = "DELETE FROM T_Benutzer WHERE benutzerId = ?";

        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(deleteCommand))
        {
            pstmt.setInt(1, Integer.parseInt(benutzerId));
            int status = pstmt.executeUpdate();
            return status == 1;
        } catch (SQLException ex)
        {
            System.err.println("Fehler beim Löschen des Benutzers " + benutzerId + ": " + ex.getMessage());
            throw ex;
        }
    }

    /**
     * Ruft eine Liste aller Benutzer aus der Datenbank ab.
     *
     * @return Eine Liste von Benutzerobjekten.
     * @throws Exception Wirft eine Ausnahme bei Fehlern während der
     * Datenbankabfrage.
     */
    public static List<Benutzer> getAllBenutzer() throws Exception {

        List<Benutzer> benutzerListe = new ArrayList<>();
        String query = "SELECT * FROM T_Benutzer";

        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(query))
        {
            datenmenge = pstmt.executeQuery();
            while (datenmenge.next())
            {
                String benutzerId = getbenutzerId();
                String nachname = getNachname();
                String vorname = getVorname();
                String anrede = getAnrede();
                String benutzerName = getBenutzerName();
                String email = getEmail();
                String passwort = getPasswort();
                String strasse = getStrasse();
                String ort = getOrt();
                String plz = getPLZ();
                String geburtsdatum = getGeburtsdatum();
                String refBenutzer = getrefBenutzer();
                String verft = getverft();

                Benutzer benutzer = new Benutzer(benutzerId, nachname, vorname, anrede, benutzerName, email, passwort, strasse, ort, plz, geburtsdatum, refBenutzer, verft);
                benutzerListe.add(benutzer);
            }
        } catch (SQLException e)
        {
            System.err.println("Fehler beim Abrufen der Benutzerliste: " + e.getMessage());
            throw e;
        }

        return benutzerListe;
    }

    /**
     * Ruft einen Benutzer anhand seiner Benutzer-ID aus der Datenbank ab.
     *
     * @param benutzerId Die ID des abzurufenden Benutzers.
     * @return Ein Benutzerobjekt, wenn ein Benutzer mit der angegebenen ID
     * gefunden wurde, sonst null.
     * @throws Exception Wirft eine Ausnahme bei Fehlern während der
     * Datenbankabfrage.
     */
    public static Benutzer getBenutzerByBenutzerId(String benutzerId) throws Exception {
        String query = "SELECT * FROM T_Benutzer WHERE benutzerid = ?";
        Benutzer benutzer = null;

        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(query))
        {
            pstmt.setString(1, benutzerId);

            datenmenge = pstmt.executeQuery();
            if (datenmenge.next())
            {
                String nachname = getNachname();
                String vorname = getVorname();
                String anrede = getAnrede();
                String benutzerName = getBenutzerName();
                String email = getEmail();
                String passwort = getPasswort();
                String strasse = getStrasse();
                String ort = getOrt();
                String plz = getPLZ();
                String geburtsdatum = getGeburtsdatum();
                String refBenutzer = getrefBenutzer();
                String verft = getverft();

                benutzer = new Benutzer(benutzerId, nachname, vorname, anrede, benutzerName, email, passwort, strasse, ort, plz, geburtsdatum, refBenutzer, verft);
            }
        } catch (SQLException e)
        {
            System.err.println("Fehler beim Abrufen des Benutzers mit ID " + benutzerId + ": " + e.getMessage());
            throw e;
        }

        return benutzer;
    }

    /**
     * Ruft einen Benutzer aus der Datenbank ab, basierend auf Login-Daten
     * (Benutzername oder E-Mail und Passwort).
     *
     * @param l_bNameOEmail Der Benutzername oder die E-Mail des Benutzers.
     * @param l_passwort Das Passwort des Benutzers.
     * @return Ein Benutzerobjekt, wenn ein entsprechender Benutzer gefunden
     * wurde, sonst null.
     * @throws Exception Wirft eine Ausnahme bei Fehlern während der
     * Datenbankabfrage.
     */
    public static Benutzer getBenutzerByLogin(String l_bNameOEmail, String l_passwort) throws Exception {
        String query = "SELECT * FROM T_Benutzer WHERE (benutzerName = ? OR email = ?) AND passwort = ?";
        Benutzer benutzer = null;

        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(query))
        {
            pstmt.setString(1, l_bNameOEmail);
            pstmt.setString(2, l_bNameOEmail);
            pstmt.setString(3, l_passwort);
            datenmenge = pstmt.executeQuery();

            if (datenmenge.next())
            {
                String benutzerId = getbenutzerId();
                String nachname = getNachname();
                String vorname = getVorname();
                String anrede = getAnrede();
                String benutzerName = getBenutzerName();
                String email = getEmail();
                String passwort = getPasswort();
                String strasse = getStrasse();
                String ort = getOrt();
                String plz = getPLZ();
                String geburtsdatum = getGeburtsdatum();
                String refBenutzer = getrefBenutzer();
                String verft = getverft();
                benutzer = new Benutzer(benutzerId, nachname, vorname, anrede, benutzerName, email, passwort, strasse, ort, plz, geburtsdatum, refBenutzer, verft);
            }
        } catch (SQLException e)
        {
            System.err.println("Fehler beim Abrufen des Benutzers: " + e.getMessage());
            throw e;
        }

        return benutzer;
    }

    /**
     * Prüft, ob weitere Daten in der Ergebnismenge vorhanden sind.
     *
     * @return true, wenn weitere Daten vorhanden sind, sonst false.
     * @throws Exception Wirft eine Ausnahme bei Fehlern während der
     * Datenbankabfrage.
     */
    public static boolean getNext() throws Exception {
        if (datenmenge.next())
        {
            return true;
        } else
        {
            close();
            return false;
        }
    }

    /**
     * Ruft die höchste Benutzer-ID aus der Datenbank ab.
     *
     * @return Die höchste vorhandene Benutzer-ID.
     * @throws Exception Wirft eine Ausnahme bei Fehlern während der
     * Datenbankabfrage.
     */
    public static String getLastId() throws Exception {
        String sql = "SELECT MAX(benutzerId) FROM T_Benutzer";

        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(sql);)
        {
            datenmenge = pstmt.executeQuery();
            if (getNext())
            {
                return datenmenge.getString(1);
            } else
            {
                return "0";
            }
        } catch (SQLException e)
        {
            System.err.println("Fehler beim Abrufen der letzten Benutzer-ID: " + e.getMessage());
            throw e;
        }
    }

    public static String getbenutzerId() throws SQLException {
        return datenmenge.getString("benutzerId");
    }

    public static String getNachname() throws SQLException {
        return datenmenge.getString("NachName");
    }

    public static String getVorname() throws SQLException {
        return datenmenge.getString("VorName");
    }

    public static String getAnrede() throws SQLException {
        return datenmenge.getString("anrede");
    }

    public static String getEmail() throws SQLException {
        return datenmenge.getString("Email");
    }

    public static String getStrasse() throws SQLException {
        return datenmenge.getString("Strasse");
    }

    public static String getOrt() throws SQLException {
        return datenmenge.getString("Ort");
    }

    public static String getPLZ() throws SQLException {
        return datenmenge.getString("PLZ");
    }

    public static String getGeburtsdatum() throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(datenmenge.getDate("Geburtsdatum"));
    }

    public static String getrefBenutzer() throws SQLException {
        return datenmenge.getString("referenzbenutzerid");
    }

    public static String getverft() throws SQLException {
        return datenmenge.getString("verifiziert");
    }

    public static String getBenutzerName() throws SQLException {
        return datenmenge.getString("benutzerName");
    }

    public static String getPasswort() throws SQLException {
        return datenmenge.getString("passwort");
    }

}
