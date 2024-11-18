package ch.zhaw.pm3.aBrain.backend.journal.entry;

import java.time.LocalDateTime;

/**
 * The type Sport.
 *
 * @author baermlau, wilphi01
 * @version 1.0
 */
public class Sport extends JournalEntry{

    private int sportId;
    private String sport;
    
    /**
     * Instantiates a new Sport.
     *
     * @param sport the sport
     */
    public Sport(String sport) {
        super();
        this.sport = sport;
    }

    public Sport(int sportId, String sport, int jrnId, LocalDateTime jrnDate) {
        setSportId(sportId);
        setSport(sport);
        setEntryId(jrnId);
        setDateTime(jrnDate);
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}
