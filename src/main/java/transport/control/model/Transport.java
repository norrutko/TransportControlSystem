package transport.control.model;

import java.util.ArrayList;
import java.util.List;

public class Transport {

    private static int lastId = 0;

    private Integer id;
    private String name;
    private Driver driver;

    private Integer consumedFuel;

    private List<Route> routes;

    public Transport() {
        this.consumedFuel = 0;
    }

    public Transport(String name) {
        this.id = ++lastId;
        this.name = name;
        this.routes = new ArrayList<Route>();
        this.consumedFuel = 0;
    }

    public Transport(Integer id, String name, Driver driver, List<Route> routes, Integer consumedFuel) {
        this.id = id;
        this.name = name;
        this.driver = driver;
        this.routes = routes;
        this.consumedFuel = consumedFuel;
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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public Integer getConsumedFuel() {
        return consumedFuel;
    }

    public void setConsumedFuel(Integer consumedFuel) {
        this.consumedFuel = consumedFuel;
    }
}
