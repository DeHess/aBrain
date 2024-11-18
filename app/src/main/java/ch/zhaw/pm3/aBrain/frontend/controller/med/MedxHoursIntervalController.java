package ch.zhaw.pm3.aBrain.frontend.controller.med;

import ch.zhaw.pm3.aBrain.aBrain;
import ch.zhaw.pm3.aBrain.backend.med.Medication;
import ch.zhaw.pm3.aBrain.backend.med.administration.DailyInterval;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.controller.ScreenController;
import ch.zhaw.pm3.aBrain.frontend.handler.MedDataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MedxHoursIntervalController implements ControlledScreens{
    
    @FXML
    private GridPane root;
    
    @FXML
    private Label screenTitle;
    @FXML
    private TextField intervalInputField;
    
    @FXML
    private TextField startTimeInputField;
   
    
    @FXML
    private TextField amountInputField;
    private ScreenController screenController = new ScreenController();
    
    //TODO: Get MedHandler from Pharmacy and give it to next MedData Class
    private MedDataHandler medDataHandler = new MedDataHandler(MedDataHandler.State.NEW);
    
    public void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }
    
    @FXML
    private void initialize(){
        setScreenController(screenController);
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
        medDataHandler.getMedication().getTreatmentPlan().setAdminInterval(new DailyInterval());
    }
    
    
    //TODO: False format exception. Tell user that he didn't enter the input in the correct format
    public void addTime(){
        DailyInterval dailyInterval = new DailyInterval();
        int amount = Integer.parseInt(amountInputField.getText());
        int interval = Integer.parseInt(intervalInputField.getText());
        String inputTime = startTimeInputField.getText();
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(startTimeInputField.getText(), timeFormatter);
            dailyInterval.addTime(time);
            System.out.println(time.toString());
        }catch (DateTimeParseException e){
            System.err.println(e.getMessage());
        }
    }
    
    @FXML
    private void next(){
        addTime();
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_DATA_START_INTERVAL));
    }
    
}
