package ch.zhaw.pm3.aBrain.frontend.factory;

import ch.zhaw.pm3.aBrain.frontend.controller.HomeScreenController;
import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Serves as a factory class for the to-do container in the class
 * {@link HomeScreenController}
 *
 * @author baermlau
 */
public class ToDoItem {
    
    private static final String STYLE_WIDGET = "home__todo-widget";
    private static final String STYLE_BUTTON = "button-box";
    
    private static final String STYLE_LABEL = "home__todo-label";
    private VBox widget;
    
    private Button intakeButton;
    
    private final StringProperty name = new SimpleStringProperty("");
    private final Property<LocalDateTime> timeDue = new SimpleObjectProperty<>(LocalDateTime.now());
    
    private final BooleanProperty isDone = new SimpleBooleanProperty(false);
    
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    //TODO: connect / replace these with the ones from a treatment plan or sports goal
    private enum LabelType {NAME, DUE, IS_DONE}
    
    /**
     * Instantiates a new Todo item.
     *
     * @param name the name
     */
    public ToDoItem(String name) {
        this.name.set(name);
        createWidget();
    }
    
    private void createWidget() {
        widget = new VBox();
        widget.getStyleClass().add(STYLE_WIDGET);
        Map<LabelType, Label> labelMap = getLabels();
        createButton();
        widget.getChildren().addAll(labelMap.get(LabelType.NAME), labelMap.get(LabelType.DUE), labelMap.get(LabelType.IS_DONE), intakeButton);
        widget.idProperty().bind(name);
    }
    
    private void createButton() {
        intakeButton = new Button();
        intakeButton.idProperty().bind(name);
        intakeButton.getStyleClass().add(STYLE_BUTTON);
        //TODO make diff language compatible
        intakeButton.setText("Jetzt einnehmen");
    }
    
    private Map<LabelType, Label> getLabels() {
        Map<LabelType, Label> labelMap = new HashMap<>();
        
        Label nameLabel = new Label();
        nameLabel.textProperty().bind(name);
        labelMap.put(LabelType.NAME, nameLabel);
        
        Label timeDueLable = new Label();
        timeDueLable.textProperty().bind(timeDue.map(x -> timeDue.getValue().format(dateTimeFormatter)));
        labelMap.put(LabelType.DUE, timeDueLable);
        
        Label takenLabel = new Label();
        takenLabel.textProperty().bind(isDone.map(x -> "Wurde eingenommen: " + x));
        labelMap.put(LabelType.IS_DONE, takenLabel);
        
        
        for (Map.Entry<LabelType, Label> entry : labelMap.entrySet()) {
            entry.getValue().idProperty().bind(name);
            entry.getValue().getStyleClass().add(STYLE_LABEL);
        }
        
        return labelMap;
        
    }
    
    /**
     * Get widget
     *
     * @return widget
     */
    public VBox getWidget() {
        return widget;
    }
    
    /**
     * Get button.
     *
     * @return the button
     */
    public Button getButton() {return intakeButton;}
    
    /**
     * Set to-do as done
     */
    public void setAsDone() {
        isDone.set(true);
    }
    
    /**
     * Set to-do as undone.
     */
    public void setAsUndone() {
        isDone.set(false);
    }
}
