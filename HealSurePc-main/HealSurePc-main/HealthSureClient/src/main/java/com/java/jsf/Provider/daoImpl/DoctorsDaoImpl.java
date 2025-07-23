package com.java.jsf.Provider.daoImpl;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.java.jsf.Provider.dao.DoctorsDao;
import com.java.jsf.Provider.model.DoctorStatus;
import com.java.jsf.Provider.model.Doctors;
import com.java.jsf.Provider.model.LoginStatus;
import com.java.jsf.Util.SessionHelper;

public class DoctorsDaoImpl implements DoctorsDao{
	SessionFactory sf;
	Session session;

	@Override
	 public void addDoctors(Doctors doctors) throws Exception {
        validateDoctor(doctors);
        doctors.setDoctorId(generateDoctorId());

        sf = SessionHelper.getSessionFactory();
        session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(doctors);
            tx.commit();
        } finally {
            session.close();
        }
    }

    private void validateDoctor(Doctors doctors) throws Exception {
        StringBuilder errors = new StringBuilder();

        if (doctors.getDoctorName() == null || !doctors.getDoctorName().matches("^[A-Za-z\\s]+$")) {
            errors.append("Doctor name must contain only alphabets and spaces.\n");
        }

        String[] validQualifications = {"MBBS", "BDS", "BAMS", "BHMS", "BUMS", "MD", "MS", "DNB"};
        boolean isQualificationValid = Arrays.stream(validQualifications)
            .anyMatch(q -> q.equalsIgnoreCase(doctors.getQualification()));
        if (!isQualificationValid) {
            errors.append("Invalid qualification.\n");
        }

        String[] validSpecializations = {
            "Cardiologist", "Dermatologist", "Neurologist", "Oncologist",
            "Orthopedic", "Psychiatrist", "General Surgeon", "Neurosurgeon"
        };
        boolean isSpecializationValid = Arrays.stream(validSpecializations)
            .anyMatch(s -> s.equalsIgnoreCase(doctors.getSpecialization()));
        if (!isSpecializationValid) {
            errors.append("Invalid specialization.\n");
        }

        if (doctors.getLicenseNo() == null ||
            !doctors.getLicenseNo().matches("^[A-Z]{2}/\\d{4}/\\d{5}/[A-Z]{1}$") ||
            doctors.getLicenseNo().length() != 15) {
            errors.append("Invalid License number format.\n");
        }

        if (doctors.getEmail() == null || 
            !doctors.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$")) {
            errors.append("Invalid email format.\n");
        }

        if (doctors.getPhoneNumber() == null || !doctors.getPhoneNumber().matches("^\\d{10}$")) {
            errors.append("Phone number must be 10 digits.\n");
        }

        if (errors.length() > 0) {
            throw new Exception("Validation Failed:\n" + errors.toString());
        }
        
        doctors.setDoctorStatus(DoctorStatus.INACTIVE);
    }

    @Override
    public void saveDoctor(Doctors doctor) {
        sf = SessionHelper.getSessionFactory();
        session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(doctor);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    
    @Override
    public String generateDoctorId() {
        sf = SessionHelper.getSessionFactory();
        session = sf.openSession();
        String newDoctorId = "DOC001";
        try {
        	 Query q = session.getNamedQuery("DoctorId");
             q.setMaxResults(1);
            String latestId = (String) q.uniqueResult();
            if (latestId != null) {
                int num = Integer.parseInt(latestId.substring(3));
                newDoctorId = "DOC" + String.format("%03d", num + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return newDoctorId;
    }

    @Override
    public Doctors searchByDoctorsId(int doctorId) throws Exception {
        sf = SessionHelper.getSessionFactory();
        session = sf.openSession();
        try {
            Query query = session.getNamedQuery("Doctor.findById");
            query.setParameter("doctorId", doctorId);
            return (Doctors) query.uniqueResult();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Doctors> getAllDoctors() throws Exception {
        sf = SessionHelper.getSessionFactory();
        session = sf.openSession();
        try {
            Query query = session.getNamedQuery("Doctor.findAll");
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Doctors> searchByProviderId(String providerId) throws Exception {
        sf = SessionHelper.getSessionFactory();
        session = sf.openSession();
        try {
            Query query = session.getNamedQuery("Doctor.findByProviderId");
            query.setParameter("providerId", providerId);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateDoctors(Doctors doctor) throws Exception {
        sf = SessionHelper.getSessionFactory();
        session = sf.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.update(doctor);
            tx.commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteDoctors(int doctorId) throws Exception {
        sf = SessionHelper.getSessionFactory();
        session = sf.openSession();
        try {
            Doctors doctor = (Doctors) session.get(Doctors.class, doctorId);
            Transaction tx = session.beginTransaction();
            session.delete(doctor);
            tx.commit();
        } finally {
            session.close();
        }
    }

   

}
