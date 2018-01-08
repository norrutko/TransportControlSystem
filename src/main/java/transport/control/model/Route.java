package transport.control.model;

import java.util.Date;
import java.util.List;

public class Route {

    private static int lastId = 0;

    private Integer id;
    private Driver driver;
    private Transport transport;

    private List<Visit> visits;

    private Coordinates start;
    private Date startTime;

    private Coordinates end;
    private Date endTime;

    public Route() {
    }

    public Route(Driver driver, Transport transport, Coordinates start, Date startTime, Coordinates end) {
        this.id = ++lastId;
        this.driver = driver;
        this.transport = transport;
        this.start = start;
        this.startTime = startTime;
        this.end = end;
    }

    public Route(Integer id, Driver driver, Transport transport, List<Visit> visits, Coordinates start,
                 Date startTime, Coordinates end, Date endTime) {
        this.id = id;
        this.driver = driver;
        this.transport = transport;
        this.visits = visits;
        this.start = start;
        this.startTime = startTime;
        this.end = end;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public Coordinates getStart() {
        return start;
    }

    public void setStart(Coordinates start) {
        this.start = start;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Coordinates getEnd() {
        return end;
    }

    public void setEnd(Coordinates end) {
        this.end = end;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
