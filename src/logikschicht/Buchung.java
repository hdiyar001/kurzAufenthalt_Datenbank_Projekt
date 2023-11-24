package logikschicht;

public class Buchung {

    private String buchungId;
    private String mieterId;
    private String wohnungId;
    private String buchungsDatum;
    private String startDatum;
    private String endDatum;

    public Buchung(String buchungId, String mieterId, String wohnungId, String buchungsDatum, String startDatum, String endDatum) {
        this.buchungId = buchungId;
        this.mieterId = mieterId;
        this.wohnungId = wohnungId;
        this.buchungsDatum = buchungsDatum;
        this.startDatum = startDatum;
        this.endDatum = endDatum;
    }

    public String getBuchungId() {
        return buchungId;
    }

    public void setBuchungId(String buchungId) {
        this.buchungId = buchungId;
    }

    public String getMieterId() {
        return mieterId;
    }

    public void setMieterId(String mieterId) {
        this.mieterId = mieterId;
    }

    public String getWohnungId() {
        return wohnungId;
    }

    public void setWohnungId(String wohnungId) {
        this.wohnungId = wohnungId;
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

    @Override
    public String toString() {
        return "Buchung{" + "buchungId=" + buchungId + ", mieterId=" + mieterId + ", wohnungId=" + wohnungId + ", buchungsDatum=" + buchungsDatum + ", startDatum=" + startDatum + ", endDatum=" + endDatum + '}';
    }

}
