# Movie Data Collector

This is a java commandline application which will collect
the data from **themoviedb.org** and fill it to the provided database.

This will always create a schema by the name `**disovermovie**`

## Usage

use the parameters:

    ```
        $ java -jar data-collector username password
    ```

Flags :

    * -h or --help to open help
    * -R or --reset to reset the database and fill again
