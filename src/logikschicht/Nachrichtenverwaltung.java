package logikschicht;

import java.util.ArrayList;
import java.util.List;
/**
 * Die Klasse Nachrichtenverwaltung dient als Schnittstelle zwischen der
 * Logikschicht und der Datenhaltungsschicht. Sie bietet Methoden zur Verwaltung
 * von Nachrichten, wie das Senden, Löschen und Abrufen von Nachrichten.
 *
 * @author Diyar, Hussam und Ronida - Autoren des Projekts
 */
import datenhaltungsschicht.DBNachrichten;

/**
 * Sendet eine Nachricht, indem sie in der Datenbank gespeichert wird.
 *
 * @param nachricht Das Nachrichtenobjekt, das gesendet werden soll.
 * @return true, wenn die Nachricht erfolgreich gesendet wurde, sonst false.
 * @throws Exception Wirft eine Exception bei Fehlern während des
 * Datenbankzugriffs.
 */
public class Nachrichtenverwaltung {

    private static List<Nachrichten> nachrichtenn = new ArrayList<>();

    public static boolean sendNachricht(Nachrichten nachricht) throws Exception {
        return DBNachrichten.Insert(nachricht);
    }

    /**
     * Löscht eine Nachricht aus der Datenbank anhand ihrer Nachrichten-ID.
     *
     * @param nachrichtId Die ID der zu löschenden Nachricht.
     * @return true, wenn die Nachricht erfolgreich gelöscht wurde, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */

    public static boolean deleteNachricht(String nachrichtId) throws Exception {

        return DBNachrichten.Delete(nachrichtId);

    }

    /**
     * Ruft eine spezifische Nachricht anhand ihrer Nachrichten-ID ab.
     *
     * @param nachrichtenid Die ID der abzurufenden Nachricht.
     * @return Ein Nachrichtenobjekt, wenn eine Nachricht mit der angegebenen ID
     * gefunden wurde, sonst null.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static Nachrichten getNachrichtenBynachrichtenId(String nachrichtenid) throws Exception {
        return DBNachrichten.getNachrichtenByNachrichtenId(nachrichtenid);
    }

    /**
     * Ruft alle Nachrichten für einen bestimmten Benutzer ab.
     *
     * @param benutzerid Die Benutzer-ID, für die die Nachrichten abgerufen
     * werden sollen.
     * @return Eine Liste von Nachrichten.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<Nachrichten> getAllNachrichten(String benutzerid) throws Exception {
        nachrichtenn = DBNachrichten.getAllNachrichten(benutzerid);

        return nachrichtenn;
    }

}
