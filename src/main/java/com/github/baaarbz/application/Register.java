package com.github.baaarbz.application;

import com.github.baaarbz.model.Car;
import com.github.baaarbz.model.Garage;
import com.github.baaarbz.model.Race;
import com.github.baaarbz.model.TypeRace;
import com.github.baaarbz.util.Menu;

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

        if (searchGarage(name) == null) {
            garages.add(new Garage(name));
        } else {
            System.console().writer().println("The garage is already registered");
        }
    }

    public static void race(TypeRace typeRace) {
        boolean isPrivate = Menu.askForConfirmation("Is it a private race? [y/N]");
        String name = System.console().readLine("\t-Name: ");
        if (!isPrivate) {
            races.add(new Race(name, typeRace));
        } else {
            String garage = System.console().readLine("\t-Garage: ");
            if (searchGarage(garage) == null) {
                if (!askForGarage(garage)) {
                    return;
                }
            }
            races.add(new Race(name, searchGarage(garage), TypeRace.STANDARD));
        }
    }

    public static Garage searchGarage(String name) {
        Optional<Garage> garage = garages.stream()
                .filter(g -> name.equals(g.getName()))
                .findFirst();
        if (garage.isPresent()) {
            return garage.get();
        }
        return null;
    }

    public static boolean askForGarage(String garage) {
        boolean answer = Menu.askForConfirmation("The garage: " + garage + " does not exists.\nDo you want to register it? [y/N]");
        if (answer) {
            garages.add(new Garage(garage));
            return true;
        } else {
            System.console().writer().println("Operation cancelled by user...");
            return false;
        }
    }
}
