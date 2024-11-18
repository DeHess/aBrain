package ch.zhaw.pm3.aBrain.backend.journal;


import ch.zhaw.pm3.aBrain.backend.PdfGenerator;
import ch.zhaw.pm3.aBrain.backend.datastorage.databaseOperation.DatabaseJournalOperation;
import ch.zhaw.pm3.aBrain.backend.journal.entry.JournalEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * The type Journal.
 */
public class Journal {
    
    private static Journal journal = new Journal();
    private static PdfGenerator pdfGenerator = new PdfGenerator();
    private DatabaseJournalOperation databaseLink = new DatabaseJournalOperation();
    private String pathBase = "src/main/resources/ch/zhaw/pm3/aBrain/pdfGenerator";
    public static Journal getInstance(){
        return journal;
    }
    
    
    /**
     * Write entry boolean.
     *
     * @param entry the entry
     * @return the boolean
     */
    public boolean writeEntry(JournalEntry entry) {
        /*
        Example:
        JournalEntry<Mood> entry1 = new Mood(Mood.MoodType.HAPPY);
        entry1.getEntry().getMood();
        
         */
        return true;
    }
    
    /**
     * Write journal list.
     *
     * @param entryList the entry list
     */
    public void writeJournalList(List<JournalEntry> entryList){}
    
    /**
     * Load journal list list.
     *
     * @param entryList the entry list
     * @return the list
     */
    public List<JournalEntry> loadJournalList(List<JournalEntry> entryList){
        return Collections.emptyList();
    }

    /**This method generates a pdf holding an extract of journal entries given a timeframe
     * Will not overwrite old entries
     * @param path where the file will be saved to
     * @param start start date, opening the timeframe-interval
     * @param end end date, closing the timeframe-interval
     * */
    public void generatePdfFromTotalList(String path, LocalDate start, LocalDate end) {
        List<JournalEntry> allEntries = (List<JournalEntry>) databaseLink.getAllJournalEntriesList();
        List<JournalEntry> entriesInTimeFrame = allEntries
                .stream()
                .filter(j -> j.getDateTime().toLocalDate().compareTo(start) >= 0 && j.getDateTime().toLocalDate().compareTo(end) <= 0)
                .toList();
        String pathAddon = pathBase + LocalDateTime.now() + ".pdf";
        pdfGenerator.generatePDF(entriesInTimeFrame, pathBase + pathAddon);
    }



}

