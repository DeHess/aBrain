package ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation;

import ch.zhaw.pm3.aBrain.backend.med.Medication;

import java.util.List;

/**
 * This interface defines all pharmacy operations.
 *
 * @author wilphi01
 * @version 1.0
 */
public interface DataPharmacyOperation {

    List<Medication> getAllMedicationsList();

    void addMedication(Medication medication);

    void deleteMedication(Medication medication);

    void updateMedication(Medication medication);
}
