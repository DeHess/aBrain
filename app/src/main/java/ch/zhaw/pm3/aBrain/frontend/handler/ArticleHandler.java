package ch.zhaw.pm3.aBrain.frontend.handler;

import ch.zhaw.pm3.aBrain.backend.community.Article;
import ch.zhaw.pm3.aBrain.backend.journal.entry.JournalEntry;
import ch.zhaw.pm3.aBrain.frontend.factory.ArticleBox;
import ch.zhaw.pm3.aBrain.frontend.factory.TopicButton;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.util.*;

public class ArticleHandler {
    
    private static String ARTICLE_DIRECTORY_PATH = "ch/zhaw/pm3/aBrain/article/";
    
    private final List<Article> articles = new ArrayList<>();
    private final ObservableMap<String, TopicButton> topics = FXCollections.observableHashMap();
    
    private final IntegerProperty numberOfArticles = new SimpleIntegerProperty(0);
    
    public void addAllArticles(){
        try {
            File directory = getArticleDirectory();
            if(directory.isDirectory()){
                addArticlesFromDirectory(directory);
            }else{System.err.println("No Directory");}
        } catch (URISyntaxException | FileNotFoundException e) {
            System.err.println("Directory not found 4");
        }
    }
    
    private File getArticleDirectory() throws FileNotFoundException, URISyntaxException {
        try {
            URI fileURI = Objects.requireNonNull(this.getClass().getClassLoader().getResource(ARTICLE_DIRECTORY_PATH)).toURI();
            File file = new File(fileURI);
            if (file.exists()) return file;
            else throw new FileNotFoundException("Directory not found 1");
        } catch (NullPointerException e) {
            throw new FileNotFoundException("Directory not found 2");
        } catch (URISyntaxException e) {
            // TODO: add logger
            throw new URISyntaxException("Directory not found 3", e.getMessage());
        }
    }
    
    private void addArticlesFromDirectory(File directory){
        File [] articleFiles = directory.listFiles();
        for(File file: articleFiles){
            addArticle(file);
        }
    }
    
    
    private void addArticle(File articleFile) {
        Article article = new Article(articleFile);
        articles.add(article);
        System.out.println(article.getTopic());
        topics.putIfAbsent(article.getTopic(), new TopicButton(article.getTopic()));
        numberOfArticles.set(articles.size());
    }
    
    public ObservableMap<String, TopicButton> getTopics() {
        return topics;
    }
    
    public List<ArticleBox> getFirstXArticles(int numberOfArticles) {
        List<ArticleBox> articleBoxes = new ArrayList<>();
        int index = 0;
        while (index < numberOfArticles && index < articles.size()) {
            articleBoxes.add(new ArticleBox(articles.get(index)));
            index++;
        }
        return articleBoxes;
    }
    
    public List<ArticleBox> getArticlesRelatedToTopic(String topic) {
        List<ArticleBox> relatedArticles = articles.stream()
                .filter(article -> article.getTopic().equals(topic))
                .map(ArticleBox::new).toList();
        if (relatedArticles.isEmpty()) return Collections.emptyList();
        else return relatedArticles;
    }
    
    public IntegerProperty getNumberOfArticles() {
        return numberOfArticles;
    }
}
