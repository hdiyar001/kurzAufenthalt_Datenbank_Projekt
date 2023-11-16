package logikschicht;

import java.util.ArrayList;
import java.util.List;

import datenhaltungsschicht.DBWohnung;

public class Wohnungenverwaltung {

    private static List<Wohnung> wohnungn = new ArrayList<>();

    public static boolean storeWohnung(Wohnung wohnung) throws Exception {
        boolean stored = DBWohnung.Insert(wohnung);
        if (stored)
        {
            wohnungn.add(wohnung);
        }
        return stored;
    }

    public static boolean updateWohnung(Wohnung wohnung) throws Exception {
        boolean updated = DBWohnung.update(wohnung);
        if (updated)
        {
            for (Wohnung existingWohnung : wohnungn)
            {
//                if (existingWohnung.getWohnungId() == wohnung.getWohnungId())
//                {
//                    existingWohnung.setAnrede(wohnung.getAnrede());
//                    existingWohnung.setVorname(wohnung.getVorname());
//                    existingWohnung.setNachname(wohnung.getNachname());
//                    existingWohnung.setGeburtsdatum(wohnung.getGeburtsdatum());
//                    existingWohnung.setEmail(wohnung.getEmail());
//                    existingWohnung.setPlz(wohnung.getPlz());
//                    existingWohnung.setOrt(wohnung.getOrt());
//                    existingWohnung.setStrasse(wohnung.getStrasse());
//                    existingWohnung.setRefWohnung(wohnung.getRefWohnung());
//                    break;
//                }
            }
        }
        return updated;
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
    public static List<Wohnung> getAllWohnungn() throws Exception {
        wohnungn = DBWohnung.getAllWohnung();
        return wohnungn;
    }
}
