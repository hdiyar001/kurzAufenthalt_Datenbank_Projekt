package dbtest;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBZugriff {

    private static Connection con;
    private static String url = "jdbc:oracle:thin:@172.22.160.22:1521:xe";
    protected static Statement befehl;

    public static void connect() throws SQLException {
        try
        {
            con = DriverManager.getConnection(url, "C##FBPOOL86", "oracle");
            befehl = con.createStatement();
            con.setAutoCommit(true);
            System.out.println("Connection was Seccssfull");
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Herstellen der Verbindung zur Datenbank aufgetreten.";
            throw new SQLException(errorMessage, ex);
        }
    }

    public static void close() throws SQLException {
        try
        {
            if (con != null)
            {
                con.close();
            }
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Schließen der Verbindung zur Datenbank aufgetreten.";
            throw new SQLException(errorMessage, ex);
        }
    }
}
