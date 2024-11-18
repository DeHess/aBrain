package ch.zhaw.pm3.aBrain.frontend.factory;


import ch.zhaw.pm3.aBrain.frontend.controller.HomeScreenController;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import ch.zhaw.pm3.aBrain.backend.journal.entry.Mood.MoodType;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class that provides mood button cells for the {@link HomeScreenController}
 *
 * @author baermlau
 */
public class MoodButton {
    
    private static final String STYLE_BUTTON = "home__mood-button";
    private final MoodType mood;
    private final Image emoji;
    private Button button;
    
    /**
     * Instantiates a new mood button cell.
     *
     * @param mood the mood
     * @throws FileNotFoundException the file not found exception
     */
    public MoodButton(MoodType mood) throws FileNotFoundException {
        this.mood = mood;
        
        String basicPath = "src/main/resources/ch/zhaw/pm3/aBrain/design/img_elements/emoji/emoji_";
        String imagePath = basicPath + mood.name().toLowerCase() + ".png";
        
        emoji = ImageLoader.getImageFromPath(imagePath);
        
        createButton();
    }
    
    private void createButton() {
        button = new Button();
        button.setId(mood.name());
        button.getStyleClass().add(STYLE_BUTTON);
        
        ImageView imageView = new ImageView(emoji);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        button.setContentDisplay(ContentDisplay.CENTER);
    }
    
    /**
     * Get button button.
     *
     * @return the button
     */
    public Button getButton() {
        return button;
    }
}
