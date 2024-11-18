package ch.zhaw.pm3.aBrain.frontend.factory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;


/**
 * Serves as a factory for the side effects list. It provides input field cells.
 *
 * @author baermlau
 */
public class SideEffectItem {
    private static final String STYLE_INPUTFIELD = "home__side-effect-text-field";
    
    private static final String PROMPT_TEXT = "Neue Nebenwirkung...";
    
    private final StringProperty sideEffect = new SimpleStringProperty();
    private final TextField inputField;
    
    /**
     * Instantiates a new Side effect item.
     */
    public SideEffectItem(){
        inputField = new TextField();
        inputField.getStyleClass().add(STYLE_INPUTFIELD);
        inputField.idProperty().bind(sideEffect);
        inputField.setPromptText(PROMPT_TEXT);
    }
    
    /**
     * Gets input field.
     *
     * @return the input field
     */
    public TextField getInputField() {
        return inputField;
    }
}
