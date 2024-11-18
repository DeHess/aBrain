package ch.zhaw.pm3.aBrain.backend.datastorage.databaseOperation;

import ch.zhaw.pm3.aBrain.backend.User;
import ch.zhaw.pm3.aBrain.backend.datastorage.DatabaseConnection;
import ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation.DataUserOperation;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

import java.sql.*;

/**
 * This class handles the user operations with the database.
 * This class implements the {@link DataUserOperation} interface.
 *
 * @author wilphi01
 * @version 1.0
 */
public class DatabaseUserOperation implements DataUserOperation {

    private Connection dbConnection = DatabaseConnection.getInstance().getConnection();

    /**
     * This method returns the user with the given id.
     * @param userId is the pk of a row in T_USR_DAT.
     * @return the user as object from the database.
     */
    @Override
    public User getUser(int userId) {
        User user = User.getInstance();

        try {
            String sql = "SELECT * FROM T_USR_DAT WHERE C_ID = ?";
            PreparedStatement select = this.dbConnection.prepareStatement(sql);
            select.setInt(1, userId);
            ResultSet result = select.executeQuery();

            if (result.next()) {
                user.setUser(
                        result.getInt("C_ID"),
                        result.getString("C_FIRST_NAME"),
                        result.getString("C_NAME"),
                        result.getDate("C_BIRTHDAY"),
                        getSex(result.getInt("T_SEX_DAT")),
                        getPostalCode(result.getInt("T_COUNTRY_STATE")),
                        getCity(result.getInt("T_COUNTRY_STATE")),
                        getCountry(result.getInt("T_COUNTRY_STATE")),
                        result.getString("C_EMAIL"),
                        result.getString("C_PASSWORD"),
                        result.getBlob("C_PRO_PIC"),
                        result.getInt("C_POINTS"));
            }

        } catch (SQLException e) {
            // TODO: Logger Warnung
        }

        return user;
    }

    /**
     * This method returns the country of the user.
     * @param locationId is the pk of a row from T_CNT_STA.
     * @return the country of the user.
     */
    private String getCountry(int locationId) {
        String userCountry = "";

        try {
            String sql = "SELECT COUNTRY_NAME FROM T_CNT_DAT WHERE C_ID = (SELECT C_ID_1 FROM T_CNT_STA WHERE C_ID = ?)";
            PreparedStatement select = this.dbConnection.prepareStatement(sql);
            select.setInt(1, locationId);
            ResultSet result = select.executeQuery();

            if (result.next()) {
                userCountry = result.toString();
            }

        } catch (SQLException e) {
            // TODO Logger Warnung
        }

        return userCountry;
    }

    /**
     * This method returns the city of the user.
     * @param locationId is the pk of a row from T_CNT_STA.
     * @return the city of the user.
     */
    private String getCity(int locationId) {
        String userCity = "";

        try {
            String sql = "SELECT STATE_NAME FROM T_STA_DAT WHERE C_ID = (SELECT C_ID_2 FROM T_CNT_STA WHERE C_ID = ?)";
            PreparedStatement select = this.dbConnection.prepareStatement(sql);
            select.setInt(1, locationId);
            ResultSet result = select.executeQuery();

            if (result.next()) {
                userCity = result.toString();
            }

        } catch (SQLException e) {
            // TODO Logger Warnung
        }

        return userCity;
    }

    /**
     * This method returns the postal code of the user.
     * @param locationId is the pk of a row from T_CNT_STA.
     * @return the postal code of the user.
     */
    private String getPostalCode(int locationId) {
        String postalCode = "";

        try {
            String sql = "SELECT C_ZIP_CODE FROM T_CNT_STA WHERE C_ID = ?";
            PreparedStatement select = this.dbConnection.prepareStatement(sql);
            select.setInt(1, locationId);
            ResultSet result = select.executeQuery();

            if (result.next()) {
                postalCode = result.toString();
            }

        } catch (SQLException e) {
            // TODO Logger Warnung
        }

        return postalCode;

    }

    /**
     * This method returns the value of the sex according to the id.
     * @param sexId is the id of the sex.
     * @return the sex as enum.
     */
    private User.Sex getSex(int sexId) {
        User.Sex userSex;

        switch (sexId) {
            case 1 -> userSex = User.Sex.FEMALE;
            case 2 -> userSex = User.Sex.MALE;
            default -> userSex = User.Sex.OTHER;
        }

        return userSex;
    }

    /**
     * This method adds a user to the database.
     * @param user is the user to add.
     * @throws NotImplementedException because this method is not implemented yet.
     */
    @Override
    public void addUser(User user) throws NotImplementedException {
        // This method will be implemented after the beta version.
        throw new NotImplementedException("Not implemented yet");
    }

    /**
     * This method deletes the user from the database.
     * @param user is the user which should be deleted.
     * @throws NotImplementedException because this method is not implemented yet.
     */
    @Override
    public void deleteUser(User user) throws NotImplementedException {
        // This method will be implemented after the beta version.
        throw new NotImplementedException("Not implemented yet");
    }

    /**
     * This method updates the user data in the database.
     * @param user is the user object with the new data.
     * @throws NotImplementedException because this method is not implemented yet.
     */
    @Override
    public void updateUser(User user) throws NotImplementedException {
        // This method will be implemented after the beta version.
        throw new NotImplementedException("Not implemented yet");
    }
}
