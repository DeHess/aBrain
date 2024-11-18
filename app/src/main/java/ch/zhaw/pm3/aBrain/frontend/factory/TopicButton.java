package ch.zhaw.pm3.aBrain.frontend.factory;

import javafx.scene.control.Button;

public class TopicButton {
    
    private static final String STYLE_BUTTON = "community__topic-button";
    private final String topic;
    private Button button;
    
    public TopicButton(String topic){
        this.topic = topic;
        createButton();
    }
    
    private void createButton(){
        button = new Button();
        button.setId(topic);
        button.getStyleClass().add(STYLE_BUTTON);
        button.setText(topic);
    }
    
    public Button getButton(){
        return button;
    }
    
    public String getTopic(){
        return topic;
    }
    
    
}
