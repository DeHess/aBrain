package ch.zhaw.pm3.aBrain.backend.med.administration;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WeekdayInterval extends AdminInterval {
    private List<DayOfWeek> adminDays;
    private List<LocalTime> adminTimes;
    
    public WeekdayInterval(){
        adminDays = new ArrayList<>();
        adminTimes = new ArrayList<>();
    }
    
    public void addWeekday(DayOfWeek day){
        adminDays.add(day);
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
    
    public void removeWeekday(DayOfWeek day){
        int index = adminTimes.indexOf(day);
        if(index > 0) {
            adminTimes.remove(index);
        }
    }
    
    public List<DayOfWeek> getAdminDays() {
        return adminDays;
    }
    
    public List<LocalTime> getAdminTimes() {
        return adminTimes;
    }
}
