package datenhaltungsschicht;

import static datenhaltungsschicht.DBZugriff.befehl;
import static datenhaltungsschicht.DBZugriff.close;
import static datenhaltungsschicht.DBZugriff.connect;
import logikschicht.Buchung;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diyar
 */
public class DBBuchung extends DBZugriff {

    private static ResultSet datenmenge;

    public static boolean Insert(Buchung buchung) throws Exception {
        connect();
        String insertCommand = "INSERT INTO T_Buchung VALUES ("
                + buchung.getBuchungId()
                + ", " + buchung.getMieterId()
                + ", '" + buchung.getWohnungId()
                + ", TO_DATE('" + buchung.getBuchungsDatum()
                + "', 'dd.MM.yyyy'), TO_DATE('" + buchung.getStartDatum()
                + "', 'dd.MM.yyyy'), TO_DATE('" + buchung.getEndDatum()
                + "', 'dd.MM.yyyy')";

        try
        {
            befehl.executeUpdate(insertCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Hinzufügen des Buchungs " + buchung.getBuchungId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean update(Buchung buchung) throws Exception {
        connect();

        String updateCommand = "UPDATE T_Buchung SET mieterId = " + buchung.getMieterId() + ", wohnungId= " + buchung.getWohnungId()
                + ", buchungsdatum= '" + buchung.getBuchungsDatum() + "', startDatum= '" + buchung.getStartDatum() + "', endDatum = " + buchung.getEndDatum() + "', WHERE buchungId = " + buchung.getBuchungId();

        try
        {
            befehl.executeUpdate(updateCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Aktualisieren des Buchungs " + buchung.getBuchungId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean Delete(Buchung buchung) throws Exception {
        connect();
        String deleteCommand = "DELETE FROM T_Buchung WHERE buchungId = " + buchung.getBuchungId();

        try
        {
            befehl.executeUpdate(deleteCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Löschen des Buchungs " + buchung.getBuchungId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static List<Buchung> getAllBuchung() throws Exception {

        ArrayList<Buchung> buchungen = new ArrayList<>();
        connect();
        try
        {
            datenmenge = befehl.executeQuery("SELECT * FROM T_Buchung");
            while (getNext())
            {
                String buchungId = getbuchungId();
                String mieterId = getMieterId();
                String wohnungId = getWohnungId();
                String buchungsDatum = getBuchungsDatum();
                String startDatum = getStartDatum();
                String endDatum = getEndDatum();

                Buchung buchung = new Buchung(buchungId, mieterId, wohnungId, buchungsDatum, startDatum, endDatum);
                buchungen.add(buchung);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Buchungdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return buchungen;
    }

    public static Buchung getBuchungByBuchungId(String buchungId) throws Exception {
        connect();
        Buchung buchung = null;
        String query = "SELECT * FROM T_Buchung WHERE buchungid = " + buchungId;

        try
        {
            datenmenge = befehl.executeQuery(query);

            if (datenmenge.next())
            {
                String mieterId = getMieterId();
                String wohnungId = getWohnungId();
                String buchungsDatum = getBuchungsDatum();
                String startDatum = getStartDatum();
                String endDatum = getEndDatum();

                buchung = new Buchung(buchungId, mieterId, wohnungId, buchungsDatum, startDatum, endDatum);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Buchungdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return buchung;
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

    public static String getbuchungId() throws SQLException {
        return datenmenge.getString("buchungId");
    }

    public static String getMieterId() throws Exception {
        return datenmenge.getString("mieterId");
    }

    public static String getWohnungId() throws Exception {
        return datenmenge.getString("wohnungId");
    }

    public static String getBuchungsDatum() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(datenmenge.getDate("buchungsDatum"));
    }

    public static String getStartDatum() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(datenmenge.getDate("startDatum"));
    }

    public static String getEndDatum() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(datenmenge.getDate("endDatum"));
    }

}
