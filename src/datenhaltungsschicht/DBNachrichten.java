package datenhaltungsschicht;

import static datenhaltungsschicht.DBZugriff.befehl;
import static datenhaltungsschicht.DBZugriff.close;
import static datenhaltungsschicht.DBZugriff.connect;
import logikschicht.Nachrichten;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diyar
 */
public class DBNachrichten extends DBZugriff {

    private static ResultSet datenmenge;

    public static boolean Insert(Nachrichten nachrichten) throws Exception {
        connect();
        String insertCommand = "INSERT INTO T_Nachrichten VALUES ("
                + nachrichten.getNachrichtenId()
                + ", " + nachrichten.getSenderId()
                + ", '" + nachrichten.getEmpfaengerId()
                + "', '" + nachrichten.getNachrichtenText()
                + ", TO_DATE('" + nachrichten.getZeitStempel() + "', 'dd.MM.yyyy'), ";
        try
        {
            befehl.executeUpdate(insertCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Hinzufügen des Nachricht " + nachrichten.getNachrichtenId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean update(Nachrichten nachrichten) throws Exception {
        connect();

        String updateCommand = "UPDATE T_Nachrichten SET senderId = " + nachrichten.getSenderId() + ", empfaengerId= " + nachrichten.getEmpfaengerId()
                + ", nachrichtentext = '" + nachrichten.getNachrichtenText() + "', zeitstempel = '" + nachrichten.getZeitStempel() + "'";

        try
        {
            befehl.executeUpdate(updateCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Aktualisieren des Nachricht " + nachrichten.getNachrichtenId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean Delete(Nachrichten nachrichten) throws Exception {
        connect();
        String deleteCommand = "DELETE FROM T_Nachrichten WHERE nachrichtenId = " + nachrichten.getNachrichtenId();

        try
        {
            befehl.executeUpdate(deleteCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Löschen des Nachrichtens " + nachrichten.getNachrichtenId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static List<Nachrichten> getAllNachrichten(String benutzerid) throws Exception {

        ArrayList<Nachrichten> nachrichtenenList = new ArrayList<>();
        connect();
        try
        {
            String sql = "SELECT t_nachrichten.nachrichtenid,t_benutzer.benutzername,t_nachrichten.senderid,t_nachrichten.empfaengerid,t_nachrichten.nachrichtentext,t_nachrichten.zeitstempel "
                    + "FROM t_benutzer JOIN t_nachrichten ON t_benutzer.benutzerid = t_nachrichten.senderid "
                    + "WHERE t_nachrichten.senderid = " + benutzerid
                    + "UNION ALL "
                    + "SELECT t_nachrichten.nachrichtenid,t_benutzer.benutzername,t_nachrichten.senderid,t_nachrichten.empfaengerid,t_nachrichten.nachrichtentext,t_nachrichten.zeitstempel "
                    + "FROM t_benutzer JOIN t_nachrichten ON t_benutzer.benutzerid = t_nachrichten.senderid "
                    + "WHERE t_nachrichten.empfaengerid = " + benutzerid;
            datenmenge = befehl.executeQuery(sql);
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
        connect();
        Nachrichten nachrichten = null;
        String query = "SELECT * FROM T_Nachrichten WHERE nachrichtenid = " + nachrichtenId;

        try
        {
            datenmenge = befehl.executeQuery(query);

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
}
