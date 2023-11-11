
import datenhaltungsschicht.DBZugriff;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Diyar
 */
//public class Main1 extends DBZugriff {
//
//    public static void main(String[] args) throws SQLException {
//        connect();
//
//        String sql = "SELECT benutzerId, vorname, nachname FROM T_Benutzer";
//
//        ResultSet rs = befehl.executeQuery(sql);
//        while (rs.next())
//        {
//            String bId = rs.getString(1);
//            String vorname = rs.getString(2).trim();
//            String nachname = rs.getString(3).trim();
//
//            System.out.println("benutzerId: " + bId + " Nachname: " + nachname + " Vorname: " + vorname);
//        }
//        close();
//
//    }
//}
import datenhaltungsschicht.DBZugriff;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import praesentationsschicht.KonsolenMenue;

/**
 *
 * @author Diyar
 */
public class Main1 extends DBZugriff {

    public static void main(String[] args) throws SQLException {
        new KonsolenMenue().startMenu();
    }
}
