package com.github.baaarbz.model;

import java.util.Objects;

public class Garage {

    private String name;

    public Garage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Garage garage = (Garage) o;
        return Objects.equals(name, garage.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
