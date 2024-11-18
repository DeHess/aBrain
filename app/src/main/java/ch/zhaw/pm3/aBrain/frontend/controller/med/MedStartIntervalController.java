package ch.zhaw.pm3.aBrain.frontend.controller.med;

import ch.zhaw.pm3.aBrain.aBrain;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.controller.ScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.time.LocalDate;


public class MedStartIntervalController implements ControlledScreens {

    private ScreenController screenController = ScreenController.getInstance();
    
    /**
     * Go to pharmacy.
     *
     * @param event the event
     */
    @FXML
    private void returnToPharmacy(ActionEvent event) {
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.PHARMACY));
    }


    @FXML
    void initialize() throws FileNotFoundException {
        dpStart.setValue(LocalDate.now());
        endDateContainer.setVisible(false);
        screenTitle.setText("Neues Medikament");
        lblErrorMessage.setVisible(false);

    }


    @FXML
    private Label screenTitle;

    @FXML
    private DatePicker dpStart;

    @FXML
    private GridPane root;

    @FXML
    private Button btnNext;

    @FXML
    private DatePicker dpEnd;

    @FXML
    private CheckBox cbHasEndDate;

    @FXML
    private VBox endDateContainer;

    @FXML
    private Label lblErrorMessage;


    @FXML
    void returnTo(ActionEvent event) {

    }

    @FXML
    void hasEndDateChanged(ActionEvent event) {
        if(cbHasEndDate.isSelected()){
            endDateContainer.setVisible(true);
        } else {
            endDateContainer.setVisible(false);
        }
    }


    @FXML
    void datePickerEndDateChanged(ActionEvent event) {
        if(dpStart.getValue().isBefore(dpEnd.getValue())){
            lblErrorMessage.setVisible(false);
            btnNext.setDisable(false);
        }
        else{
            lblErrorMessage.setVisible(true);
            btnNext.setDisable(true);
        }
    }

    @FXML
    void next(ActionEvent event) {
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_STOCK));
    }
}
