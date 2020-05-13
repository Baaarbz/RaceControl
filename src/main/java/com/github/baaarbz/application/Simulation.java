package com.github.baaarbz.application;

import com.github.baaarbz.model.*;
import com.github.baaarbz.util.Menu;
import com.github.baaarbz.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.baaarbz.Application.*;

public class Simulation {

    private static List<Car> carsToCompete;
    private static List<Car> pole;

    public static void initRace() {
        // If there are not races to simulate close simulation
        if (races.isEmpty()) {
            System.console().writer().println("There are not races registered");
            return;
        }
        // Asks if the user wants to see a list of available races.
        if (Menu.askForConfirmation("Do you want the list of races? [y/N] ")) {
            displayRaces();
        }
        // Get the race to simulate
        Race race = getRace();
        if (race == null) {
            return;
        }
        // Simulate the race
        getCompetitors(race.isPrivate(), race.getOwner());
        if (race.getTypeRace() == TypeRace.ELIMINATION) {
            elimination();
        } else {
            standard();
        }
        // Export results
        Export.saveResult(pole, race);
    }

    public static void initTournament() {
        // If there are not tournaments to simulate, close simulation.
        if (tournaments.isEmpty()) {
            System.console().writer().println("There are not tournaments registered");
            return;
        }
        // Asks if the user wants to see a list of available tournaments.
        if (Menu.askForConfirmation("Do you want the list of tournaments? [y/N] ")) {
            displayTournaments();
        }
        // Gets the tournament to simulate
        Tournament tournament = getTournament();
        if (tournament == null) {
            return;
        }
        // Get competitors and simulate the tournament to get the standings.
        getCompetitors(tournament.isPrivate(), tournament.getOwner());
        List<Pair> standings = getTournamentStandings(tournament);
        // Export results
        Export.saveResult(standings.subList(0, 3), tournament);
    }

    private static void standard() {
        pole = new ArrayList<>();
        Collections.shuffle(carsToCompete);
        for (int i = 0; i < 3; i++) {
            pole.add(carsToCompete.get(i));
        }
    }

    private static void elimination() {
        pole = new ArrayList<>();
        List<Car> temp = new ArrayList<>(carsToCompete);
        for (Car ignored : carsToCompete) {
            Collections.shuffle(temp);
            if (temp.size() > 3) {
                temp.remove(0);
            } else {
                pole.add(0, temp.remove(0));
            }
        }
    }

    private static void getCompetitors(boolean isPrivate, Garage owner) {
        carsToCompete = new ArrayList<>();

        if (isPrivate) {
            // Get all the cars of specified garage.
            carsToCompete = cars.stream()
                    .filter(car -> car.getGarage().equals(owner))
                    .collect(Collectors.toList());
        } else {
            // Group the cars by Garage (Key: Garage - Value: List of cars)
            Map<Garage, List<Car>> carsByGarage = cars.stream()
                    .collect(Collectors.groupingBy(Car::getGarage));
            // Shuffle the values of the map to get a random car per garage.
            carsByGarage.values().stream()
                    .forEach(Collections::shuffle);
            // Collect to list one element per Key
            carsToCompete = carsByGarage.values().stream()
                    .map(x -> x.stream().findAny().orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
    }

    public static Race getRace() {
        // Ask user to select a race
        try {
            int id = Integer.parseInt(System.console().readLine("\t-ID of race: "));
            return races.get(id);
        } catch (NumberFormatException e) {
            System.console().writer().println("Wrong ID or format error");
        } catch (IndexOutOfBoundsException e) {
            System.console().writer().println("The selected race does not exist.");
        }
        return null;
    }

    public static Tournament getTournament() {
        // ASk user to select a tournament
        try {
            int id = Integer.parseInt(System.console().readLine("\t-ID of tournament: "));
            return tournaments.get(id);
        } catch (NumberFormatException e) {
            System.console().writer().println("Wrong ID or format error");
        } catch (IndexOutOfBoundsException e) {
            System.console().writer().println("The selected race does not exist.");
        }
        return null;
    }

    public static List<Pair> getTournamentStandings(Tournament tournament) {
        // Simulates races of tournament, Map<Car, Points> to store the points of each car
        Map<Car, Integer> standings = new HashMap<>();
        for (Race race : tournament.getRaces()) {
            if (race.getTypeRace().name().equalsIgnoreCase("ELIMINATION")) {
                elimination();
            } else {
                standard();
            }
            int firstPosition, secondPosition, thirdPosition;

            if (standings.get(pole.get(0)) == null) {
                firstPosition = 10;
            } else {
                firstPosition = standings.get(pole.get(0)) + 10;
            }

            if (standings.get(pole.get(1)) == null) {
                secondPosition = 8;
            } else {
                secondPosition = standings.get(pole.get(1)) + 8;
            }

            if (standings.get(pole.get(2)) == null) {
                thirdPosition = 6;
            } else {
                thirdPosition = standings.get(pole.get(2)) + 6;
            }

            standings.put(pole.get(0), firstPosition);
            standings.put(pole.get(1), secondPosition);
            standings.put(pole.get(2), thirdPosition);
        }

        // Collect the values of the map to a List
        List<Pair> orderedStanding = new ArrayList<>();
        for (Map.Entry<Car, Integer> car : standings.entrySet()) {
            orderedStanding.add(new Pair(car.getKey(), car.getValue()));
        }
        // Sort the standing of the cars
        orderedStanding.sort(Comparator.comparingInt(Pair::getValue));
        Collections.reverse(orderedStanding);
        return orderedStanding;
    }

    public static void displayRaces() {
        Comparator<Race> comparator = Comparator
                .comparing(Race::isPrivate)
                .thenComparing(Race::getTypeRace);
        races.stream()
                .sorted(comparator)
                .forEach(race -> System.console().writer().println("[" + races.indexOf(race) + "] - " + race));

    }

    public static void displayTournaments() {
        tournaments.stream()
                .sorted(Comparator.comparing(Tournament::isPrivate))
                .forEach(tournament -> System.console().writer().println("[" + tournaments.indexOf(tournament) + "] - " + tournament));
    }
}
