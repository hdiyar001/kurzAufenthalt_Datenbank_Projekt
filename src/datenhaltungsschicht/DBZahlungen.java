package datenhaltungsschicht;

import static datenhaltungsschicht.DBZugriff.befehl;
import static datenhaltungsschicht.DBZugriff.close;
import static datenhaltungsschicht.DBZugriff.connect;
import logikschicht.Zahlungen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diyar
 */
public class DBZahlungen extends DBZugriff {

    private static ResultSet datenmenge;

    public static boolean Insert(Zahlungen zahlungen) throws Exception {
        connect();
        String insertCommand = "INSERT INTO T_Zahlungen VALUES ("
                + zahlungen.getZahlungsId()
                + ", " + zahlungen.getBuchungId()
                + ", '" + zahlungen.getBetrag()
                + ", TO_DATE('" + zahlungen.getZahlungsdatum() + "', 'dd.MM.yyyy') ', '"
                + zahlungen.getZahlungsart() + "'";
        try
        {
            befehl.executeUpdate(insertCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Hinzufügen des zahlungs " + zahlungen.getZahlungsId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean update(Zahlungen zahlungen) throws Exception {
        connect();

        String updateCommand = "UPDATE T_Zahlungen SET buchungId = " + zahlungen.getBuchungId() + ", betrag= " + zahlungen.getBetrag()
                + ", zahlungsdatum = '" + zahlungen.getZahlungsdatum() + "', zahlungsart = '" + zahlungen.getZahlungsart() + "' WHERE zahlungsId = " + zahlungen.getZahlungsId();

        try
        {
            befehl.executeUpdate(updateCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Aktualisieren des zahlungs " + zahlungen.getZahlungsId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean Delete(Zahlungen zahlungen) throws Exception {
        connect();
        String deleteCommand = "DELETE FROM T_Zahlungen WHERE zahlungsId = " + zahlungen.getZahlungsId();

        try
        {
            befehl.executeUpdate(deleteCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Löschen des Zahlungens " + zahlungen.getZahlungsId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static List<Zahlungen> getAllZahlungen() throws Exception {

        ArrayList<Zahlungen> zahlungenListe = new ArrayList<>();
        connect();
        try
        {
            datenmenge = befehl.executeQuery("SELECT * FROM T_Zahlungen");
            while (getNext())
            {
                String zahlungsId = getzahlungsId();
                String buchungId = getBuchungId();
                String betrag = getBetrag();
                String zahlungsdatum = getZahlungsdatum();
                String zahlungsart = getZahlungsart();

                Zahlungen zahlungen = new Zahlungen(zahlungsId, buchungId, betrag, zahlungsdatum, zahlungsart);
                zahlungenListe.add(zahlungen);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Zahlungsdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return zahlungenListe;
    }

    public static Zahlungen getZahlungenByZahlungenId(String zahlungsId) throws Exception {
        connect();
        Zahlungen zahlungen = null;
        String query = "SELECT * FROM T_Zahlungen WHERE zahlungenid = " + zahlungsId;

        try
        {
            datenmenge = befehl.executeQuery(query);

            if (datenmenge.next())
            {
                String buchungId = getBuchungId();
                String betrag = getBetrag();
                String zahlungsdatum = getZahlungsdatum();
                String zahlungsart = getZahlungsart();

                zahlungen = new Zahlungen(zahlungsId, buchungId, betrag, zahlungsdatum, zahlungsart);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Zahlungendaten aufgetreten. ");
        } finally
        {
            close();
        }
        return zahlungen;
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

    public static String getzahlungsId() throws SQLException {
        return datenmenge.getString("zahlungsId");
    }

    public static String getBuchungId() throws Exception {
        return datenmenge.getString("buchungId");
    }

    public static String getBetrag() throws Exception {
        return datenmenge.getString("betrag");
    }

    public static String getZahlungsdatum() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(datenmenge.getDate("zahlungsdatum"));
    }

    public static String getZahlungsart() throws Exception {
        return datenmenge.getString("zahlungsart");
    }
}