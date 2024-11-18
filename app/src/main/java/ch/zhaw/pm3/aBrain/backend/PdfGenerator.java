package ch.zhaw.pm3.aBrain.backend;

import ch.zhaw.pm3.aBrain.backend.journal.entry.*;
import ch.zhaw.pm3.aBrain.backend.med.Medication;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**This class generates a PDF given an unordered list of Journal Entries
 This class uses the OpenPDF library*/
public class PdfGenerator {

    private final Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 28, Font.BOLD);
    private final Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
    private final Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);


    /**
     * This method orders a list of Journal entries, maps them to days,
     * and generates a pdf with the data at a given path.
     *
     * @param journalEntries unordered list of JournalEntries
     * @param filepath Path where file will be saved
     */
    public void generatePDF(List<JournalEntry> journalEntries, String filepath) {
        Document document = new Document(PageSize.A4, 10f, 10f, 0, 10f);
        document.setDocumentLanguage("de_GER");
        Map<LocalDate, List<JournalEntry>> days = mapToDays(journalEntries);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filepath));
            document.setFooter(createFooter());

            document.open();
            document.add(createLogo());
            document.add(createTitle());
            document.add(createTimeframe(journalEntries));

            days.keySet().stream().sorted().forEach(day -> {
                document.add(createDayTitle(day));
                List<JournalEntry> entriesOfTheDay = days.get(day);
                entriesOfTheDay.stream()
                        .sorted(Comparator.comparing(JournalEntry::getDateTime))
                        .forEach(entry -> document.add(createEntryParagraph(entry)));
            });
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
        document.close();
    }

    private Paragraph createEntryParagraph(JournalEntry entry) {
        Class typeOfEntry = entry.getClass();
        if (typeOfEntry == MedIntake.class) return medIntakeParagraph((MedIntake) entry);
        if (typeOfEntry == Mood.class) return moodParagraph((Mood) entry);
        if (typeOfEntry == SideEffect.class) return sideEffectParagraph((SideEffect) entry);
        if (typeOfEntry == Sport.class) return sportParagraph((Sport) entry);
        return defaultParagraph(entry);
    }

    private Paragraph sportParagraph(Sport entry) {
        String hour = getHourFromEntry(entry);
        String minute = getMinuteFromEntry(entry);
        return new Paragraph(hour + ":" + minute + " Eingabe Sport: " + entry.getSport(), normalFont);
    }

    private Paragraph sideEffectParagraph(SideEffect entry) {
        String hour = getHourFromEntry(entry);
        String minute = getMinuteFromEntry(entry);
        return new Paragraph(hour + ":" + minute + " Eingabe Nebeneffekt: " + entry.getSideEffect(), normalFont);
    }

    private Paragraph moodParagraph(Mood entry) {
        String hour = getHourFromEntry(entry);
        String minute = getMinuteFromEntry(entry);
        String englishEntry = entry.getMood().toString().toUpperCase();
        String germanEntry = "";
        switch (englishEntry) {
            case "HAPPY" -> germanEntry = "Gl\u00fccklich";
            case "FINE" -> germanEntry = "Zufrieden";
            case "INDIFFERENT" -> germanEntry = "Neutral";
            case "ANGRY" -> germanEntry = "W\u00fctend";
            case "SAD" -> germanEntry = "Traurig";
        }
        return new Paragraph(hour + ":" + minute + " Eingabe Stimmung: " + germanEntry, normalFont);
    }

    private Paragraph medIntakeParagraph(MedIntake medIntake) {
        String hour = getHourFromEntry(medIntake);
        String minute = getMinuteFromEntry(medIntake);
        Medication medication  = medIntake.getMedication();
        return new Paragraph(hour + ":" + minute + " Einnahme Medikament: " + medication.getName(), normalFont);
    }

    private Paragraph defaultParagraph(JournalEntry entry) {
        String hour = getHourFromEntry(entry);
        String minute = getMinuteFromEntry(entry);
        return new Paragraph(hour + ":" + minute + " " + entry.getClass().toString(), normalFont);
    }

    private Paragraph createDayTitle(LocalDate day) {
        Paragraph dayTitle = new Paragraph("\n", dateFont);
        dayTitle.add(day.format(DateTimeFormatter.ofPattern("EEEE, dd.MM.yyyy", Locale.GERMAN)));
        return dayTitle;
    }

    private Paragraph createTimeframe(List<JournalEntry> journalEntries) {
        LocalDate startDate = journalEntries.stream().min(Comparator.comparing(JournalEntry::getDateTime)).get().getDateTime().toLocalDate();
        LocalDate endDate = journalEntries.stream().max(Comparator.comparing(JournalEntry::getDateTime)).get().getDateTime().toLocalDate();

        Paragraph timeframe = new Paragraph("Vom "
                + startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                + " bis "
                + endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                + "\n\n"
                , normalFont);
        timeframe.setAlignment(Element.ALIGN_CENTER);
        return timeframe;
    }

    private Paragraph createTitle() {
        Paragraph title = new Paragraph("Journal Extrakt\n", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        return title;
    }

    private HeaderFooter createFooter() {
        HeaderFooter footer = new HeaderFooter(true, new Phrase(". Seite"));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setBorderWidthBottom(0);
        return footer;
    }

    private Paragraph createLogo() throws IOException {
        Paragraph image = new Paragraph("");
        Image logo = Image.getInstance("src/main/resources/ch/zhaw/pm3/aBrain/pdfGenerator/aBrainLogo.jpg");
        logo.setAlignment(Image.UNDERLYING);
        logo.scaleAbsolute(100, 100);
        logo.setIndentationLeft(475);
        image.add(logo);
        return image;
    }

    private String getMinuteFromEntry(JournalEntry entry) {
        String[] d = entry.getDateTime().toString().split("[T:]");
        return d[2];
    }

    private String getHourFromEntry(JournalEntry entry) {
        String[] d = entry.getDateTime().toString().split("[T:]");
        return d[1];
    }

    private Map<LocalDate, List<JournalEntry>> mapToDays(List<JournalEntry> journalEntries) {
        return journalEntries.stream().collect(Collectors.groupingBy(j -> j.getDateTime().toLocalDate()));

    }
}
