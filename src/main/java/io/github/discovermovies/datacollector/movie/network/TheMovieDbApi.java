package io.github.discovermovies.datacollector.movie.network;

import io.github.discovermovies.datacollector.movie.Application;
import io.github.discovermovies.datacollector.movie.ConfigValues;
import io.github.discovermovies.datacollector.movie.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.*;

import static io.github.discovermovies.datacollector.movie.Application.properties;

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
public class TheMovieDbApi {
    private static final String URL = "https://api.themoviedb.org/3/movie/";
    private static final String LATEST = "latest";
    private static final String PARAMETERS = "?";
    private static final String API_KEY = "api_key=";

    private CloseableHttpClient client;
    private String apiKey = null;

    public Logger log ;
    public Logger errLog;
    public TheMovieDbApi(){
        client = HttpClients.createDefault();
        apiKey = properties.getProperty("Key");
        apiKey = apiKey==null?"":apiKey;
        String apiLogFileName = properties.getProperty(Application.KEY_LIST[ConfigValues.APT_LOG]);
        String apiErrorLogName = properties.getProperty(Application.KEY_LIST[ConfigValues.API_ERR_LOG]);
        log = new Logger(apiLogFileName,false,false);
        errLog = new Logger(apiErrorLogName,true,true);
    }

    public JSONObject getLatestMovie() throws IOException {
        String output = getResponseFromServer(URL+LATEST+PARAMETERS+API_KEY+apiKey);
        log.log("Output from server: \n" + output);
        return new JSONObject(output);
    }

    public JSONObject getMovie(String id) throws IOException {
        String output = getResponseFromServer(URL+id+PARAMETERS+API_KEY + apiKey);
        log.log("Output from server: \n" + URL+id+PARAMETERS+API_KEY + apiKey +"\n"+output);
        return new JSONObject(output);
    }

    private String getResponseFromServer(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        if(Application.DEBUG) {
            System.out.print("\nDownloading");
        }
        CloseableHttpResponse response = client.execute(request);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (response.getEntity().getContent())));
        request.releaseConnection();
        String line;
        String output = "";
        while ((line = br.readLine()) != null) {
            output += line;
        }
        if(Application.DEBUG) {
            System.out.println();
        }
        return output;

    }


}
