package com.github.baaarbz.model;

import java.util.Objects;

public class Race {

    private String name;
    private boolean isPrivate;
    private Garage owner;
    private TypeRace typeRace;

    public Race(String name, TypeRace typeRace) {
        this.name = name;
        this.typeRace = typeRace;
        this.isPrivate = false;
    }

    public Race(String name, Garage owner, TypeRace typeRace) {
        this.name = name;
        this.owner = owner;
        this.typeRace = typeRace;
        this.isPrivate = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeRace getTypeRace() {
        return typeRace;
    }

    public void setTypeRace(TypeRace typeRace) {
        this.typeRace = typeRace;
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
        Race race = (Race) o;
        return isPrivate == race.isPrivate &&
                Objects.equals(name, race.name) &&
                Objects.equals(owner, race.owner) &&
                typeRace == race.typeRace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isPrivate, owner, typeRace);
    }

    @Override
    public String toString() {
        if (isPrivate) {
            return "[PRIVATE - " + owner + "] " + name + " | type of race: " + typeRace;
        } else {
            return "[PUBLIC] " + name + " | Type of race: " + typeRace;
        }
    }
}
