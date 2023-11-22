package logikschicht;

import java.util.ArrayList;
import java.util.List;

import datenhaltungsschicht.DBNachrichten;

public class Nachrichtenverwaltung {

    private static List<Nachrichten> nachrichtenn = new ArrayList<>();

    public static boolean sendNachricht(Nachrichten nachricht) throws Exception {
        return DBNachrichten.Insert(nachricht);
    }

    public static boolean deleteNachricht(String nachrichtId) throws Exception {

        return DBNachrichten.Delete(nachrichtId);

    }

    public static Nachrichten getNachrichtenBynachrichtenId(String nachrichtenid) throws Exception {
        return DBNachrichten.getNachrichtenByNachrichtenId(nachrichtenid);
    }

//    public static boolean checkUserExists(String bNameOEmail, String passwort) throws Exception {
//
//        return DBNachrichten.getNachrichtenByLogin(bNameOEmail, passwort) != null;
//    }
    public static List<Nachrichten> getAllNachrichten(String benutzerid) throws Exception {
        nachrichtenn = DBNachrichten.getAllNachrichten(benutzerid);

        return nachrichtenn;
    }

}
