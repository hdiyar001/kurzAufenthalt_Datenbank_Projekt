package logikschicht;

public class Benutzer {

    private String benutzerId;
    private String nachname;
    private String vorname;
    private String anrede;
    private String benutzerName;
    private String email;
    private String passwort;
    private String strasse;
    private String ort;
    private String plz;
    private String geburtsdatum;
    private String refBenutzer;
    private String verft;

    public Benutzer(String benutzerId, String nachname, String vorname, String anrede, String benutzerName, String email, String passwort, String strasse, String ort, String plz, String geburtsdatum, String refBenutzer, String verft) {
        this.benutzerId = benutzerId;
        this.nachname = nachname;
        this.vorname = vorname;
        this.anrede = anrede;
        this.benutzerName = benutzerName;
        this.email = email;
        this.passwort = passwort;
        this.strasse = strasse;
        this.ort = ort;
        this.plz = plz;
        this.geburtsdatum = geburtsdatum;
        this.refBenutzer = refBenutzer;
        this.verft = verft;
    }

    public String getVerft() {
        return verft;
    }

    public void setVerft(String verft) {
        this.verft = verft;
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

    public String getBenutzerName() {
        return benutzerName;
    }

    public void setBenutzerName(String benutzerName) {
        this.benutzerName = benutzerName;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    @Override
    public String toString() {
        return "Benutzer{" + "benutzerId=" + benutzerId + ", nachname=" + nachname + ", vorname=" + vorname + ", anrede=" + anrede + ", benutzerName=" + benutzerName + ", email=" + email + ", passwort=" + passwort + ", strasse=" + strasse + ", ort=" + ort + ", plz=" + plz + ", geburtsdatum=" + geburtsdatum + ", refBenutzer=" + refBenutzer + ", verft=" + verft + '}';
    }

}
