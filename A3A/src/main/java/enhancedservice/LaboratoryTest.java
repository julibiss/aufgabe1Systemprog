package enhancedservice;

import de.hshn.mi.pdbg.PersistentObject;

/**
 * A special type of examination.
 */
public interface LaboratoryTest extends PersistentObject {

    /**
     * @ param testType must not be {@code null}, must not be {@code ""}.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    public void setTestType(String testType);

    /**
     * @ param testType must not be {@code null}, must be greater or equal to 0.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    public void setStandardValue(double standardValue);

    /**
     * @ return A test type of an laboratory test. May return {@code null} if it is unset, that is: unknown.
     */
    public String getTestType();

    /**
     * @ return A the standard value of a laboratory test.
     */
    public double getStandardValue();
}
