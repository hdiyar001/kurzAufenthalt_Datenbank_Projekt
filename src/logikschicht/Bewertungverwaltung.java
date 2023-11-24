package logikschicht;

import datenhaltungsschicht.DBBewertung;
import java.util.List;

/**
 * Die Klasse Bewertungverwaltung dient als Schnittstelle zwischen der
 * Logikschicht und der Datenhaltungsschicht. Sie bietet Methoden zur Verwaltung
 * von Bewertungsdaten, wie das Erstellen, Löschen und Abrufen von Bewertungen.
 *
 * @author Diyar, Hussam und Ronida
 */
public class Bewertungverwaltung {

    /**
     * Speichert eine neue Bewertung in der Datenbank.
     *
     * @param bewertung Das Bewertungsobjekt, das gespeichert werden soll.
     * @return true, wenn die Bewertung erfolgreich gespeichert wurde, sonst
     * false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean Bewertungschreiben(Bewertung bewertung) throws Exception {
        return DBBewertung.Insert(bewertung);
    }

    /**
     * Löscht eine Bewertung aus der Datenbank anhand ihrer Bewertungs-ID.
     *
     * @param bewertungId Die ID der zu löschenden Bewertung.
     * @return true, wenn die Bewertung erfolgreich gelöscht wurde, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean deleteBewrtung(String bewertungId) throws Exception {

        return DBBewertung.Delete(bewertungId);

    }

    /**
     * Ruft Bewertungen anhand ihrer Bewertungs-ID ab.
     *
     * @param bewertungid Die ID der abzurufenden Bewertungen.
     * @return Eine Liste von Bewertungen, die der angegebenen ID entsprechen.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<Bewertung> getBewertungBybewertungId(String bewertungid) throws Exception {
        return DBBewertung.getBewertungByBewertungId(bewertungid);
    }

    /**
     * Ruft alle Bewertungs-IDs für einen bestimmten Benutzer anhand seiner
     * Benutzer-ID ab.
     *
     * @param benutzerId Die Benutzer-ID, für die die Bewertungen abgerufen
     * werden sollen.
     * @return Eine Liste von Bewertungs-IDs.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<Bewertung> getAllBewertungIdByBenutzerId(String benutzerId) throws Exception {
        return DBBewertung.getAllBewertungIdByBenutzerId(benutzerId);
    }

}
