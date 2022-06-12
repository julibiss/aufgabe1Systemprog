package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.exception.FetchException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements {@link Patient}.
 */
public class PatientImpl extends PersistentJDBCObject implements Patient {

    private String lastname;
    private String firstname;
    private Date birthdate = null;
    private String healthInsurance = null;
    private String healtInsuranceNumber = null;
    private PreparedStatement preparedStatementPatientInsert;
    private PreparedStatement preparedStatementPatientUpdate;
    private Set<HospitalStay> hospitalStays = new HashSet<>();

    /**
     * Default constructor initializes the following values.
     * @ param lastname
     * @ param firstname
     * @ param service
     * @ param id
     */
    public PatientImpl(long id, String lastname, String firstname, BasicDBService service) {
        super(service, id);
        this.setLastname(lastname);
        this.setFirstname(firstname);

    }

    /**
     * Default constructor initializes the following values.
     * @ param lastname l
     * @ param firstname f
     * @ param service s
     * @ param birthdate b
     * @ param healthInsurance h
     * @ param healthInsuranceNumber h
     * @ param id i
     */
    public PatientImpl(
            BasicDBService service,
            long id,
            String firstname,
            String lastname,
            Date birthdate,
            String healthInsurance,
            String healthInsuranceNumber
    ) {
        this(id, lastname, firstname, service);
        this.setHealthInsurance(healthInsurance);
        this.setInsuranceNumber(healthInsuranceNumber);
        this.setDateOfBirth(birthdate);
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
        assert lastname != null;
        assert !lastname.isBlank();
        this.lastname = lastname;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public void setFirstname(String firstname) {
        assert firstname != null;
        assert !firstname.isBlank();
        this.firstname = firstname;
    }

    @Override
    public Date getDateOfBirth() {
        return birthdate;
    }

    @Override
    public void setDateOfBirth(Date birthdate) {
        assert birthdate == null || birthdate.before(new Date(System.currentTimeMillis()));
        this.birthdate = birthdate;
    }

    @Override
    public Set<HospitalStay> getHospitalStays() throws FetchException {
        HashSet<HospitalStay> hs = new HashSet<>();
        if (this.getObjectID() != PersistentJDBCObject.INVALID_OBJECT_ID) {
            hs = new HashSet(getBasicDBService().getHospitalStays(getObjectID()));
            return hs;
        } else {
            return hs;
        }
    }


    @Override
    public long store(Connection connection) throws SQLException {
        assert connection != null;
        if (!this.isPersistent()) {
            this.setObjectID(this.generateID(connection, "idsequence"));
            preparedStatementPatientInsert = connection.prepareStatement( """
                    insert into patient (
                    id,
                    firstname,
                    lastname,
                    dateofbirth,
                    healthinsurancecompany,
                    insurancenumber)
                    values (?, ?, ?, ?, ?, ?)
                    """);;
            preparedStatementPatientInsert.setLong(1, this.getObjectID());
            preparedStatementPatientInsert.setString(2, this.getFirstname());
            preparedStatementPatientInsert.setString(3, this.getLastname());
            if (this.getDateOfBirth() != null) {
                preparedStatementPatientInsert.setDate(4,
                        new java.sql.Date(this.getDateOfBirth().getTime()));
            } else {
                preparedStatementPatientInsert.setDate(4, null);
            }
            preparedStatementPatientInsert.setString(5, this.getHealthInsurance());
            preparedStatementPatientInsert.setString(6, this.getInsuranceNumber());

            preparedStatementPatientInsert.execute();
            preparedStatementPatientInsert.close();
        } else {
            preparedStatementPatientUpdate = connection.prepareStatement("""
                    UPDATE patient
                    SET 
                    firstname = ?,
                    lastname = ?,
                    dateofbirth = ?,
                    healthinsurancecompany = ?,
                    insurancenumber = ?
                    WHERE id = ?
                    """);
            preparedStatementPatientUpdate.setString(1, this.getFirstname());
            preparedStatementPatientUpdate.setString(2, this.getLastname());
            if (this.getDateOfBirth() != null) {
                preparedStatementPatientUpdate.setDate(3, new java.sql.Date(this.getDateOfBirth().getTime()));
            } else {
                preparedStatementPatientUpdate.setDate(3, null);
            }
            preparedStatementPatientUpdate.setString(4, this.getHealthInsurance());
            preparedStatementPatientUpdate.setString(5, this.getInsuranceNumber());
            preparedStatementPatientUpdate.setLong(6, this.getObjectID());

            preparedStatementPatientUpdate.execute();
            preparedStatementPatientUpdate.close();
        }
        return getObjectID();
    }
}
