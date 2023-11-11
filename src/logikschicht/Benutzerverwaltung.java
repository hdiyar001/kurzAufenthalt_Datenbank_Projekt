package logikschicht;

import java.util.ArrayList;
import java.util.List;

import datenhaltungsschicht.DBBenutzer;

public class Benutzerverwaltung {

    private static List<Benutzer> benutzern = new ArrayList<>();

    public static boolean storeBenutzer(Benutzer benutzer) throws Exception {
        boolean stored = DBBenutzer.Insert(benutzer);
        if (stored)
        {
            benutzern.add(benutzer);
        }
        return stored;
    }

    public static boolean updateBenutzer(Benutzer benutzer) throws Exception {
        boolean updated = DBBenutzer.update(benutzer);
        if (updated)
        {
            for (Benutzer existingKunde : benutzern)
            {
                if (existingKunde.getBenutzerId() == benutzer.getBenutzerId())
                {
                    existingKunde.setAnrede(benutzer.getAnrede());
                    existingKunde.setVorname(benutzer.getVorname());
                    existingKunde.setNachname(benutzer.getNachname());
                    existingKunde.setGeburtsdatum(benutzer.getGeburtsdatum());
                    existingKunde.setEmail(benutzer.getEmail());
                    existingKunde.setPlz(benutzer.getPlz());
                    existingKunde.setOrt(benutzer.getOrt());
                    existingKunde.setStrasse(benutzer.getStrasse());
                    existingKunde.setRefBenutzer(benutzer.getRefBenutzer());
                    break;
                }
            }
        }
        return updated;
    }

    public static boolean deleteBenutzer(Benutzer benutzer) throws Exception {
        if (DBBenutzer.Delete(benutzer) && benutzern.contains(benutzer))
        {
            benutzern.remove(benutzer);
            return true;
        } else
        {
            return false;
        }
    }

    public static Benutzer getBenutzerBybenutzerId(String kundennummer) throws Exception {
        return DBBenutzer.getBenutzerByBenutzerId(kundennummer);
    }

    public static List<Benutzer> getAllBenutzern() throws Exception {
        benutzern = DBBenutzer.getAllBenutzer();
        return benutzern;
    }
}
