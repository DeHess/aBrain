package ch.zhaw.pm3.aBrain.backend.journal.entry;

import java.time.LocalDateTime;

/**
 * The type Side effect.
 *
 * @author baermlau, wilphi01
 * @version 1.0
 */
public class SideEffect extends JournalEntry {

    private int sideEffectId;
    private String sideEffect;
    
    
    /**
     * Instantiates a new Side effect.
     *
     * @param sideEffect the side effect
     */
    public SideEffect(String sideEffect) {
        super();
        setSideEffect(sideEffect);
    }

    public SideEffect(int sideEffectId, String sideEffect, int jrnId, LocalDateTime jrnDate) {
        setSideEffectId(sideEffectId);
        setSideEffect(sideEffect);
        setEntryId(jrnId);
        setDateTime(jrnDate);
    }

    public int getSideEffectId() {
        return sideEffectId;
    }
    public void setSideEffectId(int sideEffectId) {
        this.sideEffectId = sideEffectId;
    }

    public String getSideEffect() {
        return sideEffect;
    }
    public void setSideEffect(String sideEffect) {
        this.sideEffect = sideEffect;
    }
}
