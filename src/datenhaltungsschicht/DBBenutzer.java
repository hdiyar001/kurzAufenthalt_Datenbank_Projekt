package datenhaltungsschicht;

import static datenhaltungsschicht.DBZugriff.befehl;
import static datenhaltungsschicht.DBZugriff.close;
import static datenhaltungsschicht.DBZugriff.connect;
import logikschicht.Benutzer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diyar
 */
public class DBBenutzer extends DBZugriff {

    private static ResultSet datenmenge;

    public static boolean Insert(Benutzer benutzer) throws Exception {
        connect();
        String insertCommand = "INSERT INTO T_Benutzer VALUES ("
                + benutzer.getBenutzerId()
                + ", '" + benutzer.getNachname()
                + "', '" + benutzer.getVorname()
                + "', '" + benutzer.getAnrede()
                + "', '" + benutzer.getEmail()
                + "', '" + benutzer.getStrasse()
                + "', '" + benutzer.getOrt()
                + "', " + benutzer.getPlz()
                + ", TO_DATE('" + benutzer.getGeburtsdatum() + "', 'dd.MM.yyyy'), "
                + benutzer.getRefBenutzer()
                + ")";

        try
        {
            befehl.executeUpdate(insertCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Hinzufügen des Benutzers " + benutzer.getBenutzerId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean update(Benutzer benutzer) throws Exception {
        connect();

        String updateCommand = "UPDATE T_Benutzer SET Anrede = '" + benutzer.getAnrede() + "', Vorname = '" + benutzer.getVorname() + "', Nachname = '" + benutzer.getNachname()
                + "', Geburtsdatum = TO_DATE('" + benutzer.getGeburtsdatum() + "', 'dd.MM.yyyy'), Email = '" + benutzer.getEmail() + "', PLZ = '" + benutzer.getPlz()
                + "', Ort = '" + benutzer.getOrt() + "', Strasse = '" + benutzer.getStrasse() + "', referenzbenutzerid = '" + benutzer.getRefBenutzer() + "' WHERE benutzerId = " + benutzer.getBenutzerId();

        try
        {
            befehl.executeUpdate(updateCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Aktualisieren des Benutzers " + benutzer.getBenutzerId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean Delete(Benutzer benutzer) throws Exception {
        connect();
        String deleteCommand = "DELETE FROM T_Benutzer WHERE benutzerId = " + benutzer.getBenutzerId();

        try
        {
            befehl.executeUpdate(deleteCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Löschen des Benutzers " + benutzer.getBenutzerId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static List<Benutzer> getAllBenutzer() throws Exception {

        ArrayList<Benutzer> benuztern = new ArrayList<>();
        connect();
        try
        {
            datenmenge = befehl.executeQuery("SELECT * FROM T_Benutzer");
            while (getNext())
            {
                String benutzerId = getbenutzerId();
                String nachname = getNachname();
                String vorname = getVorname();
                String anrede = getAnrede();
                String email = getEmail();
                String strasse = getStrasse();
                String ort = getOrt();
                String plz = getPLZ();
                String geburtsdatum = getGeburtsdatum();
                String refBenutzer = getrefBenutzer();

                Benutzer benuzter = new Benutzer(benutzerId, nachname, vorname, anrede, email, strasse, ort, plz, geburtsdatum, refBenutzer);
                benuztern.add(benuzter);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Benutzerdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return benuztern;
    }

    public static Benutzer getBenutzerByBenutzerId(String benutzerId) throws Exception {
        connect();
        Benutzer benuzter = null;
        String query = "SELECT * FROM T_Benutzer WHERE benutzerid = " + benutzerId;

        try
        {
            datenmenge = befehl.executeQuery(query);

            if (datenmenge.next())
            {
                String nachname = getNachname();
                String vorname = getVorname();
                String anrede = getAnrede();
                String email = getEmail();
                String strasse = getStrasse();
                String ort = getOrt();
                String plz = getPLZ();
                String geburtsdatum = getGeburtsdatum();
                String refBenutzer = getrefBenutzer();

                benuzter = new Benutzer(benutzerId, nachname, vorname, anrede, email, strasse, ort, plz, geburtsdatum, refBenutzer);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Benutzerdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return benuzter;
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

    public static String getbenutzerId() throws SQLException {
        return datenmenge.getString("benutzerId");
    }

    public static String getNachname() throws Exception {
        return datenmenge.getString("NachName");
    }

    public static String getVorname() throws Exception {
        return datenmenge.getString("VorName");
    }

    public static String getAnrede() throws Exception {
        return datenmenge.getString("anrede");
    }

    public static String getEmail() throws Exception {
        return datenmenge.getString("Email");
    }

    public static String getStrasse() throws Exception {
        return datenmenge.getString("Strasse");
    }

    public static String getOrt() throws Exception {
        return datenmenge.getString("Ort");
    }

    public static String getPLZ() throws Exception {
        return datenmenge.getString("PLZ");
    }

    public static String getGeburtsdatum() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(datenmenge.getDate("Geburtsdatum"));
    }

    public static String getrefBenutzer() throws Exception {
        return datenmenge.getString("referenzbenutzerid");
    }
}
