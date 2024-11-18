package ch.zhaw.pm3.aBrain.frontend.controller.med;

import ch.zhaw.pm3.aBrain.aBrain;
import ch.zhaw.pm3.aBrain.backend.med.Medication;
import ch.zhaw.pm3.aBrain.backend.med.TreatmentPlan;
import ch.zhaw.pm3.aBrain.backend.med.administration.DailyInterval;
import ch.zhaw.pm3.aBrain.backend.med.administration.XHoursInterval;
import ch.zhaw.pm3.aBrain.backend.med.administration.SingleEvent;
import ch.zhaw.pm3.aBrain.backend.med.administration.WeekdayInterval;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.controller.ScreenController;
import ch.zhaw.pm3.aBrain.frontend.factory.MedDetailItem;
import ch.zhaw.pm3.aBrain.frontend.handler.MedDataHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MedDataOverviewController implements ControlledScreens {
    
    @FXML
    private GridPane root;
    @FXML
    private Button btnNext;
    @FXML
    private VBox overviewContainer;

    private ScreenController screenController = ScreenController.getInstance();
    private MedDataHandler medicationHandler;

    private List<HBox> detailListItems = new ArrayList<>();

    /**
     * Go to pharmacy.
     *
     * @param event the event
     */


    @FXML
    void initialize() throws FileNotFoundException {
        setMedicationHandler(createDummyMedication());
        createDetailList(medicationHandler);
    }

    public void setMedicationHandler(MedDataHandler medDataHandler){
        this.medicationHandler = medDataHandler;
    }

    private MedDataHandler createDummyMedication(){
        Medication medication = new Medication("Pille grün");
        medication.setName("Pille grün");

        LocalDateTime start = LocalDateTime.parse("2021-01-07 00:00:00");
        LocalDateTime end = LocalDateTime.parse("2022-01-07 23:59:59");

        TreatmentPlan treatmentPlan = new TreatmentPlan(start, end);
    
        DailyInterval dailyInterval = new DailyInterval();

        LocalTime time1 = LocalTime.parse("08:15");
        LocalTime time2 = LocalTime.parse("11:45");
        LocalTime time3 = LocalTime.parse("18:30");
    
        dailyInterval.addTime(time1);
        dailyInterval.addTime(time2);
        dailyInterval.addTime(time3);

        treatmentPlan.setAdminInterval(dailyInterval);

        medication.setTreatmentPlan(treatmentPlan);

        MedDataHandler medDataHandler = new MedDataHandler(MedDataHandler.State.NEW, medication);

        return medDataHandler;
    }

    public void createDetailList(MedDataHandler medDataHandler){

        Medication med = medicationHandler.getMedication();

        detailListItems.add(MedDetailItem.getDetailItem(0,"Name vom Medikament", med.getName(), buttonClickEvent));

        if(med.getTreatmentPlan().getAdminInterval() instanceof DailyInterval){
            detailListItems.add(MedDetailItem.getDetailItem(1,"Intervall", "Täglich", buttonClickEvent));
            DailyInterval dailyInterval = (DailyInterval) med.getTreatmentPlan().getAdminInterval();
            for (int i = 0; i < dailyInterval.getAdminTimes().size(); i++) {
                detailListItems.add(MedDetailItem.getDetailItem(2,i+1 + ". Einnahmezeit", dailyInterval.getAdminTimes().get(i).toString() + " 1 Einheit(en)", buttonClickEvent));
            }
        }else if(med.getTreatmentPlan().getAdminInterval() instanceof XHoursInterval){
            detailListItems.add(MedDetailItem.getDetailItem(-1, "Intervall", "Stündlich", buttonClickEvent));
        }else if(med.getTreatmentPlan().getAdminInterval() instanceof SingleEvent){
            detailListItems.add(MedDetailItem.getDetailItem(-1, "Intervall", "Einmalig", buttonClickEvent));
        }else if(med.getTreatmentPlan().getAdminInterval() instanceof WeekdayInterval){
            detailListItems.add(MedDetailItem.getDetailItem(-1, "Intervall", "Wöchentlich", buttonClickEvent));
        }

        detailListItems.add(MedDetailItem.getDetailItem(3, "Behandlungsstart", med.getTreatmentPlan().getTreatmentStart().toString(), buttonClickEvent));
        detailListItems.add(MedDetailItem.getDetailItem(3, "Behandlungsende", med.getTreatmentPlan().getTreatmentEnd().toString(), buttonClickEvent));

        for (int i = 0; i < detailListItems.size(); i++) {
            overviewContainer.getChildren().add(i, detailListItems.get(i));
        }
        overviewContainer.setSpacing(15);
    }

    private EventHandler<ActionEvent> buttonClickEvent = e -> {
        var x = e;

        int typeOfItem = Integer.parseInt(((Button)e.getSource()).getId());

        switch (typeOfItem) {
            case 0 -> root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_NAME));
            case 1 -> root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_ADMIN_INTERVAL));
            case 2 -> root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_INTAKE_TIME));
            case 3 -> root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_DATA_START_INTERVAL));
        }
    };

    @FXML
    void next(ActionEvent event) {
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.PHARMACY));
    }

    @FXML
    private void returnToPharmacy(ActionEvent event) {
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.PHARMACY));
    }
}
