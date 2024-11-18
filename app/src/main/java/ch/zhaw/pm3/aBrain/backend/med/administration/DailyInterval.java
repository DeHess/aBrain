package ch.zhaw.pm3.aBrain.backend.med.administration;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DailyInterval extends AdminInterval{
    private final List<LocalTime> adminTimes;
    
    public DailyInterval(){
    
        adminTimes = new ArrayList<>();
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
