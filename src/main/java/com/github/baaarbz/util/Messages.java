package com.github.baaarbz.util;

public interface Messages {

    String[] SYNTAX_ERROR = {
            "Option not recognized",
            "Write <help> or <[command] help> to get a list of possible options"
    };

    String[] NO_ARGS = {
            "No arguments provided, write <help> or <[command] help> to get a list of possible options"
    };

    String[] HELP = {
            "\t\tRACE CONTROL - HELP:",
            "- new [type] | help -> Create new car, garage...",
            "- quit(q) | exit(e) -> Exit the application",
            "- import [type] | help -> Import data of cars, backups...",
            "- list [type] | help -> List of registered cars, tournaments...",
            "- sim [type] | help -> Simulate one race or tournament"
    };

    String[] NEW_HELP = {
            "\t\tRACE CONTROL - NEW HELP:",
            "- new car -> Create car",
            "- new tournament standard -> Create standard tournament",
            "- new tournament elimination -> Create elimination tournament",
            "- new race standard -> Create standard race",
            "- new race elimination -> Create elimination race",
            "- new garage -> Create garage"
    };

    String[] LIST_HELP = {
            "\t\tRACE CONTROL - LIST HELP:",
            "- list files -> List all valid files",
            "- list cars -> List all registered cars",
            "- list races -> List all registered races",
            "- list tournaments -> List all registered tournaments",
            "- list garages -> List all registered garages",
    };

    String[] IMPORT_HELP = {
            "\t\tRACE CONTROL - NEW HELP:",
            "- import cars -> Import car data from a 'csv' file"
    };

    String[] SIM_HELP = {
            "\t\tRACE CONTROL - NEW SIMULATION:",
            "- sim race -> Simulate one race",
            "- sim tournament -> Simulate one tournament"
    };
}
