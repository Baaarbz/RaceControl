package com.github.baaarbz.model;

import java.util.List;
import java.util.Objects;

public class Tournament {

    private List<Race> races;
    private List<Garage> garages;
    private int prize;
    private boolean isPrivate = false;
    private Garage owner;

    public Tournament(List<Race> races, List<Garage> garages, int prize) {
        this.races = races;
        this.garages = garages;
        this.prize = prize;
    }

    public Tournament(List<Race> races, List<Garage> garages, int prize, Garage owner) {
        this.races = races;
        this.garages = garages;
        this.prize = prize;
        this.owner = owner;
        this.isPrivate = true;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

    public List<Garage> getGarages() {
        return garages;
    }

    public void setGarages(List<Garage> garages) {
        this.garages = garages;
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
                Objects.equals(races, that.races) &&
                Objects.equals(garages, that.garages) &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(races, garages, prize, isPrivate, owner);
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "races=" + races +
                ", garages=" + garages +
                ", prize=" + prize +
                ", isPrivate=" + isPrivate +
                ", owner=" + owner +
                '}';
    }
}
