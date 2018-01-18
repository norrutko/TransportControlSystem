package sample;

/**
 * Created by megi2 on 18.01.2018.
 */
public class Coordinates {
    private Double lat;
    private Double lon;

    public Coordinates() {
    }

    public Coordinates(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
