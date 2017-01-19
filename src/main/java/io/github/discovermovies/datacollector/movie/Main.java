package io.github.discovermovies.datacollector.movie;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;



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
        try {
            InputStream configInputStream = new FileInputStream(Application.CONFIG_FILE_NAME);
        } catch (IOException e) {
            try {
                File file = new File(Application.CONFIG_FILE_NAME);
                OutputStream os = new FileOutputStream(file);
                InputStream is = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(Application.CONFIG_FILE_NAME);
                byte[] buffer = new byte[is.available()];
                //noinspection ResultOfMethodCallIgnored
                is.read(buffer);
                os.write(buffer);
                os.close();
                is.close();
            } catch (IOException e1) {
                System.err.println("Fatal error: Cannot access/create config File");
                throw new RuntimeException();
            }
        }
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(Application.CONFIG_FILE_NAME));
            if(!properties.containsKey("Key")) {
                System.out.println("API key is not Set.\nPlease input the key:");
                Scanner scanner = new Scanner(System.in);
                String key = scanner.nextLine();
                properties.setProperty("Key", key);
                properties.store(new FileOutputStream(Application.CONFIG_FILE_NAME), null);
            }
        } catch (IOException e) {
            System.err.println("Fatal error: Cannot access/create config File");
            throw new RuntimeException();
        }
        new Application().start(args);
    }
}
