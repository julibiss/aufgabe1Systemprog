package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.PersistentObject;
import de.hshn.mi.pdbg.exception.FetchException;
import de.hshn.mi.pdbg.exception.StoreException;
import de.hshn.mi.pdbg.util.DateHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implements {@link BasicDBService}.
 */
public class BasicDBServiceImpl implements BasicDBService {
    private Connection connection;
    PreparedStatement[] prepStatGetPatients = new PreparedStatement[36];
    PreparedStatement prepStatGetWards;
    PreparedStatement prepStatGetWard;

    /**
     * Default constructor which creates a connection to the database.
     * @ param jdbcUrl link to our local database
     * @ param sqlUser
     * @ param sqlPassword
     */
    public BasicDBServiceImpl(String jdbcUrl, String sqlUser, String sqlPassword) throws SQLException {
        connection = getConnection(jdbcUrl, sqlUser, sqlPassword);
        fillPrepStat();
        prepStatGetWard = connection.prepareStatement("""
                                                                               SELECT * FROM "Ward"
                                                                               WHERE ID = ?
                                                                               """);
        prepStatGetWards = connection.prepareStatement("""
                SELECT * FROM "Ward"
                """);
    }

    private void fillPrepStat() {
        String base = "SELECT * FROM " + "\"Patient\"" + " WHERE '1' = '1'";
        int index = 0;
        for (int last = 0; last <= 2; last++) {
            for (int first = 0; first <= 2; first++) {
                for (int start = 0; start <= 1; start++) {
                    for (int end = 0; end <= 1; end++) {
                        String temp = base;
                        if (last == 1) {
                            temp += " AND firstname = ?";
                        }
                         if(last == 2) {
                             temp += " AND firstname LIKE ?";
                          }
                        if (first == 1) {
                            temp += " AND lastname = ?";
                        }
                          if (first == 2) {
                            temp += " AND lastname = ?";
                         }
                        if (start == 1) {
                            temp += " AND dateOfBirth >= ?";
                        }
                        if (end == 1) {
                            temp += " AND dateOfBirth <= ?";

                        }
                        temp += ";";
                        try {
                            prepStatGetPatients[index] = connection.prepareStatement(temp);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        index++;
                    }
                }
            }

        }
    }

    /**
     * Returns the connection.
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
    public void removeHospitalStay(long hospitalStayID) {
        assert hospitalStayID > 0 && hospitalStayID != PersistentObject.INVALID_OBJECT_ID;
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                                DELETE FROM "Hospitalstay" WHERE id = ?;
                                                """)) {
            preparedStatement.setLong(1, hospitalStayID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException("Error occurred while trying to delete a hospital stay!");
        }
    }

    @Override
    public List<Patient> getPatients(String lastname, String firstname, Date startDate, Date endDate) {
        List<Patient> patients = new ArrayList();

        int counter = 0;
        if (lastname != null) {
            counter = counter + 4;
        }
        if (firstname != null) {
            counter = counter + 12;
        }
        if (startDate != null) {
            counter = counter + 2;
        }
        if (endDate != null) {
            counter = counter + 1;
        }

        try {
            if (counter == 0) {
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (counter == 1) {
                prepStatGetPatients[counter].setDate(1, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (counter == 2) {
                prepStatGetPatients[counter].setDate(1, DateHelper.convertDate(startDate));
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }

            } else if (counter == 3) {
                prepStatGetPatients[counter].setDate(1, DateHelper.convertDate(startDate));
                prepStatGetPatients[counter].setDate(2, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (counter == 4) {
                prepStatGetPatients[counter].setString(1, lastname);
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }

            } else if (counter == 5) {
                prepStatGetPatients[counter].setString(1, lastname);
                prepStatGetPatients[counter].setDate(2, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (counter == 6) {
                prepStatGetPatients[counter].setString(1, lastname);
                prepStatGetPatients[counter].setDate(2, DateHelper.convertDate(startDate));
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }


            }  else if (counter == 7) {

                prepStatGetPatients[counter].setString(1, lastname);
                prepStatGetPatients[counter].setDate(2, DateHelper.convertDate(startDate));
                prepStatGetPatients[counter].setDate(3, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }


            } else if (counter == 12) {
                prepStatGetPatients[counter].setString(1, firstname);
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (counter == 13) {
                prepStatGetPatients[counter].setString(1, firstname);
                prepStatGetPatients[counter].setDate(2, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (counter == 14) {
                prepStatGetPatients[counter].setString(1, firstname);
                prepStatGetPatients[counter].setDate(2, DateHelper.convertDate(startDate));
                try (ResultSet rs =  prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }

            } else if (counter == 15) {

                prepStatGetPatients[counter].setString(1, firstname);
                prepStatGetPatients[counter].setDate(2, DateHelper.convertDate(startDate));
                prepStatGetPatients[counter].setDate(3, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }

            } else if (counter == 16) {
                prepStatGetPatients[counter].setString(1, firstname);
                prepStatGetPatients[counter].setString(2, lastname);
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (counter == 17) {

                prepStatGetPatients[counter].setString(1, firstname);
                prepStatGetPatients[counter].setString(2, lastname);
                prepStatGetPatients[counter].setDate(3, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (counter == 18) {
                prepStatGetPatients[counter].setString(1, firstname);
                prepStatGetPatients[counter].setString(2, lastname);
                prepStatGetPatients[counter].setDate(3, DateHelper.convertDate(startDate));
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (counter == 19) {
                prepStatGetPatients[counter].setString(1, firstname);
                prepStatGetPatients[counter].setString(2, lastname);
                prepStatGetPatients[counter].setDate(3, DateHelper.convertDate(startDate));
                prepStatGetPatients[counter].setDate(4, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[counter].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    /**
     * Returns an array filled with patient objects bases on the sql query.
     * @ param patients
     * @ param rs
     * @ returns an arraylist
     */
    public List<Patient> getPatientList(List<Patient> patients, ResultSet rs) throws SQLException {
        while (rs.next()) {
            patients.add(new PatientImpl(this, rs.getLong(1), rs.getString(2),
                    rs.getString(3), rs.getDate(4), rs.getString(5),
                    rs.getString(6)));
        }
        return patients;
    }


    @Override
    public Patient getPatient(long patientID) {
        assert patientID > 0;
        Patient patient = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                                                                SELECT * FROM "Patient" WHERE ID = ?;
                                                                                        """);) {
            preparedStatement.setLong(1, patientID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                patient = new PatientImpl(this, rs.getLong(1), rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4), rs.getString(5), rs.getString(6));
            }
            rs.close();
        } catch (SQLException throwables) {
            throw new FetchException("Error occurred while fetching a patient object!");
        }
        return patient;
    }

    @Override
    public List<Ward> getWards() {
        List<Ward> wards = new ArrayList();
        try (ResultSet rs = prepStatGetWards.executeQuery();) {
            while (rs.next()) {
                wards.add(new WardImpl(this, rs.getLong(1), rs.getString(2),
                        rs.getInt(3)));
            }
            return wards;
        } catch (SQLException throwables) {
            throw new FetchException("Error occurred while fetching a ward object!");
        }
    }

    @Override
    public Ward getWard(long wardID) {
        assert wardID > 0;
        try {
            prepStatGetWard.setLong(1, wardID);
            try (ResultSet rs = prepStatGetWard.executeQuery()) {
                while (rs.next()) {
                    WardImpl ward = new WardImpl(this, rs.getLong(1),
                            rs.getString(2), rs.getInt(3));
                    return ward;
                }
            }
        } catch (SQLException throwables) {
            throw new FetchException("Error occurred while fetching a ward object!");
        }
        return null;
    }

    @Override
    public List<HospitalStay> getHospitalStays(long patientID) {
        assert patientID > 0;
        assert getPatient(patientID).getObjectID() != PersistentObject.INVALID_OBJECT_ID;
        List<HospitalStay> hospitalStays = new ArrayList();
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                                                               SELECT * FROM "HospitalStay"
                                                                               AS hs, "Ward"
                                                                               AS wa, "Patient"
                                                                               AS p
                                                                               WHERE hs.w_id = wa.id
                                                                               AND hs.p_id = p.id
                                                                               AND p.id = ?
                                                                               """);) {

            preparedStatement.setLong(1, patientID);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    hospitalStays.add(new HospitalStayImpl(this, rs.getLong(1),
                            rs.getDate(4), rs.getDate(5),
                            new WardImpl(this, rs.getLong(3), rs.getString("name"),
                                    rs.getInt("numberofbeds")),
                            new PatientImpl(rs.getLong(2), rs.getString("firstname"),
                                    rs.getString("lastname"), this)));
                }
            }

        } catch (SQLException throwables) {
            throw new FetchException("Error occurred while fetching a hospital stay object!");
        }
        return hospitalStays;
    }

    @Override
    public List<HospitalStay> getHospitalStays(long patientID, Date startDate, Date endDate) {

        assert patientID > 0;
        assert startDate == null && endDate == null || startDate != null && endDate == null
               || startDate == null && endDate != null
               || startDate != null && endDate != null && startDate.before(endDate);

        List<HospitalStay> hospitalStays = new ArrayList();


        if (startDate == null && endDate == null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                                                              SELECT * FROM "Hospitalstay" 
                                                                              AS hs, "Ward"
                                                                              AS wa, "Patient"
                                                                              AS p
                                                                              WHERE hs.w_id = wa.id
                                                                              AND  hs.p_id = p.id 
                                                                              AND p.id  = ?
                                                                              """)) {
                preparedStatement.setLong(1, patientID);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        hospitalStays.add(new HospitalStayImpl(this, rs.getLong(1),
                               rs.getDate(4), rs.getDate(5),
                              new WardImpl(this, rs.getLong(3), rs.getString("name"),
                                       rs.getInt("numberofbeds")),
                               new PatientImpl(rs.getLong(2), rs.getString("firstname"),
                                     rs.getString("lastname"), this)));
                    }
                }
            } catch (SQLException e) {
                throw new FetchException(e.getMessage());
            }
        } else if (startDate == null && endDate != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                                                              SELECT * FROM "Hospitalstay"
                                                                              AS hs, "Ward" 
                                                                              AS wa, "Patient"
                                                                              AS p
                                                                              WHERE hs.w_id = wa.id AND
                                                                              hs.p_id = p.id
                                                                              AND p.id  = ?
                                                                              AND dateofdischarge <= ?
                                                                              """)) {
                preparedStatement.setLong(1, patientID);
                preparedStatement.setDate(2, DateHelper.convertDate(endDate));
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        hospitalStays.add(new HospitalStayImpl(this, rs.getLong(1),
                                rs.getDate(4), rs.getDate(5),
                                new WardImpl(this, rs.getLong(3), rs.getString("name"),
                                        rs.getInt("numberofbeds")),
                                new PatientImpl(rs.getLong(2), rs.getString("firstname"),
                                        rs.getString("lastname"), this)));
                    }
                }
            } catch (SQLException e) {
                throw new FetchException(e.getMessage());
            }
        } else if (startDate != null && endDate == null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                                                              SELECT * FROM "Hospitalstay"
                                                                              AS hs, "Ward"
                                                                              AS wa, "Patient"
                                                                              AS p
                                                                              WHERE hs.w_id = wa.id AND
                                                                              hs.p_id = p.id
                                                                              AND p.id  = ?
                                                                              AND dateofadmission >= ?
                                                                              """)) {
                preparedStatement.setLong(1, patientID);
                preparedStatement.setDate(2, DateHelper.convertDate(startDate));
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        hospitalStays.add(new HospitalStayImpl(this, rs.getLong(1),
                                rs.getDate(4), rs.getDate(5),
                                new WardImpl(this, rs.getLong(3), rs.getString("name"),
                                        rs.getInt("numberofbeds")),
                                new PatientImpl(rs.getLong(2), rs.getString("firstname"),
                                        rs.getString("lastname"), this)));
                    }
                }
            } catch (SQLException e) {
                throw new FetchException(e.getMessage());
            }
        } else if (startDate != null && endDate != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT * FROM "HospitalStay" AS hs, "Ward" AS wa, "Patient" AS p 
                    WHERE  hs.w_id = wa.id AND hs.p_id = p.id AND p.id AND dateofadmission >= ? AND dateofdischarge <= ?;
                                                """)) {
                preparedStatement.setLong(1, patientID);
                preparedStatement.setDate(2, DateHelper.convertDate(startDate));
                preparedStatement.setDate(3, DateHelper.convertDate(endDate));
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        hospitalStays.add(new HospitalStayImpl(this, rs.getLong(1),
                                rs.getDate(4), rs.getDate(5),
                                new WardImpl(this, rs.getLong(3), rs.getString("name"),
                                        rs.getInt("numberofbeds")),
                                new PatientImpl(rs.getLong(2), rs.getString("firstname"),
                                        rs.getString("lastname"), this)));
                    }
                }
            } catch (SQLException e) {
                throw new FetchException(e.getMessage());
            }
        }
        return hospitalStays;
    }

    @Override
    public double getAverageHospitalStayDuration(long wardID) {
        assert wardID > 0;

        double average = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                                    SELECT AVG(dateofdischarge-dateofadmission) AS test 
                                                    FROM "HospitalStay" AS hs 
                                                    WHERE hs.w_id = ? AND dateofdischarge IS NOT NULL
                                                    """
        )) {

            preparedStatement.setLong(1, wardID);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    average += rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return average;
    }

    @Override

    public int getAllocatedBeds(Ward ward) {
        int numberOfBeds = 0;


        if (ward == null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                                                        SELECT COUNT(*) FROM "HospitalStay"
                                                                        WHERE dateofdischarge IS NULL;
                                                                        """);
                 ResultSet rs = preparedStatement.executeQuery();) {
                if (rs.next()) {
                    numberOfBeds += rs.getInt(1);
                }
            } catch (SQLException e) {
                throw new FetchException(e.getMessage());
            }
        } else {
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                                                            SELECT COUNT(*) From HospitalStay 
                                                                            WHERE dateofdischarge IS NULL 
                                                                            AND HospitalStay.w_id = ?;
                                                                            """)) {
                preparedStatement.setLong(1, ward.getObjectID());
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        numberOfBeds += rs.getInt(1);
                    }
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return numberOfBeds;
    }

    @Override
    public int getFreeBeds(Ward ward) {
        int freeBeds = 0;
        assert ward == null || ward.isPersistent();
        if (ward == null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
SELECT SUM(numberofbeds) FROM "Ward";
"""
                    )) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        freeBeds += rs.getLong(1) - getAllocatedBeds(ward);
                    }
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
                                    SELECT SUM(numberofbeds) FROM "Ward" WHERE "Ward".id = ?;
                                      """
            )) {
                preparedStatement.setLong(1, ward.getObjectID());
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        freeBeds += rs.getLong(1) - getAllocatedBeds(ward);
                    }
                }
            } catch (SQLException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return freeBeds;
    }

    @Override
    public long store(PersistentObject persistentObject) {
        assert persistentObject != null;
        if (persistentObject instanceof PatientImpl) {
            try {
                return ((PatientImpl) persistentObject).store(connection);
            } catch (SQLException e) {
                throw new StoreException("Error occurred while trying to store a patient object!");
            }
        } else if (persistentObject instanceof WardImpl) {
            try {
                return ((WardImpl) persistentObject).store(connection);
            } catch (SQLException e) {
                throw new StoreException("Error occurred while trying to store a ward object!");
            }
        } else if (persistentObject instanceof HospitalStayImpl) {
            try {
                return ((HospitalStayImpl) persistentObject).store(connection);
            } catch (SQLException e) {
                throw new StoreException("Error occurred while trying to store a hospital stay object!");
            }
        } else {
            throw new StoreException("Error occurred while trying to strore the object!");
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
