package logikschicht;

/**
 *
 * @author Diyar
 */
public class FilterBuchung {

    private String buchungsDatum;
    private String startDatum;
    private String endDatum;
    private String anschrift;
    private String preisProNacht;
    private String textBewertung;
    private String sternBewertung;

    public FilterBuchung(String buchungsDatum, String startDatum, String endDatum, String anschrift, String preisProNacht, String textBewertung, String sternBewertung) {
        this.buchungsDatum = buchungsDatum;
        this.startDatum = startDatum;
        this.endDatum = endDatum;
        this.anschrift = anschrift;
        this.preisProNacht = preisProNacht;
        this.textBewertung = textBewertung;
        this.sternBewertung = sternBewertung;
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

    @Override
    public String toString() {
        return "FilterBuchung{" + "buchungsDatum=" + buchungsDatum + ", startDatum=" + startDatum + ", endDatum=" + endDatum + ", anschrift=" + anschrift + ", preisProNacht=" + preisProNacht + ", textBewertung=" + textBewertung + ", sternBewertung=" + sternBewertung + '}';
    }

}
