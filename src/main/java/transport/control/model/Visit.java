package transport.control.model;

import java.util.Date;

public class Visit {

    private String name;
    private Date date;
    private Coordinates coordinates;


    public Visit() {
    }

    public Visit(String name, Date date, Coordinates coordinates) {
        this.name = name;
        this.date = date;
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
