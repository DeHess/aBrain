package ch.zhaw.pm3.aBrain.backend.datastorage.databaseOperation;

import ch.zhaw.pm3.aBrain.backend.User;
import ch.zhaw.pm3.aBrain.backend.datastorage.DatabaseConnection;
import ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation.DataJournalOperation;
import ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation.DataLoggerOperation.LogType;
import ch.zhaw.pm3.aBrain.backend.journal.entry.*;
import ch.zhaw.pm3.aBrain.backend.journal.entry.Mood.MoodType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the journal operations with the database.
 * This class implements the {@link DataJournalOperation} interface.
 *
 * @author wilphi01
 * @version 1.0
 */
public class DatabaseJournalOperation implements DataJournalOperation {

    private final Connection dbConnection = DatabaseConnection.getInstance().getConnection();
    private final DatabaseLoggerOperation logger = new DatabaseLoggerOperation();

    @Override
    public List<JournalEntry> getAllJournalEntriesList() {
        List<JournalEntry> journalEntries = new ArrayList<>();

        journalEntries.addAll(getMedIntakeEntriesList());
        journalEntries.addAll(getMoodEntriesList());
        journalEntries.addAll(getSideEffectEntriesList());
        journalEntries.addAll(getSportEntriesList());

        return journalEntries;
    }

    @Override
    public List<MedIntake> getMedIntakeEntriesList() {
        List<MedIntake> medIntakeList = new ArrayList<MedIntake>();
        String sql = "SELECT * FROM T_JRN_MEI WHERE C_ID = ?";
        PreparedStatement select = null;
        ResultSet result = null;

        try {
            select = this.dbConnection.prepareStatement(sql);
            select.setInt(1, User.getInstance().getUserId());
            result = select.executeQuery();

            while (result.next()) {
            }


        } catch (SQLException e) {
            logger.writeLog("Error while getting medintake entries from database.", LogType.ERROR);
        } finally {
            try {
                if (select != null) {
                    select.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing the select / result statement.", LogType.WARNING);
            }
        }

        return medIntakeList;
    }

    @Override
    public List<Mood> getMoodEntriesList() {
        List<Mood> moodList = new ArrayList<Mood>();
        String sql = "SELECT * FROM T_JRN_MOD WHERE T_JRN_DAT IN (SELECT C_ID FROM T_JRN_DAT WHERE T_JRN_AUTHOR = ?)";
        PreparedStatement select = null;
        ResultSet result = null;

        try {
            select = this.dbConnection.prepareStatement(sql);
            select.setInt(1, User.getInstance().getUserId());
            result = select.executeQuery();

            while (result.next()) {
                moodList.add(new Mood(result.getInt("C_ID"), getMoodEnum(result.getInt("C_MOOD")), result.getInt("T_JRN_DAT"), getDateOfJournalEntry(result.getInt("T_JRN_DAT"))));
            }

        } catch (SQLException e) {
            logger.writeLog("Error while getting mood entries from database.", LogType.ERROR);
        } finally {
            try {
                if (select != null) {
                    select.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing the select / result statement.", LogType.WARNING);
            }
        }

        return moodList;
    }

    private LocalDateTime getDateOfJournalEntry(int jrnId) {
        LocalDateTime dateTime = null;
        String sql = "SELECT C_JRN_DATE FROM T_JRN_DAT WHERE C_ID = ?";
        PreparedStatement select = null;
        ResultSet result = null;

        try {
            select = this.dbConnection.prepareStatement(sql);
            select.setInt(1, jrnId);
            result = select.executeQuery();

            while (result.next()) {
                dateTime = result.getTimestamp("C_JRN_DATE").toLocalDateTime();
            }

        } catch (SQLException e) {
            logger.writeLog("Error while getting date of journal entry with the id " + jrnId + " from database.", LogType.ERROR);
        } finally {
            try {
                if (select != null) {
                    select.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing the select / result statement.", LogType.WARNING);
            }
        }

        return dateTime;
    }

    private MoodType getMoodEnum(int moodValue) {
        MoodType moodEnum = null;

        for (MoodType mood : MoodType.values()) {
            if (mood.getNumericalValue() == moodValue) {
                moodEnum = mood;
            }
        }

        return moodEnum;
    }

    @Override
    public List<SideEffect> getSideEffectEntriesList() {
        List<SideEffect> sideEffectList = new ArrayList<SideEffect>();
        String sql = "SELECT * FROM T_JRN_SIE WHERE T_JRN_DAT IN (SELECT C_ID FROM T_JRN_DAT WHERE T_JRN_AUTHOR = ?)";
        PreparedStatement select = null;
        ResultSet result = null;

        try {
            select = this.dbConnection.prepareStatement(sql);
            select.setInt(1, User.getInstance().getUserId());
            result = select.executeQuery();

            while (result.next()) {
                sideEffectList.add(new SideEffect(result.getInt("C_ID"), result.getString("C_SIDE_EFFECT"), result.getInt("T_JRN_DAT"), getDateOfJournalEntry(result.getInt("T_JRN_DAT"))));
            }

        } catch (SQLException e) {
            logger.writeLog("Error while getting side effect entries from database.", LogType.ERROR);
        } finally {
            try {
                if (select != null) {
                    select.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing the select / result statement.", LogType.WARNING);
            }
        }

        return sideEffectList;
    }

    @Override
    public List<Sport> getSportEntriesList() {
        List<Sport> sportList = new ArrayList<Sport>();
        String sql = "SELECT * FROM T_JRN_SPR WHERE T_JRN_DAT IN (SELECT C_ID FROM T_JRN_DAT WHERE T_JRN_AUTHOR = ?)";
        PreparedStatement select = null;
        ResultSet result = null;

        try {
            select = this.dbConnection.prepareStatement(sql);
            select.setInt(1, User.getInstance().getUserId());
            result = select.executeQuery();

            while (result.next()) {
                sportList.add(new Sport(result.getInt("C_ID"), result.getString("C_SPORT"), result.getInt("T_JRN_DAT"), getDateOfJournalEntry(result.getInt("T_JRN_DAT"))));
            }

        } catch (SQLException e) {
            logger.writeLog("Error while getting sport entries from database.", LogType.ERROR);
        } finally {
            try {
                if (select != null) {
                    select.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing the select / result statement.", LogType.WARNING);
            }
        }

        return sportList;
    }

    @Override
    public JournalEntry getJournalEntry(int entryId) {

        List<JournalEntry> journalEntries = getAllJournalEntriesList();

        for (JournalEntry journalEntry : journalEntries) {
            if (journalEntry.getEntryId() == entryId) {
                return journalEntry;
            }
        }

        return null;
    }

    /**
     * This method writes a journal entry to the database.
     * @param entry is the journal entry to be added.
     */
    @Override
    public void addJournalEntry(JournalEntry entry) {
        String sql = "INSERT INTO T_JRN_DAT (C_JRN_DATE, T_JRN_AUTHOR) VALUES (?, ?)";
        PreparedStatement insert = null;
        int entryId = 0;

        try {
            insert = this.dbConnection.prepareStatement(sql);
            insert.setTimestamp(1, Timestamp.valueOf(entry.getDateTime()));
            insert.setInt(2, User.getInstance().getUserId());
            insert.execute();

            entryId = getJournalId();

        } catch (SQLException e) {
            logger.writeLog("Error while adding journal entry to database.", LogType.ERROR);
        } finally {
            try {
                if (insert != null) {
                    insert.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing insert statement.", LogType.WARNING);
            }
        }

        switch (entry.getClass().getSimpleName()) {
            case "MedIntake" -> addMedIntakeEntry((MedIntake) entry, entryId);
            case "Mood" -> addMoodEntry((Mood) entry, entryId);
            case "SideEffect" -> addSideEffectEntry((SideEffect) entry, entryId);
            case "Sport" -> addSportEntry((Sport) entry, entryId);

        }

    }

    /**
     * This method adds a med intake entry to the database.
     * @param entry is the med intake entry to be added.
     * @param jrnId is the id of the journal entry.
     */
    private void addMedIntakeEntry(MedIntake entry, int jrnId) {
        String sql = "INSERT INTO T_JRN_MEI (T_JRN_DAT, T_USR_MFM) VALUES (?, ?)";
        PreparedStatement insert = null;

        try {
            insert = this.dbConnection.prepareStatement(sql);
            insert.setInt(1, jrnId);
            insert.setInt(2, entry.getMedication().getMedId());
            insert.execute();

        } catch (SQLException e) {
            logger.writeLog("Error while adding medintake entry to database with the jorunal id " + jrnId + ".", LogType.ERROR);
        } finally {
            try {
                if (insert != null) {
                    insert.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing insert statement.", LogType.WARNING);
            }
        }
    }

    /**
     * This method adds a mood entry to the database.
     * @param entry is the mood entry to be added.
     * @param jrnId is the id of the journal entry.
     */
    private void addMoodEntry(Mood entry, int jrnId) {
        String sql = "INSERT INTO T_JRN_MOD (C_MOOD, T_JRN_DAT) VALUES (?, ?)";
        PreparedStatement insert = null;

        try {
            insert = this.dbConnection.prepareStatement(sql);
            insert.setInt(1, entry.getMood().getNumericalValue());
            insert.setInt(2, jrnId);
            insert.execute();

        } catch (SQLException e) {
            logger.writeLog("Error while adding mood entry to database with the jorunal id " + jrnId + ".", LogType.ERROR);
        } finally {
            try {
                if (insert != null) {
                    insert.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing insert statement.", LogType.WARNING);
            }
        }

    }

    /**
     * This method adds a side effect entry to the database.
     * @param entry is the side effect entry to be added.
     * @param jrnId is the id of the journal entry.
     */
    private void addSideEffectEntry(SideEffect entry, int jrnId) {
        String sql = "INSERT INTO T_JRN_SIE (C_SIDE_EFFECT, T_JRN_DAT) VALUES (?, ?)";
        PreparedStatement insert = null;

        try {
            insert = this.dbConnection.prepareStatement(sql);
            insert.setString(1, entry.getSideEffect());
            insert.setInt(2, jrnId);
            insert.execute();

        } catch (SQLException e) {
            logger.writeLog("Error while adding sideeffect entry to database with the jorunal id " + jrnId + ".", LogType.ERROR);
        } finally {
            try {
                if (insert != null) {
                    insert.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing insert statement.", LogType.WARNING);
            }
        }
    }

    /**
     * This method adds a sport entry to the database.
     * @param entry is the sport entry to be added.
     * @param jrnId is the id of the journal entry.
     */
    private void addSportEntry(Sport entry, int jrnId) {
        String sql = "INSERT INTO T_JRN_SPR (C_SPORT, T_JRN_DAT) VALUES (?, ?)";
        PreparedStatement insert = null;

        try {
            insert = this.dbConnection.prepareStatement(sql);
            insert.setString(1, entry.getSport());
            insert.setInt(2, jrnId);
            insert.execute();

        } catch (SQLException e) {
            logger.writeLog("Error while adding sport entry to database with the jorunal id " + jrnId + ".", LogType.ERROR);
        } finally {
            try {
                if (insert != null) {
                    insert.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing insert statement.", LogType.WARNING);
            }
        }
    }

    /**
     * This method deletes a journal entry from the database.
     * @param entry is the journal entry to be deleted.
     */
    @Override
    public void deleteJournalEntry(JournalEntry entry) {
        int jrnDatId = entry.getEntryId();

        String sql = getDeleteJournalEntrySql(entry);
        PreparedStatement delete = null;

        try {
            delete = this.dbConnection.prepareStatement(sql);
            delete.execute();

        } catch (SQLException e) {
            logger.writeLog("Error while deleting journal entry with the id " + jrnDatId + " from the database.", LogType.ERROR);
        } finally {
            try {
                if (delete != null) {
                    delete.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing delete statement.", LogType.WARNING);
            }
        }

        deleteJournalDat(jrnDatId);
    }

    /**
     * This method returns the sql statement for deleting a journal entry.
     * @param entry is the journal entry to be deleted.
     * @return the sql statement for deleting a journal entry.
     */
    private String getDeleteJournalEntrySql(JournalEntry entry) {
        String sql = "";

        switch (entry.getClass().getSimpleName()) {
            case "MedIntake" -> {
                sql = "DELETE FROM T_JRN_MEI WHERE T_JRN_DAT = " + ((MedIntake) entry).getMedIntakeId() + ";";
            }
            case "Mood" -> {
                sql = "DELETE FROM T_JRN_MOD WHERE T_JRN_DAT = " + ((Mood) entry).getMoodId() + ";";
            }
            case "SideEffect" -> {
                sql = "DELETE FROM T_JRN_SIE WHERE T_JRN_DAT = " + ((SideEffect) entry).getSideEffectId() + ";";
            }
            case "Sport" -> {
                sql = "DELETE FROM T_JRN_SPR WHERE T_JRN_DAT = " + ((Sport) entry).getSportId() + ";";
            }
        }

        return sql;
    }

    /**
     * This method deletes a journal entry from the database.
     * @param entryId is the id of the journal entry to be deleted.
     */
    private void deleteJournalDat(int entryId) {
        String sql = "DELETE FROM T_JRN_DAT WHERE C_ID = ?;";
        PreparedStatement delete = null;

        try {
            delete = this.dbConnection.prepareStatement(sql);
            delete.setInt(1, entryId);
            delete.execute();

        } catch (SQLException e) {
            logger.writeLog("Error while deleting journal entry with id " + entryId + " from database.", LogType.ERROR);
        } finally {
            try {
                if (delete != null) {
                    delete.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing delete statement.", LogType.WARNING);
            }
        }
    }

    /**
     * This method updates a journal entry in the database.
     * @param entry is the journal entry to be updated.
     */
    @Override
    public void updateJournalEntry(JournalEntry entry) {
        String sql = getUpdateSql(entry);
        PreparedStatement update = null;

        try {
            update = this.dbConnection.prepareStatement(sql);
            update.setTimestamp(1, Timestamp.valueOf(entry.getDateTime()));
            update.setInt(2, entry.getEntryId());
            update.execute();

        } catch (SQLException e) {
            logger.writeLog("Error while updating journal entry with the id " + entry.getEntryId() + ".", LogType.ERROR);
        } finally {
            try {
                if (update != null) {
                    update.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing update statement.", LogType.WARNING);
            }
        }
    }

    /**
     * This method returns the sql statement for updating a journal entry.
     * @param entry is the journal entry to be updated.
     * @return the sql statement for updating a journal entry.
     */
    private String getUpdateSql(JournalEntry entry) {
        String sql = "UPDATE T_JRN_DAT SET C_JRN_DATE = ? WHERE C_ID = ?;";
        switch (entry.getClass().getSimpleName()) {
            case "MedIntake" -> sql += "UPDATE T_JRN_MEI SET ";
            case "Mood" -> sql += "UPDATE T_JRN_MOD SET C_MOOD = " + ((Mood) entry).getMood() + " WHERE C_ID = " + ((Mood) entry).getMoodId() + ";";
            case "SideEffect" -> sql += "UPDATE T_JRN_SIE SET C_SIDE_EFFECT = " + ((SideEffect) entry).getSideEffect() + " WHERE C_ID = " + ((SideEffect) entry).getSideEffectId() + ";";
            case "Sport" -> sql += "UPDATE T_JRN_SPR SET C_SPORT = " + ((Sport) entry).getSportId() + " WHERE C_ID = " + ((Sport) entry).getSportId() + ";";
        }

        return sql;

    }

    /**
     * This method returns the current highest id of all journal entries.
     * @return the current highest id of all journal entries.
     */
    private int getJournalId() {
        String sql = "SELECT MAX(C_ID) FROM T_JRN_DAT";
        Statement select = null;
        ResultSet result = null;
        int entryId = 0;

        try {
            select = this.dbConnection.createStatement();
            result = select.executeQuery(sql);

            while (result.next()) {
                entryId = result.getInt(1);
            }

        } catch (SQLException e) {
            logger.writeLog("Error while getting highest journal id.", LogType.ERROR);
        } finally {
            try {
                if (select != null) {
                    select.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException e) {
                logger.writeLog("Error while closing statement / result.", LogType.WARNING);
            }
        }

        return entryId;
    }
}
