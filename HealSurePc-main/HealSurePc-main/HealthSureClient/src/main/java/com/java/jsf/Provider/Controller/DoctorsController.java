package com.java.jsf.Provider.Controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.java.jsf.Provider.dao.DoctorsDao;
import com.java.jsf.Provider.daoImpl.DoctorsDaoImpl;
import com.java.jsf.Provider.model.Doctors;
import com.java.jsf.Provider.model.Provider;

public class DoctorsController implements Serializable{
	private static final long serialVersionUID = 1L;
	
	 private Doctors doctors = new Doctors();
	    private List<Doctors> doctorList;

	    private DoctorsDao doctorDao = new DoctorsDaoImpl();
	    
	    public DoctorsController() {
	        this.doctors = new Doctors();
	        this.doctors.setProviders(new Provider()); 
	    }


	    // === Add/register new doctor ===
	    public String addDoctor() {
	        try {
	            doctorDao.addDoctors(doctors);
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "Doctor Added Successfully", ""));
	            return "success.xhtml?faces-redirect=true";
	        } catch (Exception e) {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to Add Doctor: " + e.getMessage(), ""));
	            return null;
	        }
	    }

	    // === Get all doctors ===
	    public List<Doctors> getDoctorList() {
	        if (doctorList == null) {
	            try {
	                doctorList = doctorDao.getAllDoctors();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return doctorList;
	    }

	    // === Get doctors by provider ID ===
	    public List<Doctors> getDoctorsByProvider(String providerId) {
	        try {
	            return doctorDao.searchByProviderId(providerId);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    // === Update doctor details ===
	    public String updateDoctor() {
	        try {
	            doctorDao.updateDoctors(doctors);
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "Doctor Updated Successfully", ""));
	            return "doctor_updated.xhtml?faces-redirect=true";
	        } catch (Exception e) {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to Update Doctor: " + e.getMessage(), ""));
	            return null;
	        }
	    }

	    // === Delete doctor by ID ===
	    public String deleteDoctor(int doctorId) {
	        try {
	            doctorDao.deleteDoctors(doctorId);
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "Doctor Deleted Successfully", ""));
	            return "doctor_deleted.xhtml?faces-redirect=true";
	        } catch (Exception e) {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to Delete Doctor: " + e.getMessage(), ""));
	            return null;
	        }
	    }

	    // === Getters and Setters ===
	    public Doctors getDoctor() {
	        return doctors;
	    }

	    public void setDoctor(Doctors doctor) {
	        this.doctors = doctor;
	    }
}
