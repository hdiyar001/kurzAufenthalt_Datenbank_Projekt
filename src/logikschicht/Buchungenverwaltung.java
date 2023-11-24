package logikschicht;

import java.util.List;

import datenhaltungsschicht.DBBuchung;

public class Buchungenverwaltung {

    public static boolean storeBuchung(Buchung buchung) throws Exception {

        return DBBuchung.Insert(buchung);
    }

    public static boolean update(Buchung buchung) throws Exception {
        return DBBuchung.update(buchung);
    }

    public static boolean deleteBuchung(String buchungId) throws Exception {
        return DBBuchung.Delete(buchungId);
    }

    public static Buchung getBuchungBybuchungId(String buchungid) throws Exception {
        return DBBuchung.getBuchungByBuchungId(buchungid);
    }

    public static String getBetragByBuchungId(String buchungid) throws Exception {
        return DBBuchung.getBetragByBuchungId(buchungid);
    }

    public static List<FilterBuchung> getAllBuchungen(String benutzerId, String buchungId, String ortP, String buchungsdatum) throws Exception {
        return DBBuchung.getAllBuchung(benutzerId, buchungId, ortP, buchungsdatum);
    }

}
