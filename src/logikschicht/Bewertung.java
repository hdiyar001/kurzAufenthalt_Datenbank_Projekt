package logikschicht;

/**
 *
 * @author Diyar
 */
public class Bewertung {

    private String bewertungId;
    private String buchungId;
    private String bewertungText;
    private String sternBewertung;

    public Bewertung(String bewertungId, String buchungId, String bewertungText, String sternBewertung) {
        this.bewertungId = bewertungId;
        this.buchungId = buchungId;
        this.bewertungText = bewertungText;
        this.sternBewertung = sternBewertung;
    }

    public String getBewertungId() {
        return bewertungId;
    }

    public void setBewertungId(String bewertungId) {
        this.bewertungId = bewertungId;
    }

    public String getBuchungId() {
        return buchungId;
    }

    public void setBuchungId(String buchungId) {
        this.buchungId = buchungId;
    }

    public String getBewertungText() {
        return bewertungText;
    }

    public void setBewertungText(String bewertungText) {
        this.bewertungText = bewertungText;
    }

    public String getSternBewertung() {
        return sternBewertung;
    }

    public void setSternBewertung(String sternBewertung) {
        this.sternBewertung = sternBewertung;
    }

    @Override
    public String toString() {
        return "Bewertung{" + "bewertungId=" + bewertungId + ", buchungId=" + buchungId + ", bewertungText=" + bewertungText + ", sternBewertung=" + sternBewertung + '}';
    }

}
