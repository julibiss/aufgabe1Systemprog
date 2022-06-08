package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.schema.SchemaGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implements {@link SchemaGenerator}.
 */
public class DBCreator implements SchemaGenerator {


    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://postgres/pdbg_baseline";
        new DBCreator().createDatabase(url, "postgres", "postgres");
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
                                 
                    CREATE index PatientFirst
                    ON Patient (firstname);
                    
                    CREATE index PatientLast
                    ON Patient (lastname);
                    
                    CREATE index PatientDoB
                    ON Patient (dateofbirth);
                    
                    CREATE index Wid
                    ON Ward (ID);
                    
                    CREATE index HWID
                    ON HospitalStay (W_ID);
                    
                    CREATE index HPID
                    ON HospitalStay (P_ID);
                    
                    CREATE index HSdoa
                    ON HospitalStay (dateofadmission);    
                      
                    CREATE index HSdod
                    ON HospitalStay (dateofdischarge);    
  
                    CREATE Sequence idsequence
                    START WITH 1 INCREMENT BY 1 CACHE 10;             
                 

                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    protected Connection createConnection(String jdbcURL, String user, String password)
            throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(jdbcURL, user, password);
    }

}
