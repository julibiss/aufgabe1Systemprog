package enhancedservice;

import de.hshn.mi.pdbg.PersistentObject;
import de.hshn.mi.pdbg.basicservice.Patient;

import java.util.Date;

/**
 * {@link Finding} references all the Diagnosis and the examination results of a patient.
 */
public interface Finding extends PersistentObject {
    /**
     * Creates and returns a {@link Patient} object.
     * @ param firstname
     * @ param lastname
     */
    public Patient createPatient(String firstname, String lastname);

    /**
     * Adds a {@link Diagnosis} object to our diagnosis list.
     * @ param iCDCode
     * @ param diagnosisText
     */
    public void addDiagnosis(String icdCode, String diagnosisText);

    /**
     * Adds a {@link Diagnosis} object to our diagnosis list.
     * @ param resultSummary
     * @ param requestDate
     * @ param resultDate
     */
    public void addExaminationResult(String resultSummary, Date requestDate, Date resultDate);

    /**
     * @ param date must not be {@code null}.
     */
    public void setFindingDate(Date date);

    /**
     * @ param summary No restrictions on this parameter.
     */
    public void setSummary(String summary);

    /**
     * @ return findingDate.
     */
    public Date getFindingDate();

    /**
     * @ return summary.
     */
    public String getSummary();

}
