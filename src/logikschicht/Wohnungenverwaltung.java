package logikschicht;

import java.util.List;

import datenhaltungsschicht.DBWohnung;

public class Wohnungenverwaltung {

    public static boolean storeWohnung(Wohnung wohnung) throws Exception {
        return DBWohnung.Insert(wohnung);
    }

    public static boolean updateWohnungStatus(String wohnungId, String status) throws Exception {
        return DBWohnung.updateWohnungStatus(wohnungId, status);
    }

    public static boolean updateWohnung(Wohnung wohnung) throws Exception {
        return DBWohnung.update(wohnung);
    }

    public static boolean deleteWohnung(String wohnungId) throws Exception {
        return DBWohnung.Delete(wohnungId);
    }

    public static Wohnung getWohnungBywohnungId(String wohnungid) throws Exception {
        return DBWohnung.getWohnungByWohnungId(wohnungid);
    }

//    public static boolean checkUserExists(String bNameOEmail, String passwort) throws Exception {
//
//        return DBWohnung.getWohnungByLogin(bNameOEmail, passwort) != null;
//    }
    public static List<Wohnung> getAllWohnungen() throws Exception {
        return DBWohnung.getAllWohnung();
    }

    public static List<FilterWohnung> getAllGefWohnungen(String preisProNachtP, String ortP) throws Exception {
        return DBWohnung.getAllWohnungenGefiltert(preisProNachtP, ortP);

    }

    public static List<FilterWohnung> getVermieteteWohnungen(String benutzerId) throws Exception {
        return DBWohnung.getAllVermieteteWohnungen(benutzerId);
    }
}
