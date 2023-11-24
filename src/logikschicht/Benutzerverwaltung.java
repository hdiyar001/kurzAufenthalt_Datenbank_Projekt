package logikschicht;

import java.util.List;
import datenhaltungsschicht.DBBenutzer;

/**
 * Die Klasse Benutzerverwaltung dient als Schnittstelle zwischen der
 * Logikschicht und der Datenhaltungsschicht. Sie bietet Methoden zur Verwaltung
 * von Benutzerdaten, wie das Speichern, Aktualisieren, Löschen und Abrufen von
 * Benutzerinformationen.
 *
 * @author Diyar, Hussam und Ronida - Autoren des Projekts
 */
public class Benutzerverwaltung {

    /**
     * Speichert einen neuen Benutzer in der Datenbank.
     *
     * @param benutzer Das Benutzerobjekt, das gespeichert werden soll.
     * @return true, wenn der Benutzer erfolgreich gespeichert wurde, sonst
     * false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean storeBenutzer(Benutzer benutzer) throws Exception {
        return DBBenutzer.Insert(benutzer);
    }

    /**
     * Aktualisiert die Daten eines existierenden Benutzers in der Datenbank.
     *
     * @param benutzer Das Benutzerobjekt mit aktualisierten Daten.
     * @return true, wenn die Aktualisierung erfolgreich war, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean updateBenutzer(Benutzer benutzer) throws Exception {
        return DBBenutzer.update(benutzer);
    }

    /**
     * Löscht einen Benutzer aus der Datenbank anhand seiner Benutzer-ID.
     *
     * @param benutzerId Die ID des zu löschenden Benutzers.
     * @return true, wenn der Benutzer erfolgreich gelöscht wurde, sonst false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean deleteBenutzer(String benutzerId) throws Exception {
        return DBBenutzer.Delete(benutzerId);
    }

    /**
     * Ruft einen Benutzer anhand seiner Benutzer-ID ab.
     *
     * @param benutzerid Die ID des abzurufenden Benutzers.
     * @return Ein Benutzerobjekt, wenn ein Benutzer mit der angegebenen ID
     * gefunden wurde, sonst null.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static Benutzer getBenutzerBybenutzerId(String benutzerid) throws Exception {
        return DBBenutzer.getBenutzerByBenutzerId(benutzerid);
    }

    /**
     * Überprüft, ob ein Benutzer mit dem angegebenen Benutzernamen oder E-Mail
     * und Passwort existiert.
     *
     * @param bNameOEmail Der Benutzername oder die E-Mail-Adresse des
     * Benutzers.
     * @param passwort Das Passwort des Benutzers.
     * @return Ein Benutzerobjekt, wenn ein Benutzer mit den angegebenen
     * Anmeldedaten existiert, sonst null.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static Benutzer checkUserExists(String bNameOEmail, String passwort) throws Exception {

        return DBBenutzer.getBenutzerByLogin(bNameOEmail, passwort);
    }

    /**
     * Ruft einen Benutzer anhand seiner Benutzer-ID ab.
     *
     * @param BenutzerId Die ID des abzurufenden Benutzers.
     * @return Ein Benutzerobjekt, wenn ein Benutzer mit der angegebenen ID
     * gefunden wurde, sonst null.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static Benutzer getBenutzer(String BenutzerId) throws Exception {
        return DBBenutzer.getBenutzerByBenutzerId(BenutzerId);
    }

    /**
     * Ruft die nächste verfügbare Benutzer-ID ab.
     *
     * @return Die nächste verfügbare Benutzer-ID als String.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static String getLastId() throws Exception {
        int lastId = Integer.parseInt(DBBenutzer.getLastId());
        return (++lastId) + "";
    }

    /**
     * Ruft eine Liste aller Benutzer aus der Datenbank ab.
     *
     * @return Eine Liste von Benutzerobjekten.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static List<Benutzer> getAllBenutzern() throws Exception {
        return DBBenutzer.getAllBenutzer();
    }

    /**
     * Aktualisiert das Passwort eines Benutzers, wenn dieser sein Passwort
     * vergessen hat.
     *
     * @param BNameemail Der Benutzername oder die E-Mail-Adresse des Benutzers.
     * @param passwort Das neue Passwort des Benutzers.
     * @return true, wenn das Passwort erfolgreich aktualisiert wurde, sonst
     * false.
     * @throws Exception Wirft eine Exception bei Fehlern während des
     * Datenbankzugriffs.
     */
    public static boolean PasswortVergessen(String BNameemail, String passwort) throws Exception {
        return DBBenutzer.updatePasswort(BNameemail, passwort);
    }
}
