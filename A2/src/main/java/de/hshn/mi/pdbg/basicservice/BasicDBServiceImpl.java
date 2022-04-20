package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.PersistentObject;

import java.util.Date;
import java.util.List;

/**
 * Klasse.
 */
public class BasicDBServiceImpl implements BasicDBService {


    @Override
    public Patient createPatient(String s, String s1) {
        return null;
    }

    @Override
    public Ward createWard(String s, int i) {
        return null;
    }

    @Override
    public HospitalStay createHospitalStay(Patient patient, Ward ward, Date date) {
        return null;
    }

    @Override
    public void removeHospitalStay(long l) {

    }

    @Override
    public List<Patient> getPatients(String s, String s1, Date date, Date date1) {
        return null;
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
        return 0;
    }

    @Override
    public void close() {

    }
}
