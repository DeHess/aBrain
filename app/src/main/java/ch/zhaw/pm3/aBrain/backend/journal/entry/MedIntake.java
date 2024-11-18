package ch.zhaw.pm3.aBrain.backend.journal.entry;

import ch.zhaw.pm3.aBrain.backend.med.Medication;

import java.time.LocalDateTime;

/**
 * The type Med intake.
 */
public class MedIntake extends JournalEntry{

    private int medIntakeId;

    Medication medication;
    
    /**
     * Instantiates a new Med intake.
     *
     * @param medication the medication
     */
    public MedIntake(Medication medication){
        super();
        this.medication = medication;
    }

    public MedIntake(int entryId, Medication medication, LocalDateTime dateTime){
        super();
        this.medication = medication;
        this.dateTime = dateTime;
    }

    public int getMedIntakeId() {
        return medIntakeId;
    }

    public void setMedIntakeId(int medIntakeId) {
        this.medIntakeId = medIntakeId;
    }

    public Medication getMedication() {
        return medication;
    }
}
