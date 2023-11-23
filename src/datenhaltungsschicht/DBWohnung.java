package datenhaltungsschicht;

import logikschicht.FilterWohnung;
import static datenhaltungsschicht.DBZugriff.befehl;
import static datenhaltungsschicht.DBZugriff.close;
import static datenhaltungsschicht.DBZugriff.connect;
import logikschicht.Wohnung;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diyar
 */
public class DBWohnung extends DBZugriff {

    private static ResultSet datenmenge;

    public static boolean Insert(Wohnung wohnung) throws Exception {
        String wohnId = wohnung.getWohnungId() == null ? (getLastId() + 1) + "" : wohnung.getWohnungId();
        connect();

        String insertCommand = "INSERT INTO T_Wohnung VALUES ("
                + wohnId
                + ", " + wohnung.getEigentuemerId()
                + ", '" + wohnung.getStrasse()
                + "', '" + wohnung.getOrt()
                + "', " + wohnung.getPlz()
                + ", " + wohnung.getPreisProNacht()
                + ", '" + wohnung.getBeschreibung()
                + "', '" + wohnung.getVerfuegbarkeit()
                + "')";

        try
        {
            befehl.executeUpdate(insertCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Hinzufügen des Wohnungs " + wohnung.getWohnungId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean updateWohnungStatus(String wohnungId, String status) throws Exception {
        boolean isUpdated;
        connect();

        String updateCommand = "UPDATE T_Wohnung SET verfuegbarkeit='" + status + "' WHERE wohnungId = " + wohnungId;

        try
        {
            befehl.executeUpdate(updateCommand);
            isUpdated = true;
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Aktualisieren des Wohnungs " + wohnungId + " aufgetreten.";
            isUpdated = false;
            throw new Exception(errorMessage);

        } finally
        {
            close();
        }
        return isUpdated;
    }

    public static boolean update(Wohnung wohnung) throws Exception {
        boolean isUpdated;
        String updateSQL = getDataToUpdate(wohnung);
        connect();
        String updateCommand = "UPDATE T_Wohnung SET " + updateSQL + " WHERE wohnungId = " + wohnung.getWohnungId();

        try
        {
            befehl.executeUpdate(updateCommand);
            isUpdated = true;

        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Aktualisieren des Wohnungs " + wohnung.getWohnungId() + " aufgetreten.";
            isUpdated = false;
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return isUpdated;
    }

    public static boolean Delete(String wohnungId) throws Exception {
        boolean deleted;
        connect();
        String deleteCommand = "DELETE FROM T_Wohnung WHERE wohnungId = " + wohnungId;

        try
        {
            deleted = befehl.executeUpdate(deleteCommand) == 1;
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Löschen des Wohnungs " + wohnungId + " aufgetreten.";
            ex.printStackTrace();
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return deleted;

    }

    public static List<Wohnung> getAllWohnung() throws Exception {

        ArrayList<Wohnung> wohnungen = new ArrayList<>();
        connect();
        try
        {
            datenmenge = befehl.executeQuery("SELECT * FROM T_Wohnung");
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

    public static List<FilterWohnung> getAllWohnungenGefiltert(String benutzerId, String preisProNachtP, String ortP) throws Exception {
        connect();
        ArrayList<FilterWohnung> filterW = new ArrayList<>();
        String whereBedingungen = "WHERE verfuegbarkeit != 'N' AND benutzerid != " + benutzerId;
        whereBedingungen += preisProNachtP.isBlank() ? "" : " AND preispronacht >" + preisProNachtP;
        whereBedingungen += ortP.isBlank() ? "" : " AND t_wohnung.ort = '" + ortP + "'";

        String query = "SELECT t_wohnung.wohnungid, t_benutzer.benutzername,t_wohnung.strasse,t_wohnung.ort,t_wohnung.plz,t_benutzer.verifiziert,t_wohnung.preispronacht,t_wohnung.beschreibung,t_wohnung.verfuegbarkeit,t_bewertung.bewertungstext,t_bewertung.sternebewertung "
                + "FROM t_benutzer JOIN t_wohnung ON t_benutzer.benutzerid = t_wohnung.eigentuemerid JOIN t_buchung ON t_wohnung.wohnungid = t_buchung.wohnungid JOIN t_bewertung ON t_buchung.buchungid = t_bewertung.buchungid "
                + (!whereBedingungen.isBlank() ? whereBedingungen : "");
//                + "WHERE preispronacht > " + preisProNachtP + " AND verfuegbarkeit != 'N' AND t_wohnung.ort = '" + ortP + "'";    
        try
        {
            datenmenge = befehl.executeQuery(query);

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
        } finally
        {
            close();
        }
        return filterW;
    }

    public static List<FilterWohnung> getAllVermieteteWohnungen(String benutzerId) throws Exception {
        connect();
        ArrayList<FilterWohnung> filterW = new ArrayList<>();
        String query = "SELECT t_wohnung.wohnungid,strasse,ort,plz,preispronacht,beschreibung,verfuegbarkeit "
                + "FROM t_wohnung "
                + "WHERE eigentuemerId =" + benutzerId;

        try
        {
            datenmenge = befehl.executeQuery(query);

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

    public static Wohnung getWohnungByWohnungId(String wohnungId) throws Exception {
        connect();
        Wohnung wohnung = null;
        String query = "SELECT * FROM T_Wohnung WHERE wohnungid = " + wohnungId;

        try
        {
            datenmenge = befehl.executeQuery(query);

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

    private static String getSternImo(String anzahl) {

        String sterne = "";
        for (int i = 0; i < Integer.parseInt(anzahl); i++)
        {
            sterne += "⭐";
        }

        return sterne;
    }

    public static int getLastId() throws Exception {
        connect();

        try
        {
            String sql = "SELECT MAX(wohnungid) FROM T_Wohnung";
            datenmenge = befehl.executeQuery(sql);
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

}
