package com.github.baaarbz.application;

import com.github.baaarbz.model.*;
import com.github.baaarbz.util.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.baaarbz.Application.*;

public class Register {

    public static void car() {
        String brand = System.console().readLine("\t-Brand: ");
        String model = System.console().readLine("\t-Model: ");
        String garage = System.console().readLine("\t-Garage: ");

        if (searchGarage(garage) == null) {
            if (!askForGarage(garage)) {
                return;
            }
        }
        cars.add(new Car(brand, model, searchGarage(garage)));
    }

    public static void garage() {
        String name = System.console().readLine("\t-Name: ");

        // Search the garage, if it is already registered close the operation
        if (searchGarage(name) == null) {
            garages.add(new Garage(name));
        } else {
            System.console().writer().println("The garage is already registered");
        }
    }

    public static void race(TypeRace typeRace) {
        boolean isPrivate = Menu.askForConfirmation("Is it a private race? [y/N] ");
        String name = System.console().readLine("\t-Name: ");
        if (!isPrivate) {
            races.add(new Race(name, typeRace));
        } else {
            String owner = System.console().readLine("\t-Owner: ");
            if (searchGarage(owner) == null) {
                if (!askForGarage(owner)) {
                    return;
                }
            }
            races.add(new Race(name, searchGarage(owner), typeRace));
        }
    }

    public static void tournament(TypeRace typeTournament) {
        if (!checkAvailableRaces(typeTournament)) {
            // If there are not available races close the operation
            System.console().writer().println("There are not registered " + typeTournament.name() + " races for this tournament\nCancelling operation...");
            return;
        }

        boolean isPrivate = Menu.askForConfirmation("Is it a private tournament? [y/N] ");
        String name = System.console().readLine("\t-Name: ");
        int price;
        try {
            price = Integer.parseInt(System.console().readLine("\t-Price: "));
        } catch (NumberFormatException e) {
            System.console().writer().println("Wrong price or format error\nClosing operation...");
            return;
        }
        List<Race> tournamentRaces = new ArrayList<>();
        Tournament tournament;
        if (isPrivate) {
            String owner = System.console().readLine("\t-Owner: ");
            tournament = new Tournament(tournamentRaces, price, searchGarage(owner), name);

            // Show the list of races available for the specified owner and type
            races.stream()
                    .filter(race -> race.getTypeRace().name().equalsIgnoreCase(typeTournament.name()) && race.isPrivate())
                    .filter(race -> race.getOwner().getName().equalsIgnoreCase(owner))
                    .forEach(race -> System.console().writer().println("[" + races.indexOf(race) + "] - " + race));
            races.stream()
                    .filter(race -> !race.isPrivate() && race.getTypeRace().name().equalsIgnoreCase(typeTournament.name()))
                    .forEach(race -> System.console().writer().println("[" + races.indexOf(race) + "] - " + race));

        } else {
            tournament = new Tournament(tournamentRaces, price, name);
            // Show the list of public races for the specified type
            races.stream()
                    .filter(race -> !race.isPrivate() && race.getTypeRace().name().equalsIgnoreCase(typeTournament.name()))
                    .forEach(race -> System.console().writer().println("[" + races.indexOf(race) + "] - " + race));
        }
        // Ask user for 10 races to create the tournament
        System.console().writer().println("Please, select 10 races for the tournament...");
        for (int i = 0; i < 10; i++) {
            tournament.getRaces().add(Simulation.getRace());
        }
        tournaments.add(tournament);
    }

    public static Garage searchGarage(String name) {
        // Search a garage and return it
        Optional<Garage> garage = garages.stream()
                .filter(g -> name.equals(g.getName()))
                .findFirst();
        if (garage.isPresent()) {
            return garage.get();
        }
        return null;
    }

    public static boolean checkAvailableRaces(TypeRace typeRace) {
        // Check if there are registered races of a specific type
        Optional<Race> result = races.stream()
                .filter(race -> race.getTypeRace().equals(typeRace))
                .findFirst();
        return result.isPresent();
    }

    public static boolean askForGarage(String garage) {
        // In case the Garage is not registered ask the user to register it.
        boolean answer = Menu.askForConfirmation("The garage: " + garage + " does not exists.\nDo you want to register it? [y/N] ");
        if (answer) {
            garages.add(new Garage(garage));
            return true;
        } else {
            System.console().writer().println("Operation cancelled by user...");
            return false;
        }
    }
}
