package enhancedservice;

import de.hshn.mi.pdbg.PersistentObject;

import java.util.Date;
import java.util.List;

/**
 * {@link ExaminationResult} describes the results obtained through the examination of the health status of a patient.
 */
public interface ExaminationResult extends PersistentObject {

    /**
     * @ param type type of examination that was performed.
     */
    public void setExamination(Examination examination);

    /**
     * @ return a List of Examination object.
     */
    public List<Examination> getExamination();

    /**
     * @ param resultSummary must not be {@code null}, must not be {@code ""}.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    public void setResultSummary(String resultSummary);

    /**
     * @ param requestDate must not be {@code null}
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    public void setRequestDate(Date requestDate);

    /**
     * @ param resultDate
     *              only valid if it's a date after the request date
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    public void setResultDate(Date resultDate);

    /**
     * @ return the result summary.
     */
    public String getResultSummary();

    /**
     * @ return the request date.
     */
    public Date getRequestDate();

    /**
     * @ return The result date or {@code null} if it has not been set.
     */
    public Date getResultDate();

}
