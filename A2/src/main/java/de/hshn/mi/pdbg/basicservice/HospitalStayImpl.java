package de.hshn.mi.pdbg.basicservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * Klasse.
 */
public class HospitalStayImpl extends PersistentJDBCObject implements HospitalStay {
    private PreparedStatement preparedStatementHospitalStayInsert;
    private PreparedStatement preparedStatementHospitalStayUpdate;
    private Date dateOfAdmission;
    private Date dateOfDischarge;
    private Ward ward;
    private Patient patient;

    public PreparedStatement getPreparedStatementHospitalStayInsert() {
        return preparedStatementHospitalStayInsert;
    }

    public PreparedStatement getPreparedStatementHospitalStayUpdate() {
        return preparedStatementHospitalStayUpdate;
    }

    protected HospitalStayImpl(BasicDBService service, long id) {
        super(service, id);
    }

    @Override
    public Date getAdmissionDate() {
        return dateOfAdmission;
    }

    @Override
    public void setAdmissionDate(Date dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    @Override
    public Date getDischargeDate() {
        return dateOfDischarge;
    }

    @Override
    public void setDischargeDate(Date dateOfDischarge) {
        this.dateOfDischarge = dateOfDischarge;
    }

    @Override
    public Ward getWard() {
        return ward;
    }

    @Override
    public void setWard(Ward ward) {
        this.ward = ward;
    }

    @Override
    public Patient getPatient() {
        return null;
    }

    @Override
    public void setCluster(PreparedStatement cluster) {

    }

    @Override
    public long store(Connection connection) throws SQLException {
        /* if (!this.isPersistent()) {
            this.setObjectID(this.generateID(connection));

            if (preparedStatementHospitalStayInsert == null) {
                preparedStatementHospitalStayInsert = getPreparedStatementHospitalStayInsert();
            }
            preparedStatementHospitalStayInsert.setLong(1, this.getObjectID());
            preparedStatementHospitalStayInsert.setLong(2, patient.getObjectID());
            preparedStatementHospitalStayInsert.setLong(3, ward.getObjectID());
            preparedStatementHospitalStayInsert.setDate(4,
                    new java.sql.Date(this.getAdmissionDate().getTime()));
            if (this.getDischargeDate() != null) {
                preparedStatementHospitalStayInsert.setDate(5,
                        new java.sql.Date(this.getDischargeDate().getTime()));
            } else {
                preparedStatementHospitalStayInsert.setDate(5, null);
            }
            preparedStatementHospitalStayInsert.executeUpdate();
        } else {
            if (preparedStatementHospitalStayUpdate == null) {
                preparedStatementHospitalStayUpdate = getPreparedStatementHospitalStayUpdate();
            }
            preparedStatementHospitalStayUpdate.setString(1, this.get());
            preparedStatementHospitalStayUpdate.setString(2, this.getInsuranceNumber());
            preparedStatementHospitalStayUpdate.setString(3, this.getFirstname());
            preparedStatementHospitalStayUpdate.setString(4, this.getLastname());
            if (this.getDateOfBirth() != null) {
                preparedStatementHospitalStayUpdate.setDate(5, new java.sql.Date(this.getDateOfBirth().getTime()));
            } else {
                preparedStatementHospitalStayUpdate.setDate(5, null);
            }
        }
        preparedStatementHospitalStayUpdate.setLong(6, this.getObjectID());
        return getObjectID;
        */
        return 0;
    }
}
