package ch.zhaw.pm3.aBrain.frontend.controller.med;

import ch.zhaw.pm3.aBrain.aBrain;
import ch.zhaw.pm3.aBrain.backend.med.Medication;
import ch.zhaw.pm3.aBrain.backend.med.administration.WeekdayInterval;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.controller.ScreenController;
import ch.zhaw.pm3.aBrain.frontend.handler.MedDataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.DayOfWeek;

public class MedWeekdayIntervalController implements ControlledScreens{
    
    @FXML
    private GridPane root;
    
    @FXML
    private Label screenTitle;
    private ScreenController screenController = ScreenController.getInstance();
    
    @FXML
    private VBox weekdayWrapper;
    
    //TODO: Get MedHandler from Pharmacy and give it to next MedData Class
    private MedDataHandler medDataHandler = new MedDataHandler(MedDataHandler.State.NEW);
    
    @FXML
    private void initialize(){
        screenTitle.setText(medDataHandler.getTitle(MedDataHandler.Screen.DAY_TIME));
        
        //TODO: remove this method call and
        setUpMedication();
    }
    
    
    
    /**
     * Go to pharmacy.
     *
     * @param event the event
     */
    @FXML
    private void returnTo(ActionEvent event) {
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.PHARMACY));
    }
    
    //TODO: delete
    public void setUpMedication(){
        medDataHandler.setMedication(new Medication("Hello Kitty"));
    }
    
    
    //TODO: False format exception. Tell user that he didn't enter the input in the correct format
    @FXML
    private void next(){
        WeekdayInterval weekdayInterval = new WeekdayInterval();
        for(Node node: weekdayWrapper.getChildren()){
            CheckBox weekday = (CheckBox) node;
            if(weekday.isSelected()){
                String id = weekday.getId().toUpperCase();
                DayOfWeek day = DayOfWeek.valueOf(id);
                weekdayInterval.addWeekday(day);
                System.out.println(day);
            }
        }
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_ADMIN_INTERVAL));
    }
    
}
