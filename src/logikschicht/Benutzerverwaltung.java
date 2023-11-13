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
            for (Benutzer existingBenutzer : benutzern)
            {
                if (existingBenutzer.getBenutzerId() == benutzer.getBenutzerId())
                {
                    existingBenutzer.setAnrede(benutzer.getAnrede());
                    existingBenutzer.setVorname(benutzer.getVorname());
                    existingBenutzer.setNachname(benutzer.getNachname());
                    existingBenutzer.setGeburtsdatum(benutzer.getGeburtsdatum());
                    existingBenutzer.setEmail(benutzer.getEmail());
                    existingBenutzer.setPlz(benutzer.getPlz());
                    existingBenutzer.setOrt(benutzer.getOrt());
                    existingBenutzer.setStrasse(benutzer.getStrasse());
                    existingBenutzer.setRefBenutzer(benutzer.getRefBenutzer());
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
        }

        return false;

    }

    public static Benutzer getBenutzerBybenutzerId(String benutzerid) throws Exception {
        return DBBenutzer.getBenutzerByBenutzerId(benutzerid);
    }

    public static boolean checkUserExists(String bNameOEmail, String passwort) throws Exception {

        return DBBenutzer.getBenutzerByLogin(bNameOEmail, passwort) != null;
    }

    public static List<Benutzer> getAllBenutzern() throws Exception {
        benutzern = DBBenutzer.getAllBenutzer();
        return benutzern;
    }
}
