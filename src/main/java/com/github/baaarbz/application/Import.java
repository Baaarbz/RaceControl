package com.github.baaarbz.application;

import com.github.baaarbz.model.Car;
import com.github.baaarbz.model.Garage;
import com.github.baaarbz.util.Menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.baaarbz.Application.cars;
import static com.github.baaarbz.Application.garages;

public class Import {

    public static void cars() {
        List<Path> files = getListFiles();
        if (files.isEmpty()) {
            System.console().writer().println("There are not file available");
            return;
        }
        if (Menu.askForConfirmation("Do you want the list of available files? [y/N] ")) {
            files.forEach(file -> System.console().writer().println(file.getFileName()));
        }
        Path path = getFilepath(System.console().readLine("\t-Filename: "));
        if (path == null) {
            System.console().writer().println("File not found");
        } else {
            readCarCsv(path.toString());
        }
    }

    private static void readCarCsv(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String row;
            while ((row = br.readLine()) != null) {
                String[] carRow = row.split(",");
                if (!carRow[0].equalsIgnoreCase("model")) {
                    if (Register.searchGarage(carRow[2]) == null) {
                        garages.add(new Garage(carRow[2]));
                    }
                    cars.add(new Car(carRow[1], carRow[0], Register.searchGarage(carRow[2])));
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Finds all files in the application directory.
     *
     * @return List with files
     */
    public static List<Path> getListFiles() {
        List<Path> files = null;
        try (Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir")))) {
            files = paths
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(".csv") || file.toString().endsWith(".json"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return files;
    }

    /**
     * Return the path of the file with the provided filename.
     *
     * @param filename
     * @return path of file.
     */
    public static Path getFilepath(String filename) {
        Optional<Path> path = null;
        try (Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir")))) {
            path = paths
                    .filter(Files::isRegularFile)
                    .filter(file -> file.endsWith(filename))
                    .findFirst();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (path.isPresent()) {
            return path.get();
        }
        return null;
    }
}
