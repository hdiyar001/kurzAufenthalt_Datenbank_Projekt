package datenhaltungsschicht;

import static datenhaltungsschicht.DBZugriff.close;
import logikschicht.Nachrichten;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diyar
 */
public class DBNachrichten {

    private static ResultSet datenmenge;
    private static DBZugriff dbZugriff = DBZugriff.getInstance();

    public static boolean Insert(Nachrichten nachrichten) throws Exception {
        String nachrichtId = nachrichten.getNachrichtenId() == null ? (getLastId() + 1) + "" : nachrichten.getNachrichtenId();
        String insertCommand = "INSERT INTO T_Nachrichten VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(insertCommand))
        {
            ps.setString(1, nachrichtId);
            ps.setString(2, nachrichten.getSenderId());
            ps.setString(3, nachrichten.getEmpfaengerId());
            ps.setString(4, nachrichten.getNachrichtenText());
            ps.setString(5, nachrichten.getZeitStempel());
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Hinzufügen der Nachricht " + nachrichtId, ex);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean update(Nachrichten nachrichten) throws Exception {
        String updateCommand = "UPDATE T_Nachrichten SET senderId = ?, empfaengerId = ?, nachrichtentext = ?, zeitstempel = ? WHERE nachrichtenId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(updateCommand))
        {
            ps.setString(1, nachrichten.getSenderId());
            ps.setString(2, nachrichten.getEmpfaengerId());
            ps.setString(3, nachrichten.getNachrichtenText());
            ps.setString(4, nachrichten.getZeitStempel());
            ps.setString(5, nachrichten.getNachrichtenId());
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Aktualisieren der Nachricht " + nachrichten.getNachrichtenId(), ex);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean Delete(String nachrichtId) throws Exception {
        String deleteCommand = "DELETE FROM T_Nachrichten WHERE nachrichtenId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(deleteCommand))
        {
            ps.setString(1, nachrichtId);
            int status = ps.executeUpdate();
            return status == 1;
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Löschen der Nachricht " + nachrichtId, ex);
        } finally
        {
            close();
        }
    }

    public static List<Nachrichten> getAllNachrichten(String benutzerid) throws Exception {

        ArrayList<Nachrichten> nachrichtenenList = new ArrayList<>();
        String sql = "SELECT t_nachrichten.nachrichtenid,t_benutzer.benutzername,t_nachrichten.senderid,t_nachrichten.empfaengerid,t_nachrichten.nachrichtentext,t_nachrichten.zeitstempel "
                + "FROM t_benutzer JOIN t_nachrichten ON t_benutzer.benutzerid = t_nachrichten.senderid "
                + "WHERE t_nachrichten.senderid = ?"
                + "UNION ALL "
                + "SELECT t_nachrichten.nachrichtenid,t_benutzer.benutzername,t_nachrichten.senderid,t_nachrichten.empfaengerid,t_nachrichten.nachrichtentext,t_nachrichten.zeitstempel "
                + "FROM t_benutzer JOIN t_nachrichten ON t_benutzer.benutzerid = t_nachrichten.senderid "
                + "WHERE t_nachrichten.empfaengerid = ?";
        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(sql))
        {
            pstmt.setString(1, benutzerid);
            pstmt.setString(2, benutzerid);
            datenmenge = pstmt.executeQuery();
            while (getNext())
            {
                String nachrichtenid = getnachrichtenId();
                String benutzerName = datenmenge.getString(2);
                String senderId = getSenderId();
                String empfaengerId = getempfaengerId();
                String nachrichtenText = getNachrichtenText();
                String zielStempel = getZeitStempel();

                Nachrichten nachrichten = new Nachrichten(nachrichtenid, benutzerName, senderId, empfaengerId, nachrichtenText, zielStempel);
                nachrichtenenList.add(nachrichten);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Nachrichtendaten aufgetreten. ");
        } finally
        {
            close();
        }
        return nachrichtenenList;
    }

    public static Nachrichten getNachrichtenByNachrichtenId(String nachrichtenId) throws Exception {
        Nachrichten nachrichten = null;
        String query = "SELECT * FROM T_Nachrichten WHERE nachrichtenid = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(query))
        {
            ps.setString(1, nachrichtenId);

            datenmenge = ps.executeQuery(query);

            if (datenmenge.next())
            {
                String senderId = getSenderId();
                String empfaengerId = getempfaengerId();
                String nachrichtenText = getNachrichtenText();
                String zielStempel = getZeitStempel();

                nachrichten = new Nachrichten(nachrichtenId, senderId, empfaengerId, nachrichtenText, zielStempel);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Nachrichtendaten aufgetreten. ");
        } finally
        {
            close();
        }
        return nachrichten;
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

    public static String getnachrichtenId() throws SQLException {
        return datenmenge.getString("nachrichtenId");
    }

    public static String getSenderId() throws Exception {
        return datenmenge.getString("senderId");
    }

    public static String getempfaengerId() throws Exception {
        return datenmenge.getString("empfaengerId");
    }

    public static String getNachrichtenText() throws Exception {
        return datenmenge.getString("nachrichtenText");
    }

    public static String getZeitStempel() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(datenmenge.getDate("zeitStempel"));
    }

    public static int getLastId() throws Exception {
        String sql = "SELECT MAX(nachrichtenId) FROM T_Nachrichten";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(sql))
        {
            datenmenge = ps.executeQuery();
            if (datenmenge.next())
            {
                return datenmenge.getInt(1);
            }
        } catch (SQLException e)
        {
            throw new Exception("Fehler beim Abrufen der letzten Nachrichten-ID", e);
        } finally
        {
            close();
        }
        return 0;
    }

}
