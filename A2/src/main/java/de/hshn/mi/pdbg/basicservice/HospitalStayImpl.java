package de.hshn.mi.pdbg.basicservice;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Klasse.
 */
public class HospitalStayImpl extends PersistentJDBCObject implements HospitalStay {

    protected HospitalStayImpl(BasicDBService service, long id) {
        super(service, id);
    }

    @Override
    public Date getAdmissionDate() {
        return null;
    }

    @Override
    public void setAdmissionDate(Date date) {

    }

    @Override
    public Date getDischargeDate() {
        return null;
    }

    @Override
    public void setDischargeDate(Date date) {

    }

    @Override
    public Ward getWard() {
        return null;
    }

    @Override
    public void setWard(Ward ward) {

    }

    @Override
    public Patient getPatient() {
        return null;
    }

    @Override
    public void setCluster(Statement cluster) {

    }

    @Override
    public long store(Connection connection) throws SQLException {
        return 0;
    }
}
