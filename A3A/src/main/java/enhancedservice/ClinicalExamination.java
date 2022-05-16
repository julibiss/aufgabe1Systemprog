package enhancedservice;

/**
 * A special type of examination.
 */
public interface ClinicalExamination {

    /**
     * @ param bodyPart must not be {@code null}, must not be {@code ""}.
     * @ throws AssertionError
     * Thrown if a given parameter value is invalid.
     */
    public void setBodyPart(String bodyPart);

    /**
     * @ return A body part of a clinical examination.
     */
    public String getBodyPart();
}
