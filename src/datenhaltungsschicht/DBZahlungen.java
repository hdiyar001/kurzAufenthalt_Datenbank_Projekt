package datenhaltungsschicht;

import static datenhaltungsschicht.DBZugriff.close;
import logikschicht.Zahlungen;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diyar
 */
public class DBZahlungen {

    private static ResultSet datenmenge;
    private static DBZugriff dbZugriff = DBZugriff.getInstance();

    public static boolean Insert(Zahlungen zahlungen) throws Exception {
        String zahlungsid = zahlungen.getZahlungsId() == null ? (getLastId() + 1) + "" : zahlungen.getZahlungsId();
        String insertCommand = "INSERT INTO T_Zahlungen VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(insertCommand))
        {
            ps.setString(1, zahlungsid);
            ps.setString(2, zahlungen.getBuchungId());
            ps.setString(3, zahlungen.getBetrag());
            ps.setString(4, zahlungen.getZahlungsdatum());
            ps.setString(5, zahlungen.getZahlungsart());
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Hinzufügen der Zahlung " + zahlungen.getZahlungsId(), ex);
        } finally
        {
            close();
        }
        return true;
    }

    public static int getLastId() throws Exception {
        String sql = "SELECT MAX(zahlungsid) FROM T_Zahlungen";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(sql))
        {
            ResultSet datenmenge = ps.executeQuery();
            if (datenmenge.next())
            {
                return datenmenge.getInt(1);
            }
        } catch (SQLException e)
        {
            throw new Exception("Fehler beim Abrufen der letzten Zahlungs-ID", e);
        } finally
        {
            close();
        }
        return 0;
    }

    public static boolean update(Zahlungen zahlungen) throws Exception {
        String updateCommand = "UPDATE T_Zahlungen SET buchungId = ?, betrag = ?, zahlungsdatum = ?, zahlungsart = ? WHERE zahlungsId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(updateCommand))
        {
            ps.setString(1, zahlungen.getBuchungId());
            ps.setString(2, zahlungen.getBetrag());
            ps.setString(3, zahlungen.getZahlungsdatum());
            ps.setString(4, zahlungen.getZahlungsart());
            ps.setString(5, zahlungen.getZahlungsId());
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Aktualisieren der Zahlung " + zahlungen.getZahlungsId(), ex);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean Delete(Zahlungen zahlungen) throws Exception {
        String deleteCommand = "DELETE FROM T_Zahlungen WHERE zahlungsId = ?";
        try (PreparedStatement ps = dbZugriff.getConnection().prepareStatement(deleteCommand))
        {
            ps.setString(1, zahlungen.getZahlungsId());
            int status = ps.executeUpdate();
            return status == 1;
        } catch (SQLException ex)
        {
            throw new Exception("Fehler beim Löschen der Zahlung " + zahlungen.getZahlungsId(), ex);
        } finally
        {
            close();
        }
    }

    public static List<Zahlungen> getAllZahlungen(String benutzerid) throws Exception {
        String query = "SELECT * FROM T_Zahlungen,T_buchung , T_benutzer "
                + "WHERE t_buchung.mieterid=T_benutzer.benutzerid AND t_zahlungen.buchungid = t_buchung.buchungid AND mieterid= ? ";

        ArrayList<Zahlungen> zahlungenListe = new ArrayList<>();
        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(query))
        {
            pstmt.setString(1, benutzerid);
            datenmenge = pstmt.executeQuery();
            while (getNext())
            {
                String zahlungsId = getzahlungsId();
                String buchungId = getBuchungId();
                String betrag = getBetrag() + " €";
                String zahlungsdatum = getZahlungsdatum();
                String zahlungsart = getZahlungsart();

                Zahlungen zahlungen = new Zahlungen(zahlungsId, buchungId, betrag, zahlungsdatum, zahlungsart);
                zahlungenListe.add(zahlungen);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Zahlungsdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return zahlungenListe;
    }

    public static Zahlungen getZahlungenByZahlungenId(String zahlungsId) throws Exception {
        Zahlungen zahlungen = null;
        String query = "SELECT * FROM T_Zahlungen WHERE zahlungenid = ?";

        try (PreparedStatement pstmt = dbZugriff.getConnection().prepareStatement(query))
        {
            pstmt.setString(1, zahlungsId);

            datenmenge = pstmt.executeQuery(query);

            if (datenmenge.next())
            {
                String buchungId = getBuchungId();
                String betrag = getBetrag();
                String zahlungsdatum = getZahlungsdatum();
                String zahlungsart = getZahlungsart();

                zahlungen = new Zahlungen(zahlungsId, buchungId, betrag, zahlungsdatum, zahlungsart);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Zahlungendaten aufgetreten. ");
        } finally
        {
            close();
        }
        return zahlungen;
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

    public static String getzahlungsId() throws SQLException {
        return datenmenge.getString("zahlungsId");
    }

    public static String getBuchungId() throws Exception {
        return datenmenge.getString("buchungId");
    }

    public static String getBetrag() throws Exception {
        return datenmenge.getString("betrag");
    }

    public static String getZahlungsdatum() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(datenmenge.getDate("zahlungsdatum"));
    }

    public static String getZahlungsart() throws Exception {
        return datenmenge.getString("zahlungsart");
    }
}
