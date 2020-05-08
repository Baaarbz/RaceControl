package com.github.baaarbz.model;

import java.util.Objects;

public class Car {

    private String brand;
    private String model;
    private Garage garage;

    public Car(String brand, String model, Garage garage) {
        this.brand = brand;
        this.model = model;
        this.garage = garage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(brand, car.brand) &&
                Objects.equals(model, car.model) &&
                Objects.equals(garage, car.garage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, garage);
    }

    @Override
    public String toString() {
        return brand + " - " + model + " GARAGE: " + garage;
    }
}
