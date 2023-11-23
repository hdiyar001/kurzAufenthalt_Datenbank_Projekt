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

    /**
     * Author Diyar
     *
     *
     * Hier werden neue Daten hizugefügt
     *
     * @param benutzer
     * @return
     * @throws Exception
     */
    public static boolean Insert(Benutzer benutzer) throws Exception {
        connect();
        String insertCommand = "INSERT INTO T_Benutzer VALUES ("
                + benutzer.getBenutzerId()
                + ", '" + benutzer.getNachname()
                + "', '" + benutzer.getVorname()
                + "', '" + benutzer.getAnrede()
                + "', '" + benutzer.getBenutzerName()
                + "', '" + benutzer.getEmail()
                + "', '" + benutzer.getPasswort()
                + "', '" + benutzer.getStrasse()
                + "', '" + benutzer.getOrt()
                + "', " + benutzer.getPlz()
                + ", '" + benutzer.getGeburtsdatum()
                + "', " + benutzer.getRefBenutzer()
                + ", '" + benutzer.getVerft()
                + "')";

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
        String dataToUpdate = getDataToUpdate(benutzer);
        System.out.println(dataToUpdate);
        String updateCommand = "UPDATE T_Benutzer SET " + dataToUpdate + " WHERE benutzerId = " + benutzer.getBenutzerId();

        try
        {
            befehl.executeUpdate(updateCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Aktualisieren des Benutzers " + benutzer.getBenutzerId() + " aufgetreten.";
            ex.printStackTrace();
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean updatePasswort(String BNameemail, String passwort) throws SQLException, Exception {
        connect();
        String updateCommand = "UPDATE T_Benutzer SET passwort='" + passwort + "' WHERE benutzername = '" + BNameemail + "' OR Email= '" + BNameemail + "'";

        try
        {
            befehl.executeUpdate(updateCommand);    
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Aktualisieren des Benutzers " + BNameemail + " aufgetreten.";
            ex.printStackTrace();
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;

    }

    public static boolean Delete(String benutzerId) throws Exception {
        connect();
        int status = -1;
        String deleteCommand = "DELETE FROM T_Benutzer WHERE benutzerId = " + benutzerId;

        try
        {
            status = befehl.executeUpdate(deleteCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Löschen des Benutzers " + benutzerId + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return status == 1;
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
                String benutzerName = getBenutzerName();
                String email = getEmail();
                String passwort = getPasswort();
                String strasse = getStrasse();
                String ort = getOrt();
                String plz = getPLZ();
                String geburtsdatum = getGeburtsdatum();
                String refBenutzer = getrefBenutzer();
                String verft = getverft();

                Benutzer benuzter = new Benutzer(benutzerId, nachname, vorname, anrede, benutzerName, email, passwort, strasse, ort, plz, geburtsdatum, refBenutzer, verft);
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
                String benutzerName = getBenutzerName();
                String email = getEmail();
                String passwort = getPasswort();
                String strasse = getStrasse();
                String ort = getOrt();
                String plz = getPLZ();
                String geburtsdatum = getGeburtsdatum();
                String refBenutzer = getrefBenutzer();
                String verft = getverft();

                benuzter = new Benutzer(benutzerId, nachname, vorname, anrede, benutzerName, email, passwort, strasse, ort, plz, geburtsdatum, refBenutzer, verft);
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

    public static Benutzer getBenutzerByLogin(String l_bNameOEmail, String l_passwort) throws Exception {
        connect();
        Benutzer benuzter = null;
        String query = "SELECT * FROM T_Benutzer WHERE (benutzerName = '" + l_bNameOEmail + "' OR  email = '" + l_bNameOEmail + "') AND passwort = '" + l_passwort + "'";

        try
        {
            datenmenge = befehl.executeQuery(query);

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
                benuzter = new Benutzer(benutzerId, nachname, vorname, anrede, benutzerName, email, passwort, strasse, ort, plz, geburtsdatum, refBenutzer, verft);
                System.out.println(benuzter);
            }
        } catch (SQLException e)
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

    public static String getLastId() throws Exception {
        connect();

        try
        {
            String sql = "SELECT MAX(benutzerId) FROM T_Benutzer";
            datenmenge = befehl.executeQuery(sql);
            if (getNext())
            {
                return datenmenge.getString(1);
            }
        } catch (SQLException e)
        {
            throw new Exception(e.getSQLState());
        } finally
        {
            close();
        }
        return "0";
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

}
