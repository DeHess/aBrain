package ch.zhaw.pm3.aBrain.backend.datastorage;

import ch.zhaw.pm3.aBrain.backend.configuration.ServerConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class handles the connection between the user and the database.
 *
 * @author wilphi01
 * @version 1.0
 */
public class DatabaseConnection {

    private static final Integer WAIT_TIME = 3;
    private static final DatabaseConnection instance = new DatabaseConnection();

    private final String url;
    private final String user;
    private final String password;


    /**
     * Creates a database connection with the current config file parameters.
     */
    protected DatabaseConnection() {
        url = "jdbc:mysql://" + ServerConfig.DEFAULT_SERVER_ADDRESS.getValue() + ":"
                + ServerConfig.DEFAULT_SERVER_PORT.getValue() + "/"
                + ServerConfig.DEFAULT_DATABASE_NAME.getValue()
                + "?serverTimezone=" + ServerConfig.DEFAULT_DATABASE_TIMEZONE.getValue();
        user = ServerConfig.DEFAULT_DATABASE_USER.getValue();
        password = ServerConfig.DEFAULT_DATABASE_USER_PASSWORD.getValue();
    }

    /**
     * This method returns the current connection.
     *
     * @return the connection.
     */
    public Connection getConnection() {
        Connection connection;
        try {
            DriverManager.setLoginTimeout(WAIT_TIME);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            connection = null;
        }
        return connection;
    }

    /**
     * This method checks if the user is connected to the database.
     *
     * @return true if, the user is connected with the database.
     */
    public boolean isDatabaseConnected() {
        return getConnection() != null;
    }

    /**
     * This method gets the instance of the Database Connection
     *
     * @return the instance
     */
    public static DatabaseConnection getInstance() {
        return instance;
    }

}