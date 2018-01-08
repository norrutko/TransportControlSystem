package transport.control.model;


public class Driver {

    private static int lastId = 0;

    private Integer id;
    private String name;

    private Transport transport;
    private Route route;

    public Driver(String name) {
        this.id = ++lastId;
        this.name = name;
    }

    public Driver(Integer id, String name, Transport transport, Route route) {
        this.id = id;
        this.name = name;
        this.transport = transport;
        this.route = route;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
