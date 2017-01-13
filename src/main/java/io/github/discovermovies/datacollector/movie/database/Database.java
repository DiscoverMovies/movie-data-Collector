package io.github.discovermovies.datacollector.movie.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

public class Database {
    private  Connection connection;

    private final class SQL_STATEMENTS{
        private SQL_STATEMENTS(){};

        public static final String INSERT_STATEMENT_END = ")";


        /* Statements to insert into Table */
        public static final String INSERT_MOVIE = "INSERT INTO MOVIES(id,imdbid,title,original_title,collection_id," +
                "language,overview,popularity,poster_url,release_date,runtime,vote_avg,vote_count,tagline) VALUES( " ;
        public static final String INSERT_GENRE = "INSERT INTO GENRE(id,name) VALUES( ";
        public static final String INSERT_COLLECTIONS = "INSERT INTO COLLECTIONS(id,name) VALUES( ";
        public static final String INSERT_PRODUCTION_COMPANIES = "INSERT INTO PRODUCTION_COMPANIES(id,name) VALUES( ";
        public static final String INSERT_DEPARTMENT = "INSERT INTO DEPARTMENT(id,name) VALUES( ";
        public static final String INSERT_CREW = "INSERT INTO CREW(id,name,deptid) VALUES( ";
        public static final String INSERT_ACTORS = "INSERT INTO ACTORS(id,name) VALUES( ";

    }

    public Database(String databaseConnection, String username, String password) throws SQLException, IOException, ClassNotFoundException {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(databaseConnection,username,password);
        } catch (ClassNotFoundException e) {
            System.err.println("Mysql driver not found. Unable to connect");
            throw e;
        } catch (SQLException e) {
            System.err.println("Unable to connect to the database.\nReason:"+e.getMessage());
            throw e;
        }

        System.out.println("Successfully connected to database.");
        System.out.println("Creating schema if doesn't exist...");
        ScriptRunner runner = new ScriptRunner(connection, false, false);
        String file = "/SQL/CreateDatabase.sql";
        try {
            runner.runScript(new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file))));
        } catch (IOException e) {
            System.err.println("Couldn't locate the necessary file: " + file);
            throw e;
        }
        System.out.println("Connection Database Schema established");
        System.out.println("Database system working .... OK");
    }

    public void insertMovie(){

    }
}
