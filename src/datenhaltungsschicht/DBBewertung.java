package datenhaltungsschicht;

import logikschicht.Bewertung;
import static datenhaltungsschicht.DBZugriff.befehl;
import static datenhaltungsschicht.DBZugriff.close;
import static datenhaltungsschicht.DBZugriff.connect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diyar+
 */
public class DBBewertung extends DBZugriff {

    private static ResultSet datenmenge;

    public static boolean Insert(Bewertung bewertung) throws Exception {
        connect();
        String insertCommand = "INSERT INTO T_Bewertung VALUES ("
                + bewertung.getBewertungId()
                + ", " + bewertung.getBuchungId()
                + ", '" + bewertung.getBewertungText()
                + "', '" + bewertung.getSternBewertung()
                + "'";
        try
        {
            befehl.executeUpdate(insertCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Hinzufügen des Bewertungs " + bewertung.getBewertungId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean update(Bewertung bewertung) throws Exception {
        connect();

        String updateCommand = "UPDATE T_Bewertung SET buchungId = " + bewertung.getBuchungId() + ", bewertungstext= " + bewertung.getBewertungText()
                + ", sternBewertung = " + bewertung.getSternBewertung() + " WHERE bewertungId = " + bewertung.getBewertungId();

        try
        {
            befehl.executeUpdate(updateCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Aktualisieren des Bewertungs " + bewertung.getBewertungId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean Delete(Bewertung bewertung) throws Exception {
        connect();
        String deleteCommand = "DELETE FROM T_Bewertung WHERE bewertungId = " + bewertung.getBewertungId();

        try
        {
            befehl.executeUpdate(deleteCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Löschen des Bewertungs " + bewertung.getBewertungId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static List<Bewertung> getAllBewertung() throws Exception {

        ArrayList<Bewertung> bewertungen = new ArrayList<>();
        connect();
        try
        {
            datenmenge = befehl.executeQuery("SELECT * FROM T_Bewertung");
            while (getNext())
            {
                String bewertungId = getbewertungId();
                String buchungId = getBuchungId();
                String bewertungText = getBewertungsText();
                String sternBewertung = getSternBewertung();

                Bewertung bewertung = new Bewertung(bewertungId, buchungId, bewertungText, sternBewertung);
                bewertungen.add(bewertung);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Bewertungdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return bewertungen;
    }

    public static Bewertung getBewertungByBewertungId(String bewertungId) throws Exception {
        connect();
        Bewertung bewertung = null;
        String query = "SELECT * FROM T_Bewertung WHERE bewertungid = " + bewertungId;

        try
        {
            datenmenge = befehl.executeQuery(query);

            if (datenmenge.next())
            {
                String buchungId = getBuchungId();
                String bewertungText = getBewertungsText();
                String sternBewertung = getSternBewertung();

                bewertung = new Bewertung(bewertungId, buchungId, bewertungText, sternBewertung);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Bewertungdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return bewertung;
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

    public static String getbewertungId() throws SQLException {
        return datenmenge.getString("bewertungId");
    }

    public static String getBuchungId() throws Exception {
        return datenmenge.getString("buchungId");
    }

    public static String getBewertungsText() throws Exception {
        return datenmenge.getString("bewertungsText");
    }

    public static String getSternBewertung() throws Exception {
        return datenmenge.getString("sterneBewertung");
    }
}
