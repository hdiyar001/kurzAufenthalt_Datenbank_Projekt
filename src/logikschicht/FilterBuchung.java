package logikschicht;

public class FilterBuchung {

    private String buchungid;
    private String buchungsDatum;
    private String startDatum;
    private String endDatum;
    private String anschrift;
    private String preisProNacht;
    private String textBewertung;
    private String sternBewertung;
    private String anzahlDerNaechte;
    private String betrag;
    private String zahlungsdatum;
    private String zahlungsart;

    public FilterBuchung(String buchungid, String buchungsDatum, String startDatum, String endDatum, String anschrift, String preisProNacht, String textBewertung, String sternBewertung, String anzahlDerNaechte, String betrag, String zahlungsdatum, String zahlungsart) {
        this.buchungid = buchungid;
        this.buchungsDatum = buchungsDatum;
        this.startDatum = startDatum;
        this.endDatum = endDatum;
        this.anschrift = anschrift;
        this.preisProNacht = preisProNacht;
        this.textBewertung = textBewertung;
        this.sternBewertung = sternBewertung;
        this.anzahlDerNaechte = anzahlDerNaechte;
        this.betrag = betrag;
        this.zahlungsdatum = zahlungsdatum;
        this.zahlungsart = zahlungsart;
    }

    public FilterBuchung(String buchungid, String buchungsDatum, String startDatum, String endDatum, String anschrift, String preisProNacht, String anzahlDerNaechte, String betrag) {
        this.buchungid = buchungid;
        this.buchungsDatum = buchungsDatum;
        this.startDatum = startDatum;
        this.endDatum = endDatum;
        this.anschrift = anschrift;
        this.preisProNacht = preisProNacht;
        this.anzahlDerNaechte = anzahlDerNaechte;
        this.betrag = betrag;
    }

    public String getBuchungid() {
        return buchungid;
    }

    public void setBuchungid(String buchungid) {
        this.buchungid = buchungid;
    }

    public String getBuchungsDatum() {
        return buchungsDatum;
    }

    public void setBuchungsDatum(String buchungsDatum) {
        this.buchungsDatum = buchungsDatum;
    }

    public String getStartDatum() {
        return startDatum;
    }

    public void setStartDatum(String startDatum) {
        this.startDatum = startDatum;
    }

    public String getEndDatum() {
        return endDatum;
    }

    public void setEndDatum(String endDatum) {
        this.endDatum = endDatum;
    }

    public String getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }

    public String getPreisProNacht() {
        return preisProNacht;
    }

    public void setPreisProNacht(String preisProNacht) {
        this.preisProNacht = preisProNacht;
    }

    public String getTextBewertung() {
        return textBewertung;
    }

    public void setTextBewertung(String textBewertung) {
        this.textBewertung = textBewertung;
    }

    public String getSternBewertung() {
        return sternBewertung;
    }

    public void setSternBewertung(String sternBewertung) {
        this.sternBewertung = sternBewertung;
    }

    public String getAnzahlDerNaechte() {
        return anzahlDerNaechte;
    }

    public void setAnzahlDerNaechte(String anzahlDerNaechte) {
        this.anzahlDerNaechte = anzahlDerNaechte;
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
        return "FilterBuchung{" + "buchungid=" + buchungid + ", buchungsDatum=" + buchungsDatum + ", startDatum=" + startDatum + ", endDatum=" + endDatum + ", anschrift=" + anschrift + ", preisProNacht=" + preisProNacht + ", textBewertung=" + textBewertung + ", sternBewertung=" + sternBewertung + ", anzahlDerNaechte=" + anzahlDerNaechte + ", betrag=" + betrag + ", zahlungsdatum=" + zahlungsdatum + ", zahlungsart=" + zahlungsart + '}';
    }

}
