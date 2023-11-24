package datenhaltungsschicht;

import logikschicht.FilterBuchung;
import static datenhaltungsschicht.DBZugriff.close;
import logikschicht.Buchung;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 * Die Klasse DBBuchung ist zuständig für die Verwaltung von Buchungsdaten in
 * der Datenbank. Sie bietet Methoden zum Einfügen, Aktualisieren, Löschen und
 * Abrufen von Buchungsinformationen. Diese Klasse ist ein Teil der
 * Datenhaltungsschicht in der 3-Schichten-Architektur.
 *
 * @author Diyar, Hussam und Ronida
 */
public class DBBuchung {

    private static ResultSet datenmenge;
    private static DBZugriff dbZugriff = DBZugriff.getInstance();

    /**
     * Fügt eine neue Buchung in die Datenbank ein.
     *
     * @param buchung Das Buchungsobjekt, das in die Datenbank eingefügt werden
     * soll.
     * @return true, wenn die Buchung erfolgreich hinzugefügt wurde, sonst
     * false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean Insert(Buchung buchung) throws Exception {
        String buchungsId = (buchung.getBuchungId() == null ? (getLastId() + 1) + "" : buchung.getBuchungId());
        String insertCommand = "INSERT INTO T_Buchung VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(insertCommand))
        {
            ps.setString(1, buchungsId);
            ps.setString(2, buchung.getMieterId());
            ps.setString(3, buchung.getWohnungId());
            ps.setString(4, buchung.getBuchungsDatum());
            ps.setString(5, buchung.getStartDatum());
            ps.setString(6, buchung.getEndDatum());
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Hinzufügen des Buchungs " + buchungsId, ex);
        } finally
        {
            close();
        }
        return true;
    }

    /**
     * Aktualisiert die Daten einer existierenden Buchung in der Datenbank.
     *
     * @param buchung Das Buchungsobjekt mit aktualisierten Daten.
     * @return true, wenn die Aktualisierung erfolgreich war, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean update(Buchung buchung) throws Exception {
        String updateCommand = "UPDATE T_Buchung SET mieterId = ?, wohnungId = ?, buchungsdatum = ?, startDatum = ?, endDatum = ? WHERE buchungId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(updateCommand))
        {
            ps.setString(1, buchung.getMieterId());
            ps.setString(2, buchung.getWohnungId());
            ps.setString(3, buchung.getBuchungsDatum());
            ps.setString(4, buchung.getStartDatum());
            ps.setString(5, buchung.getEndDatum());
            ps.setString(6, buchung.getBuchungId());
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Aktualisieren des Buchungs " + buchung.getBuchungId(), ex);
        } finally
        {
            close();
        }
        return true;
    }

    /**
     * Löscht eine Buchung aus der Datenbank anhand ihrer Buchungs-ID.
     *
     * @param buchungId Die ID der zu löschenden Buchung.
     * @return true, wenn die Buchung erfolgreich gelöscht wurde, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean Delete(String buchungId) throws Exception {
        String deleteCommand = "DELETE FROM T_Buchung WHERE buchungId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(deleteCommand))
        {
            ps.setString(1, buchungId);
            int status = ps.executeUpdate();
            return status == 1;
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Löschen des Buchungs " + buchungId, ex);
        } finally
        {
            close();
        }
    }

    /**
     * Ruft alle Buchungen ab, die bestimmten Filterkriterien entsprechen.
     *
     * @param benutzerId Der Benutzer, dessen Buchungen abgerufen werden sollen.
     * @param buchungId Die spezifische Buchungs-ID, nach der gefiltert wird.
     * @param ortP Der Ort, nach dem gefiltert wird.
     * @param buchungsdatum Das Datum, nach dem gefiltert wird.
     * @return Eine Liste von FilterBuchungen, die den angegebenen Kriterien
     * entsprechen.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<FilterBuchung> getAllBuchung(String benutzerId,
            String buchungId,
            String ortP,
            String buchungsdatum) throws Exception {

        String whereKlausel = "WHERE t_buchung.enddatum > CURRENT_DATE AND ";
        whereKlausel += benutzerId == null ? "" : " Mieterid = " + benutzerId + " AND ";
        whereKlausel += buchungId == null ? "" : " t_buchung.buchungId = " + buchungId + " AND ";
        whereKlausel += ortP == null ? "" : " t_wohnung.ort = '" + ortP + "' AND ";
        whereKlausel += buchungsdatum == null ? "" : " t_buchung.buchungsdatum = '" + buchungsdatum + "' AND ";

        whereKlausel = whereKlausel.length() == 6 ? "" : whereKlausel.substring(0, whereKlausel.length() - 4);

        String sql = "SELECT t_buchung.buchungid,t_buchung.buchungsdatum, t_buchung.startdatum, t_buchung.enddatum, "
                + "t_wohnung.strasse, t_wohnung.ort, t_wohnung.plz, t_wohnung.preispronacht, (t_buchung.enddatum - t_buchung.startdatum) AS AnzahlDerNaechte ,"
                + " (t_buchung.enddatum - t_buchung.startdatum)* t_wohnung.preispronacht as betrag "
                + "FROM    t_benutzer "
                + "   JOIN t_buchung ON t_benutzer.benutzerid = t_buchung.mieterid "
                + "   JOIN t_wohnung ON t_buchung.wohnungid = t_wohnung.wohnungid " + whereKlausel;

        ArrayList<FilterBuchung> filterBuchungen = new ArrayList<>();
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(sql))
        {

            datenmenge = ps.executeQuery();
            while (datenmenge.next())
            {
                String buchungid = datenmenge.getString("buchungid");
                String buchungsDatum = datenmenge.getString("buchungsdatum");
                String startDatum = datenmenge.getString("startdatum");
                String endDatum = datenmenge.getString("enddatum");
                String strasse = datenmenge.getString("strasse");
                String ort = datenmenge.getString("Ort");
                String plz = datenmenge.getString("PLZ");
                String anschrift = strasse + " " + plz + " " + ort;
                String preisProNacht = datenmenge.getString("preisProNacht") + " €";
                String anzahlDerNaechte = datenmenge.getString("AnzahlDerNaechte");
                String betrag = datenmenge.getString("betrag") + " €";

                FilterBuchung buchung = new FilterBuchung(buchungid, buchungsDatum, startDatum, endDatum, anschrift, preisProNacht, anzahlDerNaechte, betrag);
                filterBuchungen.add(buchung);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("Es ist ein Fehler beim Lesen der Buchungdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return filterBuchungen;
    }

    /**
     * Überprüft, ob eine Buchung für eine bestimmte Kombination aus Benutzer
     * und Wohnung existiert.
     *
     * @param benutzerid Die ID des Benutzers.
     * @param wohnungId Die ID der Wohnung.
     * @return true, wenn eine solche Buchung existiert, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean checkBuchungExists(String benutzerid, String wohnungId) throws Exception {
        String query = " SELECT benutzerid, wohnungid FROM t_benutzer "
                + "JOIN t_buchung ON t_benutzer.benutzerid = t_buchung.mieterid"
                + " WHERE benutzerid = " + benutzerid + " AND wohnungid =" + wohnungId;

        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(query))
        {
            datenmenge = ps.executeQuery();
            if (datenmenge.next())
            {
                return true;
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Benutzerdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return false;
    }

    /**
     * Ruft eine spezifische Buchung anhand ihrer Buchungs-ID ab.
     *
     * @param buchungId Die ID der Buchung, die abgerufen werden soll.
     * @return Ein Buchungsobjekt, wenn eine Buchung mit der angegebenen ID
     * gefunden wurde, sonst null.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static Buchung getBuchungByBuchungId(String buchungId) throws Exception {
        Buchung buchung = null;
        String query = "SELECT * FROM T_Buchung WHERE buchungid = " + buchungId;

        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(query))
        {
            datenmenge = ps.executeQuery();

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

    /**
     * Prüft, ob im aktuellen ResultSet weitere Daten vorhanden sind.
     *
     * @return true, wenn weitere Daten vorhanden sind, sonst false.
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

    /**
     * Ruft die höchste Buchungs-ID aus der Datenbank ab.
     *
     * @return Die höchste vorhandene Buchungs-ID.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static int getLastId() throws Exception {
        String sql = "SELECT MAX(buchungId) FROM T_Buchung";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(sql))
        {
            ResultSet datenmenge = ps.executeQuery();
            if (datenmenge.next())
            {
                return datenmenge.getInt(1);
            }
        } catch (SQLException e)
        {
            throw new Exception("Fehler beim Abrufen der letzten Buchungs-ID.", e);
        } finally
        {
            close();
        }
        return 0;
    }

    /**
     * Konvertiert die numerische Bewertung in eine Stern-Darstellung.
     *
     * @param anzahl Die Anzahl der Sterne als numerischer Wert.
     * @return Eine String-Darstellung der Sternebewertung.
     */
    private static String getSternImo(String anzahl) {

        String sterne = "";
        for (int i = 0; i < Integer.parseInt(anzahl); i++)
        {
            sterne += "⭐";
        }

        return sterne;
    }

    /**
     * Ermittelt den Gesamtbetrag einer bestimmten Buchung anhand ihrer
     * Buchungs-ID.
     *
     * @param buchungid Die ID der Buchung, für die der Betrag berechnet werden
     * soll.
     * @return Der berechnete Gesamtbetrag der Buchung als String.
     * @throws SQLException Wirft eine SQLException bei Fehlern während des
     * Datenbankzugriffs.
     * @throws Exception Wirft eine allgemeine Exception, wenn zusätzliche
     * Fehler auftreten.
     */
    public static String getBetragByBuchungId(String buchungid) throws SQLException, Exception {
        String query = "SELECT (t_buchung.enddatum - t_buchung.startdatum)* t_wohnung.preispronacht as betrag FROM T_Buchung, t_wohnung WHERE t_buchung.wohnungid=t_wohnung.wohnungid AND buchungId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(query))
        {
            ps.setString(1, buchungid);
            ResultSet datenmenge = ps.executeQuery();
            if (datenmenge.next())
            {
                return datenmenge.getString(1);
            }
        } catch (Exception e)
        {
            throw new Exception("Fehler beim Lesen des Betrags für Buchung ID " + buchungid, e);
        } finally
        {
            close();
        }
        return null;
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
        Date date = datenmenge.getDate("buchungsDatum");
        if (date != null)
        {
            return new SimpleDateFormat("dd.MM.yyyy").format(date);
        } else
        {
            throw new Exception("Buchungsdatum ist null oder im falschen Format");
        }
    }

    public static String getStartDatum() throws Exception {
        Date date = datenmenge.getDate("startDatum");
        if (date != null)
        {
            return new SimpleDateFormat("dd.MM.yyyy").format(date);
        } else
        {
            throw new Exception("Startdatum ist null oder im falschen Format");
        }
    }

    public static String getEndDatum() throws Exception {
        Date date = datenmenge.getDate("endDatum");
        if (date != null)
        {
            return new SimpleDateFormat("dd.MM.yyyy").format(date);
        } else
        {
            throw new Exception("Enddatum ist null oder im falschen Format");
        }
    }

}
