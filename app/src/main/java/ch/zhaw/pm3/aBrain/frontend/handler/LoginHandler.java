package ch.zhaw.pm3.aBrain.frontend.handler;

import ch.zhaw.pm3.aBrain.backend.datastorage.databaseOperation.DatabaseLoginOperation;
import ch.zhaw.pm3.aBrain.backend.datastorage.databaseOperation.DatabaseUserOperation;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * This class handles the start screen.
 *
 * @author wilphi01
 * @version 1.0
 */
public class LoginHandler {

    private DatabaseLoginOperation dbLoinOperation = new DatabaseLoginOperation();
    private DatabaseUserOperation dbUserOperation = new DatabaseUserOperation();

    /**
     * This method checks if the entered login data of a user is in the database.
     *
     * @param email is the name which is checked.
     * @param password is hashed for the comparison with the database value.
     * @return true if the login data is matching with the database.
     * @throws SQLException if there is database access error.
     */
    public int isLogin(String email, String password) throws SQLException {
        return dbLoinOperation.isLoginUser(email, encodeMD5(password));
    }

    /**
     * This method hashes a word.
     * The usage for this method is, that the hashed word is compared with an already hashed word.
     *
     * @param word is the word which should be hashed.
     * @return is the hashed value.
     */
    private String encodeMD5(String word) {

        String hashedWord = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(word.getBytes());
            byte[] digest = messageDigest.digest();
            hashedWord = DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashedWord;
    }

    /**
     * This method creates all objects related to the logged-in user.
     */
    public void initUser(int userId) {
        dbUserOperation.getUser(userId);
    }

    public int loginId(String email, String password) {
        return dbLoinOperation.loginId(email, encodeMD5(password));
    }
}
