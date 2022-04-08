CREATE TABLE Patient(
    ID int  PRIMARY KEY ,
    firstname varchar (50) NOT NULL,
    lastname varchar (30) NOT NULL,
    dateOfBirth Date NOT NULL,
    healthInsuranceCompany varchar (50),
    insuranceNumber String
);

CREATE TABLE Department(
    ID int PRIMARY KEY,
    name varchar (15) NOT NULL
);

CREATE TABLE Ward(
    ID int PRIMARY KEY ,
    D_ID int,
    FOREIGN KEY (D_ID) REFERENCES Department(ID) ON DELETE RESTRICT,
    name varchar (15) NOT NULL,
    numberOfBeds int NOT NULL CHECK (numberOfBeds > 0)
);

CREATE TABLE HospitalStay(
    ID int PRIMARY KEY ,
    P_ID int,
    W_ID int,
    FOREIGN KEY (P_ID) REFERENCES Patient(ID) ON DELETE CASCADE,
    FOREIGN KEY (W_ID) REFERENCES Ward(ID) ON DELETE RESTRICT,
    dateOfAdmission date NOT NULL,
    dateOfDischarge date CHECK (dateOfDischarge > dateOfAdmission)
);

CREATE TABLE Physician(
    ID int PRIMARY KEY,
    Supervisor int, 
    W_ID int,
    D_ID int,
    FOREIGN KEY (Supervisor) REFERENCES Physician(ID) ON DELETE RESTRICT,
    FOREIGN KEY (W_ID) REFERENCES Ward(ID) ON DELETE RESTRICT,
    FOREIGN KEY (D_ID) REFERENCES Department(ID) ON DELETE RESTRICT,
    firstname varchar (50) NOT NULL,
    lastname varchar (30) NOT NULL,
    dateOfBirth Date NOT NULL,
    academicDegree varchar (20) NOT NULL
);



CREATE TABLE Nurse(
    ID int PRIMARY KEY,
    Supervisor int, 
    W_ID int,
    FOREIGN KEY (Supervisor) REFERENCES Nurse(ID) ON DELETE RESTRICT,
    FOREIGN KEY (W_ID) REFERENCES Ward(ID) ON DELETE RESTRICT,
    firstname varchar NOT NULL,
    lastname varchar NOT NULL,
    dateOfBirth Date NOT NULL
);

CREATE TABLE Examination(
    ID int PRIMARY KEY,
    E_ID int,
    FOREIGN KEY (E_ID) REFERENCES Examination(ID) ON DELETE RESTRICT,
    name varchar (30) NOT NULL
);

CREATE TABLE ClinicalExamination(
    ID INT PRIMARY KEY,
    FOREIGN KEY (ID) REFERENCES Examination(ID) ON DELETE CASCADE,
    bodyPart varchar (30) NOT NULL
) inherits(Examination);

CREATE TABLE TechnicalExamination(
    ID INT PRIMARY KEY,
    FOREIGN KEY (ID) REFERENCES Examination(ID) ON DELETE CASCADE,
) inherits(Examination);

CREATE TABLE LaboratoryTest(
    ID INT PRIMARY KEY,
    FOREIGN KEY (ID) REFERENCES Examination(ID) ON DELETE CASCADE,  
    testType varchar(30) NOT NULL,
    standardValue int  CHECK (standardValue <= 1.5 AND standardValue >=0.5)
) inherits(Examination);

CREATE TABLE Finding(
    ID int PRIMARY KEY,
    PH_ID int,
    P_ID int,
    FOREIGN KEY (PH_ID) REFERENCES Physician(ID) ON DELETE RESTRICT,
    FOREIGN KEY (P_ID) REFERENCES Patient(ID) ON DELETE RESTRICT,
    dateOfFinding date NOT NULL,
    summary varchar NOT NULL
);





CREATE TABLE ExaminationResult(
    ID int PRIMARY KEY,
    E_ID int,
    F_ID int,
    FOREIGN KEY (E_ID) REFERENCES Examination(ID) ON DELETE CASCADE,
    FOREIGN KEY (F_ID) REFERENCES Finding(ID) ON DELETE RESTRICT,
    resultSummary varchar,
    requestDate date NOT NULL,
    resultDate date CHECK (resultDate > requestDate)
);

CREATE TABLE Diagnosis(
    ID int PRIMARY KEY,
    iCDCode String NOT NULL,
    diagnosisText varchar NOT NULL
);

CREATE TABLE Diagnosis_Finding(
    F_ID int REFERENCES Finding(ID) ON DELETE RESTRICT,
    D_ID int REFERENCES Diagnosis(ID) ON DELETE RESTRICT,
    PRIMARY KEY(F_ID,D_ID)
)

