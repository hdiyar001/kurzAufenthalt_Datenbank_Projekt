package logikschicht;

import java.util.List;

import datenhaltungsschicht.DBZahlungen;

public class Zahlenverwaltung {

    public static boolean storeZahlungen(Zahlungen zahlung) throws Exception {
        return DBZahlungen.Insert(zahlung);
    }

    public static boolean updateZahlungen(Zahlungen zahlung) throws Exception {
        return DBZahlungen.update(zahlung);
    }

    public static boolean deleteZahlungen(Zahlungen zahlung) throws Exception {
        return DBZahlungen.Delete(zahlung);
    }

    public static List<Zahlungen> getAllZahlungenen(String benutzerid) throws Exception {
        return DBZahlungen.getAllZahlungen(benutzerid);
    }

}
