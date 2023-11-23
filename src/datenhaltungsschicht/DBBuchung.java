package datenhaltungsschicht;

import logikschicht.FilterBuchung;
import static datenhaltungsschicht.DBZugriff.befehl;
import static datenhaltungsschicht.DBZugriff.close;
import static datenhaltungsschicht.DBZugriff.connect;
import logikschicht.Buchung;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 *
 * @author Diyar
 */
public class DBBuchung extends DBZugriff {

    private static ResultSet datenmenge;

    public static boolean Insert(Buchung buchung) throws Exception {
        String buchungsId = (buchung.getBuchungId() == null ? (getLastId() + 1) + "" : buchung.getBuchungId());
        connect();
        System.out.println(buchung);
        String insertCommand = "INSERT INTO T_Buchung VALUES  (" + buchungsId
                + "  , " + buchung.getMieterId()
                + "  , " + buchung.getWohnungId()
                + "  , '" + buchung.getBuchungsDatum()
                + "' , '" + buchung.getStartDatum()
                + "' , '" + buchung.getEndDatum()
                + "')";
        try
        {
            befehl.executeUpdate(insertCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Hinzufügen des Buchungs " + buchungsId + " aufgetreten.";
            ex.printStackTrace();
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean update(Buchung buchung) throws Exception {
        connect();

        String updateCommand = "UPDATE T_Buchung SET mieterId = " + buchung.getMieterId() + ", wohnungId= " + buchung.getWohnungId()
                + ", buchungsdatum= '" + buchung.getBuchungsDatum() + "', startDatum= '" + buchung.getStartDatum() + "', endDatum = " + buchung.getEndDatum() + "', WHERE buchungId = " + buchung.getBuchungId();

        try
        {
            befehl.executeUpdate(updateCommand);
        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Aktualisieren des Buchungs " + buchung.getBuchungId() + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return true;
    }

    public static boolean Delete(String buchungId) throws Exception {
        int stauts = -1;
        connect();
        String deleteCommand = "DELETE FROM T_Buchung WHERE buchungId = " + buchungId;

        try
        {
            stauts = befehl.executeUpdate(deleteCommand);

        } catch (SQLException ex)
        {
            String errorMessage = "Es ist ein Fehler beim Löschen des Buchungs " + buchungId + " aufgetreten.";
            throw new Exception(errorMessage);
        } finally
        {
            close();
        }
        return stauts == 1;
    }

    public static List<FilterBuchung> getAllBuchung(String benutzerId,
            String buchungId,
            String ortP,
            String buchungsdatum) throws Exception {

        String whereKlausel = "WHERE t_buchung.enddatum > CURRENT_DATE AND ";
        whereKlausel += benutzerId == null ? "" : " Mieterid = " + benutzerId + " AND ";
        whereKlausel += buchungId == null ? "" : " t_buchung.buchungId = " + buchungId + " AND ";
        whereKlausel += ortP == null ? "" : " t_wohnung.ort = '" + ortP + "' AND ";
        whereKlausel += buchungsdatum == null ? "" : " t_buchung.buchungsdatum = '" + buchungsdatum + "' AND ";

        whereKlausel = whereKlausel.length() == 6 ? "" : whereKlausel.substring(0, whereKlausel.length() - 4);

        String sql = "SELECT t_buchung.buchungid,t_buchung.buchungsdatum, t_buchung.startdatum, t_buchung.enddatum, "
                + "t_wohnung.strasse, t_wohnung.ort, t_wohnung.plz, t_wohnung.preispronacht, (t_buchung.enddatum - t_buchung.startdatum) AS AnzahlDerNaechte ,"
                + " (t_buchung.enddatum - t_buchung.startdatum)* t_wohnung.preispronacht as betrag "
                + "FROM    t_benutzer "
                + "   JOIN t_buchung ON t_benutzer.benutzerid = t_buchung.mieterid "
                + "   JOIN t_wohnung ON t_buchung.wohnungid = t_wohnung.wohnungid " + whereKlausel;

        ArrayList<FilterBuchung> filterBuchungen = new ArrayList<>();
        connect();
        try
        {

            datenmenge = befehl.executeQuery(sql);
            while (datenmenge.next())
            {
                String buchungid = datenmenge.getString("buchungid");
                String buchungsDatum = datenmenge.getString("buchungsdatum");
                String startDatum = datenmenge.getString("startdatum");
                String endDatum = datenmenge.getString("enddatum");
                String strasse = datenmenge.getString("strasse");
                String ort = datenmenge.getString("Ort");
                String plz = datenmenge.getString("PLZ");
                String anschrift = strasse + " " + plz + " " + ort;
                String preisProNacht = datenmenge.getString("preisProNacht") + " €";
                String anzahlDerNaechte = datenmenge.getString("AnzahlDerNaechte");
                String betrag = datenmenge.getString("betrag") + " €";

//                FilterBuchung buchung = new FilterBuchung(buchungid, buchungsDatum, startDatum, endDatum, anschrift, preisProNacht,
//                        textBewertung, sternBewertung, anzahlDerNaechte, betrag, zahlungsdatum, zahlungsart);
                FilterBuchung buchung = new FilterBuchung(buchungid, buchungsDatum, startDatum, endDatum, anschrift, preisProNacht, anzahlDerNaechte, betrag);
                filterBuchungen.add(buchung);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("Es ist ein Fehler beim Lesen der Buchungdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return filterBuchungen;
    }

    public static boolean checkBuchungExists(String benutzerid, String wohnungId) throws Exception {
        connect();
        String query = " SELECT benutzerid, wohnungid FROM t_benutzer "
                + "JOIN t_buchung ON t_benutzer.benutzerid = t_buchung.mieterid"
                + " WHERE benutzerid = " + benutzerid + " AND wohnungid =" + wohnungId;

        try
        {
            datenmenge = befehl.executeQuery(query);
            if (datenmenge.next())
            {
                return true;
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Benutzerdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return false;
    }

    public static Buchung getBuchungByBuchungId(String buchungId) throws Exception {
        connect();
        Buchung buchung = null;
        String query = "SELECT * FROM T_Buchung WHERE buchungid = " + buchungId;

        try
        {
            datenmenge = befehl.executeQuery(query);

            if (datenmenge.next())
            {
                String mieterId = getMieterId();
                String wohnungId = getWohnungId();
                String buchungsDatum = getBuchungsDatum();
                String startDatum = getStartDatum();
                String endDatum = getEndDatum();

                buchung = new Buchung(buchungId, mieterId, wohnungId, buchungsDatum, startDatum, endDatum);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Buchungdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return buchung;
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

    public static String getbuchungId() throws SQLException {
        return datenmenge.getString("buchungId");
    }

    public static String getMieterId() throws Exception {
        return datenmenge.getString("mieterId");
    }

    public static String getWohnungId() throws Exception {
        return datenmenge.getString("wohnungId");
    }

    public static String getBuchungsDatum() throws Exception {
        Date date = datenmenge.getDate("buchungsDatum");
        if (date != null)
        {
            return new SimpleDateFormat("dd.MM.yyyy").format(date);
        } else
        {
            throw new Exception("Buchungsdatum ist null oder im falschen Format");
        }
    }

    public static String getStartDatum() throws Exception {
        Date date = datenmenge.getDate("startDatum");
        if (date != null)
        {
            return new SimpleDateFormat("dd.MM.yyyy").format(date);
        } else
        {
            throw new Exception("Startdatum ist null oder im falschen Format");
        }
    }

    public static String getEndDatum() throws Exception {
        Date date = datenmenge.getDate("endDatum");
        if (date != null)
        {
            return new SimpleDateFormat("dd.MM.yyyy").format(date);
        } else
        {
            throw new Exception("Enddatum ist null oder im falschen Format");
        }
    }

    public static int getLastId() throws Exception {
        connect();

        try
        {
            String sql = "SELECT MAX(buchungId) FROM T_Buchung";
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

    private static String getSternImo(String anzahl) {

        String sterne = "";
        for (int i = 0; i < Integer.parseInt(anzahl); i++)
        {
            sterne += "⭐";
        }

        return sterne;
    }

    public static String getBetragByBuchungId(String buchungid) throws SQLException, Exception {
        connect();
        String query = " SELECT (t_buchung.enddatum - t_buchung.startdatum)* t_wohnung.preispronacht as betrag "
                + "FROM T_Buchung,t_wohnung WHERE t_buchung.wohnungid=t_wohnung.wohnungid AND buchungId = " + buchungid;

        try
        {
            datenmenge = befehl.executeQuery(query);
            if (datenmenge.next())
            {
                return datenmenge.getString(1);
            }
        } catch (Exception e)
        {
            throw new Exception("Es ist ein Fehler beim Lesen der Benutzerdaten aufgetreten. ");
        } finally
        {
            close();
        }
        return null;
    }
}
