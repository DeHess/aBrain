package ch.zhaw.pm3.aBrain.frontend.controller;

import ch.zhaw.pm3.aBrain.backend.journal.entry.Mood;
import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.factory.MoodButton;
import ch.zhaw.pm3.aBrain.frontend.factory.SideEffectItem;
import ch.zhaw.pm3.aBrain.frontend.factory.ToDoItem;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

/**
 * Controller for the scene home
 *
 * @author baermlau, zimmlu5
 */
public class HomeScreenController implements ControlledScreens {
    
    @FXML
    private Button notificationButton;
    
    @FXML
    private Label moodSectionLabel;
    
    @FXML
    private Label toDoSectionLabel;
    
    @FXML
    private Label sideEffectSectionLabel;
    
    @FXML
    private HBox moodContainer;
    
    @FXML
    private VBox sideEffectContainer;
    
    @FXML
    private ScrollPane toDoScrollPane;
    
    @FXML
    private HBox toDoContainer;
    
    /**
     * The to-do cell map.
     */
    private ObservableMap<Integer, ToDoItem> toDoCells = FXCollections.observableHashMap();
    /**
     * The Side effect cell map.
     */
    private ObservableMap<Integer, SideEffectItem> sideEffectCells = FXCollections.observableHashMap();
    
    /**
     * This method initializes the components for the homescreen.
     *
     * @throws FileNotFoundException the file not found exception
     */
    @FXML
    void initialize() throws FileNotFoundException {
        setupMoodButtons();
        
        setupTodoContainer();
        toDoScrollPane.setContent(toDoContainer);
        toDoScrollPane.setFitToHeight(true);
        setupSideEffectContainer();
        //TODO Remove the three addToDo() calls-> only for presentation purposes,
        addTodoItem("Elvanse, 50mg");
        addTodoItem("Elvanse, 30mg");
        addTodoItem("Multivitamin");
    }
    
    /**
     * Setup mood container
     * @see #moodContainer
     */
    private void setupMoodButtons() throws FileNotFoundException {
        int index = 0;
        for (Mood.MoodType mood : Mood.MoodType.values()) {
            final int finalButtonIndex = index;
            MoodButton moodButton = new MoodButton(mood);
            moodButton.getButton().setOnAction(event -> moodButtonAction(moodButton));
            moodContainer.getChildren().add(finalButtonIndex, moodButton.getButton());
            index++;
        }
    }
    
    /**
     * Setup side to-do container
     * @see #toDoContainer
     * @see #toDoCells
     */
    
    private void setupTodoContainer() {
        toDoContainer.setFillHeight(true);
        HBox.setHgrow(toDoContainer, Priority.ALWAYS);
        toDoCells.addListener((MapChangeListener.Change<? extends Integer, ? extends ToDoItem> item) -> {
            if (item.wasAdded()) {addToDoCell(item.getKey(), item.getValueAdded());
            } else if (item.wasRemoved()) {
                ToDoItem todo = item.getValueRemoved();
                toDoContainer.getChildren().remove(todo.getWidget());
            }
        });
    }
    
    
    /**
     * Setup side effect container
     * @see #sideEffectContainer
     * @see #sideEffectCells
     */
    private void setupSideEffectContainer() {
        sideEffectCells.addListener((MapChangeListener.Change<? extends Integer, ? extends SideEffectItem> item) -> {
            if (item.wasAdded()) {addSideEffectCell(item.getKey(),item.getValueAdded());
            } else if (item.wasRemoved()) {
                SideEffectItem sideEffect = item.getValueRemoved();
                sideEffectContainer.getChildren().remove(sideEffect.getInputField());
            }
        });
        addEmptySideEffectItem();
    }
    
    /**
     * Add cell to to-do container
     * @param index of cell in container
     * @param cell {@link ToDoItem}
     * @see #toDoContainer
     */
    private void addToDoCell(int index, ToDoItem cell){
        cell.getButton().setOnAction(button -> {
            //TODO Link to screen for medication intake entry and make a journal entry
            cell.getButton().setEffect(new InnerShadow());
            cell.setAsDone();
        });
        toDoContainer.getChildren().add(index,cell.getWidget());
    }
    
    /**
     * Add cell to side effect container
     * @param index of cell in container
     * @param cell {@link SideEffectItem}
     * @see #sideEffectContainer
     */
    private void addSideEffectCell(int index, SideEffectItem cell){
        cell.getInputField().setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                //TODO: Save sideeffect in journal entry
                String sideEffectInput = cell.getInputField().getText();
                cell.getInputField().setEditable(false);
                addEmptySideEffectItem();
            }
        });
        sideEffectContainer.getChildren().add(index, cell.getInputField());
    }
    
    
    /**
     * Mood button action.
     *
     * @param moodButton the mood button
     */
     void moodButtonAction(MoodButton moodButton) {
        moodButton.getButton().setEffect(new InnerShadow());
        moodContainer.getChildren().stream().forEach(child -> child.setDisable(true));
        //TODO make new journal entry
    }
    
    /**
     * Add cell to map.
     * Cell contains empty side effect input field.
     *
     * @see #sideEffectCells
     */
    private void addEmptySideEffectItem() {
        int index = sideEffectCells.size();
        sideEffectCells.put(index, new SideEffectItem());
    }
    
    /**
     * Add toDo item cell to observable map.
     *
     * @param toDoName the todo name
     * @see #toDoCells
     */
    private void addTodoItem(String toDoName) {
        int index = toDoCells.size();
        toDoCells.put(index, new ToDoItem(toDoName));
    }
    
    @FXML
    private void goToNotifications(){
        //TODO set scene to notifications screen
        notificationButton.setOnAction(event -> notificationButton.setEffect(new InnerShadow()));
    }
}
