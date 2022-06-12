package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.PersistentObject;
import de.hshn.mi.pdbg.exception.FetchException;
import de.hshn.mi.pdbg.exception.StoreException;
import de.hshn.mi.pdbg.util.DateHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implements {@link BasicDBService}.
 */
public class BasicDBServiceImpl implements BasicDBService {
    private Connection connection;
    PreparedStatement[] prepStatGetPatients = new PreparedStatement[16];
    PreparedStatement[] prepStatGetHS = new PreparedStatement[4];
    PreparedStatement prepStatgetOneHS;
    PreparedStatement prepStatGetWards;
    PreparedStatement prepStatGetWard;
    PreparedStatement prepStatGetPatient;
    /**
     * Default constructor which creates a connection to the database.
     * @ param jdbcUrl link to our local database
     * @ param sqlUser
     * @ param sqlPassword
     */
    public BasicDBServiceImpl(String jdbcUrl, String sqlUser, String sqlPassword) throws SQLException {
        connection = getConnection(jdbcUrl, sqlUser, sqlPassword);
        fillPrepStatPatients();
        fillPrepStatHospitalStay();
        prepStatGetPatient = connection.prepareStatement("""
                                                                                SELECT * FROM Patient WHERE ID = ?;
                                                                                        """);
        prepStatgetOneHS = connection.prepareStatement("""
                                                           SELECT * FROM hospitalstay AS hs, ward as wa, patient as p
                                                           WHERE hs.w_id = wa.id AND hs.p_id = p.id AND p.id = ?;
                                                           """);
        prepStatGetWard = connection.prepareStatement("""
                                                          SELECT * FROM Ward
                                                          WHERE ID = ?
                                                          """);
        prepStatGetWards = connection.prepareStatement("""
                                                           SELECT * FROM Ward
                                                           """);
    }

    private void fillPrepStatHospitalStay() {
        String base = "SELECT * FROM hospitalstay AS hs, ward as wa, patient as p " +
                      "WHERE hs.w_id = wa.id AND hs.p_id = p.id AND p.id = ?";

        int index = 0;
        for (int start = 0; start <= 1; start++) {
            for (int end = 0; end <= 1; end++) {
                String temp = base;
                if (end == 1) {
                    temp += " AND dateofdischarge <= ?";
                }
                if (start == 1) {
                    temp += " AND dateofadmission >= ?";
                }
                temp += ";";
                try {
                    prepStatGetHS[index] = connection.prepareStatement(temp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                index++;
            }
        }
    }

    private void fillPrepStatPatients() {
        String base = "SELECT * FROM Patient WHERE '1' = '1'";
        int index = 0;
        for (int last = 0; last <= 1; last++) {
            for (int first = 0; first <= 1; first++) {
                for (int start = 0; start <= 1; start++) {
                    for (int end = 0; end <= 1; end++) {
                        String temp = base;
                        if (last == 1) {
                            temp += " AND firstname LIKE ?";
                        }
                        if (first == 1) {
                            temp += " AND lastname LIKE ?";
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
                                                DELETE FROM HospitalStay WHERE id = ?;
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

        try {
            if (firstname == null && lastname == null && startDate == null && endDate == null) {
                try (ResultSet rs = prepStatGetPatients[0].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (firstname != null && lastname == null && startDate == null && endDate == null) {

                prepStatGetPatients[8].setString(1, firstname);
                try (ResultSet rs = prepStatGetPatients[8].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (firstname == null && lastname != null && startDate == null && endDate == null) {
                prepStatGetPatients[4].setString(1, lastname);
                try (ResultSet rs = prepStatGetPatients[4].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (firstname == null && lastname == null && startDate != null && endDate == null) {
                prepStatGetPatients[2].setDate(1, DateHelper.convertDate(startDate));
                try (ResultSet rs = prepStatGetPatients[2].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (firstname == null && lastname == null && startDate == null && endDate != null) {
                prepStatGetPatients[1].setDate(1, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[1].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (firstname != null && lastname != null && startDate == null && endDate == null) {
                prepStatGetPatients[12].setString(1, firstname);
                prepStatGetPatients[12].setString(2, lastname);
                try (ResultSet rs = prepStatGetPatients[12].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (firstname == null && lastname != null && startDate != null && endDate == null) {
                prepStatGetPatients[6].setString(1, lastname);
                prepStatGetPatients[6].setDate(2, DateHelper.convertDate(startDate));
                try (ResultSet rs = prepStatGetPatients[6].executeQuery()) {
                    return getPatientList(patients, rs);
                }

            } else if (firstname == null && lastname == null && startDate != null && endDate != null) {
                prepStatGetPatients[3].setDate(1, DateHelper.convertDate(startDate));
                prepStatGetPatients[3].setDate(2, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[3].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (firstname != null && lastname == null && startDate != null && endDate == null) {
                prepStatGetPatients[10].setString(1, firstname);
                prepStatGetPatients[10].setDate(2, DateHelper.convertDate(startDate));
                try (ResultSet rs = prepStatGetPatients[10].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (firstname != null && lastname == null && startDate == null && endDate != null) {
                prepStatGetPatients[9].setString(1, firstname);
                prepStatGetPatients[9].setDate(2, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[9].executeQuery()) {
                    return getPatientList(patients, rs);
                }

            } else if (firstname == null && lastname != null && startDate == null && endDate != null) {
                prepStatGetPatients[5].setString(1, lastname);
                prepStatGetPatients[5].setDate(2, DateHelper.convertDate(endDate));
                    try (ResultSet rs = prepStatGetPatients[5].executeQuery()) {
                        return getPatientList(patients, rs);
                    }
            } else if (firstname != null && lastname != null && startDate == null && endDate != null) {
                prepStatGetPatients[13].setString(1, firstname);
                prepStatGetPatients[13].setString(2, lastname);
                prepStatGetPatients[13].setDate(3, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[13].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (firstname != null && lastname != null && startDate != null && endDate == null) {
                prepStatGetPatients[14].setString(1, firstname);
                prepStatGetPatients[14].setString(2, lastname);
                prepStatGetPatients[14].setDate(3, DateHelper.convertDate(startDate));
                try (ResultSet rs = prepStatGetPatients[14].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (firstname != null && lastname == null && startDate != null && endDate != null) {
                prepStatGetPatients[11].setString(1, firstname);
                prepStatGetPatients[11].setDate(2, DateHelper.convertDate(startDate));
                prepStatGetPatients[11].setDate(3, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[11].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (firstname == null && lastname != null && startDate != null && endDate != null) {
                prepStatGetPatients[7].setString(1, lastname);
                prepStatGetPatients[7].setDate(2, DateHelper.convertDate(startDate));
                prepStatGetPatients[7].setDate(3, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[7].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            } else if (firstname != null && lastname != null && startDate != null && endDate != null) {

                prepStatGetPatients[15].setString(1, firstname);
                prepStatGetPatients[15].setString(2, lastname);
                prepStatGetPatients[15].setDate(3, DateHelper.convertDate(startDate));
                prepStatGetPatients[15].setDate(4, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetPatients[15].executeQuery()) {
                    return getPatientList(patients, rs);
                }
            }
        } catch (SQLException e) {
            throw new FetchException(e.getMessage());
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
        try  {
            prepStatGetPatient.setLong(1, patientID);
            ResultSet rs = prepStatGetPatient.executeQuery();
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
        try {
            prepStatgetOneHS.setLong(1, patientID);
            try (ResultSet rs = prepStatgetOneHS.executeQuery()) {
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


        int counter = 0;

        if (startDate != null) {
            counter = counter + 2;
        }
        if (endDate != null) {
            counter = counter + 1;
        }

        if (counter == 0) {
            try {
                prepStatGetHS[0].setLong(1, patientID);
                try (ResultSet rs = prepStatGetHS[0].executeQuery()) {
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
        } else if (counter == 1) {
            try {
                prepStatGetHS[1].setLong(1, patientID);
                prepStatGetHS[1].setDate(2, DateHelper.convertDate(endDate));
                try (ResultSet rs = prepStatGetHS[1].executeQuery()) {
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
        } else if (counter == 2) {
            try {
                prepStatGetHS[2].setLong(1, patientID);
                prepStatGetHS[2].setDate(2, DateHelper.convertDate(startDate));
                try (ResultSet rs = prepStatGetHS[2].executeQuery()) {
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
        } else if (counter == 3) {
            try {
                prepStatGetHS[3].setLong(1, patientID);
                prepStatGetHS[3].setDate(2, DateHelper.convertDate(endDate));
                prepStatGetHS[3].setDate(3, DateHelper.convertDate(startDate));
                try (ResultSet rs = prepStatGetHS[3].executeQuery()) {
                    while (rs.next()) {
                        hospitalStays.add(new HospitalStayImpl(this, rs.getLong(1),
                                rs.getDate(4), rs.getDate(5),
                                new WardImpl(this, rs.getLong(3), rs.getString("name"),
                                        rs.getInt("numberofbeds")),
                                new PatientImpl(rs.getLong(2), rs.getString("firstname"),
                                        rs.getString("lastname"), this)));
                    }
                }
                prepStatGetHS[3].close();
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
                                                    FROM HospitalStay AS hs 
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
                                                                        SELECT COUNT(*) FROM HospitalStay
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
                                SELECT SUM(numberofbeds) FROM Ward;
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
                                    SELECT SUM(numberofbeds) FROM Ward WHERE Ward.id = ?;
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
