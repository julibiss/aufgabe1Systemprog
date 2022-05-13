package enhancedservice;

import java.util.Date;

public interface Finding {

    public void setFindingDate(Date date);

    public void setSummary(String summary);

    public Date getFindingDate();

    public String getSummary();

}
