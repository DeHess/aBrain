package ch.zhaw.pm3.aBrain.backend.med;

import ch.zhaw.pm3.aBrain.backend.med.administration.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The type Treatment plan.
 */
public class TreatmentPlan {

    private int medId;
    private LocalDateTime treatmentStart;
    private LocalDateTime treatmentEnd;
    private AdminInterval adminInterval;
    
    // TODO noch nicht sicher wie umsetzen
    

    public TreatmentPlan(){

    }
    /**
     * Instantiates a new Treatment plan.
     *
     * @param treatmentStart the treatment start
     */
    public TreatmentPlan(LocalDateTime treatmentStart){
        this.treatmentStart = treatmentStart;
    }
    
    /**
     * Instantiates a new Treatment plan.
     *
     * @param treatmentStart the treatment start
     * @param treatmentEnd   the treatment end
     */
    public TreatmentPlan(LocalDateTime treatmentStart, LocalDateTime treatmentEnd){
        this(treatmentStart);
        this.treatmentEnd = treatmentEnd;
    }
    
    /**
     * Sets reccurance.
     *
     * @param interval the reccurance
     */
    public void setAdminInterval(AdminInterval interval) {
        this.adminInterval = interval;
    }

    public AdminInterval getAdminInterval() {
        return adminInterval;
    }
    
    /**
     * Add intake.
     *
     * @param day  the day
     * @param time the time
     */
    public void addIntake(DayOfWeek day, LocalTime time){
    }
    
    /**
     * Sets treatment end.
     *
     * @param treatmentEnd the treatment end
     */
    public void setTreatmentEnd(LocalDateTime treatmentEnd) {
        this.treatmentEnd = treatmentEnd;
    }
    
    /**
     * Gets treatment start.
     *
     * @return the treatment start
     */
    public LocalDateTime getTreatmentStart() {
        return treatmentStart;
    }
    
    /**
     * Gets treatment end.
     *
     * @return the treatment end
     */
    public LocalDateTime getTreatmentEnd() {
        return treatmentEnd;
    }
    
    public String getNameOfIntervalType() throws IllegalArgumentException {
        return adminInterval.getInterval().getSimpleName();
    }
    
}
