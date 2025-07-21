package com.java.jsf.Provider.Controller;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.java.jsf.Provider.dao.ProviderDao;
import com.java.jsf.Provider.dao.ProviderOtpDao;
import com.java.jsf.Provider.daoImpl.ProviderDaoImpl;
import com.java.jsf.Provider.daoImpl.ProviderOtpDaoImpl;
import com.java.jsf.Provider.model.LoginStatus;
import com.java.jsf.Provider.model.Provider;
import com.java.jsf.Provider.model.ProviderOtp;
import com.java.jsf.Util.EncryptPassword;



public class ProviderController implements Serializable{

	 Timestamp now = new Timestamp(System.currentTimeMillis());
	    Timestamp expiry = new Timestamp(now.getTime() + 2 * 60 * 1000); // 2 minutes
	
    private static final long serialVersionUID = 1L;
    
	private Provider provider = new Provider();
	private String otpCode;
	private ProviderDao providerDaoImpl;
    private ProviderOtpDao providerOtpDaoImpl;
    
    public ProviderController() {
        // Manually initialize DAO implementations
       providerDaoImpl = new ProviderDaoImpl();
       providerOtpDaoImpl = new ProviderOtpDaoImpl();
    }


    // ✅ Register a new provider with password confirmation
    public String register() throws Exception {
    	System.out.println("controller called...");
        System.out.println("Registering provider...");
        boolean a=false;

        if (provider == null) {
            System.out.println("Provider object is null!");
            return null;
        } 

        if (providerDaoImpl == null || providerOtpDaoImpl == null) {
            System.out.println("DAO is not initialized!");
            return null;
        }
        
     // ✅ Validate Provider Name: Only letters and spaces
        if (!provider.getProviderName().matches("^[A-Za-z\\s]+$")) {
        	System.out.println(1);
            FacesContext.getCurrentInstance().addMessage("providerName",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name must contain only alphabets.", null));
            a=true;
        }

        // ✅ Validate Hospital Name: Optional but must be non-numeric if provided
        String hospital = provider.getHospitalName();
        if (hospital != null && !hospital.trim().isEmpty() && !hospital.matches("^[A-Za-z\\s]+$")) {
        	System.out.println(12);
        	FacesContext.getCurrentInstance().addMessage("hospitalName",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hospital name must contain only alphabets.", null));
        	a=true;
        }

        // ✅ Email Format Validation
        if (!provider.getEmail().matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}$")) {
        	System.out.println(13);
        	FacesContext.getCurrentInstance().addMessage("email",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email format.", null));
            a=true;
        }

        // ✅ Email Uniqueness Check
        if (providerDaoImpl.emailExists(provider.getEmail())) {
        	System.out.println(14);
        	FacesContext.getCurrentInstance().addMessage("email",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email already exists.", null));
        	a=true;
        }

        // ✅ Phone Number Format Validation
        if (!provider.getTelephone().matches("^[0-9]{10}$")) {
        	System.out.println(15);
        	FacesContext.getCurrentInstance().addMessage("telephone",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Phone number must be exactly 10 digits.", null));
        	a=true;
        }

        // ✅ Phone Number Uniqueness Check
        if (providerDaoImpl.phoneExists(provider.getTelephone())) {
        	System.out.println(16);
        	FacesContext.getCurrentInstance().addMessage("telephone",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Phone number already exists.",null));
        	a=true;
        }

        // ✅ Zipcode Format Validation
        if (!provider.getZipcode().matches("^[0-9]{6}$")) {
        	System.out.println(17);
        	FacesContext.getCurrentInstance().addMessage("zipcode",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zipcode must be exactly 6 digits.", null));
        	a=true;
        }

        // ✅ Zipcode Uniqueness Check
        if (providerDaoImpl.zipcodeExists(provider.getZipcode())) {
        	System.out.println(18);
        	FacesContext.getCurrentInstance().addMessage("zipcode",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zipcode already exists.", null));
        	a=true;
        }
        if(a) {
        	return null;
        }
        provider.setStatus(LoginStatus.PENDING);

        // Save provider to database
        providerDaoImpl.addProvider(provider);
        System.out.println("Provider added successfully.");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        
        // Generate OTP
        String otp = providerOtpDaoImpl.generateOtp(provider);

        // Simulate sending OTP (replace with actual email/SMS logic)
        System.out.println("OTP sent to: " + provider.getEmail() + " | OTP: " + otp);

        // Store email in session for OTP verification
        FacesContext.getCurrentInstance().getExternalContext()
            .getSessionMap().put("providerEmail", provider.getEmail());

        // Redirect to OTP verification page
        return "VerifyOtp.jsf?faces-redirect=true";
    }
    
 // ✅ Submit OTP
    
    private boolean otpVerified = false;

    public boolean isOtpVerified() {
        return otpVerified;
    }

    
    public String verifyOtp() throws Exception {
    	String email = provider.getEmail();
    	String inputOtp = this.otpCode;
        ProviderOtp latestOtp = providerOtpDaoImpl.getLatestOtp(email);
        System.out.println(email+inputOtp);

        if (latestOtp == null || !latestOtp.getOtpCode().equals(inputOtp)) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "OTP Verified Successfully", null));
            otpVerified = false;
            return null;
        }

        if (latestOtp.getExpiresAt().equals(expiry)) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "OTP has expired.", null));
            otpVerified = false;
            return null;
        }

        latestOtp.setIsVerified(true);
        providerOtpDaoImpl.updateOtp(latestOtp);
        
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "OTP Verified Successfully", null));
        otpVerified = true;
        System.out.println("OTP verified. Provider approved.");
        return "GeneratePassword.jsp?faces-redirect=true";
    }

    // ✅ Resend OTP
    public String resendOtp() throws Exception {
        String email = (String) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("providerEmail");

        String newOtp = providerOtpDaoImpl.generateOtp(provider);
        System.out.println("New OTP sent to: " + email + " | OTP: " + newOtp);

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "New OTP Sent.", null));
        return null;
    }


    // ✅ Login existing provider
    public String login() throws Exception {
        System.out.println("Login method triggered");

        if (provider == null) {
            System.out.println("Provider object is null");
            return null;
        }

        System.out.println("Email entered: " + provider.getEmail());
        System.out.println("Password entered (plain): " + provider.getPassword());

        if (provider.getEmail() == null || provider.getPassword() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email and password are required.", null));
            return null;
        }

        String encryptedPassword = EncryptPassword.getCode(provider.getPassword());
        System.out.println("Encrypted password: " + encryptedPassword);

        Provider dbProvider = providerDaoImpl.login(provider.getEmail(), encryptedPassword);

        if (dbProvider != null) {
            if (dbProvider.getStatus() == LoginStatus.PENDING) {
                System.out.println("Login successful.");
                return "loginSuccess?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Your account is not approved yet.", null));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid email or password.", null));
            return null;
        }
    }
        // ✅ Update password method (NEW)
        public String updatePassword() throws Exception {
            System.out.println("Password update triggered");


            // Validate inputs
            if (provider.getEmail() == null || provider.getEmail().trim().isEmpty()
                    || provider.getNewPassword() == null || provider.getNewPassword().trim().isEmpty()
                    || provider.getConfirmPassword() == null || provider.getConfirmPassword().trim().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "All fields are required.", null));
                return null;
            }

            // Check if passwords match
            if (!provider.getNewPassword().equals(provider.getConfirmPassword())) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match.", null));
                return null;
            }

            // Encrypt new password
            String encryptedPassword = EncryptPassword.getCode(provider.getNewPassword());

            // Update password in database
            boolean updated = providerDaoImpl.updatePasswordByEmail(
                provider.getEmail().trim(),encryptedPassword
            );

            if (updated) {
                System.out.println("Password updated successfully for: " + provider.getEmail());

                // ✅ Clear provider fields for safety
                provider.setNewPassword(null);
                provider.setConfirmPassword(null);
                provider.setPassword(null);

                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Password updated successfully. Please login.", null));

                // ✅ Redirect to login page
                return "login?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No provider found with this email.", null));
                return null;
            }
        }

  
    // ✅ Getters and Setters
    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }


	public ProviderOtpDao getProviderOtpDao() {
		return providerOtpDaoImpl;
	}

	public void setProviderOtpDao(ProviderOtpDao providerOtpDao) {
		this.providerOtpDaoImpl = providerOtpDao;
	}
	
	public String getOtpCode() {
		return otpCode;
	}
	 public void setOtpCode(String otpCode) {
		 this.otpCode = otpCode;
	 }


	 public ProviderDao getProviderDaoImpl() {
		return providerDaoImpl;
	 }


	 public void setProviderDaoImpl(ProviderDao providerDaoImpl) {
		this.providerDaoImpl = providerDaoImpl;
	 }


	 public ProviderOtpDao getProviderOtpDaoImpl() {
		return providerOtpDaoImpl;
	 }


	 public void setProviderOtpDaoImpl(ProviderOtpDao providerOtpDaoImpl) {
		this.providerOtpDaoImpl = providerOtpDaoImpl;
	 }
}
