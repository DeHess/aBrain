package ch.zhaw.pm3.aBrain.backend.datastorage.databaseOperation;

import ch.zhaw.pm3.aBrain.backend.User;
import ch.zhaw.pm3.aBrain.backend.datastorage.DatabaseConnection;
import ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation.DataLoggerOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * This class handles the logger operations with the database.
 * This class implements the {@link DataLoggerOperation} interface.
 *
 * @author wilphi01
 * @version 1.0
 */
public class DatabaseLoggerOperation implements DataLoggerOperation {

    private final Connection dbConnection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void writeLog(String log, LogType logType) {
        String sql = "INSERT INTO T_LOG_DAT (C_LEVEL, C_MESSAGE, T_USR_ID) VALUES (?, ?, ?)";
        PreparedStatement insert = null;

        try {
            insert = this.dbConnection.prepareStatement(sql);
            insert.setInt(1, logType.getId());
            insert.setString(2, log);
            insert.setInt(3, User.getInstance().getUserId());
            insert.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error while writing log to database: " + e.getMessage());
        } finally {
            try {
                if (insert != null) {
                    insert.close();
                }

            } catch (Exception e) {
                System.err.println("Error while closing database connection: " + e.getMessage());
            }
        }

    }

}
