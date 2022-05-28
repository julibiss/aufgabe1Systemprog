package enhancedservice;

import de.hshn.mi.pdbg.PersistentObject;
import de.hshn.mi.pdbg.basicservice.BasicDBService;
import de.hshn.mi.pdbg.basicservice.Patient;
import de.hshn.mi.pdbg.exception.StoreException;

import java.util.Date;
import java.util.List;

/**
 * This service mainly supports three types of methods.
 * <ol>
 * <li>The methods named <code>create...()</code> creates persistable objects.
 * <b>Note well that the objects are not yet persistent after this step.</b>
 * <li>Methods named <code>remove...()</code> remove objects from the
 * persistence store.
 * <li>Methods named <code>get...()</code> can be used to retrieve objects from
 * the persistence store.
 * </ol>
 */
public interface EnhancedDBService extends BasicDBService {

    /**
     * @ param type
     *            must not be {@code null} and must not be {@code ""}
     * @ param bodyPart
     *            must not be negative
     * @ return A {@link ClinicalExamination} object.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    ClinicalExamination createClinicalExamination(String type, String bodyPart);


    /**
     * @ param icdCode
     *            must not be {@code null} and must not be {@code ""}
     * @ param diagnosisText
     *            must not be {@code null} and must not be {@code ""}
     * @ return A {@link Diagnosis} object.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    Diagnosis createDiagnosis(String icdCode, String diagnosisText);

    /**
     * @ param type
     *            must not be {@code null} and must not be {@code ""}
     * @ return A {@link Examination} object.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    Examination createExamination(String type);

    /**
     * @ param resultSummary
     *            must not be {@code null} and must not be {@code ""}
     * @ param requestDate
     *            must not be negative and must not be {@code null}
     * @ param resultDate
     *            must not be negative and must not be {@code null}
     * @ param examination
     *            must not be {@code null}
     * @ return A {@link ExaminationResult} object.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    ExaminationResult createExaminationResult(String resultSummary, Date requestDate, Date resultDate,
                                              List<Examination> examinations);

    /**
     * @ param date
     *            must not be {@code null}
     * @ param summary
     *            must not be {@code null} and must not be {@code ""}
     * @ param diagnosisList
     *            must not be {@code null}
     * @ param examinationResults
     *            must not be {@code null}
     * @ return A {@link Diagnosis} object.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    Finding createFinding(Date date, String summary, Patient patient, List<Diagnosis> diagnosisList,
                          List<ExaminationResult> examinationResults);

    /**
     * @ param type
     *            must not be {@code null} and must not be {@code ""}
     * @ return A {@link TechnicalExamination} object.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    TechnicalExamination createTechnicalExamination(String type);

    /**
     * @ param type
     *            must not be {@code null} and must not be {@code ""}
     * @ param testType
     *            must not be {@code null} and must not be {@code ""}
     * @ param standardValue
     *            must not be {@code null} and must be greater or equal to 0
     * @ return A {@link LaboratoryTest} object.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    LaboratoryTest createLaboratoryTest(String type, String testType, double standardValue);

    /**
     * Removes a examination result from the database.
     *
     * @param examinationID
     *            the object id of the stay to be removed. It must be greater
     *            than zero ({@code 0}). It must not be
     *            {@link PersistentObject#INVALID_OBJECT_ID}
     *
     * @throws AssertionError
     *             Thrown if a given parameter value is invalid.
     * @throws StoreException
     *             Thrown if an error occurred while removing the object.
     */
    public void removeExaminationResult(long examinationID);


    /**
     * Returns a List of {@link Examination} objects that matche a set of search
     * criteria. When searching via first name and last name, one may use the
     * SQL wildcards ("%", "*", "_") in the search string.
     * @ param examinationName
     *            Must not be {@code null} and must be {@code ""}
     * @ return A list of {@link Examination} objects is returned.
     * @ throws FetchException
     *             Thrown if an error occurred while fetching objects.
     */
    public List<Examination> getExaminations(String examinationName);


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
     * Returns a list of {@link ExaminationResult} objects that match a set of search
     * criteria.
     * @ param patientID
     *            Must not be {@code null} and must be greater than 0
     * @ return A list of matching {@link ExaminationResult} objects is returned. It may be empty.
     * @ throws FetchException
     *             Thrown if an error occurred while fetching an object.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    public List<ExaminationResult> getExaminationResults(long patientID);
}
