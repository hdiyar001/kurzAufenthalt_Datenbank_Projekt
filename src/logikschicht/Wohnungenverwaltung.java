package logikschicht;

import java.util.ArrayList;
import java.util.List;

import datenhaltungsschicht.DBWohnung;

public class Wohnungenverwaltung {

    private static List<Wohnung> wohnungn = new ArrayList<>();
    private static List<FilterWohnung> GefWohnungn = new ArrayList<>();
    private static List<FilterWohnung> VermWohnungn = new ArrayList<>();

    public static boolean storeWohnung(Wohnung wohnung) throws Exception {
        boolean stored = DBWohnung.Insert(wohnung);
        if (stored)
        {
            wohnungn.add(wohnung);
        }
        return stored;
    }

    public static boolean updateWohnungStatus(String wohnungId) throws Exception {
        return DBWohnung.updateWohnungStatus(wohnungId);
    }

    public static boolean deleteWohnung(Wohnung wohnung) throws Exception {
        if (DBWohnung.Delete(wohnung) && wohnungn.contains(wohnung))
        {
            wohnungn.remove(wohnung);
            return true;
        }

        return false;

    }

    public static Wohnung getWohnungBywohnungId(String wohnungid) throws Exception {
        return DBWohnung.getWohnungByWohnungId(wohnungid);
    }

//    public static boolean checkUserExists(String bNameOEmail, String passwort) throws Exception {
//
//        return DBWohnung.getWohnungByLogin(bNameOEmail, passwort) != null;
//    }
    public static List<Wohnung> getAllWohnungen() throws Exception {
        wohnungn = DBWohnung.getAllWohnung();
        return wohnungn;
    }

    public static List<FilterWohnung> getAllGefWohnungen(String preisProNachtP, String ortP) throws Exception {
        GefWohnungn = DBWohnung.getAllWohnungenGefiltert(preisProNachtP, ortP);
        return GefWohnungn;
    }

    public static List<FilterWohnung> getVermieteteWohnungen(String benutzerId) throws Exception {
        VermWohnungn = DBWohnung.getAllVermieteteWohnungen(benutzerId);
        return VermWohnungn;
    }
}
