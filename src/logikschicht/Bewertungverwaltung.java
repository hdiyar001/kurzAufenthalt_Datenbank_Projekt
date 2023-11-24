package logikschicht;

import java.util.ArrayList;
import java.util.List;

import datenhaltungsschicht.DBBewertung;

public class Bewertungverwaltung {

    public static boolean sendNachricht(Bewertung bewertung) throws Exception {
        return DBBewertung.Insert(bewertung);
    }

    public static boolean deleteNachricht(String bewertungId) throws Exception {

        return DBBewertung.Delete(bewertungId);

    }

    public static Bewertung getBewertungBybewertungId(String bewertungid) throws Exception {
        return DBBewertung.getBewertungByBewertungId(bewertungid);
    }

}
