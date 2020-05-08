package com.github.baaarbz;

import com.github.baaarbz.model.Car;
import com.github.baaarbz.model.Garage;
import com.github.baaarbz.model.Race;
import com.github.baaarbz.model.Tournament;
import com.github.baaarbz.util.Menu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static List<Tournament> tournaments;
    public static List<Race> races;
    public static List<Garage> garages;
    public static List<Car> cars;

    static {
        tournaments = new ArrayList<>();
        races = new ArrayList<>();
        garages = new ArrayList<>();
        cars = new ArrayList<>();
        // Create the folders for backups and results.
        new File(System.getProperty("user.dir") + "/backups").mkdir();
        new File(System.getProperty("user.dir") + "/results").mkdir();
    }

    public static void main(String[] args) {
        while (true) {
            Menu.options(System.console().readLine());
        }
    }
}
