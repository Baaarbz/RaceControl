package com.github.baaarbz.application;

import com.github.baaarbz.model.Car;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class Export {

    public static void saveResult(List<?> result, Object event) {
        File file = new File(System.getProperty("user.dir") + "/results/results" + System.currentTimeMillis() + ".csv");
        try {
            FileWriter fw = new FileWriter(file);
            CSVWriter writer = new CSVWriter(fw);

            String[] header = {"event", "first position", "second position", "third position", "date event"};
            writer.writeNext(header);

            String[] row = {event.toString(), result.get(0).toString(), result.get(1).toString(), result.get(2).toString(), LocalDateTime.now().toString()};
            writer.writeNext(row);
            writer.close();
        } catch (IOException e) {
            System.console().writer().println(e.getMessage());
        }
    }
}
