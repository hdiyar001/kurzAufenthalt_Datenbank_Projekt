package logikschicht;

import java.util.ArrayList;
import java.util.List;

import datenhaltungsschicht.DBNachrichten;

public class Nachrichtenverwaltung {

    private static List<Nachrichten> nachrichtenn = new ArrayList<>();

    public static boolean storeNachrichten(Nachrichten nachrichten) throws Exception {
        boolean stored = DBNachrichten.Insert(nachrichten);
        if (stored)
        {
            nachrichtenn.add(nachrichten);
        }
        return stored;
    }

    public static boolean updateNachrichten(Nachrichten nachrichten) throws Exception {
        boolean updated = DBNachrichten.update(nachrichten);
        if (updated)
        {
            for (Nachrichten existingNachrichten : nachrichtenn)
            {
//                if (existingNachrichten.getNachrichtenId() == nachrichten.getNachrichtenId())
//                {
//                    existingNachrichten.setAnrede(nachrichten.getAnrede());
//                    existingNachrichten.setVorname(nachrichten.getVorname());
//                    existingNachrichten.setNachname(nachrichten.getNachname());
//                    existingNachrichten.setGeburtsdatum(nachrichten.getGeburtsdatum());
//                    existingNachrichten.setEmail(nachrichten.getEmail());
//                    existingNachrichten.setPlz(nachrichten.getPlz());
//                    existingNachrichten.setOrt(nachrichten.getOrt());
//                    existingNachrichten.setStrasse(nachrichten.getStrasse());
//                    existingNachrichten.setRefNachrichten(nachrichten.getRefNachrichten());
//                    break;
//                }
            }
        }
        return updated;
    }

    public static boolean deleteNachrichten(Nachrichten nachrichten) throws Exception {
        if (DBNachrichten.Delete(nachrichten) && nachrichtenn.contains(nachrichten))
        {
            nachrichtenn.remove(nachrichten);
            return true;
        }

        return false;

    }

    public static Nachrichten getNachrichtenBynachrichtenId(String nachrichtenid) throws Exception {
        return DBNachrichten.getNachrichtenByNachrichtenId(nachrichtenid);
    }

//    public static boolean checkUserExists(String bNameOEmail, String passwort) throws Exception {
//
//        return DBNachrichten.getNachrichtenByLogin(bNameOEmail, passwort) != null;
//    }
    public static List<Nachrichten> getAllNachrichten() throws Exception {
        nachrichtenn = DBNachrichten.getAllNachrichten();
        return nachrichtenn;
    }
}
