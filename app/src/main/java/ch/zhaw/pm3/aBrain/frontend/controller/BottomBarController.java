package ch.zhaw.pm3.aBrain.frontend.controller;

import ch.zhaw.pm3.aBrain.aBrain.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class BottomBarController {

    private static ScreenController screenController = ScreenController.getInstance();

    @FXML
    private Button btnCommunity;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnJournal;

    @FXML
    private Button btnPharmacy;

    @FXML
    private Button btnSettings;

    @FXML
    private HBox hBox;

    @FXML
    void goToCommunity(ActionEvent event) {
        hBox.getScene().setRoot(screenController.getScreen(Screen.COMMUNITY));
    }

    @FXML
    void goToHome(ActionEvent event) {
        hBox.getScene().setRoot(screenController.getScreen(Screen.HOME));
    }

    @FXML
    void goToJournal(ActionEvent event) {
        hBox.getScene().setRoot(screenController.getScreen(Screen.JOURNAL));
    }

    @FXML
    void goToPharmacy(ActionEvent event) {
        hBox.getScene().setRoot(screenController.getScreen(Screen.PHARMACY));
    }

    @FXML
    void goToSettings(ActionEvent event) {
        hBox.getScene().setRoot(screenController.getScreen(Screen.SETTINGS));
    }
}
