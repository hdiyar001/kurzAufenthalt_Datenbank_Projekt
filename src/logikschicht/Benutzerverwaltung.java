package logikschicht;

import java.util.List;

import datenhaltungsschicht.DBBenutzer;

public class Benutzerverwaltung {

    public static boolean storeBenutzer(Benutzer benutzer) throws Exception {
        return DBBenutzer.Insert(benutzer);
    }

    public static boolean updateBenutzer(Benutzer benutzer) throws Exception {
        return DBBenutzer.update(benutzer);
    }

    public static boolean deleteBenutzer(String benutzerId) throws Exception {
        return DBBenutzer.Delete(benutzerId);
    }

    public static Benutzer getBenutzerBybenutzerId(String benutzerid) throws Exception {
        return DBBenutzer.getBenutzerByBenutzerId(benutzerid);
    }

    public static Benutzer checkUserExists(String bNameOEmail, String passwort) throws Exception {

        return DBBenutzer.getBenutzerByLogin(bNameOEmail, passwort);
    }

    public static Benutzer getBenutzer(String BenutzerId) throws Exception {
        return DBBenutzer.getBenutzerByBenutzerId(BenutzerId);
    }

    public static String getLastId() throws Exception {
        int lastId = Integer.parseInt(DBBenutzer.getLastId());
        return (++lastId) + "";
    }

    public static List<Benutzer> getAllBenutzern() throws Exception {
        return DBBenutzer.getAllBenutzer();
    }
}
