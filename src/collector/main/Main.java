package collector.main;

import collector.main.database.Database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author: Sidhin S Thomas (ParadoxZero)
 * Email : sidhin.thomas@gmail.com
 */
public class Main {
    public static void main(String []args){
        ArrayList<String> flags = new ArrayList<>();
        ArrayList<String> parameters = new ArrayList<>();
        for(String s:args){
            if(s.startsWith("-") || s.startsWith("--")){
                flags.add(s);
            }
            else{
                parameters.add(s);
            }
        }
        for (String s: flags ) {
            if(s.equals("-h") || s.equals("--help")){
                System.out.print("Help\n\n" +
                        "To use the application following parameters are required\n\n" +
                        "1)username\n" +
                        "2)password\n\n");
                System.exit(0);
            }
        }
        if(parameters.size() < 2 ){
            System.err.println("Parameters missing.\n\n try with --help or -h for help\n\n");
            System.exit(1);
        }
        System.out.println("Initializing.....");
        try {
            Database db = new Database("jdbc:mysql://localhost/",parameters.get(0),parameters.get(1));
        } catch (SQLException e) {
            System.exit(10);
        } catch (IOException e) {
            System.exit(11);
        } catch (ClassNotFoundException e) {
            System.exit(12);
        }
    }
}
