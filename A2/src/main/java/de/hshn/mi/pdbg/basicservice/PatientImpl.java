package de.hshn.mi.pdbg.basicservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Set;

/**
 * Klasse.
 */
public class PatientImpl extends PersistentJDBCObject implements Patient {

    private PreparedStatement cluster;
    private String lastname;
    private String firstname;
    private String healthInsurance = null;
    private String healtInsuranceNumber = null;
    private Date birthdate = null;
    private PreparedStatement preparedStatementPatientInsert;
    private PreparedStatement preparedStatementPatientUpdate;

    /**
     * Konstruktor.
     * @ param lastname l
     * @ param firstname f
     * @ param service s
     * @ param id i
     */
    public PatientImpl(String lastname, String firstname, BasicDBService service, long id) {
        super(service, id);
        this.setLastname(lastname);
        this.setFirstname(firstname);
    }

    /**
     * Konstruktor.
     * @ param lastname l
     * @ param firstname f
     * @ param service s
     * @ param birthdate b
     * @ param healthInsurance h
     * @ param healthInsuranceNumber h
     * @ param id i
     */
    public PatientImpl(String lastname,
                       String firstname,
                       BasicDBService service,
                       Date birthdate,
                       String healthInsurance,
                       String healthInsuranceNumber,
                       long id) {
        this(lastname, firstname, service, id);
        this.setHealthInsurance(healthInsurance);
        this.setInsuranceNumber(healthInsuranceNumber);
        this.setDateOfBirth(birthdate);
    }

    public PreparedStatement getPreparedStatementPatientInsert() {
        return preparedStatementPatientInsert;
    }

    public PreparedStatement getPreparedStatementPatientUpdate() {
        return preparedStatementPatientUpdate;
    }

    @Override
    public void setHealthInsurance(String name) {
        this.healthInsurance = name;
    }

    @Override
    public void setInsuranceNumber(String number) {
        this.healtInsuranceNumber = number;
    }

    @Override
    public String getHealthInsurance() {
        return healthInsurance;
    }

    @Override
    public String getInsuranceNumber() {
        return healtInsuranceNumber;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public Date getDateOfBirth() {
        return birthdate;
    }

    @Override
    public void setDateOfBirth(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public Set<HospitalStay> getHospitalStays() {
        return null;
    }

    @Override
    public void setCluster(Statement cluster) {

    }

    @Override
    public long store(Connection connection) throws SQLException {

        if (!this.isPersistent()) {
            this.setObjectID(this.generateID(connection));

            if (preparedStatementPatientInsert == null) {
                preparedStatementPatientInsert = getPreparedStatementPatientInsert();
            }
            preparedStatementPatientInsert.setString(1, this.getHealthInsurance());
            preparedStatementPatientInsert.setString(2, this.getInsuranceNumber());
            preparedStatementPatientInsert.setString(3, this.getFirstname());
            preparedStatementPatientInsert.setString(4, this.getLastname());
            if (this.getDateOfBirth() != null) {
                preparedStatementPatientInsert.setDate(6,
                        new java.sql.Date(this.getDateOfBirth().getTime()));
            } else {
                preparedStatementPatientInsert.setDate(6, null);
            }
            preparedStatementPatientInsert.executeUpdate();
        } else {
            if (preparedStatementPatientUpdate == null) {
                preparedStatementPatientUpdate = getPreparedStatementPatientUpdate();
            }
            preparedStatementPatientUpdate.setString(1, this.getHealthInsurance());
            preparedStatementPatientUpdate.setString(2, this.getInsuranceNumber());
            preparedStatementPatientUpdate.setString(3, this.getFirstname());
            preparedStatementPatientUpdate.setString(4, this.getLastname());
            if (this.getDateOfBirth() != null) {
                preparedStatementPatientUpdate.setDate(5, new java.sql.Date(this.getDateOfBirth().getTime()));
            } else {
                preparedStatementPatientUpdate.setDate(5, null);
            }
        }
        preparedStatementPatientUpdate.setLong(6, this.getObjectID());
        return 0;
    }
}
