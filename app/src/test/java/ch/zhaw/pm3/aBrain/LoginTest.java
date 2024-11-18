package ch.zhaw.pm3.aBrain;

import ch.zhaw.pm3.aBrain.frontend.handler.LoginHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the various login functions.
 * @author wilphi01
 * @version 1.0
 */
public class LoginTest {

    private static final String USERNAME = "admin@aBrain.ch";
    private static final String PASSWORD = "Passwort123";

    private static final int NO_CONNECTION = 0;
    private static final int PASSWORD_WRONG = 1;
    private static final int CONNECTED = 2;

    private final LoginHandler loginHandler = new LoginHandler();

    @Test
    void testLogin() {
        try {
            assertEquals(CONNECTED, loginHandler.isLogin(USERNAME, PASSWORD));
            assertEquals(PASSWORD_WRONG, loginHandler.isLogin(PASSWORD, USERNAME));
            assertEquals(CONNECTED, loginHandler.isLogin(USERNAME.toUpperCase(), PASSWORD));
            assertEquals(CONNECTED, loginHandler.isLogin(USERNAME.toLowerCase(), PASSWORD));
            assertEquals(PASSWORD_WRONG, loginHandler.isLogin(USERNAME, PASSWORD.toUpperCase()));
            assertEquals(PASSWORD_WRONG, loginHandler.isLogin("Joey@Friends.com", PASSWORD));
            assertEquals(PASSWORD_WRONG, loginHandler.isLogin("Chandler@Friends.com", "MonicaG2004"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
