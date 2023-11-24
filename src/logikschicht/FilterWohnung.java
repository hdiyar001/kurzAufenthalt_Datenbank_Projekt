package logikschicht;

public class FilterWohnung {

    private String wohnungId;
    private String benutzerName;
    private String anschrift;
    private String verifiziert;
    private String preisProNacht;
    private String beschreibung;
    private String verfuegbarkeit;
    private String bewertungstext;
    private String sternBewertung;

    public FilterWohnung(String wohnungId, String benutzerName, String anschrift, String verifiziert, String preisProNacht, String beschreibung, String verfuegbarkeit, String bewertungstext, String sternBewertung) {
        this.wohnungId = wohnungId;
        this.benutzerName = benutzerName;
        this.anschrift = anschrift;
        this.verifiziert = verifiziert;
        this.preisProNacht = preisProNacht;
        this.beschreibung = beschreibung;
        this.verfuegbarkeit = verfuegbarkeit;
        this.bewertungstext = bewertungstext;
        this.sternBewertung = sternBewertung;
    }

    public FilterWohnung(String wohnungId, String anschrift, String preisProNacht, String beschreibung, String verfuegbarkeit) {
        this.wohnungId = wohnungId;
        this.anschrift = anschrift;
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

    public String getBenutzerName() {
        return benutzerName;
    }

    public void setBenutzerName(String benutzerName) {
        this.benutzerName = benutzerName;
    }

    public String getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }

    public String getVerifiziert() {
        return verifiziert;
    }

    public void setVerifiziert(String verifiziert) {
        this.verifiziert = verifiziert;
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

    public String getBewertungstext() {
        return bewertungstext;
    }

    public void setBewertungstext(String bewertungstext) {
        this.bewertungstext = bewertungstext;
    }

    public String getSternBewertung() {
        return sternBewertung;
    }

    public void setSternBewertung(String sternBewertung) {
        this.sternBewertung = sternBewertung;
    }

    @Override
    public String toString() {
        return "FilterWohnung{" + "wohnungId=" + wohnungId + ", benutzerName=" + benutzerName + ", anschrift=" + anschrift + ", verifiziert=" + verifiziert + ", preisProNacht=" + preisProNacht + ", beschreibung=" + beschreibung + ", verfuegbarkeit=" + verfuegbarkeit + ", bewertungstext=" + bewertungstext + ", sternBewertung=" + sternBewertung + '}';
    }

}
