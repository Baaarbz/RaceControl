package com.github.baaarbz.util;

import com.github.baaarbz.application.Backup;
import com.github.baaarbz.application.Import;
import com.github.baaarbz.application.Register;
import com.github.baaarbz.application.Simulation;
import com.github.baaarbz.model.TypeRace;

import static com.github.baaarbz.Application.cars;
import static com.github.baaarbz.Application.tournaments;

public class Menu {

    public static void options(String command) {
        switch (command) {
            case "help":
                display(Messages.HELP);
                break;
            case "new help":
                display(Messages.NEW_HELP);
                break;
            case "new car":
                Register.car();
                break;
            case "new garage":
                Register.garage();
                break;
            case "new race standard":
                Register.race(TypeRace.STANDARD);
                break;
            case "new race elimination":
                Register.race(TypeRace.ELIMINATION);
                break;
            case "new tournament standard":
                Register.tournament(TypeRace.STANDARD);
                break;
            case "new tournament elimination":
                Register.tournament(TypeRace.ELIMINATION);
                break;
            case "import help":
                display(Messages.IMPORT_HELP);
                break;
            case "import cars":
                Import.cars();
                break;
            case "list help":
                display(Messages.LIST_HELP);
                break;
            case "list cars":
                cars.forEach(System.console().writer()::println);
                break;
            case "list files":
                Import.getListFiles().forEach(file -> System.console().writer().println(file.getFileName()));
                break;
            case "list races":
                Simulation.displayRaces();
                break;
            case "list tournaments":
                tournaments.forEach(System.console().writer()::println);
                break;
            case "sim help":
                display(Messages.SIM_HELP);
                break;
            case "sim race":
                Simulation.initRace();
                break;
            case "sim tournament":
                Simulation.initTournament();
                break;
            case "quit":
            case "q":
            case "exit":
            case "e":
                Backup.create();
                System.console().writer().println("Bye!");
                System.exit(0);
                break;
            case "import":
            case "sim":
            case "new":
            case "new race":
            case "new tournament":
            case "list":
                display(Messages.NO_ARGS);
                break;
            default:
                display(Messages.SYNTAX_ERROR);
        }
    }

    private static void display(String[] msg) {
        for (String s : msg) {
            System.console().writer().println(s);
        }
    }

    public static boolean askForConfirmation(String question) {
        String answer;
        do {
            answer = System.console().readLine(question);
        } while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n") && !answer.equalsIgnoreCase(""));

        if (answer.equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }
}
