package datenhaltungsschicht;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBZugriff {

    private static Connection con;
    private static DBZugriff instance;
    private static String url = "jdbc:oracle:thin:@172.22.160.22:1521:xe";

    private DBZugriff() {
        try
        {
            con = DriverManager.getConnection(url, "C##FBPOOL86", "oracle");
            con.setAutoCommit(true);
            System.out.println("Connection was Seccssfull");
        } catch (SQLException ex)
        {
            System.err.println("Fehler beim Herstellen der Verbindung: " + ex.getMessage());
        }
    }

    public static DBZugriff getInstance() {
        if (instance == null)
        {
            instance = new DBZugriff();
        }
        return instance;
    }

    public Connection getConnection() {
        new DBZugriff();
        return con;
    }

    public static void close() {
        try
        {
            if (con != null)
            {
                con.close();
            }
        } catch (SQLException ex)
        {
            System.err.println("Fehler beim Schließen der Verbindung: " + ex.getMessage());
        }
    }
}
