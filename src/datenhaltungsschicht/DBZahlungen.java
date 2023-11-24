package datenhaltungsschicht;

import static datenhaltungsschicht.DBZugriff.close;
import logikschicht.Zahlungen;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse DBZahlungen ist verantwortlich für die Verwaltung von
 * Zahlungsinformationen in der Datenbank. Sie bietet Methoden zum Einfügen,
 * Aktualisieren, Löschen und Abrufen von Zahlungsdaten. Diese Klasse ist ein
 * Teil der Datenhaltungsschicht in der 3-Schichten-Architektur.
 *
 * @author Diyar, Hussam und Ronida
 */
public class DBZahlungen {

    private static ResultSet datenmenge;
    private static DBZugriff dbZugriff = DBZugriff.getInstance();

    /**
     * Fügt eine neue Zahlung in die Datenbank ein.
     *
     * @param zahlungen Das Zahlungsobjekt, das in die Datenbank eingefügt
     * werden soll.
     * @return true, wenn die Zahlung erfolgreich hinzugefügt wurde, sonst
     * false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean Insert(Zahlungen zahlungen) throws Exception {
        String zahlungsid = zahlungen.getZahlungsId() == null ? (getLastId() + 1) + "" : zahlungen.getZahlungsId();
        String insertCommand = "INSERT INTO T_Zahlungen VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(insertCommand))
        {
            ps.setString(1, zahlungsid);
            ps.setString(2, zahlungen.getBuchungId());
            ps.setString(3, zahlungen.getBetrag());
            ps.setString(4, zahlungen.getZahlungsdatum());
            ps.setString(5, zahlungen.getZahlungsart());
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Hinzufügen der Zahlung " + zahlungen.getZahlungsId(), ex);
        } finally
        {
            close();
        }
        return true;
    }

    /**
     * Ruft die höchste Zahlungs-ID aus der Datenbank ab.
     *
     * @return Die höchste vorhandene Zahlungs-ID.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static int getLastId() throws Exception {
        String sql = "SELECT MAX(zahlungsid) FROM T_Zahlungen";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(sql))
        {
            ResultSet datenmenge = ps.executeQuery();
            if (datenmenge.next())
            {
                return datenmenge.getInt(1);
            }
        } catch (SQLException e)
        {
            throw new Exception("Fehler beim Abrufen der letzten Zahlungs-ID", e);
        } finally
        {
            close();
        }
        return 0;
    }

    /**
     * Aktualisiert die Daten einer existierenden Zahlung in der Datenbank.
     *
     * @param zahlungen Das Zahlungsobjekt mit aktualisierten Daten.
     * @return true, wenn die Aktualisierung erfolgreich war, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean update(Zahlungen zahlungen) throws Exception {
        String updateCommand = "UPDATE T_Zahlungen SET buchungId = ?, betrag = ?, zahlungsdatum = ?, zahlungsart = ? WHERE zahlungsId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(updateCommand))
        {
            ps.setString(1, zahlungen.getBuchungId());
            ps.setString(2, zahlungen.getBetrag());
            ps.setString(3, zahlungen.getZahlungsdatum());
            ps.setString(4, zahlungen.getZahlungsart());
            ps.setString(5, zahlungen.getZahlungsId());
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Aktualisieren der Zahlung " + zahlungen.getZahlungsId(), ex);
        } finally
        {
            close();
        }
        return true;
    }

    /**
     * Löscht eine Zahlung aus der Datenbank anhand ihrer Zahlungs-ID.
     *
     * @param zahlungen Das Zahlungsobjekt, das gelöscht werden soll.
     * @return true, wenn die Zahlung erfolgreich gelöscht wurde, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean Delete(Zahlungen zahlungen) throws Exception {
        String deleteCommand = "DELETE FROM T_Zahlungen WHERE zahlungsId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(deleteCommand))
        {
            ps.setString(1, zahlungen.getZahlungsId());
            int status = ps.executeUpdate();
            return status == 1;
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Löschen der Zahlung " + zahlungen.getZahlungsId(), ex);
        } finally
        {
            close();
        }
    }

    /**
     * Ruft alle Zahlungen für einen bestimmten Benutzer ab.
     *
     * @param benutzerid Die Benutzer-ID für die Abfrage der Zahlungen.
     * @return Eine Liste von Zahlungen.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<Zahlungen> getAllZahlungen(String benutzerid) throws Exception {
        String query = "SELECT * FROM T_Zahlungen,T_buchung , T_benutzer "
                + "WHERE t_buchung.mieterid=T_benutzer.benutzerid AND t_zahlungen.buchungid = t_buchung.buchungid AND mieterid= ? ";

        ArrayList<Zahlungen> zahlungenListe = new ArrayList<>();
        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(query))
        {
            pstmt.setString(1, benutzerid);
            datenmenge = pstmt.executeQuery();
            while (getNext())
            {
                String zahlungsId = getzahlungsId();
                String buchungId = getBuchungId();
                String betrag = getBetrag() + " €";
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

    /**
     * Ruft eine spezifische Zahlung anhand ihrer Zahlungs-ID ab.
     *
     * @param zahlungsId Die ID der Zahlung, die abgerufen werden soll.
     * @return Ein Zahlungsobjekt, wenn eine Zahlung mit der angegebenen ID
     * gefunden wurde, sonst null.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static Zahlungen getZahlungenByZahlungenId(String zahlungsId) throws Exception {
        Zahlungen zahlungen = null;
        String query = "SELECT * FROM T_Zahlungen WHERE zahlungenid = ?";

        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(query))
        {
            pstmt.setString(1, zahlungsId);

            datenmenge = pstmt.executeQuery(query);

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

    /**
     * Prüft, ob im aktuellen ResultSet weitere Zahlungen vorhanden sind.
     *
     * @return true, wenn weitere Zahlungen vorhanden sind, sonst false.
     * @throws Exception Wirft eine Exception, wenn Fehler während der Abfrage
     * auftreten.
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
