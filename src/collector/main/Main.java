package collector.main;

import collector.main.database.Database;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Author: Sidhin S Thomas (ParadoxZero)
 * Email : sidhin.thomas@gmail.com
 */
public class Main {
    public static void main(String []args){
        System.out.println("Initializing.....");
        try {
            Database db = new Database("jdbc:mysql://localhost/","root","root");
        } catch (SQLException e) {
            System.exit(10);
        } catch (IOException e) {
            System.exit(11);
        } catch (ClassNotFoundException e) {
            System.exit(12);
        }
    }
}
