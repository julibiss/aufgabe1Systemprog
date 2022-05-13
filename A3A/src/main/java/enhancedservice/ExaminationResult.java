package enhancedservice;

import java.util.Date;

public interface ExaminationResult {

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
     * @ param requestDate
     *              only valid if it's a date after the request date
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    public void setResultDate(Date resultDate);

    /**
     * @ return the result summary.
     */
    public String getResultSummary();

    public Date getRequestDate();

    public Date getResultDate();

}
