package ch.zhaw.pm3.aBrain.frontend.handler;

import ch.zhaw.pm3.aBrain.backend.med.Medication;

import java.util.HashMap;
import java.util.Map;

public class MedDataHandler {
    
    private final State state;
    private Medication medication;
    
    private final Map<Screen, String> titles;
    
    public enum State{
        NEW, EDIT
    }
    public enum Screen{
        NAME, INTERVAL_SELECTION,
        DAY_TIME, WEEKDAYS, DAILY_INTERVAL, HOURLY_INTERVAL, ONCE,
        TREATMENT_START_END, STOCK, ADMINISTRATION, OVERVIEW;
        
        String getFormatedName(){
            String name = this.name().substring(0,1) + this.name().substring(1).toLowerCase();
            name = name.replace('_', ' ');
            return name;
        }
    }
    
    public MedDataHandler(State state){
        this.state = state;
        this.titles = new HashMap<>();
        if(state == State.NEW) addAllNewStateTitles();
        else if(state == State.EDIT) addAllEditStateTitles();
    }
    
    public MedDataHandler(State state, Medication medication){
        this.state = state;
        this.medication = medication;
        this.titles = new HashMap<>();
        if(state == State.NEW) addAllNewStateTitles();
        else if(state == State.EDIT) addAllEditStateTitles();
    }
    
    public void setMedication(Medication medication) {
        this.medication = medication;
    }
    
    public Medication getMedication() {
        return medication;
    }
    
    private void addAllNewStateTitles(){
        for (Screen screen : Screen.values()){
            titles.put(screen, "Medikament hinzufügen");
        }
        titles.replace(Screen.OVERVIEW, "Medikament Übersicht");
    }
    
    private void addAllEditStateTitles(){
        for (Screen screen : Screen.values()){
            titles.put(screen,screen.getFormatedName());
        }
    }
    
    public String getTitle(Screen screen){
        return titles.get(screen);
    }
}
