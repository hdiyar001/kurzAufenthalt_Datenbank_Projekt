package logikschicht;

/**
 *
 * @author Diyar
 */
public class Benutzer {

    private String benutzerId;
    private String nachname;
    private String vorname;
    private String anrede;
    private String email;
    private String strasse;
    private String ort;
    private String plz;
    private String geburtsdatum;
    private String refBenutzer;

    public Benutzer(String benutzerId, String nachname, String vorname, String anrede, String email, String strasse, String ort, String plz, String geburtsdatum, String refBenutzer) {
        this.benutzerId = benutzerId;
        this.nachname = nachname;
        this.vorname = vorname;
        this.anrede = anrede;
        this.email = email;
        this.strasse = strasse;
        this.ort = ort;
        this.plz = plz;
        this.geburtsdatum = geburtsdatum;
        this.refBenutzer = refBenutzer;
    }

    public String getBenutzerId() {
        return benutzerId;
    }

    public void setBenutzerId(String benutzerId) {
        this.benutzerId = benutzerId;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getRefBenutzer() {
        return refBenutzer;
    }

    public void setRefBenutzer(String refBenutzer) {
        this.refBenutzer = refBenutzer;
    }

    @Override
    public String toString() {
        return "Benutzer{" + "benutzerId=" + benutzerId + ", nachname=" + nachname + ", vorname=" + vorname + ", anrede=" + anrede + ", email=" + email + ", strasse=" + strasse + ", ort=" + ort + ", plz=" + plz + ", geburtsdatum=" + geburtsdatum + ", refBenutzer=" + refBenutzer + '}';
    }

}
