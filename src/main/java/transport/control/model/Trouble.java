package transport.control.model;

public class Trouble {

    private Integer transportId;
    private String message;

    public Trouble() {
    }

    public Trouble(Integer transportId, String message) {
        this.transportId = transportId;
        this.message = message;
    }

    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
