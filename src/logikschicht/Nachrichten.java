package logikschicht;

/**
 *
 * @author Diyar
 */
public class Nachrichten {

    private String nachrichtenId;
    private String senderId;
    private String empfaengerId;
    private String nachrichtenText;
    private String zeitStempel;

    public Nachrichten(String nachrichtenId, String senderId, String empfaengerId, String nachrichtenText, String zeitStempel) {
        this.nachrichtenId = nachrichtenId;
        this.senderId = senderId;
        this.empfaengerId = empfaengerId;
        this.nachrichtenText = nachrichtenText;
        this.zeitStempel = zeitStempel;
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
        return "Nachrichten{" + "nachrichtenId=" + nachrichtenId + ", senderId=" + senderId + ", empfaengerId=" + empfaengerId + ", nachrichtenText=" + nachrichtenText + ", zeitStempel=" + zeitStempel + '}';
    }

}
