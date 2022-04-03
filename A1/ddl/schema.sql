CREATE TABLE Patient(
    ID int  PRIMARY KEY ,
    firstname varchar (50) NOT NULL,
    lastname varchar (30) NOT NULL,
    dateOfBirth Date NOT NULL,
    healthInsuranceCompany varchar (50),
    insuranceNumber int
);

CREATE TABLE Department(
    ID int PRIMARY KEY,
    name varchar (15) NOT NULL
);

CREATE TABLE Ward(
    ID int PRIMARY KEY ,
	D_ID int REFERENCES Department(ID) ON DELETE RESTRICT,
    name varchar (15) NOT NULL,
    numberOfBeds int NOT NULL CHECK (numberOfBeds > 0)
);

CREATE TABLE HospitalStay(
    ID int PRIMARY KEY ,
    P_ID int REFERENCES Patient(ID) ON DELETE RESTRICT,
    W_ID int REFERENCES Ward(ID) ON DELETE RESTRICT,
    dateOfAdmission date NOT NULL,
    dateOfDischarge date
);

CREATE TABLE Physician(
    ID int PRIMARY KEY,
    Supervisor int REFERENCES Physician(ID) ON DELETE RESTRICT,
	W_ID int REFERENCES Ward(ID) ON DELETE RESTRICT,
    D_ID int REFERENCES Department(ID) ON DELETE RESTRICT,
    firstname varchar (50) NOT NULL,
    lastname varchar (30) NOT NULL,
    dateOfBirth Date NOT NULL,
    academicDegree varchar (20) NOT NULL
);

CREATE TABLE Nurse(
    ID int PRIMARY KEY,
	Supervisor int REFERENCES Nurse(ID) ON DELETE RESTRICT,
	W_ID int REFERENCES Ward(ID) ON DELETE RESTRICT,
    firstname varchar NOT NULL,
    lastname varchar NOT NULL,
    dateOfBirth Date NOT NULL
);

CREATE TABLE Examination(
    ID int PRIMARY KEY,
	E_ID int REFERENCES Examination(ID) ON DELETE RESTRICT,
    name varchar (30) NOT NULL
);

CREATE TABLE ClinicalExamination(
    ID INT PRIMARY KEY,
	FOREIGN KEY (ID) REFERENCES Examination(ID) ON DELETE CASCADE,
    name varchar (30) NOT NULL,
    bodyPart varchar (30) NOT NULL
) inherits(Examination);

CREATE TABLE TechnicalExamination(
    ID INT PRIMARY KEY,
	FOREIGN KEY (ID) REFERENCES Examination(ID) ON DELETE CASCADE,
    name varchar (30) NOT NULL
) inherits(Examination);

CREATE TABLE LaboratoryTest(
    ID INT PRIMARY KEY,
	FOREIGN KEY (ID) REFERENCES Examination(ID) ON DELETE CASCADE,	
    testName varchar (30) NOT NULL,
	testType varchar(30) NOT NULL,
    standardValue int  CHECK (standardValue <= 1.5 AND standardValue >=0.5)
) inherits(Examination);

CREATE TABLE Finding(
    ID int PRIMARY KEY,
    PH_ID int REFERENCES Physician(ID) ON DELETE RESTRICT,
    P_ID int REFERENCES Patient(ID) ON DELETE RESTRICT,
    dateOfFinding date NOT NULL,
    summary varchar
);

CREATE TABLE ExaminationResult(
    ID int PRIMARY KEY,
    E_ID int REFERENCES Examination(ID) ON DELETE CASCADE,
	F_ID int REFERENCES Finding(ID) ON DELETE RESTRICT,
    resultSummary varchar,
    requestDate date NOT NULL,
    resultDate date
);

CREATE TABLE Diagnosis(
    ID int PRIMARY KEY,
    iCDCode int NOT NULL,
    diagnosisText varchar
);

CREATE TABLE Diagnoseliste(
	F_ID int REFERENCES Finding(ID) ON DELETE RESTRICT,
	D_ID int REFERENCES Diagnosis(ID) ON DELETE RESTRICT
)
