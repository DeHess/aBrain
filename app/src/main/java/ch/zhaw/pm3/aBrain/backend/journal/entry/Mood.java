package ch.zhaw.pm3.aBrain.backend.journal.entry;

import java.time.LocalDateTime;

/**
 * The type Mood.
 *
 * @author baermlau, wilphi01
 * @version 1.0
 */
public class Mood extends JournalEntry {
    /**
     * The enum Mood type.
     */
    public enum MoodType {
        /**
         * Happy mood type.
         */
        HAPPY(0),
        /**
         * Fine mood type.
         */
        FINE(1),
        /**
         * Indifferent mood type.
         */
        INDIFFERENT(2),
        /**
         * Angry mood type.
         */
        ANGRY(3),
        /**
         * Sad mood type.
         */
        SAD(4);
    
        /**
         * The Numerical value.
         */
        int numericalValue;
        
        MoodType(int numericalValue) {
            this.numericalValue = numericalValue;
        }
    
        /**
         * Gets numerical value.
         *
         * @return the numerical value
         */
        public int getNumericalValue() {
            return numericalValue;
        }
    }
    
    /**
     * The Mood.
     */
    private int moodId;
    private MoodType mood;
    
    
    /**
     * Instantiates a new Mood.
     *
     * @param mood the mood
     */
    public Mood(MoodType mood) {
        super();
        this.mood = mood;
    }

    public Mood(int moodId, MoodType mood, int jrnId, LocalDateTime jrnDate) {
        setMoodId(moodId);
        setMood(mood);
        setEntryId(jrnId);
        setDateTime(jrnDate);
    }

    public int getMoodId() {
        return moodId;
    }

    public void setMoodId(int moodId) {
        this.moodId = moodId;
    }

    public MoodType getMood() {
        return mood;
    }

    public void setMood(MoodType mood) {
        this.mood = mood;
    }

}
