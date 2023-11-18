package datenhaltungsschicht;

import logikschicht.FilterBuchung;
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

    public static List<FilterBuchung> getAllBuchung(String benutzerId) throws Exception {

        ArrayList<FilterBuchung> filterBuchungen = new ArrayList<>();
        connect();
        try
        {
            datenmenge = befehl.executeQuery("SELECT t_buchung.buchungsdatum, t_buchung.startdatum,t_buchung.enddatum,t_wohnung.strasse,t_wohnung.ort,t_wohnung.plz,t_wohnung.preispronacht,t_bewertung.bewertungstext,t_bewertung.sternebewertung "
                    + "FROM t_benutzer JOIN t_wohnung ON t_benutzer.benutzerid = t_wohnung.eigentuemerid JOIN t_buchung ON t_wohnung.wohnungid = t_buchung.wohnungid JOIN t_bewertung ON t_buchung.buchungid = t_bewertung.buchungid  WHERE Mieterid= " + benutzerId);
            while (getNext())
            {
                String buchungsDatum = getBuchungsDatum();
                String startDatum = getStartDatum();
                String endDatum = getEndDatum();
                String strasse = datenmenge.getString("Strasse");
                String ort = datenmenge.getString("Ort");
                String plz = datenmenge.getString("PLZ");
                String anschrift = strasse + " " + plz + " " + ort;
                String preisProNacht = datenmenge.getString("preisProNacht") + " €";
                String textBewertung = datenmenge.getString("bewertungstext");
                String sternBewertung = datenmenge.getString("sternebewertung");

                FilterBuchung buchung = new FilterBuchung(buchungsDatum, startDatum, endDatum, anschrift, preisProNacht, textBewertung, sternBewertung);
                filterBuchungen.add(buchung);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Buchungdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return filterBuchungen;
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
