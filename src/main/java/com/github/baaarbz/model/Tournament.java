package com.github.baaarbz.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tournament {

    private String name;
    private List<Race> races;
    private List<Car> cars;
    private int prize;
    private boolean isPrivate = false;
    private Garage owner;

    public Tournament(List<Race> races, int prize, String name) {
        this.races = races;
        this.cars = new ArrayList<>();
        this.prize = prize;
        this.name = name;
    }

    public Tournament(List<Race> races, int prize, Garage owner, String name) {
        this.races = races;
        this.cars = new ArrayList<>();
        this.prize = prize;
        this.owner = owner;
        this.isPrivate = true;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Garage getOwner() {
        return owner;
    }

    public void setOwner(Garage owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournament that = (Tournament) o;
        return prize == that.prize &&
                isPrivate == that.isPrivate &&
                Objects.equals(name, that.name) &&
                Objects.equals(races, that.races) &&
                Objects.equals(cars, that.cars) &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, races, cars, prize, isPrivate, owner);
    }

    @Override
    public String toString() {
        if (isPrivate) {
            return "[PRIVATE - " + owner + "] " + name;
        } else {
            return "[PUBLIC] " + name;
        }
    }
}
