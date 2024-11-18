package ch.zhaw.pm3.aBrain.backend.datastorage.databaseOperation;

import ch.zhaw.pm3.aBrain.backend.med.Medication;
import ch.zhaw.pm3.aBrain.backend.datastorage.DatabaseConnection;
import ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation.DataPharmacyOperation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the pharmacy operations with the database.
 * This class implements the {@link DataPharmacyOperation} interface.
 *
 * @author wilphi01
 * @version 1.0
 */
public class DatabasePharmacyOperation implements DataPharmacyOperation {

    private Connection dbConnection = DatabaseConnection.getInstance().getConnection();

    /**
     * This method adds a medication to the database.
     * @param medication is the medication to be added.
     */
    @Override
    public void addMedication(Medication medication) {
        String sql = "INSERT INTO T_MED_DAT (C_NAME, C_DESCRIPTION, C_DOSAGE, C_PRICE, C_STOCK, C_IMAGE) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement insert = null;

        try {
            insert = this.dbConnection.prepareStatement(sql);
            /*insert.setString(1, medication.getName());
            insert.setString(2, medication.getDescription());
            insert.setString(3, medication.getDosage());
            insert.setDouble(4, medication.getPrice());
            insert.setInt(5, medication.getStock());
            insert.setBlob(6, medication.getImage());*/
            insert.executeUpdate();

        } catch (Exception e) {
            // TODO Logger Warnung
        } finally {
            try {
                insert.close();

            } catch (SQLException e) {
                // TODO Logger Warnung
            }
        }

    }

    /**
     * This method deletes a medication from the database.
     * @param medication is the medication to be deleted.
     */
    @Override
    public void deleteMedication(Medication medication) {
        String sql = "DELETE FROM T_MED_DAT WHERE C_ID = ?";
        PreparedStatement delete = null;

        try {
            delete = this.dbConnection.prepareStatement(sql);
            delete.setInt(1, medication.getMedId());
            delete.executeUpdate();

        } catch (Exception e) {
            // TODO Logger Warnung
        } finally {
            try {
                delete.close();

            } catch (SQLException e) {
                // TODO Logger Warnung
            }
        }

    }

    /**
     * This method updates a medication in the database.
     * @param medication is the medication to be updated.
     */
    @Override
    public void updateMedication(Medication medication) {
        String sql = "UPDATE T_MED_DAT SET C_NAME = ?, C_DESCRIPTION = ?, C_DOSAGE = ?, C_PRICE = ?, C_STOCK = ?, C_IMAGE = ? WHERE C_ID = ?";
        PreparedStatement update = null;

        try {

        } catch (Exception e) {
            // TODO Logger Warnung
        } finally {
            try {
                update.close();

            } catch (SQLException e) {
                // TODO Logger Warnung
            }
        }

    }

    @Override
    public List<Medication> getAllMedicationsList() {
        List<Medication> medicationList = new ArrayList<Medication>();
        String sql = "SELECT * FROM T_USR_MFM WHERE C_ID_1 = ?";
        PreparedStatement select = null;
        ResultSet result = null;

        try {
            select = this.dbConnection.prepareStatement(sql);
            result = select.executeQuery();

            while(result.next()) {
                System.out.println("Result found");
                //medicationList.add(new Medication(result.getInt("C_ID"), getMedicationName(result.getInt("C_ID_2"))));
            }

        } catch (Exception e) {
            // TODO Logger Warnung
        } finally {
            try {
                select.close();
                result.close();

            } catch (SQLException e) {
                // TODO Logger Warnung
            }
        }

        return medicationList;
    }

    public String getMedicationName(int mfmId) {
        String sql = "SELECT * FROM T_MED_DAT WHERE C_ID = ?";
        PreparedStatement select = null;
        ResultSet result = null;
        String medicationName = "";

        try {
            select = this.dbConnection.prepareStatement(sql);
            select.setInt(1, mfmId);
            result = select.executeQuery();

            while(result.next()) {
                medicationName = result.getString("MED_NAME");
            }

        } catch (Exception e) {
            // TODO Logger Warnung
        } finally {
            try {
                select.close();
                result.close();
            } catch (SQLException e) {
                // TODO Logger Warnung
            }
        }

        return medicationName;
    }
}
