package io.github.discovermovies.datacollector.movie.database;

import io.github.discovermovies.datacollector.movie.Application;
import io.github.discovermovies.datacollector.movie.ConfigValues;
import io.github.discovermovies.datacollector.movie.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

import static javax.swing.UIManager.getInt;

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
    public Logger log,errLog ;

    public Database(String databaseConnection, String username, String password) throws SQLException, IOException, ClassNotFoundException {
        String logName = Application.properties.getProperty(Application.KEY_LIST[ConfigValues.DATABASE_LOG]);
        String errLogName = Application.properties.getProperty(Application.KEY_LIST[ConfigValues.DATABASE_ERR_LOG]);
        log = new Logger(logName,false,true);
        errLog = new Logger(errLogName,true,true);
        try{
            Class.forName("com.mysql.jdbc.Driver");
            log.log("Connecting to database...");
            connection = DriverManager.getConnection(databaseConnection+"?autoReconnect=true&useSSL=false",username,password);
        } catch (ClassNotFoundException e) {
            System.err.println("Mysql driver not found. Unable to connect");
            throw e;
        } catch (SQLException e) {
            System.err.println("Unable to connect to the database.\nReason:"+e.getMessage());
            throw e;
        }
        connection.setAutoCommit(true);
        log.log("Successfully connected to database.");
        log.log("Creating schema if doesn't exist...");
        ScriptRunner runner = new ScriptRunner(connection, false, true);
        String file = "/SQL/CreateDatabase.sql";
        try {
            runner.runScript(new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file))));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw e;
        }
        log.log("Connection Database Schema established");
        log.log("Database system working .... OK");
    }

    public int getLatestID() throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute(SQL_STATEMENTS.GET_LATEST_ID);
        ResultSet rs = stm.getResultSet();
        if(!rs.first())
            return 0;
        return rs.getInt(1);
    }

    public void insertRecord(JSONObject data){
        try{
            insertMovie(data);
            String mid = data.get("id").toString();
            try {
                for (Object o : data.getJSONArray("genres")) {
                    JSONObject genre = (JSONObject) o;
                    insertGenre(genre.get("id"), genre.get("name").toString().replace("'","\\'"));
                    insertMovieGenre(mid,genre.get("id").toString());
                }
            }catch (JSONException e) {
                System.err.println("No genre record found.");
            }
            try {
                for (Object o : data.getJSONArray("production_companies")) {
                    JSONObject production = (JSONObject) o;
                    insertProduction(production.get("id"), production.get("name").toString().replace("'","\\'"));
                    insertProducedBy(mid, production.get("id").toString());
                }
            }catch (JSONException e) {
                System.err.println("No Production record found.");
            }
            try {
                JSONObject object = data.getJSONObject("belongs_to_collection");
                insertCollection(object.get("id"),object.get("name"),object.get("poster_path"));
            }catch (JSONException e) {
                System.err.println("No collection record found.");
            }
        }
        catch (JSONException e){

        }
    }

    public void insertMovie(JSONObject data) {
        try {
            String id = data.get("id") + ",";
            String imdbid = "'" + data.get("imdb_id") + "',";
            String title = "'" + data.get("title").toString().replace("'","\\'") +"',";
            String original_title = "'" + data.get("original_title").toString().replace("'","\\'") + "',";
            String collection ;
            try {
                JSONObject object = data.getJSONObject("belongs_to_collection");
                collection = object.get("id").toString()+",";
            }catch (JSONException e) {
                collection = "NULL,";
            }
            String languages = "'";
            for (Object o : data.getJSONArray("spoken_languages")) {
                JSONObject object = (JSONObject) o;
                languages += object.get("iso_639_1")+",";
            }
            languages += "',";
            String overview = "'" + data.get("overview").toString().replace("'","\\'") + "',";
            String popularity = data.get("popularity") + ",";
            String poster_url = "'" + data.get("poster_path") + "',";
            String release = "'" + data.get("release_date") + "',";
            String runtime = data.get("runtime") + ",";
            String vote_avg = data.get("vote_average") + ",";
            String vote_count = data.get("vote_count") + ",";
            String tagline = "'" + data.get("tagline").toString().replace("'","\\'") + "'";
            String statement = SQL_STATEMENTS.INSERT_MOVIE + id + imdbid + title + original_title + languages + overview
                    + popularity + poster_url + release + runtime + vote_avg + vote_count +collection+ tagline
                    + SQL_STATEMENTS.END;
            executeStatement(statement);
        } catch(JSONException e){
            System.err.println("Valid movie entry not found.");
        }
    }

    public void insertCrew(String id, String name, String deptid) {
        executeStatement(SQL_STATEMENTS.INSERT_CREW + id +",'"+name+"'" +
                ","+deptid+ SQL_STATEMENTS.END);

    }

    public void insertActor(String id,String name) {
        executeStatement(SQL_STATEMENTS.INSERT_ACTORS + id +",'"+name+"'" +
                SQL_STATEMENTS.END);
    }

    public void insertGenre(Object id, Object name){
        executeStatement(SQL_STATEMENTS.INSERT_GENRE + id +",'"+name+"'" +
                SQL_STATEMENTS.END);
    }

    public void insertCollection(Object id, Object name, Object poster) {
        executeStatement(SQL_STATEMENTS.INSERT_COLLECTIONS + id +",'"+name+"','" +
                poster + "'" + SQL_STATEMENTS.END);
    }

    public void insertProduction(Object id, Object name){
        executeStatement(SQL_STATEMENTS.INSERT_PRODUCTION_COMPANIES + id +",'"+name+"'" +
                SQL_STATEMENTS.END);
    }

    public void insertDepartment(String id,String name){
        executeStatement(SQL_STATEMENTS.INSERT_DEPARTMENT + id +",'"+name+"'" +
                SQL_STATEMENTS.END);
    }

    private void insertWorkedOn(String mid, String objectid){
        executeStatement(SQL_STATEMENTS.INSERT_WORKED_ON + mid +"," + objectid + SQL_STATEMENTS.END);
    }

    private void insertApearsOn(String mid, String objectid,String character){
        executeStatement(SQL_STATEMENTS.INSERT_APEARS_ON + mid +"," + objectid +
                ",'" + character + "'" + SQL_STATEMENTS.END);
    }

    private void insertProducedBy(String mid, String objectid){
        executeStatement(SQL_STATEMENTS.INSERT_PRODUCED_BY + mid +"," + objectid + SQL_STATEMENTS.END);
    }

    private void insertMovieGenre(String mid, String objectid){
        executeStatement(SQL_STATEMENTS.INSERT_MOVIE_GENRE + mid +"," + objectid + SQL_STATEMENTS.END);
    }

    private void executeStatement(String statement) {
        log.log(statement);
        try {
            connection.createStatement().execute(statement);
        } catch (SQLException e) {
            errLog.log("Error: "+statement +":\n"+e.getMessage()+"\n");
        }
    }

    private final class SQL_STATEMENTS{

        static final String END = ")" ;

        /* Statements to insert into Table */
        static final String INSERT_MOVIE = "INSERT  INTO movie(id,imdbid,title,original_title," +
                "language,overview,popularity,poster_url,release_date,runtime,vote_avg,vote_count,collection_id," +
                "tagline) VALUES( " ;
        static final String INSERT_GENRE = "INSERT  INTO genre(id,name) VALUES( ";
        static final String INSERT_COLLECTIONS = "INSERT  INTO collections(id,name,poster_url) VALUES( ";
        static final String INSERT_PRODUCTION_COMPANIES = "INSERT  INTO production_companies(id,name) VALUES( ";
        static final String INSERT_DEPARTMENT = "INSERT  INTO department(id,name) VALUES( ";
        static final String INSERT_CREW = "INSERT  INTO crew(id,name,deptid) VALUES( ";
        static final String INSERT_ACTORS = "INSERT  INTO actors(id,name) VALUES( ";


        static final String INSERT_MOVIE_GENRE = "INSERT INTO movie_genre(mid,gid) VALUES( ";
        static final String INSERT_PRODUCED_BY = "INSERT INTO produced_by(mid,pid) VALUES( ";
        static final String INSERT_APEARS_ON = "INSERT INTO appears_on(mid,aid,character_name) VALUES( ";
        static final String INSERT_WORKED_ON = "INSERT INTO worked_on(mid,cid) VALUES( ";

        /* SELECT STATEMENTS */
        static final String GET_LATEST_ID = "SELECT max(id) FROM movie";

        private SQL_STATEMENTS(){};

    }
}
