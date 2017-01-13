package io.github.discovermovies.datacollector.movie;

import io.github.discovermovies.datacollector.movie.database.Database;
import io.github.discovermovies.datacollector.movie.Application;
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


/*
 * Exit codes:
 *  0 - success
 *  1 -
 *  15 - commandline error
 *  10 - SQL error
 *  11 - Couldn't read file / access IO
 *  12 - Couldn't find the database driver for mysql
 */
public class Main {
    public static void main(String []args){
        new Application().start(args);
    }
}
