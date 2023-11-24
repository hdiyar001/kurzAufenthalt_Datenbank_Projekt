package logikschicht;

public class Zahlungen {

    private String zahlungsId;
    private String buchungId;
    private String betrag;
    private String zahlungsdatum;
    private String zahlungsart;

    public Zahlungen(String zahlungsId, String buchungId, String betrag, String zahlungsdatum, String zahlungsart) {
        this.zahlungsId = zahlungsId;
        this.buchungId = buchungId;
        this.betrag = betrag;
        this.zahlungsdatum = zahlungsdatum;
        this.zahlungsart = zahlungsart;
    }

    public String getZahlungsId() {
        return zahlungsId;
    }

    public void setZahlungsId(String zahlungsId) {
        this.zahlungsId = zahlungsId;
    }

    public String getBuchungId() {
        return buchungId;
    }

    public void setBuchungId(String buchungId) {
        this.buchungId = buchungId;
    }

    public String getBetrag() {
        return betrag;
    }

    public void setBetrag(String betrag) {
        this.betrag = betrag;
    }

    public String getZahlungsdatum() {
        return zahlungsdatum;
    }

    public void setZahlungsdatum(String zahlungsdatum) {
        this.zahlungsdatum = zahlungsdatum;
    }

    public String getZahlungsart() {
        return zahlungsart;
    }

    public void setZahlungsart(String zahlungsart) {
        this.zahlungsart = zahlungsart;
    }

    @Override
    public String toString() {
        return "Zahlungen{" + "zahlungsId=" + zahlungsId + ", buchungId=" + buchungId + ", betrag=" + betrag + ", zahlungsdatum=" + zahlungsdatum + ", zahlungsart=" + zahlungsart + '}';
    }

}
