package ch.zhaw.pm3.aBrain.backend.med.administration;

import java.time.LocalDateTime;

public abstract class AdminInterval {
    
    
    /*
    public enum Type{
        DAILY, X_DAYS, X_HOURS, WEEKDAYS, SINGLE_OCCURRENCE
    }
    */
    
    
    
    public Class<? extends AdminInterval> getInterval(){
        return this.getClass();
    }

}
