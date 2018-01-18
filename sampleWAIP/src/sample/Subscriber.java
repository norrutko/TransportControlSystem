package sample;

import java.util.Random;

public class Subscriber {
    private String number;
    private double fuel;

    public Subscriber(String number) {
        this.number = number;
        int minFuel = 500;
        int maxFuel = 1200;
        double rand = new Random().nextDouble();
        this.fuel = minFuel+rand*(maxFuel-minFuel);
    }

    public String getNumber() {
        return number;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }
}
