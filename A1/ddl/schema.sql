CREATE TABLE Patient(
 ID int PRIMARY KEY ,
 firstname varchar (50) NOT NULL,
 lastname varchar (30) NOT NULL,
 dateOfBirth Date NOT NULL,
 healthInsuranceCompany varchar (50),
 insuranceNumber varchar (50)
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
