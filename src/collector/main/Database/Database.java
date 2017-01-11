package collector.main.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Author: Sidhin S Thomas (ParadoxZero)
 * Email : sidhin.thomas@gmail.com
 */


public class Database {
    private  Connection connection;

    Database(String databaseConnection, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(databaseConnection,username,password);
    }

}
