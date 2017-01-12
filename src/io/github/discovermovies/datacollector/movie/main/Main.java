package io.github.discovermovies.datacollector.movie.main;

import io.github.discovermovies.datacollector.movie.main.database.Database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


/*
*   Copyright (C) 2017 Sidhin S Thomas
*
*   This file is part of data-collector.
*
*   data-collector is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   data-collector is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with data-collector.  If not, see <http://www.gnu.org/licenses/>.
*/

public class Main {
    public static void main(String []args){
        ArrayList<String> flags = new ArrayList<>();
        ArrayList<String> parameters = new ArrayList<>();
        for(String s:args){
            if(s.startsWith("-") || s.startsWith("--")){
                flags.add(s);
            }
            else{
                parameters.add(s);
            }
        }
        for (String s: flags ) {
            if(s.equals("-h") || s.equals("--help")){
                System.out.print("Help\n\n" +
                        "To use the application following parameters are required\n\n" +
                        "1)username\n" +
                        "2)password\n\n");
                System.exit(0);
            }
            if(s.equals("--reset")||s.equals("-R")){

            }
        }
        if(parameters.size() < 2 ){
            System.err.println("Parameters missing.\n\n try with --help or -h for help\n\n");
            System.exit(1);
        }
        System.out.println("Initializing.....");
        try {
            Database db = new Database("jdbc:mysql://localhost/",parameters.get(0),parameters.get(1));
        } catch (SQLException e) {
            System.exit(10);
        } catch (IOException e) {
            System.exit(11);
        } catch (ClassNotFoundException e) {
            System.exit(12);
        }
    }
}
