package logikschicht;

import java.util.List;

import datenhaltungsschicht.DBZahlungen;

/**
 * Die Klasse Zahlenverwaltung dient als Schnittstelle zwischen der Logikschicht
 * und der Datenhaltungsschicht. Sie bietet Methoden zur Verwaltung von
 * Zahlungsdaten, wie das Speichern, Aktualisieren, Löschen und Abrufen von
 * Zahlungen.
 *
 * @author Diyar, Hussam und Ronida
 */
public class Zahlenverwaltung {

    /**
     * Speichert eine neue Zahlung in der Datenbank.
     *
     * @param zahlung Das Zahlungsobjekt, das gespeichert werden soll.
     * @return true, wenn die Zahlung erfolgreich gespeichert wurde, sonst
     * false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean storeZahlungen(Zahlungen zahlung) throws Exception {
        return DBZahlungen.Insert(zahlung);
    }

    /**
     * Aktualisiert die Daten einer existierenden Zahlung in der Datenbank.
     *
     * @param zahlung Das Zahlungsobjekt mit aktualisierten Daten.
     * @return true, wenn die Aktualisierung erfolgreich war, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean updateZahlungen(Zahlungen zahlung) throws Exception {
        return DBZahlungen.update(zahlung);
    }

    /**
     * Löscht eine Zahlung aus der Datenbank anhand ihrer Zahlungs-ID.
     *
     * @param zahlung Das Zahlungsobjekt, das gelöscht werden soll.
     * @return true, wenn die Zahlung erfolgreich gelöscht wurde, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean deleteZahlungen(Zahlungen zahlung) throws Exception {
        return DBZahlungen.Delete(zahlung);
    }

    /**
     * Ruft alle Zahlungen für einen bestimmten Benutzer ab.
     *
     * @param benutzerid Die Benutzer-ID, für die die Zahlungen abgerufen werden
     * sollen.
     * @return Eine Liste von Zahlungsobjekten.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<Zahlungen> getAllZahlungenen(String benutzerid) throws Exception {
        return DBZahlungen.getAllZahlungen(benutzerid);
    }

}
