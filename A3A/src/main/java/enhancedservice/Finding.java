package enhancedservice;

import de.hshn.mi.pdbg.PersistentObject;
import de.hshn.mi.pdbg.basicservice.Patient;

import java.util.Date;
import java.util.List;

/**
 * {@link Finding} references all the Diagnosis and the examination results of a patient.
 */
public interface Finding extends Examination {
    /**
     * Creates and returns a {@link Patient} object.
     * @ param firstname
     * @ param lastname
     * @ param birthDate
     * @ param insuranceCompany
     * @ param insuranceNumber
     */
    public void setPatient(Patient patient);

    /**
     * Returns a {@link Patient} object.
     */
    public Patient getPatient();

    /**
     * Adds a {@link Diagnosis} object to our diagnosis list.
     * @ param diagnosis
     */
    public void addDiagnosis(Diagnosis diagnosis);

    /**
     * @ return A list of {@link Diagnosis} objects.
     * @ throws FetchException
     *             Thrown if an error occurred while fetching an object.
     */
    public List<Diagnosis> getDiagnosis();

    /**
     * Adds a {@link Examination} object to our diagnosis list.
     * @ param resultSummary
     * @ param requestDate
     * @ param resultDate
     */
    public void addExaminationResult(String resultSummary, Date requestDate, Date resultDate);

    /**
     * Returns a list of {@link ExaminationResult} objects.
     * @ return A list of matching {@link ExaminationResult} objects is returned. It may be empty.
     * @ throws FetchException
     *             Thrown if an error occurred while fetching an object.
     */
    List<ExaminationResult> getExaminationResults();

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
