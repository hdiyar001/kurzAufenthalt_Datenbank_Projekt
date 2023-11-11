package dbtest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Diyar
 */
public class Main1 extends DBZugriff {

    public static void main(String[] args) throws SQLException {
        connect();

        String sql = "SELECT\n"
                + "    kunde.kundennummer,\n"
                + "    nachname,\n"
                + "    vorname\n"
                + "FROM\n"
                + "    kunde,\n"
                + "    warenkorb\n"
                + "WHERE\n"
                + "        anrede = 'Herr'\n"
                + "    AND anzahl > 0\n"
                + "    AND kunde.kundennummer = warenkorb.kundennummer";

        ResultSet rs = befehl.executeQuery(sql);
        while (rs.next())
        {
            String knr = rs.getString("kundennummer");
            String nachname = rs.getString("nachname").trim();
            String vorname = rs.getString("vorname").trim();

            System.out.println("kundennummer: " + knr + " Nachname: " + nachname + " Vorname: " + vorname);
        }
        close();

    }
}
