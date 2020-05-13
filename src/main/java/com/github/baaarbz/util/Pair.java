package com.github.baaarbz.util;

import com.github.baaarbz.model.Car;

import java.util.Objects;

public class Pair {
    private Car key;
    private int value;

    public Pair(Car key, int value) {
        this.key = key;
        this.value = value;
    }

    public Car getKey() {
        return key;
    }

    public void setKey(Car key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return value == pair.value &&
                Objects.equals(key, pair.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return key + " - Points: " + value;
    }
}
