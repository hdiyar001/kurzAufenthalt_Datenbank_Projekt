package datenhaltungsschicht;

import static datenhaltungsschicht.DBZugriff.close;
import logikschicht.Benutzer;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diyar
 */
public class DBBenutzer {

    private static ResultSet datenmenge;
    private static DBZugriff dbZugriff = DBZugriff.getInstance();

    /**
     * Author Diyar
     *
     *
     * Hier werden neue Daten hizugefügt
     *
     * @param benutzer
     * @return true, wenn erfolgreich hinzugefügt wurde!
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
