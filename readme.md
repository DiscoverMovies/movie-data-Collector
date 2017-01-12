# Movie Data Collector 
|status|
--------
|[![Build Status](https://travis-ci.org/DiscoverMovies/movie-data-Collector.svg?branch=master)](https://travis-ci.org/DiscoverMovies/movie-data-Collector)|


This is a java commandline application which will collect
the data from **themoviedb.org** and fill it to the provided database.

This will always create a schema by the name `disovermovie`

## Usage

use the parameters:
    
    
    $ java -jar data-collector username password
    
    
Flags :

   * `-h` or `--help` to open help
   * `-R` or `--reset` to reset the database and fill again

## Contributing

### Handling source code

* The project has gradle support to handle dependencies.<br>
  Run the following command: *(if windows then* `gradlew.bat` *)*


       $ gradlew build



* Now create run configurations depending on your IDE. <br>

  > Make sure your running directory has the `src/main/resources` folder in it.
