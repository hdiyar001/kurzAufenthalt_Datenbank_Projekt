package logikschicht;

public class Wohnung {

    private String wohnungId;
    private String eigentuemerId;
    private String strasse;
    private String ort;
    private String plz;
    private String preisProNacht;
    private String beschreibung;
    private String verfuegbarkeit;

    public Wohnung(String wohnungId, String eigentuemerId, String strasse, String ort, String plz, String preisProNacht, String beschreibung, String verfuegbarkeit) {
        this.wohnungId = wohnungId;
        this.eigentuemerId = eigentuemerId;
        this.strasse = strasse;
        this.ort = ort;
        this.plz = plz;
        this.preisProNacht = preisProNacht;
        this.beschreibung = beschreibung;
        this.verfuegbarkeit = verfuegbarkeit;
    }

    public String getWohnungId() {
        return wohnungId;
    }

    public void setWohnungId(String wohnungId) {
        this.wohnungId = wohnungId;
    }

    public String getEigentuemerId() {
        return eigentuemerId;
    }

    public void setEigentuemerId(String eigentuemerId) {
        this.eigentuemerId = eigentuemerId;
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

    public String getPreisProNacht() {
        return preisProNacht;
    }

    public void setPreisProNacht(String preisProNacht) {
        this.preisProNacht = preisProNacht;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getVerfuegbarkeit() {
        return verfuegbarkeit;
    }

    public void setVerfuegbarkeit(String verfuegbarkeit) {
        this.verfuegbarkeit = verfuegbarkeit;
    }

    @Override
    public String toString() {
        return "Wohnung{" + "wohnungId=" + wohnungId + ", eigentuemerId=" + eigentuemerId + ", strasse=" + strasse + ", ort=" + ort + ", plz=" + plz + ", preisProNacht=" + preisProNacht + ", beschreibung=" + beschreibung + ", verfuegbarkeit=" + verfuegbarkeit + '}';
    }

}
