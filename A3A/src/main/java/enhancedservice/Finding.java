package enhancedservice;

import java.util.Date;

/**
 * {@link Finding} references all the Diagnosis and the examination results of a patient.
 */
public interface Finding {

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
