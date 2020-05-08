package com.github.baaarbz.application;

import com.github.baaarbz.model.Car;
import com.github.baaarbz.model.Garage;
import com.github.baaarbz.model.Race;
import com.github.baaarbz.model.TypeRace;
import com.github.baaarbz.util.Menu;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.baaarbz.Application.cars;
import static com.github.baaarbz.Application.races;

public class Simulation {

    private static List<Car> carsToCompete;
    private static List<Car> pole;

    public static void initRace() {
        if (races.isEmpty()) {
            System.console().writer().println("There are not races registered");
        } else {
            if (Menu.askForConfirmation("Do you want the list of races? [y/N] ")) {
                displayRaces();
            }
            Race race = getRace();
            if (race == null) {
                return;
            }
            getCompetitors(race);
            pole = new ArrayList<>();
            if (race.getTypeRace() == TypeRace.ELIMINATION) {
                elimination();
            } else {
                standard();
            }
            displayWinners();
        }
    }

    private static void standard() {
        Collections.shuffle(carsToCompete);
        for (int i = 0; i < 3; i++) {
            pole.add(carsToCompete.get(i));
        }
    }

    private static void elimination() {
        List<Car> temp = new ArrayList<>(carsToCompete);
        for (Car ignored : carsToCompete) {
            Collections.shuffle(temp);
            if (temp.size() > 3) {
                temp.remove(0);
            } else {
                pole.add(temp.remove(0));
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

    public static void displayRaces() {
        Comparator<Race> comparator = Comparator
                .comparing(Race::isPrivate)
                .thenComparing(Race::getTypeRace);
        races.stream()
                .sorted(comparator)
                .forEach(race -> System.console().writer().println("[" + races.indexOf(race) + "] - " + race));

    }

    private static void displayWinners() {
        pole.forEach(System.console().writer()::println);
    }

    private static void getCompetitors(Race race) {
        carsToCompete = new ArrayList<>();

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
