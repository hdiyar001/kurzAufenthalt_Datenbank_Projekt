package logikschicht;

import java.util.List;

import datenhaltungsschicht.DBWohnung;

/**
 * Die Klasse Wohnungenverwaltung dient als Schnittstelle zwischen der
 * Logikschicht und der Datenhaltungsschicht. Sie bietet Methoden zur Verwaltung
 * von Wohnungsdaten, wie das Speichern, Aktualisieren, Löschen und Abrufen von
 * Wohnungsdaten.
 *
 * @author Diyar, Hussam und Ronida
 */
public class Wohnungenverwaltung {

    /**
     * Speichert eine neue Wohnung in der Datenbank.
     *
     * @param wohnung Das Wohnungobjekt, das gespeichert werden soll.
     * @return true, wenn die Wohnung erfolgreich gespeichert wurde, sonst
     * false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean storeWohnung(Wohnung wohnung) throws Exception {
        return DBWohnung.Insert(wohnung);
    }

    /**
     * Aktualisiert den Status einer Wohnung in der Datenbank.
     *
     * @param wohnungId Die ID der zu aktualisierenden Wohnung.
     * @param status Der neue Status der Wohnung.
     * @return true, wenn der Status erfolgreich aktualisiert wurde, sonst
     * false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean updateWohnungStatus(String wohnungId, String status) throws Exception {
        return DBWohnung.updateWohnungStatus(wohnungId, status);
    }

    /**
     * Aktualisiert die Daten einer existierenden Wohnung in der Datenbank.
     *
     * @param wohnung Das Wohnungobjekt mit aktualisierten Daten.
     * @return true, wenn die Aktualisierung erfolgreich war, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean updateWohnung(Wohnung wohnung) throws Exception {
        return DBWohnung.update(wohnung);
    }

    /**
     * Löscht eine Wohnung aus der Datenbank anhand ihrer Wohnung-ID.
     *
     * @param wohnungId Die ID der zu löschenden Wohnung.
     * @return true, wenn die Wohnung erfolgreich gelöscht wurde, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean deleteWohnung(String wohnungId) throws Exception {
        return DBWohnung.Delete(wohnungId);
    }

    /**
     * Ruft eine spezifische Wohnung anhand ihrer Wohnung-ID ab.
     *
     * @param wohnungid Die ID der abzurufenden Wohnung.
     * @return Ein Wohnungobjekt, wenn eine Wohnung mit der angegebenen ID
     * gefunden wurde, sonst null.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static Wohnung getWohnungBywohnungId(String wohnungid) throws Exception {
        return DBWohnung.getWohnungByWohnungId(wohnungid);
    }

    /**
     * Ruft eine Liste aller Wohnungen aus der Datenbank ab.
     *
     * @return Eine Liste von Wohnungobjekten.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<Wohnung> getAllWohnungen() throws Exception {
        return DBWohnung.getAllWohnung();
    }

    /**
     * Ruft gefilterte Wohnungsdaten basierend auf spezifizierten Kriterien ab.
     *
     * @param benutzerId Die Benutzer-ID für die Filterung.
     * @param preisProNachtP Der Preis pro Nacht als Filterkriterium.
     * @param ortP Der Ort als Filterkriterium.
     * @return Eine Liste von gefilterten Wohnungdaten.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<FilterWohnung> getAllGefWohnungen(String benutzerId, String preisProNachtP, String ortP) throws Exception {
        return DBWohnung.getAllWohnungenGefiltert(benutzerId, preisProNachtP, ortP);

    }

    /**
     * Ruft alle vermieteten Wohnungen eines bestimmten Benutzers ab.
     *
     * @param benutzerId Die Benutzer-ID des Eigentümers.
     * @return Eine Liste von vermieteten Wohnungen des Benutzers.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<FilterWohnung> getVermieteteWohnungen(String benutzerId) throws Exception {
        return DBWohnung.getAllVermieteteWohnungen(benutzerId);
    }
}
