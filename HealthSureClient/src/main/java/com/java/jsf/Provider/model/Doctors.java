package com.java.jsf.Provider.model;

public class Doctors {
	    private String doctorId;
	    private Provider provider;
	    private String doctorName;
	    private Gender gender;
	    private String qualification;
	    private String specialization;
	    private String licenseNo;
	    private String email;
	    private String address;
	    private DoctorType type;  // STANDARD or ADHOC
	    private DoctorStatus status;

		public String getDoctorId() {
			return doctorId;
		}
		public void setDoctorId(String doctorId) {
			this.doctorId = doctorId;
		}
		public Provider getProvider() {
			return provider;
		}
		public void setProvider(Provider provider) {
			this.provider = provider;
		}

		public String getDoctorName() {
			return doctorName;
		}
		public void setDoctorName(String doctorName) {
			this.doctorName = doctorName;
		}
		public Gender getGender() {
			return gender;
		}
		public void setGender(Gender gender) {
			this.gender = gender;
		}
		public String getQualification() {
			return qualification;
		}
		public void setQualification(String qualification) {
			this.qualification = qualification;
		}
		public String getSpecialization() {
			return specialization;
		}
		public void setSpecialization(String specialization) {
			this.specialization = specialization;
		}
		public String getLicenseNo() {
			return licenseNo;
		}
		public void setLicenseNo(String licenseNo) {
			this.licenseNo = licenseNo;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public DoctorType getType() {
			return type;
		}
		public void setType(DoctorType type) {
			this.type = type;
		}
		public DoctorStatus getStatus() {
			return status;
		}
		public void setStatus(DoctorStatus status) {
			this.status = status;
		}

		public Doctors(String doctorId, Provider provider, String providerId, String doctorName, Gender gender,
				String qualification, String specialization, String licenseNo, String email, String address,
				DoctorType type, DoctorStatus status) {
			super();
			this.doctorId = doctorId;
			this.provider = provider;
			this.doctorName = doctorName;
			this.gender = gender;
			this.qualification = qualification;
			this.specialization = specialization;
			this.licenseNo = licenseNo;
			this.email = email;
			this.address = address;
			this.type = type;
			this.status = status;
		}

		public Doctors() {
			super();
			// TODO Auto-generated constructor stub
		}




}
