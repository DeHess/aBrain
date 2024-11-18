package ch.zhaw.pm3.aBrain.frontend.controller;

import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class JournalScreenController implements ControlledScreens {
    
    @FXML
    private Button gamificationButton;
    
    @FXML
    private Button reportButton;
    
    @FXML
    private HBox infoContainer;
    
    @FXML
    private VBox journalEntryContainer;
    
    @FXML
    void initialize() {
    
    }
    
    
    @FXML
    void goToGamificationTrophies(ActionEvent event) {
    
    }
    
    @FXML
    void goToReportTypeSelection(ActionEvent event) {
    
    }
    
    @FXML
    void goToMoodEntries(ActionEvent event) {
    
    }
    
    @FXML
    void goToSideEffectEntries(ActionEvent event) {
    
    }
    
    @FXML
    void goToMedicationIntakeEntries(ActionEvent event) {
    
    }
    
    @FXML
    void goToSportsEntries(ActionEvent event) {
    
    }
}


