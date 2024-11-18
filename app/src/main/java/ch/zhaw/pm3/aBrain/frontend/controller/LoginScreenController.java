package ch.zhaw.pm3.aBrain.frontend.controller;

import ch.zhaw.pm3.aBrain.aBrain.Screen;
import ch.zhaw.pm3.aBrain.backend.User;
import ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation.DataLoggerOperation.LogType;
import ch.zhaw.pm3.aBrain.backend.datastorage.databaseOperation.DatabaseLoggerOperation;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.handler.LoginHandler;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the start screen.
 *
 * @author wilphi01
 * @version 1.0
 */
public class LoginScreenController implements Initializable, ControlledScreens {

    private final LoginHandler loginHandler = new LoginHandler();
    private final DatabaseLoggerOperation logger = new DatabaseLoggerOperation();
    private static final int PASSWORD_WRONG = 1;
    private static final int CONNECTED = 2;
    
    /**
     * The Screen controller.
     */
    ScreenController screenController = ScreenController.getInstance();

    private Transition downToLogin, downToSignUp, upToLogin;
    @FXML
    private ScrollPane rootScrollPane;
    @FXML
    private ImageView downButton;
    @FXML
    private Label signUpLabel;
    @FXML
    private Label loginLabel;
    
    @FXML
    private Button signUpButton;
    @FXML
    private TextField emailLoginField;
    
    @FXML
    private PasswordField passwordLoginPwField;

    @FXML
    private ComboBox sexCombo;

    @FXML
    private DatePicker birthdayDatePicker;

    @FXML
    private TextField emailField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    /**
     * This method initialize the controller for the start page.
     * @param url is the url.
     * @param resourceBundle is the resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // 0 -> 1488 -> 2232 ..
        // TODO make compatible with language changes
        sexCombo.getItems().add("weiblich");
        sexCombo.getItems().add("männlich");
        sexCombo.getItems().add("anderes");

        rootScrollPane.setVvalue(0.0d);
        KeyCombination ctrl_d = KeyCodeCombination.keyCombination("Ctrl + D");
        KeyCombination ctrl_a = KeyCodeCombination.keyCombination("Ctrl + A");
        KeyCombination ctrl_r = KeyCodeCombination.keyCombination("Ctrl + R");

        EventHandler downEventHandler = new EventHandler<Event>(){

            @Override
            public void handle(Event e) {
                if(!(e instanceof KeyEvent) || e instanceof KeyEvent && ctrl_d.match((KeyEvent) e)) {
                    downToLogin.play();
                }

                if(e instanceof KeyEvent && ctrl_r.match((KeyEvent) e)) {
                    downToSignUp.play();
                }

                if(e instanceof KeyEvent && ctrl_a.match((KeyEvent) e)) {
                    upToLogin.play();
                }
            }
        };

        downButton.addEventHandler(MouseEvent.MOUSE_CLICKED, downEventHandler);


        rootScrollPane.setOnKeyPressed(downEventHandler);

        this.downToLogin = new Transition() {
            {
                setCycleDuration(Duration.INDEFINITE);
            }
            @Override
            protected void interpolate(double v) {
                rootScrollPane.setVvalue(rootScrollPane.getVvalue()+0.02);
                if(rootScrollPane.getVvalue() >= 0.479) {
                    downToLogin.stop();
                    rootScrollPane.setVvalue(0.479);
                }
            }
        };

        loginLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>(){

            @Override
            public void handle(Event e) {
                upToLogin.play();
            }
        });


        this.upToLogin = new Transition() {
            {
                setCycleDuration(Duration.INDEFINITE);
            }
            @Override
            protected void interpolate(double v) {
                rootScrollPane.setVvalue(rootScrollPane.getVvalue()-0.02);
                if(rootScrollPane.getVvalue() <= 0.479) {
                    upToLogin.stop();
                    rootScrollPane.setVvalue(0.479);
                }
            }
        };

        signUpLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>(){

            @Override
            public void handle(Event e) {
                if(!(e instanceof KeyEvent) || e instanceof KeyEvent && ctrl_r.match((KeyEvent) e)) {
                    downToSignUp.play();

                }
            }
        });

        this.downToSignUp = new Transition() {
            {
                setCycleDuration(Duration.INDEFINITE);
            }
            @Override
            protected void interpolate(double v) {
                rootScrollPane.setVvalue(rootScrollPane.getVvalue()+0.02);
                if(rootScrollPane.getVvalue() >= 0.958) {
                    downToSignUp.stop();
                    rootScrollPane.setVvalue(0.958);
                }
            }
        };

    }
    
    /**
     * This method processes a user's login after clicking the "Anmelden" button.
     *
     * @param actionEvent is the event that triggers the login.
     */
    @FXML
    public void login(ActionEvent actionEvent) {
        try {
            if (loginHandler.isLogin(this.emailLoginField.getText(), this.passwordLoginPwField.getText()) == PASSWORD_WRONG) {
                // TODO extra logger write (no user connected) after beta version
                System.out.println("Falsche Logindaten");
            }

            if (loginHandler.isLogin(this.emailLoginField.getText(), this.passwordLoginPwField.getText()) == CONNECTED) {
                loginHandler.initUser(loginHandler.loginId(this.emailLoginField.getText(), this.passwordLoginPwField.getText()));
                logger.writeLog("User " + User.getInstance().getEmail() + " logged in.", LogType.INFO);


                rootScrollPane.getScene().setRoot(screenController.getScreen(Screen.HOME));
            }
        } catch (Exception e) {
            // TODO extra logger write (no user connected) after beta version
        }
    }
    
    /**
     * This method processes the sign-up of a user after clicking the "Sign-Up" button.
     *
     * @param actionEvent is the event that triggers the registration.
     */
    @FXML
    public void signUp(ActionEvent actionEvent) throws NotImplementedException {
        // This method will be implemented after the beta version.
        throw new NotImplementedException("Not implemented yet");

    }
}
