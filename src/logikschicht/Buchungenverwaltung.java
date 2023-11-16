package logikschicht;

import java.util.ArrayList;
import java.util.List;

import datenhaltungsschicht.DBBuchung;

public class Buchungenverwaltung {

    private static List<Buchung> buchungn = new ArrayList<>();

    public static boolean storeBuchung(Buchung buchung) throws Exception {
        boolean stored = DBBuchung.Insert(buchung);
        if (stored)
        {
            buchungn.add(buchung);
        }
        return stored;
    }

    public static boolean updateBuchung(Buchung buchung) throws Exception {
        boolean updated = DBBuchung.update(buchung);
        if (updated)
        {
            for (Buchung existingBuchung : buchungn)
            {
//                if (existingBuchung.getBuchungId() == buchung.getBuchungId())
//                {
//                    existingBuchung.setAnrede(buchung.getAnrede());
//                    existingBuchung.setVorname(buchung.getVorname());
//                    existingBuchung.setNachname(buchung.getNachname());
//                    existingBuchung.setGeburtsdatum(buchung.getGeburtsdatum());
//                    existingBuchung.setEmail(buchung.getEmail());
//                    existingBuchung.setPlz(buchung.getPlz());
//                    existingBuchung.setOrt(buchung.getOrt());
//                    existingBuchung.setStrasse(buchung.getStrasse());
//                    existingBuchung.setRefBuchung(buchung.getRefBuchung());
//                    break;
//                }
            }
        }
        return updated;
    }

    public static boolean deleteBuchung(Buchung buchung) throws Exception {
        if (DBBuchung.Delete(buchung) && buchungn.contains(buchung))
        {
            buchungn.remove(buchung);
            return true;
        }

        return false;

    }

    public static Buchung getBuchungBybuchungId(String buchungid) throws Exception {
        return DBBuchung.getBuchungByBuchungId(buchungid);
    }

//    public static boolean checkUserExists(String bNameOEmail, String passwort) throws Exception {
//
//        return DBBuchung.getBuchungByLogin(bNameOEmail, passwort) != null;
//    }
    public static List<Buchung> getAllBuchungen() throws Exception {
        buchungn = DBBuchung.getAllBuchung();
        return buchungn;
    }
}
