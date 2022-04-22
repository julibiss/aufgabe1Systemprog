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
        assert dateOfAdmission.before(dateOfDischarge);
        this.dateOfAdmission = dateOfAdmission;
    }

    @Override
    public Date getDischargeDate() {
        return dateOfDischarge;
    }

    @Override
    public void setDischargeDate(Date dateOfDischarge) {
        assert dateOfDischarge.after(dateOfAdmission);
        this.dateOfDischarge = dateOfDischarge;
    }

    @Override
    public Ward getWard() {
        return ward;
    }

    @Override
    public void setWard(Ward ward) {
        assert ward !=null;
        this.ward = ward;
    }

    @Override
    public Patient getPatient() {
        assert this.patient.isPersistent();
        return patient;
    }

    @Override
    public void setCluster(PreparedStatement cluster) {

    }

    @Override
    public long store(Connection connection) throws SQLException {
        if (!this.isPersistent()) {
            this.setObjectID(this.generateID(connection));
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
            preparedStatementHospitalStayUpdate.setDate(3, (java.sql.Date) this.getAdmissionDate());
            preparedStatementHospitalStayUpdate.setDate(4, (java.sql.Date) this.getDischargeDate());
            preparedStatementHospitalStayUpdate.setLong(5, this.getObjectID());


            preparedStatementHospitalStayUpdate.execute();
        }

        return this.getObjectID();
    }
}
