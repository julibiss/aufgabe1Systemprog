package enhancedservice;

import de.hshn.mi.pdbg.PersistentObject;
import de.hshn.mi.pdbg.basicservice.Patient;

import java.util.Date;
import java.util.List;

/**
 * {@link Finding} references all the Diagnosis and the examination results of a patient.
 */
public interface Finding extends PersistentObject {
    /**
     * Creates and returns a {@link Patient} object.
     * @ param firstname
     * @ param lastname
     * @ param birthDate
     * @ param insuranceCompany
     * @ param insuranceNumber
     */
    public void setPatient(String firstname, String lastname, Date birthDate,
                           String insuranceCompany, String insuranceNumber);

    /**
     * Returns a {@link Patient} object based on the given patientID.
     * @ param patientID
     */
    public Patient getPatient(long patientID);

    /**
     * Adds a {@link Diagnosis} object to our diagnosis list.
     * @ param iCDCode
     * @ param diagnosisText
     */
    public void addDiagnosis(String icdCode, String diagnosisText);

    /**
     * @ param icdCode must not be {@code null}.
     *         Used to find a diagnosis based on the given code.
     * @ return A list of {@link Diagnosis} objects or {@code null}, if none was
     *         found with the given {@code icdCode}.
     * @ throws FetchException
     *             Thrown if an error occurred while fetching an object.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    public List<Diagnosis> getDiagnosis(String icdCode);

    /**
     * Adds a {@link Examination} object to our diagnosis list.
     * @ param resultSummary
     * @ param requestDate
     * @ param resultDate
     */
    public void addExaminationResult(String resultSummary, Date requestDate, Date resultDate);

    /**
     * Returns a list of {@link ExaminationResult} objects that match a set of search
     * criteria. When searching via first name and last name, one may use the
     * SQL wildcards ("%", "*", "_") in the search string.
     * @ param patientID
     *            Must not be {@code null} and must be greater than 0
     * @ return A list of matching {@link ExaminationResult} objects is returned. It may be empty.
     * @ throws FetchException
     *             Thrown if an error occurred while fetching an object.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    List<ExaminationResult> getExaminationResults(long patientID);

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
