package transport.control;

import transport.control.model.Coordinates;
import transport.control.model.Driver;
import transport.control.model.Route;
import transport.control.model.Transport;

import java.util.Date;

public class CommandLineMain {

    public static void main(String[] args) {
        Driver driver = new Driver("Vasia");

        Transport fura = new Transport("Vasina Fura");

        driver.setTransport(fura);
        fura.setDriver(driver);

        Route route = new Route();

        route.setTransport(fura);
        route.setDriver(driver);

        route.setStart(new Coordinates(0.0, 0.0));
        route.setStartTime(new Date());

        route.setEnd(new Coordinates(1.0, 1.0));
    }
}
