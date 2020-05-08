package com.github.baaarbz.application;

import com.github.baaarbz.model.Car;
import com.github.baaarbz.model.Garage;
import com.github.baaarbz.model.Race;
import com.github.baaarbz.util.Menu;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.baaarbz.Application.cars;
import static com.github.baaarbz.Application.races;

public class Simulation {

    private static List<Car> carsToCompete;
    private static List<Car> pole;

    public static void init() {
        if (races.isEmpty()) {
            System.console().writer().println("There are not races registered");
        } else {
            display();
            Race race = getRace();
            if (race == null) {
                return;
            }
            getCompetitors(race);
        }
    }

    private static void standard() {
        Collections.shuffle(carsToCompete);
        for (int i = 0; i <= 3; i++) {
            pole.add(carsToCompete.get(i));
        }
    }

    private static void elimination() {
        for (Car ignored : carsToCompete) {
            Collections.shuffle(carsToCompete);
            if (pole.size() > 3) {
                carsToCompete.remove(0);
            } else {
                pole.add(carsToCompete.remove(0));
            }
        }
    }

    private static Race getRace() {
        try {
            int id = Integer.parseInt(System.console().readLine("\t-ID of race: "));
            return races.get(id);
        } catch (NumberFormatException e) {
            System.console().writer().println("Wrong ID or format error\nClosing race simulation");
        }
        return null;
    }

    private static void display() {
        Comparator<Race> comparator = Comparator
                .comparing(Race::isPrivate)
                .thenComparing(Race::getTypeRace);
        if (Menu.askForConfirmation("Do you want the list of races? [y/N] ")) {
            races.stream()
                    .sorted(comparator)
                    .forEach(race -> System.console().writer().println("[" + races.indexOf(race) + "] - " + race));
        }
    }

    private static void getCompetitors(Race race) {
        if (race.isPrivate()) {
            carsToCompete = cars.stream()
                    .filter(car -> car.getGarage().equals(race.getOwner()))
                    .collect(Collectors.toList());
        } else {
            // Group the cars by Garage (Key: Garage - Value: List of cars)
            Map<Garage, List<Car>> carsByGarage = cars.stream()
                    .collect(Collectors.groupingBy(Car::getGarage));
            // Shuffle the values of the map.
            carsByGarage.values().stream()
                    .forEach(Collections::shuffle);
            // Collect to list one element per Key
            carsToCompete = carsByGarage.values().stream()
                    .map(x -> x.stream().findAny().orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
    }
}
