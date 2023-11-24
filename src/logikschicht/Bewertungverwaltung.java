package logikschicht;

import datenhaltungsschicht.DBBewertung;
import java.util.List;

public class Bewertungverwaltung {

    public static boolean Bewertungschreiben(Bewertung bewertung) throws Exception {
        return DBBewertung.Insert(bewertung);
    }

    public static boolean deleteBewrtung(String bewertungId) throws Exception {

        return DBBewertung.Delete(bewertungId);

    }

    public static List<Bewertung> getBewertungBybewertungId(String bewertungid) throws Exception {
        return DBBewertung.getBewertungByBewertungId(bewertungid);
    }

    public static List<Bewertung> getAllBewertungIdByBenutzerId(String benutzerId) throws Exception {
        return DBBewertung.getAllBewertungIdByBenutzerId(benutzerId);
    }

}
