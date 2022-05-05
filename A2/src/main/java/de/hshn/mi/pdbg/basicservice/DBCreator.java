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
     * Creates our local database.
     * @ param jdbcUrl
     * @ param user
     * @ param password
     * @ return true/false signals if the database was created successfully
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
                                        
                    create sequence sequence1 
                    as bigint increment by 1 minvalue 1 no maxvalue start 1 owned by patient.ID;
                    create sequence sequence2 
                    as bigint increment by 1 minvalue 1 no maxvalue start 1 owned by ward.ID; 
                    create sequence sequence3
                    as bigint increment by 1 minvalue 1 no maxvalue start 1 owned by hospitalstay.ID;
                  

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
