package io.github.discovermovies.datacollector.movie;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

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
public class Logger {
    private PrintWriter logWriter ;
    private PrintWriter stdWriter;

    private Boolean printToStdOut ;


    public Logger(Boolean isError){
        if(isError) {
            logWriter = new PrintWriter(System.err);
        }
        else{
            logWriter = new PrintWriter(System.out);
        }
        printToStdOut = false;
    }
    public Logger(String logFileName, Boolean isError, Boolean printToStdOut){
        if(isError) {
            stdWriter = new PrintWriter(System.err);
        }
        else{
            stdWriter = new PrintWriter(System.out);
        }
        File logFile = new File(logFileName);
        try {
            if (logFile.exists()) {
                logWriter = new PrintWriter(new FileWriter(logFile, true));
            } else {
                logWriter = new PrintWriter(new FileWriter(logFile, false));
            }
        } catch(IOException e){
            System.err.println("Unable to access or create the log file: " + logFileName);
        }
        String timeStamp = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss").format(new java.util.Date());
        this.printToStdOut = false;
        log("\n===================\n=== " + timeStamp + "\n===================\n");
        this.printToStdOut = printToStdOut;
    }

    public void log(String message){
        if(printToStdOut){
            stdWriter.println(message);
            stdWriter.flush();
        }
        logWriter.println(message);
        logWriter.println();
        logWriter.flush();
    }

}
