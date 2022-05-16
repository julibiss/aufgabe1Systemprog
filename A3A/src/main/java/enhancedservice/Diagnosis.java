package enhancedservice;

/**
 * The diagnosis describes the detection of an illness.
 */
public interface Diagnosis {

    /**
     * @ param icdCode must not be {@code null}, must not be {@code ""}.
     * @ throws AssertionError
     * Thrown if a given parameter value is invalid.
     */
    public void setICDCode(String icdCode);

    /**
     * @ param diagnosisText must not be {@code null}, must not be {@code ""}.
     * @ throws AssertionError
     * Thrown if a given parameter value is invalid.
     */
    public void setDiagnosisText(String diagnosisText);

    /**
     * @ return a ICDCode of a diagnosis. Must not be null {@code null}.
     */
    public void getICDCode();

    /**
     * @ return A diagnosis text.
     */
    public void getDiagnosisText();


}
