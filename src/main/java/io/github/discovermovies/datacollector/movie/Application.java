package io.github.discovermovies.datacollector.movie;

import io.github.discovermovies.datacollector.movie.database.Database;
import io.github.discovermovies.datacollector.movie.network.TheMovieDbApi;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/*
 *   Copyright (C) 2017 Sidhin S Thomas
 *
 *   This file is part of movie-data-Collector.
 *
 *    movie-data-Collector is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   movie-data-Collector is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with movie-data-Collector.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Application {

    public final static String APPLICATION_NAME = "Movie Data Collector";
    public final static String VERSION = "1.0";
    public final static String COPYRIGHT = "Copyright (C) 2017 Sidhin S Thomas\n" +
            "This is a free software; See source for copying conditions.\n" +
            "There is no warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.\n";

    public static final String CONFIG_FILE_NAME = "app.config";


    private Options options;

    void start(String[] args){
        options = getOptions();
        CommandLineParser parser = new DefaultParser();
        try{
            CommandLine cmd = parser.parse(options,args);
            executeCommand(cmd);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            printHelp();
        }
    }

    private Options getOptions() {
        Options options = new Options();

        //Boolean options
        Option version = new Option("v","version",false,"Output the version and exit");
        Option help = new Option("h","help",false,"Show help");

        //Arguement options
        Option credentials = Option.builder("u")
                .numberOfArgs(2)
                .argName("Username and password")
                .desc("Username followed by password")
                .build();

        Option databse = Option.builder("d")
                .hasArg()
                .argName("Host URL")
                .desc("URL of Mysql server host if it is not localhost")
                .build();

        options.addOption(databse);
        options.addOption(credentials);
        options.addOption(help);
        options.addOption(version);

        return options;
    }

    /*
        These functions makes
     */
    private void startCollectingData(String username, String password){
        startCollectingData(username, password,"localhost");
    }

    private void startCollectingData(String username, String password, String hoststring){
        System.out.println("Initializing.....");
        try {
            Database db = new Database("jdbc:mysql://"+ hoststring +"/",username,password);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException();
        }
        TheMovieDbApi api = new TheMovieDbApi();
        for(int i=0;i<5;++i) {
            try {
                api.getLatestMovie();
                break;
            } catch (IOException e) {
                if(i<5)
                    System.err.println("Retrying: " + i);
                else
                    throw new RuntimeException("Cannot connect to server");

            }
        }
        System.out.println("Successfully executed.\nExiting...");
    }

    private void executeCommand(CommandLine cmd){
        if(cmd.hasOption("v")){
            System.out.println(APPLICATION_NAME + " Version "+VERSION);
            System.out.println(COPYRIGHT);
        }
        if (cmd.hasOption("h")){
            printHelp();
        }
        if (cmd.hasOption("u")){
            String []argList = cmd.getOptionValues("u");
            startCollectingData(argList[0],argList[1]);
        }
        if (cmd.hasOption("d")){
            if(cmd.hasOption("u")){
                String []argList = cmd.getOptionValues("u");
                startCollectingData(argList[0],argList[1],cmd.getOptionValue("d"));
            }
            else{
                System.err.println("Unspecified Credentials.\n use paramenter -u");
                printHelp();
            }
        }

    }


    /*
        This function prints help Text to System.out
     */
    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Movie Data Collector",options);
    }
}
