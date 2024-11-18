package ch.zhaw.pm3.aBrain.backend.community;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Article {
    private String topic;
    private String title;
    
    private String description;
    
    private String text;
    private File txtFile;
    
    public Article(File txtFile) {
        this.txtFile = txtFile;
        extractInfo();
        
    }
    
    public void extractInfo(){
        try(BufferedReader reader = Files.newBufferedReader(txtFile.toPath())){
            List<String> lines = reader.lines().collect(Collectors.toList());
            if(lines.size() < 4) {
                System.err.println("Article must contain at least 4 lines.");
                return;
            }
        
            topic = lines.get(0).strip();
            title = lines.get(1).strip();
            description = lines.get(2).strip();
            text = lines.subList(3, lines.size()).stream().collect(Collectors.joining("\n")).toString();
        
        } catch (IOException e) {
            System.err.println("Could not read article file");
        }
    }
    
    
    
    public String getTopic() {
        return topic;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription(){
        return description;
    }
    
    public String getText(){
        return text;
    }
    
    
}
