package de.hshn.mi.pdbg.enhancedservice;

import de.hshn.mi.pdbg.basicservice.BasicDBService;

import java.util.Date;

public interface EnhancedDBService extends BasicDBService {

    public void createClinicalExamination(String type, String bodyPart);

    public void createDiagnosis(String iCDCode, String diagnosisText);

    public void createExamination(String type);

    public void createExaminationResult(String resultSummary, Date requestDate, Date resultDate);

    public void createFinding(Date date, String summary);

    public void createTechnicalExamination(String type);

    public void createLaboratoryTest(String type, String testType, double standardValue);

}
