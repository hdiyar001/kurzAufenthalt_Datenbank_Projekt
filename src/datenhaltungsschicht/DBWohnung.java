package datenhaltungsschicht;

import logikschicht.FilterWohnung;
import static datenhaltungsschicht.DBZugriff.close;
import logikschicht.Wohnung;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse DBWohnung ist verantwortlich für die Verwaltung von
 * Wohnungsinformationen in der Datenbank. Sie bietet Methoden zum Einfügen,
 * Aktualisieren, Löschen und Abrufen von Wohnungsdaten. Diese Klasse ist ein
 * Teil der Datenhaltungsschicht in der 3-Schichten-Architektur.
 *
 * @author Diyar, Hussam und Ronida
 */
public class DBWohnung {

    private static ResultSet datenmenge;
    private static DBZugriff dbZugriff = DBZugriff.getInstance();

    /**
     * Fügt eine neue Wohnung in die Datenbank ein.
     *
     * @param wohnung Das Wohnungobjekt, das in die Datenbank eingefügt werden
     * soll.
     * @return true, wenn die Wohnung erfolgreich hinzugefügt wurde, sonst
     * false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean Insert(Wohnung wohnung) throws Exception {
        String wohnId = wohnung.getWohnungId() == null ? (getLastId() + 1) + "" : wohnung.getWohnungId();

        String insertCommand = "INSERT INTO T_Wohnung VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(insertCommand))
        {
            ps.setString(1, wohnId);
            ps.setString(2, wohnung.getEigentuemerId());
            ps.setString(3, wohnung.getStrasse());
            ps.setString(4, wohnung.getOrt());
            ps.setString(5, wohnung.getPlz());
            ps.setString(6, wohnung.getPreisProNacht());
            ps.setString(7, wohnung.getBeschreibung());
            ps.setString(8, wohnung.getVerfuegbarkeit());
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Hinzufügen der Wohnung " + wohnung.getWohnungId(), ex);
        } finally
        {
            close();
        }
        return true;
    }

    /**
     * Aktualisiert den Status einer Wohnung in der Datenbank.
     *
     * @param wohnungId Die ID der zu aktualisierenden Wohnung.
     * @param status Der neue Status der Wohnung.
     * @return true, wenn der Status erfolgreich aktualisiert wurde, sonst
     * false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean updateWohnungStatus(String wohnungId, String status) throws Exception {
        String updateCommand = "UPDATE T_Wohnung SET verfuegbarkeit = ? WHERE wohnungId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(updateCommand))
        {
            ps.setString(1, status);
            ps.setString(2, wohnungId);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Aktualisieren der Wohnung " + wohnungId, ex);
        } finally
        {
            close();
        }
    }

    /**
     * Aktualisiert die Daten einer existierenden Wohnung in der Datenbank.
     *
     * @param wohnung Das Wohnungobjekt mit aktualisierten Daten.
     * @return true, wenn die Aktualisierung erfolgreich war, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean update(Wohnung wohnung) throws Exception {
        String updateSQL = getDataToUpdate(wohnung);
        String updateCommand = "UPDATE T_Wohnung SET " + updateSQL + " WHERE wohnungId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(updateCommand))
        {
            ps.setString(1, wohnung.getWohnungId());
            return ps.executeUpdate() == 1;
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Aktualisieren der Wohnung " + wohnung.getWohnungId(), ex);
        } finally
        {
            close();
        }
    }

    /**
     * Löscht eine Wohnung aus der Datenbank anhand ihrer Wohnung-ID.
     *
     * @param wohnungId Die ID der zu löschenden Wohnung.
     * @return true, wenn die Wohnung erfolgreich gelöscht wurde, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean Delete(String wohnungId) throws Exception {
        String deleteCommand = "DELETE FROM T_Wohnung WHERE wohnungId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(deleteCommand))
        {
            ps.setString(1, wohnungId);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Löschen der Wohnung " + wohnungId, ex);
        } finally
        {
            close();
        }
    }

    /**
     * Ruft eine Liste aller Wohnungen aus der Datenbank ab.
     *
     * @return Eine Liste von Wohnungobjekten.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<Wohnung> getAllWohnung() throws Exception {

        ArrayList<Wohnung> wohnungen = new ArrayList<>();
        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement("SELECT * FROM T_Wohnung"))
        {
            datenmenge = pstmt.executeQuery();
            while (getNext())
            {
                String wohnungId = datenmenge.getString(1);
                String eigentuemerId = getEigentuemerId();
                String strasse = getStrasse();
                String ort = getOrt();
                String plz = getPLZ();
                String preisProNacht = getPreisProNacht();
                String beschreibung = getBeschreibung();
                String verfuegbarkeit = getVerfuegbarkeit();

                Wohnung wohnung = new Wohnung(wohnungId, eigentuemerId, strasse, ort, plz, preisProNacht, beschreibung, verfuegbarkeit);
                wohnungen.add(wohnung);
            }
        } catch (SQLException e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Wohnungdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return wohnungen;
    }

    /**
     * Ruft gefilterte Wohnungsdaten basierend auf spezifizierten Kriterien ab.
     *
     * @param benutzerId Die Benutzer-ID für die Filterung.
     * @param preisProNachtP Der Preis pro Nacht als Filterkriterium.
     * @param ortP Der Ort als Filterkriterium.
     * @return Eine Liste von gefilterten Wohnungdaten.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<FilterWohnung> getAllWohnungenGefiltert(String benutzerId, String preisProNachtP, String ortP) throws Exception {
        ArrayList<FilterWohnung> filterW = new ArrayList<>();
        String whereBedingungen = "WHERE verfuegbarkeit != 'N' AND benutzerid != " + benutzerId;
        whereBedingungen += preisProNachtP.isBlank() ? "" : " AND preispronacht >" + preisProNachtP;
        whereBedingungen += ortP.isBlank() ? "" : " AND t_wohnung.ort = '" + ortP + "'";

        String query = "SELECT t_wohnung.wohnungid, t_benutzer.benutzername,t_wohnung.strasse,t_wohnung.ort,t_wohnung.plz,t_benutzer.verifiziert,t_wohnung.preispronacht,t_wohnung.beschreibung,t_wohnung.verfuegbarkeit,t_bewertung.bewertungstext,t_bewertung.sternebewertung "
                + "FROM t_benutzer JOIN t_wohnung ON t_benutzer.benutzerid = t_wohnung.eigentuemerid "
                + "JOIN t_buchung ON t_wohnung.wohnungid = t_buchung.wohnungid "
                + "JOIN t_bewertung ON t_buchung.buchungid = t_bewertung.buchungid "
                + (!whereBedingungen.isBlank() ? whereBedingungen : "");

        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(query))
        {
            datenmenge = pstmt.executeQuery();

            while (datenmenge.next())
            {

                String wohnungId = getwohnungId();
                String benutzerName = datenmenge.getString("benutzerName");
                String strasse = getStrasse();
                String ort = getOrt();
                String plz = getPLZ();
                String anschrift = strasse + " " + plz + " " + ort;
                String verifiziert = datenmenge.getString("verifiziert").equals("J") ? "JA" : "NEIN";
                String preisProNacht = getPreisProNacht() + " €";
                String beschreibung = getBeschreibung();
                String verfuegbarkeit = getVerfuegbarkeit().equals("J") ? "JA" : "NEIN";
                String bewertungstext = datenmenge.getString("bewertungsText");
                String sternBewertung = getSternImo(datenmenge.getString("sterneBewertung"));

                FilterWohnung filter = new FilterWohnung(wohnungId, benutzerName, anschrift, verifiziert, preisProNacht, beschreibung, verfuegbarkeit, bewertungstext, sternBewertung);
                filterW.add(filter);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            throw new Exception("Es ist ein Fehler beim Lesen der Wohnungdaten aufgetreten. ");
        }

        return filterW;
    }

    /**
     * Ruft alle vermieteten Wohnungen eines bestimmten Benutzers ab.
     *
     * @param benutzerId Die Benutzer-ID des Eigentümers.
     * @return Eine Liste von vermieteten Wohnungen des Benutzers.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<FilterWohnung> getAllVermieteteWohnungen(String benutzerId) throws Exception {
        ArrayList<FilterWohnung> filterW = new ArrayList<>();
        String query = "SELECT t_wohnung.wohnungid,strasse,ort,plz,preispronacht,beschreibung,verfuegbarkeit "
                + "FROM t_wohnung "
                + "WHERE eigentuemerId =? ";

        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(query))
        {
            pstmt.setString(1, benutzerId);
            datenmenge = pstmt.executeQuery();

            while (datenmenge.next())
            {
                String wohnungId = getwohnungId();
                String strasse = getStrasse();
                String ort = getOrt();
                String plz = getPLZ();
                String anschrift = strasse + " " + plz + " " + ort;
                String preisProNacht = getPreisProNacht() + " €";
                String beschreibung = getBeschreibung();
                String verfuegbarkeit = getVerfuegbarkeit();

                FilterWohnung filter = new FilterWohnung(wohnungId, anschrift, preisProNacht, beschreibung, verfuegbarkeit);
                filterW.add(filter);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            throw new Exception("Es ist ein Fehler beim Lesen der Wohnungdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return filterW;
    }

    /**
     * Ruft eine spezifische Wohnung anhand ihrer Wohnung-ID ab.
     *
     * @param wohnungId Die ID der Wohnung, die abgerufen werden soll.
     * @return Ein Wohnungobjekt, wenn eine Wohnung mit der angegebenen ID
     * gefunden wurde, sonst null.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static Wohnung getWohnungByWohnungId(String wohnungId) throws Exception {
        Wohnung wohnung = null;
        String query = "SELECT * FROM T_Wohnung WHERE wohnungid = " + wohnungId;

        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(query))
        {
            datenmenge = pstmt.executeQuery();

            if (datenmenge.next())
            {
                String eigentuemerId = getEigentuemerId();
                String strasse = getStrasse();
                String ort = getOrt();
                String plz = getPLZ();
                String preisProNacht = getPreisProNacht();
                String beschreibung = getBeschreibung();
                String verfuegbarkeit = getVerfuegbarkeit();

                wohnung = new Wohnung(wohnungId, eigentuemerId, strasse, ort, plz, preisProNacht, beschreibung, verfuegbarkeit);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Wohnungdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return wohnung;
    }

    /**
     * Prüft, ob im aktuellen ResultSet weitere Wohnungen vorhanden sind.
     *
     * @return true, wenn weitere Wohnungen vorhanden sind, sonst false.
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
     * Ruft die höchste Wohnung-ID aus der Datenbank ab.
     *
     * @return Die höchste vorhandene Wohnung-ID.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static int getLastId() throws Exception {

        String sql = "SELECT MAX(wohnungid) FROM T_Wohnung";
        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(sql))
        {
            datenmenge = pstmt.executeQuery();
            if (getNext())
            {
                return datenmenge.getInt(1);
            }
        } catch (SQLException e)
        {
            throw new Exception(e.getSQLState());
        } finally
        {
            close();
        }
        return 0;
    }

    private static String getDataToUpdate(Wohnung wohnung) {
        String sql = "";
        sql += wohnung.getStrasse() != null ? " STRASSE = '" + wohnung.getStrasse() + "', " : "";
        sql += wohnung.getOrt() != null ? " ORT = '" + wohnung.getOrt() + "', " : "' ";
        sql += wohnung.getPlz() != null ? " PLZ = " + wohnung.getPlz() + ", " : "";
        sql += wohnung.getPreisProNacht() != null ? " PreisProNacht = " + wohnung.getPreisProNacht() + ", " : "";
        sql += wohnung.getVerfuegbarkeit() != null ? " verfuegbarkeit = '" + wohnung.getVerfuegbarkeit() + "', " : "";
        sql += wohnung.getBeschreibung() != null ? " beschreibung = '" + wohnung.getBeschreibung() + "', " : "";

        return sql.substring(0, sql.length() - 2);
    }

    public static String getwohnungId() throws SQLException {
        return datenmenge.getString("wohnungId");
    }

    public static String getEigentuemerId() throws Exception {
        return datenmenge.getString("eigentuemerId");
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

    public static String getPreisProNacht() throws Exception {
        return datenmenge.getString("preisProNacht");
    }

    public static String getBeschreibung() throws Exception {
        return datenmenge.getString("beschreibung");
    }

    public static String getVerfuegbarkeit() throws Exception {
        return datenmenge.getString("verfuegbarkeit");
    }

}
