package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.PersistentObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Klasse.
 */
public class BasicDBServiceImpl implements BasicDBService {
    private Connection connection;

    /**
     * Default constructor which creates a connection to the database.
     *
     * @ param jdbcUrl link to our local database
     * @ param sqlUser
     * @ param sqlPassword
     */
    public BasicDBServiceImpl(String jdbcUrl, String sqlUser, String sqlPassword) {
        try {
            connection = getConnection(jdbcUrl, sqlUser, sqlPassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Returns the connection.
     *
     * @ param jdbcUrl link to our local database
     * @ param user
     * @ param password
     * @ return connection current connection
     */
    private Connection getConnection(String jdbcUrl, String user, String password) throws SQLException {
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    @Override
    public Patient createPatient(String lastname, String firstname) {
        assert lastname != null && !lastname.isBlank();
        assert firstname != null && !firstname.isBlank();
        Patient patient = new PatientImpl(PersistentObject.INVALID_OBJECT_ID, lastname, firstname, this);
        return patient;
    }

    @Override
    public Ward createWard(String name, int numberOfBeds) {
        assert name != null && !name.isBlank();
        assert numberOfBeds > 0;
        Ward ward = new WardImpl(this, PersistentObject.INVALID_OBJECT_ID, name, numberOfBeds);
        return ward;
    }

    @Override
    public HospitalStay createHospitalStay(Patient patient, Ward ward, Date admissionDate) {
        assert patient != null;
        assert ward != null;
        assert admissionDate != null;
        HospitalStay hospitalStay =
                new HospitalStayImpl(this, PersistentObject.INVALID_OBJECT_ID, admissionDate,
                        null, ward, patient);
        return hospitalStay;
    }

    @Override
    public void removeHospitalStay(long l) {
        assert l > 0 && l != PersistentObject.INVALID_OBJECT_ID;

    }

    @Override
    public List<Patient> getPatients(String firstname, String lastname, Date date, Date date1) {
        List<Patient> patients = new ArrayList();
        if (firstname == null && lastname == null && date == null && date1 == null) {
            return sqlthing(patients, "SELECT * FROM patient");
        } else if (firstname != null && lastname == null && date == null && date1 == null) {
            return sqlthing(patients, "SELECT * FROM patient WHERE firstname LIKE '" + firstname + "'");
        } else if (firstname == null && lastname != null && date == null && date1 == null) {
            return sqlthing(patients, "SELECT * FROM patient WHERE lastname LIKE '" + lastname + "'");
        } else if (firstname == null && lastname == null && date != null && date1 == null) {
            return sqlthing(patients, "SELECT * FROM patient WHERE dateofbirth >= '" + DateHelper.convertDate(date).toString() + "'");
        } else if (firstname == null && lastname == null && date == null && date1 != null) {
            return sqlthing(patients, "SELECT * FROM patient WHERE dateofbirth <= '" + DateHelper.convertDate(date1).toString() + "'");
        } else if (firstname != null && lastname != null && date == null && date1 == null) {
            return sqlthing(patients, "SELECT * FROM patient WHERE lastname LIKE '" + lastname + "' AND " +
                                      "firstname LIKE '" + firstname + "'");
        } else if (firstname == null && lastname != null && date != null && date1 == null) {
            return sqlthing(patients, "SELECT * FROM patient WHERE lastname LIKE '" + lastname + "' AND " +
                                      "dateofbirth >= '" + DateHelper.convertDate(date).toString() + "'");
        } else if (firstname == null && lastname == null && date != null && date1 != null) {
            return sqlthing(patients, "SELECT * FROM patient WHERE dateofbirth <='" +
                                      DateHelper.convertDate(date1).toString() + "' AND " +
                                      "dateofbirth >= '" + DateHelper.convertDate(date).toString() + "'");
        } else if (firstname != null && lastname == null && date != null && date1 == null) {
            return sqlthing(patients, "SELECT * FROM patient  WHERE firstname LIKE '" + firstname + "' AND " +
                                      "dateofbirth >= '" + DateHelper.convertDate(date).toString() + "'");
        } else if (firstname != null && lastname == null && date == null && date1 != null) {
            return sqlthing(patients, "SELECT * FROM patient  WHERE firstname LIKE '" + firstname + "' AND " +
                                      "dateofbirth <= '" + DateHelper.convertDate(date1).toString() + "'");
        } else if (firstname == null && lastname != null && date == null && date1 != null) {
            return sqlthing(patients, "SELECT * FROM patient  WHERE lastname LIKE '" + lastname + "' AND " +
                                      "dateofbirth <= '" + DateHelper.convertDate(date1).toString() + "'");
        } else if (firstname != null && lastname != null && date == null && date1 != null) {
            return sqlthing(patients, "SELECT * FROM patient  WHERE firstname LIKE '" + firstname + "' AND lastname LIKE '" + lastname + "' AND " +
                                      "dateofbirth <= '" + DateHelper.convertDate(date1).toString() + "'");
        } else if (firstname != null && lastname != null && date != null && date1 == null) {
            return sqlthing(patients, "SELECT * FROM patient  WHERE firstname LIKE '" + firstname + "' AND lastname LIKE '" + lastname + "' AND " +
                                      "dateofbirth >= '" + DateHelper.convertDate(date).toString() + "'");
        } else if (firstname != null && lastname == null && date != null && date1 != null) {
            return sqlthing(patients, "SELECT * FROM patient  WHERE firstname LIKE '" + firstname + "' AND " +
                                      "dateofbirth >= '" + DateHelper.convertDate(date).toString() + "' AND " +
                                      "dateofbirth <= '" + DateHelper.convertDate(date1).toString() + "'");
        } else if (firstname == null && lastname != null && date != null && date1 != null) {
            return sqlthing(patients, "SELECT * FROM patient  WHERE lastname LIKE '" + lastname + "' AND '" +
                                      "dateofbirth <= '" + DateHelper.convertDate(date1).toString() + "' AND " +
                                      "dateofbirth >= '" + DateHelper.convertDate(date).toString() +"'");
        } else if (firstname != null && lastname != null && date != null && date1 != null) {
            return sqlthing(patients, "SELECT * FROM patient WHERE name LIKE '" + firstname + "' AND lastname LIKE '"
                                      + lastname + "' AND dateofbirth >= '" + DateHelper.convertDate(date).toString() +
                                      "' AND dateofbirth <=  '" + DateHelper.convertDate(date1).toString() + "'");
        }
        return patients;
    }

    private List<Patient> sqlthing(List<Patient> arrayList, String sql) {
        try {
            PreparedStatement pS = connection.prepareStatement(sql);
            ResultSet rs = pS.executeQuery();
            while (rs.next()) {
                arrayList.add(new PatientImpl(this, rs.getLong(1), rs.getString(2),
                        rs.getString(3), rs.getDate(4), rs.getString(5),
                        rs.getString(6)));
            }
            return arrayList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Patient getPatient(long l) {
        assert l > 0;
        Patient patient = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE ID = ?");
            preparedStatement.setLong(1, l);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            patient = new PatientImpl(this, rs.getLong(1), rs.getString(2), rs.getString(3),
                    rs.getDate(4), rs.getString(5), rs.getString(6));
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return patient;
    }

    @Override
    public List<Ward> getWards() {
        List<Ward> wards = new ArrayList();
        try {
            PreparedStatement pS = connection.prepareStatement("SELECT * FROM ward");
            ResultSet rs = pS.executeQuery();
            while (rs.next()) {
                wards.add(new WardImpl(this, rs.getLong(1), rs.getString(2),
                        rs.getInt(3)));
            }
            return wards;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Ward getWard(long l) {
        assert l > 0;
        try {
            PreparedStatement pS = connection.prepareStatement("SELECT * FROM ward WHERE ID = ?");
            pS.setLong(1, l);
            ResultSet rs = pS.executeQuery();
            rs.next();
            return new WardImpl(this, rs.getLong(1), rs.getString(2), rs.getInt(3));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    @Override
    public List<HospitalStay> getHospitalStays(long l) {
        List<HospitalStay> hospitalStays2 = new ArrayList();
        assert l > 0;

        return hospitalStays2;
    }

    @Override
    public List<HospitalStay> getHospitalStays(long l, Date date, Date date1) {
        List<HospitalStay> hospitalStays2 = new ArrayList();
        assert l > 0;

        return hospitalStays2;
    }

    @Override
    public double getAverageHospitalStayDuration(long l) {
        double average = 0;
        assert l > 0;

        return average;
    }

    @Override

    public int getAllocatedBeds(Ward ward) {
        int numberOfBeds = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(numberofbeds) " +
                                                                              "FROM ward");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return numberOfBeds;
    }

    @Override
    public int getFreeBeds(Ward ward) {
        int freeBeds = 0;
        assert ward == null || ward.isPersistent();

        return freeBeds;

    }

    @Override
    public long store(PersistentObject persistentObject) {
        assert persistentObject != null;
        if (persistentObject instanceof PatientImpl) {
            try {
                return ((PatientImpl) persistentObject).store(connection);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        if (persistentObject instanceof WardImpl) {
            try {
                return ((WardImpl) persistentObject).store(connection);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        if (persistentObject instanceof HospitalStayImpl) {
            try {
                return ((HospitalStayImpl) persistentObject).store(connection);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return persistentObject.getObjectID();

    }

    @Override
    public void close() {
        int i = 0;
    }
}