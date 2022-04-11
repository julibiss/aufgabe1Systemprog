package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.schema.SchemaGenerator;
import org.hsqldb.jdbc.JDBCCallableStatement;
import org.hsqldb.jdbcDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCreator implements SchemaGenerator {

    protected static final String [] SQL_DDL_STATEMENTS = {
            "CREATE TABLE Patient(\n" +
                " ID int PRIMARY KEY ,\n" +
                " firstname varchar (50) NOT NULL,\n" +
                " lastname varchar (30) NOT NULL,\n" +
                " dateOfBirth Date NOT NULL,\n" +
                " healthInsuranceCompany varchar (50),\n" +
                " insuranceNumber varchar (50)\n" +
                ");\n" +
                "CREATE TABLE Department(\n" +
                " ID int PRIMARY KEY,\n" +
                " name varchar (15) NOT NULL\n" +
                ");\n" +
                "CREATE TABLE Ward(\n" +
                " ID int PRIMARY KEY ,\n" +
                " D_ID int,\n" +
                " FOREIGN KEY (D_ID) REFERENCES Department(ID) ON DELETE RESTRICT,\n" +
                " name varchar (15) NOT NULL,\n" +
                " numberOfBeds int NOT NULL CHECK (numberOfBeds > 0)\n" +
                ");\n" +
                "CREATE TABLE HospitalStay(\n" +
                " ID int PRIMARY KEY ,\n" +
                " P_ID int,\n" +
                " W_ID int,\n" +
                " FOREIGN KEY (P_ID) REFERENCES Patient(ID) ON DELETE CASCADE,\n" +
                " FOREIGN KEY (W_ID) REFERENCES Ward(ID) ON DELETE RESTRICT,\n" +
                " dateOfAdmission date NOT NULL,\n" +
                " dateOfDischarge date CHECK (dateOfDischarge > dateOfAdmission)\n" +
                ");\n" +
                "CREATE TABLE Physician(\n" +
                " ID int PRIMARY KEY,\n" +
                " Supervisor int,\n" +
                " W_ID int,\n" +
                " D_ID int,\n" +
                " FOREIGN KEY (Supervisor) REFERENCES Physician(ID) ON DELETE RESTRICT,\n" +
                " FOREIGN KEY (W_ID) REFERENCES Ward(ID) ON DELETE RESTRICT,\n" +
                " FOREIGN KEY (D_ID) REFERENCES Department(ID) ON DELETE RESTRICT,\n" +
                " firstname varchar (50) NOT NULL,\n" +
                " lastname varchar (30) NOT NULL,\n" +
                " dateOfBirth Date NOT NULL,\n" +
                " academicDegree varchar (20) NOT NULL\n" +
                ");\n" +
                "CREATE TABLE Nurse(\n" +
                " ID int PRIMARY KEY,\n" +
                " Supervisor int,\n" +
                " W_ID int,\n" +
                " FOREIGN KEY (Supervisor) REFERENCES Nurse(ID) ON DELETE RESTRICT,\n" +
                " FOREIGN KEY (W_ID) REFERENCES Ward(ID) ON DELETE RESTRICT,\n" +
                " firstname varchar NOT NULL,\n" +
                " lastname varchar NOT NULL,\n" +
                " dateOfBirth Date NOT NULL\n" +
                ");\n" +
                "CREATE TABLE Examination(\n" +
                " ID int PRIMARY KEY,\n" +
                " E_ID int,\n" +
                " FOREIGN KEY (E_ID) REFERENCES Examination(ID) ON DELETE RESTRICT,\n" +
                " name varchar (30) NOT NULL\n" +
                ");\n" +
                "CREATE TABLE ClinicalExamination(\n" +
                " ID INT PRIMARY KEY,\n" +
                " FOREIGN KEY (ID) REFERENCES Examination(ID) ON DELETE CASCADE,\n" +
                " bodyPart varchar (30) NOT NULL\n" +
                ") inherits(Examination);\n" +
                "CREATE TABLE TechnicalExamination(\n" +
                " ID INT PRIMARY KEY,\n" +
                " FOREIGN KEY (ID) REFERENCES Examination(ID) ON DELETE CASCADE\n" +
                ") inherits(Examination);\n" +
                "CREATE TABLE LaboratoryTest(\n" +
                " ID INT PRIMARY KEY,\n" +
                " FOREIGN KEY (ID) REFERENCES Examination(ID) ON DELETE CASCADE,\n" +
                " testType varchar(30) NOT NULL,\n" +
                " standardValue float CHECK (standardValue <= 1.5 AND standardValue >=0.5)\n" +
                ") inherits(Examination);\n" +
                "CREATE TABLE Finding(\n" +
                " ID int PRIMARY KEY,\n" +
                " PH_ID int,\n" +
                " P_ID int,\n" +
                " FOREIGN KEY (PH_ID) REFERENCES Physician(ID) ON DELETE RESTRICT,\n" +
                " FOREIGN KEY (P_ID) REFERENCES Patient(ID) ON DELETE RESTRICT,\n" +
                " dateOfFinding date NOT NULL,\n" +
                " summary varchar NOT NULL\n" +
                ");\n" +
                "CREATE TABLE ExaminationResult(\n" +
                " ID int PRIMARY KEY,\n" +
                " E_ID int,\n" +
                " F_ID int,\n" +
                " FOREIGN KEY (E_ID) REFERENCES Examination(ID) ON DELETE CASCADE,\n" +
                " FOREIGN KEY (F_ID) REFERENCES Finding(ID) ON DELETE RESTRICT,\n" +
                " resultSummary varchar,\n" +
                " requestDate date NOT NULL,\n" +
                " resultDate date CHECK (resultDate > requestDate)\n" +
                ");\n" +
                "CREATE TABLE Diagnosis(\n" +
                " ID int PRIMARY KEY,\n" +
                " iCDCode varchar (30) NOT NULL,\n" +
                " diagnosisText varchar NOT NULL\n" +
                ");\n" +
                "CREATE TABLE Diagnosis_Finding(\n" +
                " F_ID int REFERENCES Finding(ID) ON DELETE RESTRICT,\n" +
                " D_ID int REFERENCES Diagnosis(ID) ON DELETE RESTRICT,\n" +
                " PRIMARY KEY(F_ID,D_ID)\n" +
                ")\n"

    };

    public static void main(String[] args) throws Exception{
        String url = "jdbc.postgresql://localhost/pdgb";
        new DBCreator().createDatabase(url, "sa", "");
    }

    public boolean createDatabase(String jdbcUrl, String user, String password){
        try {
            createConnection(jdbcUrl, user, password);
            Statement statement = createConnection();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Connection createConnection(String jdbcURL, String user, String password) throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");

        Connection conPSQL = DriverManager.getConnection(jdbcURL, user, password);
        return conPSQL;
    }

}
