package datenhaltungsschicht;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Die Klasse DBZugriff verwaltet die Verbindung zur Datenbank im Rahmen der
 * 3-Schichten-Architektur. Sie implementiert das Singleton-Muster, um
 * sicherzustellen, dass nur eine Instanz der Klasse existiert.
 *
 * @author Diyar, Hussam, Ronida - Gruppenmitglieder
 */
public class DBZugriff {

    private static Connection con;
    private static DBZugriff instance;

    private static final String url = "jdbc:oracle:thin:@dihas001.fritz.box:1521:xe";
    private static final String user = "system";
    private static final String password = "system";

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }sfs

    /**
     * Privater Konstruktor zur Verhinderung der Instanzerstellung von außen.
     * Stellt die Verbindung zur Datenbank her. Setzt Auto-Commit auf true, um
     * sicherzustellen, dass alle Transaktionen automatisch in der Datenbank
     * festgeschrieben werden, ohne dass explizit commit aufgerufen werden muss.
     */
    private DBZugriff() {

    }

    /**
     * Liefert die Singleton-Instanz der Klasse. Erstellt eine neue Instanz,
     * wenn sie noch nicht existiert.
     *
     * @return Die Instanz von DBZugriff.
     */
    public static DBZugriff getInstance() {
        if (instance == null) {
            instance = new DBZugriff();
        }
        return instance;
    }

    /**
     * Gibt die Verbindungsinstanz zur Datenbank zurück. Stellt eine neue
     * Verbindung her, falls notwendig.
     *
     * @return Die Datenbankverbindung.
     */
    public Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            connection = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(true);
            System.out.println("Connection was Successful");
        } catch (SQLException ex) {
            System.err.println("Fehler beim Herstellen der Verbindung: " + ex.getMessage());
        }
        return con;
    }

    /**
     * Schließt die Datenbankverbindung.
     */
    public static void close() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.err.println("Fehler beim Schließen der Verbindung: " + ex.getMessage());
        }
    }
}
