package ch.zhaw.pm3.aBrain.frontend.controller.med;

import ch.zhaw.pm3.aBrain.aBrain;
import ch.zhaw.pm3.aBrain.backend.med.Medication;
import ch.zhaw.pm3.aBrain.backend.med.TreatmentPlan;
import ch.zhaw.pm3.aBrain.backend.med.administration.DailyInterval;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.controller.ScreenController;
import ch.zhaw.pm3.aBrain.frontend.factory.AddTimeBox;
import ch.zhaw.pm3.aBrain.frontend.handler.MedDataHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MedIntakeTimeController  implements ControlledScreens {
    private ScreenController screenController = ScreenController.getInstance();
    private MedDataHandler medDataHandler;
    private List<HBox> timeBox = new ArrayList<>();

    @FXML
    private Button btnAddTime;
    @FXML
    private VBox overviewContainer;
    @FXML
    private GridPane root;
    @FXML
    private ScrollPane sbTimes;
    @FXML
    private Button btnNext;
    @FXML
    void initialize() throws FileNotFoundException {
        setMedicationHandler(createDummyMedication());
        createTimeList();
    }
    @FXML
    void btnAddTimeClicked(ActionEvent event) {
        Medication med = medDataHandler.getMedication();

        if(med.getTreatmentPlan().getAdminInterval() instanceof DailyInterval){
            DailyInterval dailyInterval = (DailyInterval) med.getTreatmentPlan().getAdminInterval();

            LocalTime time = LocalTime.parse("08:00");
            dailyInterval.addTime(time);
        }

        createTimeList();
        btnNext.setDisable(checkAllDataSet());
    }

    public void setMedicationHandler(MedDataHandler medDataHandler){
        this.medDataHandler = medDataHandler;
    }

    private void createTimeList(){
        timeBox = new ArrayList<>();
        Medication med = medDataHandler.getMedication();

        overviewContainer.getChildren().clear();

        if(med.getTreatmentPlan().getAdminInterval() instanceof DailyInterval){
            DailyInterval dailyInterval = (DailyInterval) med.getTreatmentPlan().getAdminInterval();

            for (int i = 0; i < dailyInterval.getAdminTimes().size(); i++) {
                timeBox.add(AddTimeBox.getDetailItem(i,i+1 + ". Einnahmezeit", dailyInterval.getAdminTimes().get(i).toString(), buttonClickEvent, textChangedEvent));
            }
        }

        for (int i = 0; i < timeBox.size(); i++) {
            overviewContainer.getChildren().add(i, timeBox.get(i));
        }
    }

    private EventHandler<ActionEvent> buttonClickEvent = e -> {
        int indexToDelete = Integer.parseInt(((Button)e.getSource()).getId());
        Medication med = medDataHandler.getMedication();

        if(med.getTreatmentPlan().getAdminInterval() instanceof DailyInterval){
            DailyInterval dailyInterval = (DailyInterval) med.getTreatmentPlan().getAdminInterval();
            dailyInterval.getAdminTimes().remove(indexToDelete);
        }

        createTimeList();
    };

    private EventHandler<? super KeyEvent> textChangedEvent = e -> {
        int indexHasChanged = Integer.parseInt(((TextField)e.getSource()).getId());
        String time = ((TextField) e.getSource()).getText();

        if(!checkTimeFormat(time)){
            Node node = timeBox.get(indexHasChanged);
            node.getStyleClass().clear();
            node.getStyleClass().add("framed-box");
        } else {
            Node node = timeBox.get(indexHasChanged);
            node.getStyleClass().clear();
            node.getStyleClass().add("framed-box-error");
        }
        updateTimeInDataSet(indexHasChanged, time);
        btnNext.setDisable(checkAllDataSet());
    };

    private void updateTimeInDataSet(int index, String time){
        if(time.length() == 4) { time = "0" + time; }

        if(!checkTimeFormat(time)){
            Medication med = medDataHandler.getMedication();

            if(med.getTreatmentPlan().getAdminInterval() instanceof DailyInterval){
                DailyInterval dailyInterval = (DailyInterval) med.getTreatmentPlan().getAdminInterval();
                dailyInterval.getAdminTimes().set(index, LocalTime.parse(time));
            }
        }
    }

    private boolean checkAllDataSet(){
        boolean hasError = false;

        for (int i = 0; i < timeBox.size(); i++) {
            String style = timeBox.get(i).getStyleClass().get(0);
            if(style == "framed-box-error") { hasError = true; }
        }
        return hasError;
    }

    private boolean checkTimeFormat(String time){
        Pattern pattern = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");

        if (!pattern.matcher(time).matches()) { return true; }
        return false;
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

        dailyInterval.addTime(time1);
        dailyInterval.addTime(time2);

        treatmentPlan.setAdminInterval(dailyInterval);

        medication.setTreatmentPlan(treatmentPlan);

        MedDataHandler medDataHandler = new MedDataHandler(MedDataHandler.State.NEW, medication);

        return medDataHandler;
    }

    @FXML
    void next(ActionEvent event) {
        root.getScene().setRoot(screenController.getScreen(aBrain.Screen.MED_WEEKDAY_INTERVAL));
    }
}
