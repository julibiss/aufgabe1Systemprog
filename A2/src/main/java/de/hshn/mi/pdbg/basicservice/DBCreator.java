package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.schema.SchemaGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Klasse.
 */
public class DBCreator implements SchemaGenerator {


    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        new DBCreator().createDatabase(url, "postgres", "password");
    }

    /**
     * TestMethode.
     *
     * @ param jdbcUrl j
     * @ param user u
     * @ param password p
     * @ return r
     */
    public boolean createDatabase(String jdbcUrl, String user, String password) {
        try {
            Connection connection = createConnection(jdbcUrl, user, password);
            String sql = """
                    CREATE TABLE Patient(
                     ID int PRIMARY KEY ,
                     firstname varchar (50) NOT NULL,
                     lastname varchar (50) NOT NULL,
                     dateOfBirth Date ,
                     healthInsuranceCompany varchar (50),
                     insuranceNumber varchar (50)
                    );
                                        
                    CREATE TABLE Ward(
                     ID int PRIMARY KEY ,
                     name varchar (50) NOT NULL,
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
                                        
                    create sequence if not exists sequence
                    as bigint increment by 1 minvalue 1 no maxvalue start 1 cache 1;
                    CREATE INDEX patient_dateOfBirth_index ON patient(dateOfBirth);
                    CREATE INDEX patient_lastname_index ON patient(lastname);
                    CREATE INDEX patient_firstname_index ON patient(firstname);
                    CREATE INDEX hospitalstay_P_ID_index ON patient(id);

                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();

            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    protected Connection createConnection(String jdbcURL, String user, String password)
            throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(jdbcURL, user, password);
    }

}
