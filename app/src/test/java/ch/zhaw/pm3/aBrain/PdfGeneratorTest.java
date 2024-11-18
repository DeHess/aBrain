package ch.zhaw.pm3.aBrain;


import ch.zhaw.pm3.aBrain.backend.med.Medication;
import ch.zhaw.pm3.aBrain.backend.PdfGenerator;
import ch.zhaw.pm3.aBrain.backend.journal.entry.*;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PdfGeneratorTest {

    private static final String journalExtractPath =
            "src/main/resources/ch/zhaw/pm3/aBrain/pdfGenerator/Journal_Extract_test.pdf";

    /**This method generates a mock pdf with some Journal entries. This PDF can later be read and tested*/
    @BeforeAll
    public static void generatePDF() {
        JournalEntry fourthEntry = new SideEffect(
                "Einige Stunden nach der Eingabe habe ich starke Kopfschmerzen bekommen. M\u00f6glicherweise liegt dies an Medikament A");
        fourthEntry.setDateTime(LocalDateTime.of(2022, 11, 13,10,0));

        JournalEntry firstEntry = new Mood(Mood.MoodType.HAPPY);
        firstEntry.setDateTime(LocalDateTime.of(2022, 11, 13,14,30));

        JournalEntry secondEntry = new MedIntake(new Medication("Medikament B", 1, LocalDate.of(2020, 3, 28)));
        secondEntry.setDateTime(LocalDateTime.of(2022, 11, 12,0,0));

        JournalEntry thirdEntry = new MedIntake(new Medication("Medikament A", 1, LocalDate.of(2021, 3, 28)));
        thirdEntry.setDateTime(LocalDateTime.of(2022, 11, 12,11,30));

        JournalEntry fifthEntry = new MedIntake(new Medication("Medikament C", 1, LocalDate.of(2021, 3, 28)));
        fifthEntry.setDateTime(LocalDateTime.of(2022, 11, 11,12,30,1));

        JournalEntry sixthEntry = new Sport("1h Jogging");
        sixthEntry.setDateTime(LocalDateTime.of(2022, 11, 21,11,5,1));


        List<JournalEntry> journalEntryList = new ArrayList<>();
        journalEntryList.add(secondEntry);
        journalEntryList.add(sixthEntry);
        journalEntryList.add(firstEntry);
        journalEntryList.add(fourthEntry);
        journalEntryList.add(fifthEntry);
        journalEntryList.add(thirdEntry);


        PdfGenerator pdfGenerator = new PdfGenerator();
        pdfGenerator.generatePDF(journalEntryList, journalExtractPath);
    }

    /**This Test checks if journal entries are written to the PDF*/
    @Test
    public void entriesAreWrittenToPDF () throws IOException {
        PdfReader pr = new PdfReader(journalExtractPath);
        PdfTextExtractor pdfExtractor = new PdfTextExtractor(pr);
        String pageAsString = pdfExtractor.getTextFromPage(1);
        assertTrue(pageAsString.contains("10:00"));
        assertTrue(pageAsString.contains("11:30"));
        pr.close();
    }

    /**This Test checks if the journal entries are sorted by day before writing to PDF*/
    @Test
    public void entriesAreSortedByDay() throws IOException {
        PdfReader pr = new PdfReader(journalExtractPath);
        PdfTextExtractor pdfExtractor = new PdfTextExtractor(pr);
        String pageAsString = pdfExtractor.getTextFromPage(1);
        Matcher dateMatcher = Pattern.compile("([\\w]+, [\\d]+.[\\d]+.[\\d]{4})").matcher(pageAsString);
        List<String> allMatches = new ArrayList<>();
        while(dateMatcher.find()) {
            allMatches.add(dateMatcher.group());
        }
        assertEquals("Freitag, 11.11.2022", allMatches.get(0));
        assertEquals("Samstag, 12.11.2022", allMatches.get(1));
        assertEquals("Sonntag, 13.11.2022", allMatches.get(2));
        pr.close();
    }

    /**This test checks whether entries are sorted by time before writing to pdf
     * The Regex only checks for times, to make sure the test doesn't break
     * when exchanging Journal Entry Types
     * */
    @Test
    public void entriesAreSortedByTime() throws IOException {
        PdfReader pr = new PdfReader(journalExtractPath);
        PdfTextExtractor pdfExtractor = new PdfTextExtractor(pr);
        String pageAsString = pdfExtractor.getTextFromPage(1);
        Matcher dateMatcher = Pattern.compile("([\\d]{2}:[\\d]{2})").matcher(pageAsString);
        List<String> allMatches = new ArrayList<>();
        while(dateMatcher.find()) {
            allMatches.add(dateMatcher.group());
        }
        assertEquals("12:30", allMatches.get(0));
        assertEquals("00:00", allMatches.get(1));
        assertEquals("11:30", allMatches.get(2));
        assertEquals("10:00", allMatches.get(3));
        assertEquals("14:30", allMatches.get(4));
        pr.close();
    }

    /**This Test checks whether the timeframe
     * (date Earliest JournalEntry to date of latest Journalentry)
     * Is written to the PDF
     */
    @Test
    public void timeframeIsWrittenToPDF() throws IOException {
        PdfReader pr = new PdfReader(journalExtractPath);
        PdfTextExtractor pdfExtractor = new PdfTextExtractor(pr);
        String pageAsString = pdfExtractor.getTextFromPage(1);
        assertTrue(pageAsString.contains("Vom 11.11.2022 bis 21.11.2022"));
    }

    /**This test checks whether the pageCount is written to the Footer of the PDF
     * (The Footer is at the beginning of the page String)
     */
    @Test
    public void pageCountIsWrittenToFooter() throws IOException {
        PdfReader pr = new PdfReader(journalExtractPath);
        PdfTextExtractor pdfExtractor = new PdfTextExtractor(pr);
        String pageAsString = pdfExtractor.getTextFromPage(1);
        assertTrue(pageAsString.startsWith("1. Seite"));
    }

    /**This test checks whether Umlaute are properly written to the PDF */
    @Test
    public void umlauteAreWrittenCorrectly() throws IOException {
        PdfReader pr = new PdfReader(journalExtractPath);
        PdfTextExtractor pdfExtractor = new PdfTextExtractor(pr);
        String pageAsString = pdfExtractor.getTextFromPage(1);
        assertTrue(pageAsString.contains("M\u00f6glicherweise"));
    }

    /**This test checks whether a newline is automatically inserted into paragraphs
     * that are too long to be displayed on one line. */
    @Test
    public void textIsWrappedToNewLine() throws IOException {
        PdfReader pr = new PdfReader(journalExtractPath);
        PdfTextExtractor pdfExtractor = new PdfTextExtractor(pr);
        String pageAsString = pdfExtractor.getTextFromPage(1);
        assertTrue(pageAsString.contains("Kopfschmerzen bekommen.\n"));

    }

    /**This test checks whether the mood is translated from its enum string to german */
    @Test
    public void moodIsTranslatedToGerman() throws IOException {
        PdfReader pr = new PdfReader(journalExtractPath);
        PdfTextExtractor pdfExtractor = new PdfTextExtractor(pr);
        String pageAsString = pdfExtractor.getTextFromPage(1);
        assertTrue(pageAsString.contains("Gl\u00fccklich"));
    }



}
