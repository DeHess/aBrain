package ch.zhaw.pm3.aBrain.frontend.controller.med;

import ch.zhaw.pm3.aBrain.aBrain;
import ch.zhaw.pm3.aBrain.backend.med.Medication;
import ch.zhaw.pm3.aBrain.backend.med.TreatmentPlan;
import ch.zhaw.pm3.aBrain.backend.med.administration.XDaysInterval;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.controller.ScreenController;
import ch.zhaw.pm3.aBrain.frontend.handler.MedDataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.List;

public class MedNameController implements ControlledScreens{
    
    @FXML
    private GridPane root;
    
    @FXML
    private Label screenTitle;
    
    @FXML
    private VBox inputBoxWrapper;
    
    private ComboBox<String> medNameBox;
    private ScreenController screenController = ScreenController.getInstance();
    
    //TODO: Get MedHandler from Pharmacy and give it to next MedData Class
    private MedDataHandler medDataHandler = new MedDataHandler(MedDataHandler.State.NEW);
    
    @FXML
    private void initialize(){
        medDataHandler.setMedication(new Medication("Hello Kitty"));
        medDataHandler.getMedication().setTreatmentPlan(new TreatmentPlan());
        medDataHandler.getMedication().getTreatmentPlan().setAdminInterval(new XDaysInterval());
        System.out.println(medDataHandler.getMedication().getTreatmentPlan().getNameOfIntervalType());
        screenTitle.setText(medDataHandler.getTitle(MedDataHandler.Screen.NAME));
        medNameBox = createAndGetInputNameComboBox();
        inputBoxWrapper.getChildren().add(medNameBox);
    }
    
    private ComboBox<String> createAndGetInputNameComboBox(){
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText("Medikamentname...");
        comboBox.setEditable(true);
        comboBox.setVisibleRowCount(10);
        List<String> medNames = List.of("Elvanse, 20mg", "Elvanse, 30mg", "Concerta, 20mg");
        comboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                    List<String> filteredList = medNames.stream().filter(name -> name.startsWith(newValue)).toList();
                    comboBox.getItems().clear();
                    comboBox.getItems().addAll(filteredList);
                }
                
        );
        return comboBox;
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
    
    private void setMedication(){
        String medName = medNameBox.getEditor().getText();
        medDataHandler.setMedication(new Medication(medName));
    }
    
    @FXML
    private void addReminder(){
        setMedication();
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_ADMIN_INTERVAL));
    }
    
    @FXML
    private void next(){
        setMedication();
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_STOCK));
    }
    
}
