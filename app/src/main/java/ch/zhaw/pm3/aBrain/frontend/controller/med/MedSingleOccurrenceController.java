package ch.zhaw.pm3.aBrain.frontend.controller.med;

import ch.zhaw.pm3.aBrain.aBrain;
import ch.zhaw.pm3.aBrain.backend.med.Medication;
import ch.zhaw.pm3.aBrain.backend.med.administration.DailyInterval;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.controller.ScreenController;
import ch.zhaw.pm3.aBrain.frontend.handler.MedDataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MedSingleOccurrenceController implements ControlledScreens{
    
    @FXML
    private GridPane root;
    
    @FXML
    private Label screenTitle;
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private TextField timeInputField;
   
    
    @FXML
    private TextField amountInputField;
    private ScreenController screenController = ScreenController.getInstance();
    
    //TODO: Get MedHandler from Pharmacy and give it to next MedData Class
    private MedDataHandler medDataHandler = new MedDataHandler(MedDataHandler.State.NEW);
    
    @FXML
    private void initialize(){
        screenTitle.setText(medDataHandler.getTitle(MedDataHandler.Screen.ONCE));
        datePicker.setPromptText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
        datePicker.setValue(LocalDate.now());
        
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
        LocalDate date;
        if(datePicker.getValue() == null){
            date = LocalDate.now();
        }else{
            date = datePicker.getValue();
        }
        String inputTime = timeInputField.getText();
        if(inputTime == null) inputTime = timeInputField.getPromptText();
        LocalTime time;
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            time = LocalTime.parse(timeInputField.getText(), timeFormatter);
            dailyInterval.addTime(time);
            LocalDateTime dateTime = LocalDateTime.of(date,time);
            System.out.println(dateTime.toString());
        }catch (DateTimeParseException e){
            System.err.println(e.getMessage());
        }
    }
    
    @FXML
    private void next(){
        addTime();
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_STOCK));
    }
    
}
