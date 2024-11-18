package ch.zhaw.pm3.aBrain.backend.med.administration;

import java.rmi.NoSuchObjectException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class XDaysInterval extends AdminInterval {
    private int xDays;
    
    private final List<LocalTime> adminTimes;
    public XDaysInterval(int xDays){
        this.xDays = xDays;
        adminTimes = new ArrayList<>();
    }
    
    public XDaysInterval(){
        this(0);
    }
    
    public void setxDays(int xDays) {
        this.xDays = xDays;
    }
    
    public int getxDays() throws NoSuchObjectException {
        if(xDays == 0)  throw new NoSuchObjectException("No hours have been set");
        return xDays;
    }
    
    public void addTime(LocalTime time){
        adminTimes.add(time);
    }
    
    public void removeTime(LocalTime time){
        int index = adminTimes.indexOf(time);
        if(index > 0) {
            adminTimes.remove(index);
        }
    }
    
    public List<LocalTime> getAdminTimes(){
        return adminTimes;
    }
    
    @Override
    public Class<? extends AdminInterval> getInterval(){
        return this.getClass();
    }
}
