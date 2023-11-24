package logikschicht;

import datenhaltungsschicht.DBBuchung;
import java.util.List;

/**
 * Die Klasse Buchungenverwaltung dient als Schnittstelle zwischen der
 * Logikschicht und der Datenhaltungsschicht. Sie bietet Methoden zur Verwaltung
 * von Buchungsdaten, wie das Speichern, Aktualisieren, Löschen und Abrufen von
 * Buchungsinformationen.
 *
 * @author Diyar, Hussam und Ronida
 */
public class Buchungenverwaltung {

    /**
     * Speichert eine neue Buchung in der Datenbank.
     *
     * @param buchung Das Buchungsobjekt, das gespeichert werden soll.
     * @return true, wenn die Buchung erfolgreich gespeichert wurde, sonst
     * false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean storeBuchung(Buchung buchung) throws Exception {

        return DBBuchung.Insert(buchung);
    }

    /**
     * Aktualisiert die Daten einer existierenden Buchung in der Datenbank.
     *
     * @param buchung Das Buchungsobjekt mit aktualisierten Daten.
     * @return true, wenn die Aktualisierung erfolgreich war, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean update(Buchung buchung) throws Exception {
        return DBBuchung.update(buchung);
    }

    /**
     * Löscht eine Buchung aus der Datenbank anhand ihrer Buchungs-ID.
     *
     * @param buchungId Die ID der zu löschenden Buchung.
     * @return true, wenn die Buchung erfolgreich gelöscht wurde, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean deleteBuchung(String buchungId) throws Exception {
        return DBBuchung.Delete(buchungId);
    }

    /**
     * Ruft eine Buchung anhand ihrer Buchungs-ID ab.
     *
     * @param buchungid Die ID der abzurufenden Buchung.
     * @return Ein Buchungsobjekt, wenn eine Buchung mit der angegebenen ID
     * gefunden wurde, sonst null.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static Buchung getBuchungBybuchungId(String buchungid) throws Exception {
        return DBBuchung.getBuchungByBuchungId(buchungid);
    }

    /**
     * Ruft den Gesamtbetrag einer Buchung anhand ihrer Buchungs-ID ab.
     *
     * @param buchungid Die ID der Buchung, für die der Betrag abgerufen werden
     * soll.
     * @return Der Gesamtbetrag der Buchung als String.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static String getBetragByBuchungId(String buchungid) throws Exception {
        return DBBuchung.getBetragByBuchungId(buchungid);
    }

    /**
     * Ruft alle Buchungen basierend auf spezifizierten Filterkriterien ab.
     *
     * @param benutzerId Die Benutzer-ID für die Filterung.
     * @param buchungId Die Buchungs-ID für die Filterung.
     * @param ortP Der Ort für die Filterung.
     * @param buchungsdatum Das Buchungsdatum für die Filterung.
     * @return Eine Liste von gefilterten Buchungen.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<FilterBuchung> getAllBuchungen(String benutzerId, String buchungId, String ortP, String buchungsdatum) throws Exception {
        return DBBuchung.getAllBuchung(benutzerId, buchungId, ortP, buchungsdatum);
    }

}
