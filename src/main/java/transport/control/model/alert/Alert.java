package transport.control.model.alert;

public class Alert {

    private AlertType alertType;
    private Integer transportId;
    private String Message;


    public Alert() {
    }

    public Alert(AlertType alertType, Integer transportId, String message) {
        this.alertType = alertType;
        this.transportId = transportId;
        Message = message;
    }

    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
