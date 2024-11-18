package ch.zhaw.pm3.aBrain.backend.journal.entry;

import java.time.LocalDateTime;

/**
 * Abstract Superclass of all different Journal entries
 */
public abstract class JournalEntry {

    int entryId;
    LocalDateTime dateTime;
    
    /**
     * Instantiates a new Journal entry.
     */
    public JournalEntry(){
        dateTime = LocalDateTime.now();
    }

    public LocalDateTime getDateTime(){
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }
}
