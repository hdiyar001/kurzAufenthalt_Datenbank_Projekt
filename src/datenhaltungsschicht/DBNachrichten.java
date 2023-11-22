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
        String nachrichtId = nachrichten.getNachrichtenId() == null ? (getLastId() + 1) + "" : nachrichten.getNachrichtenId();
        connect();
        String insertCommand = "INSERT INTO T_Nachrichten VALUES ("
                + nachrichtId
                + ", " + nachrichten.getSenderId()
                + ", " + nachrichten.getEmpfaengerId()
                + ", '" + nachrichten.getNachrichtenText()
                + "','" + nachrichten.getZeitStempel() + "')";
        System.out.println(insertCommand);
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

    public static boolean Delete(String nachrichtId) throws Exception {
        int status = -1;
        connect();
        String deleteCommand = "DELETE FROM T_Nachrichten WHERE nachrichtenId = " + nachrichtId;

        try
        {
            status = befehl.executeUpdate(deleteCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Löschen des Nachrichtens " + nachrichtId + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return status == 1;
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

    public static int getLastId() throws Exception {
        connect();

        try
        {
            String sql = "SELECT MAX(nachrichtenId) FROM T_Nachrichten";
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
}
