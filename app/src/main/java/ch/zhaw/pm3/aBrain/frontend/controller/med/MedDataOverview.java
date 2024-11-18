package ch.zhaw.pm3.aBrain.frontend.controller.med;

import ch.zhaw.pm3.aBrain.aBrain;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.controller.ScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MedDataOverview implements ControlledScreens {
    
    @FXML
    private AnchorPane root;
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
    
}
