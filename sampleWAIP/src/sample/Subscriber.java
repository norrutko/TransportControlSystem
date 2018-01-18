package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Subscriber {
    private String number;
    private double startFuel;
    private double actualFuel;
    private double speed;
    private List<Coordinates> route;
    private String startDate;

    public Subscriber(String number) {
        this.number = number;
        int minFuel = 500;
        int maxFuel = 1200;
        double rand = new Random().nextDouble();
        this.startFuel = minFuel+rand*(maxFuel-minFuel);
        this.actualFuel = startFuel;
        //todo zdefiniowanie speed, startdate
        this.route = new ArrayList<Coordinates>();
    }

    public String getNumber() {
        return number;
    }
    public double getStartFuel() { return startFuel; }
    public double getActualFuel() { return actualFuel; }
    public double getSpeed() { return speed; }
    public List<Coordinates> getRoute() { return route; }
    public String getStartDate() { return startDate; }


    public void setActualFuel(double actualFuel) {
        this.actualFuel = actualFuel;
    }
    public void setSpeed(double speed) {this.speed = speed; }
    public void addRoute(Coordinates coordinates) {this.route.add(coordinates); }
}
