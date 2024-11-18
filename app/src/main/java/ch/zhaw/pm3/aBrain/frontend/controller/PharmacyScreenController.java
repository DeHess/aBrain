package ch.zhaw.pm3.aBrain.frontend.controller;

import ch.zhaw.pm3.aBrain.aBrain.Screen;
import ch.zhaw.pm3.aBrain.backend.med.Medication;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.factory.PharmacyItem;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.FileNotFoundException;


/**
 * Controller class for the pharmacy scene
 * @author baermlau, zimmlu5
 */
public class PharmacyScreenController implements ControlledScreens {
    
    public ScreenController screenController = ScreenController.getInstance();
    
    @FXML
    private BorderPane root;
    
    @FXML
    private TextField searchField;
    /**
     * The Scroll pane.
     */
    @FXML
    public ScrollPane scrollPane;
    
    @FXML
    private VBox gridWrapper;
    
    private GridPane grid;
    
    private final ObservableMap<Point, PharmacyItem> pharmacyItemCells = FXCollections.observableHashMap();
    
    private static final String STYLE_GRID = "pharmacy__grid";
    
    private static int NODES_PER_ROW = 2;
    
    /**
     * Initialize.
     */
    @FXML
    void initialize() {
        assert gridWrapper != null : "fx:id=\"gridWrapper\" was not injected: check your FXML file 'PharmacyScreen.fxml'.";
        assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'PharmacyScreen.fxml'.";
        createGrid();
    }
    
    /**
     * Go to community.
     *
     * @param event the event
     */
    @FXML
    void goToCommunity(ActionEvent event) {
        root.getScene().setRoot(screenController.getScreen(Screen.COMMUNITY));
    }
    
    /**
     * Go to home.
     *
     * @param event the event
     */
    @FXML
    void goToHome(ActionEvent event) {
        root.getScene().setRoot(screenController.getScreen(Screen.HOME));
    }
    
    /**
     * Go to journal.
     *
     * @param event the event
     */
    @FXML
    void goToJournal(ActionEvent event) {
        root.getScene().setRoot(screenController.getScreen(Screen.JOURNAL));
    }
    
    /**
     * Go to pharmacy.
     *
     * @param event the event
     */
    @FXML
    void goToPharmacy(ActionEvent event) {
        root.getScene().setRoot(screenController.getScreen(Screen.PHARMACY));
    }
    
    /**
     * Go to settings.
     *
     * @param event the event
     */
    @FXML
    void goToSettings(ActionEvent event) {
    
    }
    
    private void createGrid() {
        setUpGrid();
        gridWrapper.getChildren().add(grid);
        addEmptyPharmacyItem();
    }
    
    private void setUpGrid(){
        grid = new GridPane();
        grid.getStyleClass().add(STYLE_GRID);
        pharmacyItemCells.addListener((MapChangeListener.Change<? extends Point, ? extends PharmacyItem> item) -> {
            if (item.wasAdded()) {
                Point location = item.getKey();
                PharmacyItem addedItem = item.getValueAdded();
                if(addedItem.isEmpty()) addedItem.getCell().setOnMouseClicked(e-> addMedication(location));
                else addedItem.getCell().setOnMouseClicked(e-> updateMedication(location));
                grid.add(addedItem.getCell(), location.x, location.y,1,1);
            } else if (item.wasRemoved()) {
                PharmacyItem removedItem = item.getValueRemoved();
                grid.getChildren().remove(removedItem.getCell());
            }
        });
    }
    
    //TODO: either remove (currently needed for test purposes) or get actual data to accurately change the medication object
    private void updateMedication(Point location){
        pharmacyItemCells.get(location).getMedication().setName("Edited");
    }
    
    private void addMedication(Point location) {
        root.getScene().setRoot(screenController.getScreen(Screen.MED_NAME));
        addMedication(location, new Medication("Elvanse"));
        addEmptyPharmacyItem();
    }
    
    private void addMedication(Point location, Medication medication) {
        try {
            removePharmacyItem(location);
            addPharmacyItem(location,new PharmacyItem(medication));
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
    
    private void addEmptyPharmacyItem() {
        Point coordinates = getPositionAfterLastItemInGrid();
        try {
            addPharmacyItem(coordinates, new PharmacyItem());
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
        
    }
    private void addPharmacyItem(Point coordinates, PharmacyItem item)  {
        pharmacyItemCells.put(coordinates, item);
    }
    
    private void removePharmacyItem(Point coordinates) {
        pharmacyItemCells.remove(coordinates);
    }
    
    private Point getPositionAfterLastItemInGrid(){
        return getCoordinatesFromListIndex(pharmacyItemCells.size());
    }
    private Point getCoordinatesFromListIndex(int index) {
        Point position = new Point();
        if (index == 0) {
            position.x = 0;
            position.y = 0;
        } else if (index % NODES_PER_ROW == 0) {
            position.x = 0;
            position.y = index / NODES_PER_ROW;
        } else {
            position.x = 1;
            position.y = (index - 1) / NODES_PER_ROW;
        }
        return position;
    }
    
    /**
     * Search.
     *
     * @param keyEvent the key event
     */
    @FXML
    public void search(KeyEvent keyEvent) {
        String searchText = searchField.getText();
        //TODO: Temporarily filter grid view -> where is a fixed collection of the users medications saved?
    }
}
