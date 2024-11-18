package ch.zhaw.pm3.aBrain.frontend.factory;

import ch.zhaw.pm3.aBrain.backend.community.Article;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.io.File;

public class ArticleBox {
    private final static String STYLE_BOX = "community__article--box";
    private final static String STYLE_TITLE_LABEL = "community__article--title";
    private final static String STYLE_DESCRIPTION_LABEL = "community__article--description";
    
    private Article article;
    private VBox box;
    
    public ArticleBox(Article article){
        this.article = article;
        createBox();
    }
    
    private void createBox(){
        box = new VBox();
        box.getStyleClass().add(STYLE_BOX);
        Label titleLabel = getTitleLabel();
        Label descriptionLabel = getDescriptionLabel();
        
        box.getChildren().add(0,titleLabel);
        box.getChildren().add(1,descriptionLabel);
    }
    
    private Label getTitleLabel(){
        Label titleLabel = new Label();
        titleLabel.getStyleClass().add(STYLE_TITLE_LABEL);
        titleLabel.setText(article.getTitle());
        return titleLabel;
    }
    
    private Label getDescriptionLabel(){
        Label descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add(STYLE_DESCRIPTION_LABEL);
        descriptionLabel.setText(article.getDescription());
        return descriptionLabel;
    }
    
    public VBox getBox(){
        return box;
    }
    
    public String getTopic(){
        return article.getTopic();
    }
    
    public String getTitle(){
        return article.getTitle();
    }
    
    private String getText(){
        return article.getText();
    }
    
}
