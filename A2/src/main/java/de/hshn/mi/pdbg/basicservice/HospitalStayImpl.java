
package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.PersistentObject;
import de.hshn.mi.pdbg.exception.FetchException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
** Implements {@link HospitalStay}.
*/
public class HospitalStayImpl extends PersistentJDBCObject implements HospitalStay {
    private PreparedStatement preparedStatementHospitalStayInsert;
    private PreparedStatement preparedStatementHospitalStayUpdate;
    private Date dateOfAdmission;
    private Date dateOfDischarge;
    private Ward ward;
    private Patient patient;


    /**
     * Default constructor initializes the following values.
     * @ param service DBService which contains our connection
     * @ param id
     * @ param dateOfAdmission
     * @ param ward
     * @ param patient
     */
    public HospitalStayImpl(BasicDBService service, long id, Date dateOfAdmission,
                            Date dateOfDischarge, Ward ward, Patient patient) {
        super(service, id);
        this.dateOfAdmission = dateOfAdmission;
        this.dateOfDischarge = dateOfDischarge;
        this.ward = ward;
        this.patient = patient;
    }

    @Override
    public Date getAdmissionDate() {
        return dateOfAdmission;
    }

    @Override
    public void setAdmissionDate(Date dateOfAdmission) {
        assert dateOfAdmission != null : "dateOfAdmission is invalid";
        assert (dateOfDischarge == null) || (dateOfAdmission != null && dateOfAdmission.before(dateOfDischarge));
        this.dateOfAdmission = dateOfAdmission;
    }

    @Override
    public Date getDischargeDate() {
        return dateOfDischarge;
    }

    @Override
    public void setDischargeDate(Date dateOfDischarge) {
        assert dateOfDischarge == null || dateOfDischarge.after(dateOfAdmission);
        this.dateOfDischarge = dateOfDischarge;
    }

    @Override
    public Ward getWard() throws FetchException {
        return ward;
    }

    @Override
    public void setWard(Ward ward) {
        assert ward != null;
        this.ward = ward;
    }

    @Override
    public Patient getPatient() throws FetchException {
        return patient;
    }

    @Override
    public long store(Connection connection) throws SQLException {
        assert connection != null;
        if (!this.isPersistent()) {
            if (patient.getObjectID() == PersistentObject.INVALID_OBJECT_ID) {
                getBasicDBService().store(patient);
            }
            if (ward.getObjectID() == PersistentObject.INVALID_OBJECT_ID) {
                getBasicDBService().store(ward);
            }
            this.setObjectID(this.generateID(connection, "hospitalStayIDSequence"));
            String sql = """
                    insert into "hospitalstay" (
                    id,
                    p_id,
                    w_id,
                    dateofadmission,
                    dateofdischarge
                    )
                    values (?, ?, ?, ?, ?)
                    """;
            preparedStatementHospitalStayInsert = connection.prepareStatement(sql);
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
            preparedStatementHospitalStayInsert.close();
        } else {
            String sql = """
                    UPDATE hospitalstay
                    SET 
                    p_id = ?,
                    w_id = ?,
                    dateofadmission = ?,
                    dateofdischarge = ?
                    WHERE id = ?
                    """;
            preparedStatementHospitalStayUpdate = connection.prepareStatement(sql);
            preparedStatementHospitalStayUpdate.setLong(1, patient.getObjectID());
            preparedStatementHospitalStayUpdate.setLong(2, ward.getObjectID());
            preparedStatementHospitalStayUpdate.setDate(3,
                    new java.sql.Date(this.getAdmissionDate().getTime()));
            preparedStatementHospitalStayUpdate.setDate(4,
                    new java.sql.Date(this.getDischargeDate().getTime()));
            preparedStatementHospitalStayUpdate.setLong(5, this.getObjectID());
            preparedStatementHospitalStayUpdate.execute();
            preparedStatementHospitalStayUpdate.close();
        }
        return this.getObjectID();
    }
}
