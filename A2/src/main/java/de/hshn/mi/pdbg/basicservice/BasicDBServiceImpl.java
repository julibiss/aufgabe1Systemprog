package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.PersistentObject;
import org.checkerframework.checker.units.qual.A;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Klasse.
 */
public class BasicDBServiceImpl implements BasicDBService {
    private Connection connection;
    private List<Patient> patients = new ArrayList();
    private List<Ward> wards = new ArrayList();
    private List<HospitalStay> hospitalStays = new ArrayList();

    /**
     * Test.
     *
     * @ param jdbcUrl j
     * @ param sqlUser s
     * @ param sqlPassword s
     */
    public BasicDBServiceImpl(String jdbcUrl, String sqlUser, String sqlPassword) {
        try {
            connection = getConnection(jdbcUrl, sqlUser, sqlPassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Connection getConnection(String jdbcUrl, String user, String password) throws SQLException {
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    @Override
    public Patient createPatient(String lastname, String firstname) {
        assert lastname != null && !lastname.isBlank();
        assert firstname != null && !firstname.isBlank();
        Patient patient = new PatientImpl(lastname, firstname, this, PersistentObject.INVALID_OBJECT_ID);
        patients.add(patient);
        return patient;
    }

    @Override
    public Ward createWard(String name, int numberOfBeds) {
        assert name != null && !name.isBlank();
        assert numberOfBeds > 0;
        Ward ward = new WardImpl(this, PersistentObject.INVALID_OBJECT_ID, name, numberOfBeds);
        wards.add(ward);
        return ward;
    }

    @Override
    public HospitalStay createHospitalStay(Patient patient, Ward ward, Date admissionDate) {
        assert patient != null;
        assert ward != null;
        assert admissionDate != null;
        HospitalStay hospitalStay =
                new HospitalStayImpl(this, PersistentObject.INVALID_OBJECT_ID, admissionDate, ward, patient);
        hospitalStays.add(hospitalStay);
        return hospitalStay;
    }

    @Override
    public void removeHospitalStay(long l) {
        assert l > 0 && l != PersistentObject.INVALID_OBJECT_ID;

        for (int i = 0; i < hospitalStays.size(); i++) {
            if (hospitalStays.get(i).getObjectID() == l) {
                hospitalStays.remove(i);
            }
        }

    }

    @Override
    public List<Patient> getPatients(String firstname, String lastname, Date date, Date date1) {
        List<Patient> patients2 = new ArrayList();
        for (int i = 0; i < patients.size(); i++) {

            if (firstname == null && lastname == null && date == null && date1 == null) {
                patients.get(i).getDateOfBirth();
                patients2.add(patients.get(i));
            } else if (firstname != null && lastname == null && date == null && date1 == null) {
                if (patients.get(i).getFirstname() == firstname) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname == null && lastname != null && date == null && date1 == null) {
                if (patients.get(i).getLastname() == lastname) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname == null && lastname == null && date != null && date1 == null) {
                if (patients.get(i).getDateOfBirth().after(date)) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname == null && lastname == null && date == null && date1 != null) {
                if (patients.get(i).getDateOfBirth().before(date1)) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname != null && lastname != null && date == null && date1 == null) {
                if (patients.get(i).getFirstname() == firstname && patients.get(i).getLastname() == lastname) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname == null && lastname != null && date != null && date1 == null) {
                if (patients.get(i).getLastname() == lastname && patients.get(i).getDateOfBirth().after(date)) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname == null && lastname == null && date != null && date1 != null) {
                if (patients.get(i).getDateOfBirth().after(date) && patients.get(i).getDateOfBirth().before(date1)) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname != null && lastname == null && date != null && date1 == null) {
                if (patients.get(i).getFirstname() == firstname && patients.get(i).getDateOfBirth().before(date1)) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname != null && lastname == null && date == null && date1 != null) {
                if (patients.get(i).getFirstname() == firstname && patients.get(i).getDateOfBirth().after(date)) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname == null && lastname != null && date == null && date1 != null) {
                if (patients.get(i).getLastname() == lastname && patients.get(i).getDateOfBirth().before(date)) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname != null && lastname != null && date == null && date1 != null) {
                if (patients.get(i).getFirstname() == firstname && patients.get(i).getLastname() == lastname
                    && patients.get(i).getDateOfBirth().before(date1)) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname != null && lastname != null && date != null && date1 == null) {
                if (patients.get(i).getFirstname() == firstname && patients.get(i).getLastname() == lastname
                    && patients.get(i).getDateOfBirth().after(date)) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname != null && lastname == null && date != null && date1 != null) {
                if (patients.get(i).getFirstname() == firstname && patients.get(i).getDateOfBirth().after(date)
                    && patients.get(i).getDateOfBirth().before(date1)) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname == null && lastname != null && date != null && date1 != null) {
                if (patients.get(i).getLastname() == lastname && patients.get(i).getDateOfBirth().after(date)
                    && patients.get(i).getDateOfBirth().before(date1)) {
                    patients2.add(patients.get(i));
                }
            } else if (firstname != null && lastname != null && date != null && date1 != null) {
                if (patients.get(i).getFirstname() == firstname && patients.get(i).getLastname() == lastname
                    && patients.get(i).getDateOfBirth().before(date1) && patients.get(i).getDateOfBirth().after(date)) {
                    patients2.add(patients.get(i));
                }
            }
        }
        return patients2;
    }

    @Override
    public Patient getPatient(long l) {
        assert l > 0 && l != PersistentObject.INVALID_OBJECT_ID;
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getObjectID() == l) {
                return patients.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Ward> getWards() {
        return wards;
    }

    @Override
    public Ward getWard(long l) {
        assert l > 0 && l != PersistentObject.INVALID_OBJECT_ID;
        for (int i = 0; i < wards.size(); i++) {
            if (wards.get(i).getObjectID() == l) {
                return wards.get(i);
            }
        }
        return null;
    }

    @Override
    public List<HospitalStay> getHospitalStays(long l) {
        List<HospitalStay> hospitalStays2 = new ArrayList();
        assert l > 0 && l != PersistentObject.INVALID_OBJECT_ID;
        for (int i = 0; i < hospitalStays.size(); i++) {
            if (hospitalStays.get(i).getObjectID() == l) {
                hospitalStays2.add(hospitalStays.get(i));
            }
        }
        return hospitalStays2;
    }

    @Override
    public List<HospitalStay> getHospitalStays(long l, Date date, Date date1) {
        List<HospitalStay> hospitalStays2 = new ArrayList();
        assert l > 0 && l != PersistentObject.INVALID_OBJECT_ID;
        for (int i = 0; i < hospitalStays.size(); i++) {
            if (hospitalStays.get(i).getObjectID() == l && hospitalStays.get(i).getAdmissionDate().after(date)
                && hospitalStays.get(i).getDischargeDate().before(date)) ;
            {
                hospitalStays2.add(hospitalStays.get(i));
            }
        }
        return hospitalStays2;
    }

    @Override
    public double getAverageHospitalStayDuration(long l) {
        double average = 0;
        assert l > 0 && l != PersistentObject.INVALID_OBJECT_ID;
        for (int i = 0; i < hospitalStays.size(); i++) {
            if (hospitalStays.get(i).getObjectID() == l && hospitalStays.get(i).getDischargeDate() != null) {
                long diff = hospitalStays.get(i).getAdmissionDate().getTime() - hospitalStays.get(i).getDischargeDate().getTime();
                average = average + TimeUnit.DAYS.convert(diff, TimeUnit.DAYS);
            }
        }
        return average;
    }

    @Override
    public int getAllocatedBeds(Ward ward) {
        int numberOfBeds = 0;

        if (ward == null) {
            for (int i = 0; i < wards.size(); i++) {
                numberOfBeds += wards.get(i).getNumberOfBeds();
            }
        } else {
            for (int i = 0; i < wards.size(); i++) {
                if(wards.get(i) == ward)
                {
                    numberOfBeds += wards.get(i).getNumberOfBeds();
                }
            }
        }
        return numberOfBeds;
    }

    @Override
    public int getFreeBeds(Ward ward) {
        return 0;
    }

    @Override
    public long store(PersistentObject persistentObject) {
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

    }
}