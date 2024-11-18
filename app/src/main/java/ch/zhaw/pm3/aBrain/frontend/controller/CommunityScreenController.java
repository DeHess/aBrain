package ch.zhaw.pm3.aBrain.frontend.controller;

import ch.zhaw.pm3.aBrain.frontend.ControlledScreens;
import ch.zhaw.pm3.aBrain.frontend.factory.ArticleBox;
import ch.zhaw.pm3.aBrain.frontend.factory.TopicButton;
import ch.zhaw.pm3.aBrain.frontend.handler.ArticleHandler;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.checkerframework.checker.units.qual.A;

import java.io.FileNotFoundException;
import java.util.*;

public class CommunityScreenController implements ControlledScreens {
    
    @FXML
    private BorderPane root;
        
    @FXML
    private VBox articlesContainer;
    
    @FXML
    private ScrollPane articlesScrollPane;
    
    @FXML
    private HBox topicsContainer;
    
    @FXML
    private ScrollPane topicsScrollPane;
    
    @FXML
    private Label articleSectionLabel;
    
    @FXML
    private Label topicSectionLabel;
    
    private final ArticleHandler articleHandler = new ArticleHandler();
    
    @FXML
    void initialize() {
        articleSectionLabel.setText("Artikel");
        topicSectionLabel.setText("Thema");
        setupTopicsContainer();
        articleHandler.getNumberOfArticles().addListener((observable, oldValue, newValue) -> loadNewestArticles());
        articleHandler.addAllArticles();
        articlesScrollPane.setContent(articlesContainer);
        topicsScrollPane.setContent(topicsContainer);
    }
    
    private void setupTopicsContainer() {
        articleHandler.getTopics().addListener((MapChangeListener.Change<? extends String, ? extends TopicButton> item) -> {
            if (item.wasAdded()) {
                TopicButton topic = item.getValueAdded();
                topic.getButton().setOnAction(event->showArticlesRelatedToTopic(topic.getTopic()));
                topicsContainer.getChildren().add(topic.getButton());
            } else if (item.wasRemoved()) {
                TopicButton topic = item.getValueRemoved();
                topicsContainer.getChildren().remove(topic.getButton());
            }
        });
    }
    
    private void loadNewestArticles(){
        articlesContainer.getChildren().clear();
        List<ArticleBox> articleBoxes = articleHandler.getFirstXArticles(10);
        for(ArticleBox article : articleBoxes){
            articlesContainer.getChildren().add(article.getBox());
        }
    }
    
    
    void goToArticle(ArticleBox box) {
        // TODO show article
    }
    
    void showArticlesRelatedToTopic(String topic) {
        //TODO show articles
        List<ArticleBox> articleBoxes = articleHandler.getArticlesRelatedToTopic(topic);
    }
}


