package io.github.discovermovies.datacollector.movie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import static io.github.discovermovies.datacollector.movie.Application.KEY_LIST;
import static io.github.discovermovies.datacollector.movie.Application.properties;



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


/*
 * Exit codes:
 *  0 - success
 *  1 - Cant access/ create config file
 *  15 - commandline error
 *  10 - SQL error
 *  11 - Couldn't read file / access IO
 *  12 - Couldn't find the database driver for mysql
 */
public class Main {


    public static void main(String []args){
        File file = new File(Application.CONFIG_FILE_NAME);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create configuration file.\n"+e.getMessage());
                System.exit(1);
            }
        }
        try {
            properties.load(new FileInputStream(Application.CONFIG_FILE_NAME));
            Scanner scanner = new Scanner(System.in);
            for(String key:KEY_LIST){
                if(!properties.containsKey(key)) {
                    System.out.println(key +" is not Set.\nPlease input the value:");
                    String value = scanner.nextLine();
                    properties.setProperty(key, value);
                }
            }
            properties.store(new FileOutputStream(Application.CONFIG_FILE_NAME), null);
        } catch (IOException e) {
            System.err.println("Fatal error: Cannot access/create config File\n\n"+e.getMessage()+"\n\n");
            System.exit(1);
        }
        new Application().start(args);
    }
}
