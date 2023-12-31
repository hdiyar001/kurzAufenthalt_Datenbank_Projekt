package logikschicht;

import praesentationsschicht_GUI.AuthenticationControllers.LoginController;

public class Nachrichten {

    private String nachrichtenId;
    private String benutzerName;
    private String senderId;
    private String empfaengerId;
    private String nachrichtenText;
    private String zeitStempel;
    private String status;

    public Nachrichten(String nachrichtenId, String senderId, String empfaengerId, String nachrichtenText, String zeitStempel) {
        this.nachrichtenId = nachrichtenId;
        this.senderId = senderId;
        this.empfaengerId = empfaengerId;
        this.nachrichtenText = nachrichtenText;
        this.zeitStempel = zeitStempel;
    }

    public Nachrichten(String nachrichtenId, String benutzerName, String senderId, String empfaengerId, String nachrichtenText, String zeitStempel) {
        this.nachrichtenId = nachrichtenId;
        this.benutzerName = benutzerName;
        this.senderId = senderId;
        this.empfaengerId = empfaengerId;
        this.nachrichtenText = nachrichtenText;
        this.zeitStempel = zeitStempel;
    }

    public String getStatus() {

        return senderId.equals(LoginController.benutzerId) ? "Gesendt" : "Empfangen";
    }

    public String getBenutzerName() {
        return benutzerName;
    }

    public void setBenutzerName(String benutzerName) {
        this.benutzerName = benutzerName;
    }

    public String getNachrichtenId() {
        return nachrichtenId;
    }

    public void setNachrichtenId(String nachrichtenId) {
        this.nachrichtenId = nachrichtenId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getEmpfaengerId() {
        return empfaengerId;
    }

    public void setEmpfaengerId(String empfaengerId) {
        this.empfaengerId = empfaengerId;
    }

    public String getNachrichtenText() {
        return nachrichtenText;
    }

    public void setNachrichtenText(String nachrichtenText) {
        this.nachrichtenText = nachrichtenText;
    }

    public String getZeitStempel() {
        return zeitStempel;
    }

    public void setzeitStempel(String zeitStempel) {
        this.zeitStempel = zeitStempel;
    }

    @Override
    public String toString() {
        return "Nachrichten{" + "nachrichtenId=" + nachrichtenId + ", benutzerName=" + benutzerName + ", senderId=" + senderId + ", empfaengerId=" + empfaengerId + ", nachrichtenText=" + nachrichtenText + ", zeitStempel=" + zeitStempel + '}';
    }

}
