# Movie Data Collector 
|Build status| Code Grade|
|-------------|-------- |
|[![Build Status](https://travis-ci.org/DiscoverMovies/movie-data-Collector.svg?branch=master)](https://travis-ci.org/DiscoverMovies/movie-data-Collector) | [![Codacy Badge](https://api.codacy.com/project/badge/Grade/f7f50ae234074097966a64e32600eba1)](https://www.codacy.com/app/ParadoxZero/movie-data-Collector?utm_source=github.com&utm_medium=referral&utm_content=DiscoverMovies/movie-data-Collector&utm_campaign=badger) |


This is a java commandline application which will collect
the data from **themoviedb.org** and fill it to the provided database.

This will always create a schema by the name `disovermovie`

## Usage

    usage: Movie-Data-Collector
     -d <Host URL>                URL of Mysql server host if it is not
                                  localhost
     -h,--help                    Show help
     -u <Username and password>   Username followed by password
     -v,--version                 Output the version and exit    
     
####Example
    
    
    $ movie-data-collector -v
      Movie Data Collector Version 1.0
      Copyright (C) 2017 Sidhin S Thomas
      This is a free software; See source for copying conditions.
      There is no warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    
    $ movie-data-collector -u root root
        Initializing.....
        Connecting to database...
        Successfully connected to database.
        Creating schema if doesn't exist...
        Connection Database Schema established
        Database system working .... OK
        Successfully executed.
        Exiting...
    
    $ movie-data-collector --help
        usage: Movie-Data-Collector
         -d <Host URL>                URL of Mysql server host if it is not
                                      localhost
         -h,--help                    Show help
         -u <Username and password>   Username followed by password
         -v,--version                 Output the version and exit  


## Contributing

### Handling source code

* The project has gradle support to handle dependencies.<br>
  Run the following command: *(if windows then* `gradlew.bat` *)*


       $ gradlew build



* Now create run configurations depending on your IDE. <br>

  > Make sure your running directory has the `src/main/resources` folder in it.
