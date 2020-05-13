package com.github.baaarbz.application;

import com.github.baaarbz.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.baaarbz.Application.*;

public class Backup {

    public static void create() {
        JSONObject obj = new JSONObject();

        // Generate array to save cars in JSON
        JSONArray carsJson = new JSONArray();
        for (Car c : cars) {
            JSONObject car = new JSONObject();
            car.put("brand", c.getBrand());
            car.put("model", c.getModel());
            car.put("garage", c.getGarage().toString());
            carsJson.add(cars.indexOf(c), car);
        }

        // Generate array to save races in JSON
        JSONArray racesJson = new JSONArray();
        for (Race r : races) {
            JSONObject race = new JSONObject();
            race.put("name", r.getName());
            race.put("isPrivate", r.isPrivate());
            if (r.isPrivate()) {
                race.put("owner", r.getOwner().toString());
            }
            race.put("type", r.getTypeRace().name());
            racesJson.add(races.indexOf(r), race);
        }

        // Generate array of tournaments
        JSONArray tournamentsJson = new JSONArray();
        for (Tournament t : tournaments) {
            JSONObject tournament = new JSONObject();
            JSONArray tournamentRaces = new JSONArray();
            for (Race r : t.getRaces()) {
                JSONObject object = new JSONObject();
                object.put("name", r.getName());
                object.put("type", r.getTypeRace().name());
                tournamentRaces.add(object);
            }
            tournament.put("races", tournamentRaces);
            tournament.put("price", t.getPrize());
            tournament.put("isPrivate", t.isPrivate());
            if (t.isPrivate()) {
                tournament.put("owner", t.getOwner().toString());
            }
            tournament.put("name", t.getName());
            tournamentsJson.add(tournaments.indexOf(t), tournament);
        }

        // Generate JSON file
        obj.put("backupDate", LocalDateTime.now().toString());
        obj.put("cars", carsJson);
        obj.put("races", racesJson);
        obj.put("tournaments", tournamentsJson);
        save(obj);

    }

    private static void save(JSONObject obj) {
        // Save the JSON file with the backup created
        try (FileWriter fw = new FileWriter(System.getProperty("user.dir") + "/backups/backup.json")) {
            fw.write(obj.toJSONString());
        } catch (IOException e) {
            System.console().writer().println(e.getMessage());
        }
    }

    public static void load() {
        Path path = Import.getFilepath("backup.json");
        if (path == null) {
            return;
        }
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(new FileReader(path.toString()));

            // Load Array of cars
            JSONArray carsJson = (JSONArray) object.get("cars");
            for (Object o : carsJson) {
                JSONObject car = (JSONObject) carsJson.get(carsJson.indexOf(o));
                String garage = car.get("garage").toString();
                String brand = car.get("brand").toString();
                String model = car.get("model").toString();
                if (Register.searchGarage(garage) == null) {
                    garages.add(new Garage(garage));
                }
                cars.add(new Car(brand, model, Register.searchGarage(garage)));
            }

            // Load array of races
            JSONArray racesJson = (JSONArray) object.get("races");
            for (Object o : racesJson) {
                JSONObject race = (JSONObject) racesJson.get(racesJson.indexOf(o));
                TypeRace typeRace;
                if (race.get("type").toString().equalsIgnoreCase("ELIMINATION")) {
                    typeRace = TypeRace.ELIMINATION;
                } else {
                    typeRace = TypeRace.STANDARD;
                }
                String name = race.get("name").toString();
                if (race.get("isPrivate").toString().equalsIgnoreCase("true")) {
                    String owner = race.get("owner").toString();
                    if (Register.searchGarage(owner) == null) {
                        garages.add(new Garage(owner));
                    }
                    races.add(new Race(name, Register.searchGarage(owner), typeRace));
                } else {
                    races.add(new Race(name, typeRace));
                }
            }

            // Load array of tournaments
            JSONArray tournamentsJson = (JSONArray) object.get("tournaments");
            for (Object o : tournamentsJson) {
                List<Race> racesTournament = new ArrayList<>();
                JSONObject tournament = (JSONObject) tournamentsJson.get(tournamentsJson.indexOf(o));
                String name = tournament.get("name").toString();
                int price = Integer.parseInt(tournament.get("price").toString());

                JSONArray jsonArray = (JSONArray) tournament.get("races");
                for (Object raceArray : jsonArray) {
                    JSONObject raceObj = (JSONObject) jsonArray.get(jsonArray.indexOf(raceArray));
                    String raceName = raceObj.get("name").toString();
                    TypeRace typeRace;
                    if (raceObj.get("type").toString().equalsIgnoreCase("ELIMINATION")) {
                        typeRace = TypeRace.ELIMINATION;
                    } else {
                        typeRace = TypeRace.STANDARD;
                    }
                    racesTournament.add(new Race(raceName, typeRace));
                }

                if (tournament.get("isPrivate").toString().equalsIgnoreCase("true")) {
                    String owner = tournament.get("owner").toString();
                    if (Register.searchGarage(owner) == null) {
                        garages.add(new Garage(owner));
                    }
                    tournaments.add(new Tournament(racesTournament, price, Register.searchGarage(owner), name));
                } else {
                    tournaments.add(new Tournament(racesTournament, price, name));
                }

            }
        } catch (FileNotFoundException e) {
            System.console().writer().println("No backup found to load");
        } catch (IOException e) {
            System.console().writer().println(e.getMessage());
        } catch (ParseException e) {
            System.console().writer().println(e.getMessage());
        }
    }
}
