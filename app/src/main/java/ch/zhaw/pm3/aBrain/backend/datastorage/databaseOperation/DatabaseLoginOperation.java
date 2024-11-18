package ch.zhaw.pm3.aBrain.backend.datastorage.databaseOperation;

import ch.zhaw.pm3.aBrain.backend.datastorage.DatabaseConnection;
import ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation.DataLoggerOperation.LogType;
import ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation.DataLoginOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles the logger operations with the database.
 * This class implements the {@link DataLoginOperation} interface.
 *
 * @author wilphi01
 * @version 1.0
 */
public class DatabaseLoginOperation implements DataLoginOperation {
    private static final int NO_CONNECTION = 0;
    private static final int PASSWORD_WRONG = 1;
    private static final int CONNECTED = 2;

    private final Connection dbConnection = DatabaseConnection.getInstance().getConnection();
    private final DatabaseLoggerOperation logger = new DatabaseLoggerOperation();

    /**
     * This method checks if the entered login data is on the database.
     * @param email is the entered username.
     * @param password is the entered password.
     * @return a number dependent on the connection, etc.
     */
    @Override
    public int isLoginUser(String email, String password)  {
        PreparedStatement select = null;
        ResultSet result = null;

        String sql = "SELECT * FROM T_USR_DAT WHERE C_EMAIL = ? AND C_PASSWORD = ?";

        try {
            select = this.dbConnection.prepareStatement(sql);
            select.setString(1, email);
            select.setString(2, password);

            result = select.executeQuery();

            if (result.next()) {
                return CONNECTED;
            } else {
                return PASSWORD_WRONG;
            }

        } catch (SQLException e) {
            // TODO extra logger write (no user connected) after beta version
        } finally {
            try {
                if (select != null) {
                    select.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                // TODO extra logger write (no user connected) after beta version
            }
        }
        return NO_CONNECTION;
    }

    @Override
    public int loginId(String email, String password) {
        int loginId = -1;
        PreparedStatement select = null;
        ResultSet result = null;

        try {
            if (isLoginUser(email, password) == CONNECTED) {

                String sql = "SELECT C_ID FROM T_USR_DAT WHERE C_EMAIL = ? AND C_PASSWORD = ?";
                select = this.dbConnection.prepareStatement(sql);
                select.setString(1, email);
                select.setString(2, password);
                result = select.executeQuery();

                if (result.next()) {
                    return result.getInt("C_ID");
                }
            }
        } catch (SQLException e) {
            try {
                if (select != null) {
                    select.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e1) {
                logger.writeLog("Error while closing database connection: " + e1.getMessage(), LogType.ERROR);

            } finally {
                logger.writeLog("Error while getting login id: " + e.getMessage(), LogType.ERROR);
            }
        }

        return loginId;
    }
}
