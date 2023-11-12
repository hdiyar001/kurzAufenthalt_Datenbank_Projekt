package datenhaltungsschicht;

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
        connect();
        String insertCommand = "INSERT INTO T_Wohnung VALUES ("
                + wohnung.getWohnungId()
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

    public static boolean update(Wohnung wohnung) throws Exception {
        connect();

        String updateCommand = "UPDATE T_Wohnung SET EigentuemerId = " + wohnung.getEigentuemerId() + ", PLZ = " + wohnung.getPlz()
                + ", Ort = '" + wohnung.getOrt() + "', Strasse = '" + wohnung.getStrasse() + "', preisProNacht = " + wohnung.getPreisProNacht()
                + ", beschreibung = '" + wohnung.getBeschreibung() + "', verfuegbarkeit = '" + wohnung.getVerfuegbarkeit() + "', WHERE wohnungId = " + wohnung.getWohnungId();

        try
        {
            befehl.executeUpdate(updateCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Aktualisieren des Wohnungs " + wohnung.getWohnungId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean Delete(Wohnung wohnung) throws Exception {
        connect();
        String deleteCommand = "DELETE FROM T_Wohnung WHERE wohnungId = " + wohnung.getWohnungId();

        try
        {
            befehl.executeUpdate(deleteCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Löschen des Wohnungs " + wohnung.getWohnungId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static List<Wohnung> getAllWohnung() throws Exception {

        ArrayList<Wohnung> wohnungen = new ArrayList<>();
        connect();
        try
        {
            datenmenge = befehl.executeQuery("SELECT * FROM T_Wohnung");
            while (getNext())
            {
                String wohnungId = getwohnungId();
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
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Wohnungdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return wohnungen;
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
        return datenmenge.getString("eigentümerId");
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