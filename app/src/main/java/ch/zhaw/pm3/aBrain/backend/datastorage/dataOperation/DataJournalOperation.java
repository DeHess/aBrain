package ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation;

import ch.zhaw.pm3.aBrain.backend.journal.entry.*;

import java.util.List;

/**
 * This interface defines all journal operations.
 *
 * @author wilphi01
 * @version 1.0
 */
public interface DataJournalOperation {

    List<JournalEntry> getAllJournalEntriesList();

    List<MedIntake> getMedIntakeEntriesList();

    List<Mood> getMoodEntriesList();

    List<SideEffect> getSideEffectEntriesList();

    List<Sport> getSportEntriesList();

    JournalEntry getJournalEntry(int entryId);

    void addJournalEntry(JournalEntry entry);

    void deleteJournalEntry(JournalEntry entry);

    void updateJournalEntry(JournalEntry entry);
}
