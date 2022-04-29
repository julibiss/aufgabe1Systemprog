package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.PersistentObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Klasse.
 */
public class BasicDBServiceImpl implements BasicDBService {
    private Connection connection;
    private List<Patient> patients = new ArrayList();


    /**
     * Test.
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

        return new PatientImpl(lastname, firstname, this, PersistentObject.INVALID_OBJECT_ID);

    }

    @Override
    public Ward createWard(String name, int numberOfBeds) {
        assert name != null && !name.isBlank();
        assert numberOfBeds > 0;
        return new WardImpl(this, PersistentObject.INVALID_OBJECT_ID, name, numberOfBeds);
    }

    @Override
    public HospitalStay createHospitalStay(Patient patient, Ward ward, Date admissionDate) {
        assert patient != null;
        assert ward != null;
        assert admissionDate != null;
        return new HospitalStayImpl(this, PersistentObject.INVALID_OBJECT_ID, admissionDate, ward, patient);
    }

    @Override
    public void removeHospitalStay(long l) {

    }

    @Override
    public List<Patient> getPatients(String firstname, String lastname, Date date, Date date1) {
        List<Patient> patients2 = new ArrayList();
        for (int i = 0; i < patients.size(); i++) {

            if (firstname == null && lastname == null && date == null && date1 == null) {
                patients.get(i).getDateOfBirth();
            }
            else if(firstname != null && lastname == null && date == null && date1 == null)
            {
                if(patients.get(i).getFirstname() == firstname)
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname == null && lastname != null && date == null && date1 == null)
            {
                if(patients.get(i).getLastname() == lastname)
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname == null && lastname == null && date != null && date1 == null)
            {
                if(patients.get(i).getDateOfBirth().after(date))
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname == null && lastname == null && date == null && date1 != null)
            {
                if(patients.get(i).getDateOfBirth().before(date1))
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname != null && lastname != null && date == null && date1 == null)
            {
                if(patients.get(i).getFirstname() == firstname && patients.get(i).getLastname() == lastname )
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname == null && lastname != null && date != null && date1 == null)
            {
                if(patients.get(i).getLastname() == lastname && patients.get(i).getDateOfBirth().after(date))
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname == null && lastname == null && date != null && date1 != null)
            {
                if(patients.get(i).getDateOfBirth().after(date) && patients.get(i).getDateOfBirth().before(date1))
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname != null && lastname == null && date != null && date1 == null)
            {
                if(patients.get(i).getFirstname() == firstname && patients.get(i).getDateOfBirth().before(date1))
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname != null && lastname == null && date == null && date1 != null)
            {
                if(patients.get(i).getFirstname() == firstname && patients.get(i).getDateOfBirth().after(date))
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname == null && lastname != null && date == null && date1 != null)
            {
                if(patients.get(i).getLastname() == lastname && patients.get(i).getDateOfBirth().before(date))
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname != null && lastname != null && date == null && date1 != null)
            {
                if( patients.get(i).getFirstname() == firstname && patients.get(i).getLastname() == lastname
                    && patients.get(i).getDateOfBirth().before(date1))
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname != null && lastname != null && date != null && date1 == null)
            {
                if( patients.get(i).getFirstname() == firstname && patients.get(i).getLastname() == lastname
                    && patients.get(i).getDateOfBirth().after(date))
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname != null && lastname == null && date != null && date1 != null)
            {
                if( patients.get(i).getFirstname() == firstname && patients.get(i).getDateOfBirth().after(date)
                    && patients.get(i).getDateOfBirth().before(date1))
                {
                    patients2.add(patients.get(i));
                }
            }else if(firstname == null && lastname != null && date != null && date1 != null)
            {
                if( patients.get(i).getLastname() == lastname && patients.get(i).getDateOfBirth().after(date)
                    && patients.get(i).getDateOfBirth().before(date1))
                {
                    patients2.add(patients.get(i));
                }
            }

            else if (firstname != null && lastname != null && date != null && date1 != null) {
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
        return null;
    }

    @Override
    public List<Ward> getWards() {
        return null;
    }

    @Override
    public Ward getWard(long l) {
        return null;
    }

    @Override
    public List<HospitalStay> getHospitalStays(long l) {
        return null;
    }

    @Override
    public List<HospitalStay> getHospitalStays(long l, Date date, Date date1) {
        return null;
    }

    @Override
    public double getAverageHospitalStayDuration(long l) {
        return 0;
    }

    @Override
    public int getAllocatedBeds(Ward ward) {
        return 0;
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