package enhancedservice;

import de.hshn.mi.pdbg.PersistentObject;

/**
 * {@link Examination} describes the method that was used in order
 * to get more information about the health status of a patient .
 */
public interface Examination extends PersistentObject {

    /**
     * @ param type must not be {@code null}, must not be {@code ""}.
     * @ throws AssertionError
     *             Thrown if a given parameter value is invalid.
     */
    public void setType(String type);

    /**
     * @ return type of examination.
     */
    public void getType();
}
