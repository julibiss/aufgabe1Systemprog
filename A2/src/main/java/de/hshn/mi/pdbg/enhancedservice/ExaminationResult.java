package de.hshn.mi.pdbg.enhancedservice;

import java.util.Date;

public interface ExaminationResult {
    public void setResultSummary();

    public void setRequestDate();

    public void setResultDate();

    public String getResultSummary();

    public Date getRequestDate();

    public Date getResultDate();

}
