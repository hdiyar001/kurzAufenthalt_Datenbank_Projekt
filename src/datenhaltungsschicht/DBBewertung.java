package datenhaltungsschicht;

import logikschicht.Bewertung;
import static datenhaltungsschicht.DBZugriff.close;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * -
 *
 * @author Diyar+
 */
public class DBBewertung {

    private static ResultSet datenmenge;
    private static DBZugriff dbZugriff = DBZugriff.getInstance();

    public static boolean Insert(Bewertung bewertung) throws Exception {
        String bewrtungId = bewertung.getBewertungId() == null ? (getLastId() + 1) + "" : bewertung.getBewertungId();
        String insertCommand = "INSERT INTO T_Bewertung VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(insertCommand))
        {
            ps.setString(1, bewrtungId);
            ps.setString(2, bewertung.getBuchungId());
            ps.setString(3, bewertung.getBewertungText());
            ps.setString(4, bewertung.getSternBewertung());
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Hinzufügen des Bewertungs " + bewrtungId + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static int getLastId() throws Exception {

        String sql = "SELECT MAX(bewertungid) FROM T_Bewertung";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(sql))
        {
            datenmenge = ps.executeQuery(sql);
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

    public static boolean update(Bewertung bewertung) throws Exception {

        String updateCommand = "UPDATE T_Bewertung SET buchungId = ?, bewertungstext= ?, sternBewertung = ? WHERE bewertungId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(updateCommand))
        {
            ps.setString(1, bewertung.getBuchungId());
            ps.setString(2, bewertung.getBewertungText());
            ps.setString(3, bewertung.getSternBewertung());
            ps.setString(4, bewertung.getBewertungId());
            ps.executeUpdate();
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

    public static boolean Delete(String bewertungId) throws Exception {
        String deleteCommand = "DELETE FROM T_Bewertung WHERE bewertungId = ?";

        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(deleteCommand))
        {
            ps.setString(1, bewertungId);
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Löschen des Bewertungs " + bewertungId + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static List<Bewertung> getAllBewertung() throws Exception {

        ArrayList<Bewertung> bewertungen = new ArrayList<>();
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement("SELECT * FROM T_Bewertung"))
        {
            datenmenge = ps.executeQuery();
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

    public static List<Bewertung> getAllBewertungIdByBenutzerId(String benutzerId) throws SQLException {
        ArrayList<Bewertung> Bewertungen_Id = new ArrayList<>();
        String sql = "SELECT bewertungid FROM T_Bewertung, T_Buchung WHERE T_Bewertung.buchungid=T_Buchung.buchungid AND mieterId= ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(sql))
        {
            ps.setString(1, benutzerId);
            ResultSet datenmenge = ps.executeQuery();
            while (datenmenge.next())
            {
                Bewertungen_Id.add(new Bewertung(datenmenge.getString(1), null, null, null));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            close();
        }
        return Bewertungen_Id;
    }

    public static List<Bewertung> getBewertungByBewertungId(String benutzerid) throws Exception {
        List<Bewertung> bewertungen = new ArrayList<>();
        String query = "SELECT bewertungid, t_bewertung.buchungid, bewertungstext, sternebewertung FROM t_buchung, t_bewertung WHERE t_buchung.buchungid = t_bewertung.buchungid AND mieterid = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(query))
        {
            ps.setString(1, benutzerid);
            datenmenge = ps.executeQuery();
            while (datenmenge.next())
            {
                String bewertungId = datenmenge.getString(1);
                String buchungId = getBuchungId();
                String bewertungText = getBewertungsText();
                String sternBewertung = getSternImo(getSternBewertung());

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

    private static String getSternImo(String anzahl) {

        String sterne = "";
        for (int i = 0; i < Integer.parseInt(anzahl); i++)
        {
            sterne += "⭐";
        }

        return sterne;
    }

}
