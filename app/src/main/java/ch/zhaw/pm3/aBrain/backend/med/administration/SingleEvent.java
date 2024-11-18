package ch.zhaw.pm3.aBrain.backend.med.administration;

import java.rmi.NoSuchObjectException;
import java.time.LocalTime;

public class SingleEvent extends AdminInterval{
    private LocalTime time;
    
    public SingleEvent(LocalTime time){
        this.time = time;
    }
    
    public SingleEvent(){}
    
    public void setTime(LocalTime time) {
        this.time = time;
    }
    
    public LocalTime getTime() throws NoSuchObjectException {
        if(time == null) throw new NoSuchObjectException("No time has been set");
        return time;
    }
}
