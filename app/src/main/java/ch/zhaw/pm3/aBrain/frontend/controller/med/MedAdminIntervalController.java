package ch.zhaw.pm3.aBrain.frontend.controller.med;

import ch.zhaw.pm3.aBrain.aBrain;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.controller.ScreenController;
import ch.zhaw.pm3.aBrain.frontend.handler.MedDataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javafx.scene.input.MouseEvent;
public class MedAdminIntervalController implements ControlledScreens {
    @FXML
    private GridPane root;
    
    @FXML
    private Label screenTitle;
    
    private ScreenController screenController = ScreenController.getInstance();
    
    private MedDataHandler medDataHandler;
    
    @FXML
    private void initialize() {
        medDataHandler = new MedDataHandler(MedDataHandler.State.NEW);
        screenTitle.setText(medDataHandler.getTitle(MedDataHandler.Screen.INTERVAL_SELECTION));
    }
    
    /**
     * Go to pharmacy.
     *
     * @param event the event
     */
    @FXML
    private void returnTo(ActionEvent event) {
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_NAME));
    }
    
    @FXML
    private void goToAdminTime(MouseEvent event) {
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_ADMIN_TIME));
        
    }
    
    @FXML
    private void goToWeekdayInterval(MouseEvent event) {
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_WEEKDAY_INTERVAL));
    }
    
    @FXML
    private void goToxDaysInterval(MouseEvent event) {
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_X_DAYS_INTERVAL));
    }
    
    @FXML
    private void goToxHoursInterval(MouseEvent event) {
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_X_HOURS_INTERVAL));
    }
    
    @FXML
    private void goToSingleEvent(MouseEvent event) {
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_SINGULAR_OCCURRENCE));
    }
}
