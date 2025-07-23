package com.java.jsf.Provider.Controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.java.jsf.Provider.dao.ProviderDao;
import com.java.jsf.Provider.dao.ProviderOtpDao;
import com.java.jsf.Provider.daoImpl.ProviderDaoImpl;
import com.java.jsf.Provider.daoImpl.ProviderOtpDaoImpl;
import com.java.jsf.Provider.model.LoginStatus;
import com.java.jsf.Provider.model.Provider;
import com.java.jsf.Provider.model.ProviderOtp;
import com.java.jsf.Util.EncryptPassword;
import com.java.jsf.Util.MailSend;
import com.java.jsf.Util.SessionHelper;



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
        FacesContext.getCurrentInstance().addMessage("null",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Success", "Provider added Successfully"));
        
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
        System.out.println("verifyOtp triggered");
        
        String email = provider.getEmail();
        System.out.println("Step 1: Retrieved provider email = " + email);
        
        String inputOtp = this.otpCode;
        System.out.println("Step 2: User entered OTP = " + inputOtp);
        
        ProviderOtp latestOtp = providerOtpDaoImpl.getLatestOtp(email);
        System.out.println("Step 3: Retrieved latestOtp = " + (latestOtp != null ? latestOtp.getOtpCode() : "null"));
        
        if (latestOtp == null) {
            System.out.println("Step 4: No OTP record found for this email");
            FacesContext.getCurrentInstance().addMessage(
                "otp",
                new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Invalid OTP",
                    "Please check the code and try again"
                )
            );
            otpVerified = false;
            System.out.println("Step 5: Returning null due to missing OTP");
            return null;
        }
        
        if (!latestOtp.getOtpCode().equals(inputOtp)) {
            System.out.println("Step 6: OTP mismatch (stored=" + latestOtp.getOtpCode() + ")");
            FacesContext.getCurrentInstance().addMessage(
                "otp",
                new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Invalid OTP",
                    "Please check the code and try again"
                )
            );
            otpVerified = false;
            System.out.println("Step 7: Returning null due to invalid OTP");
            return null;
        }
        
        Timestamp now = new Timestamp(System.currentTimeMillis());
        System.out.println("Step 8: Current timestamp = " + now);
        
        Timestamp expiresAt = latestOtp.getExpiresAt();
        System.out.println("Step 9: existing expiresAt = " + expiresAt);
        
        if (expiresAt == null) {
            expiresAt = new Timestamp(latestOtp.getCreatedAt().getTime() + 2 * 60 * 1000);
            latestOtp.setExpiresAt(expiresAt);
            providerOtpDaoImpl.updateOtp(latestOtp);
            System.out.println("Step 10: Set new expiresAt = " + expiresAt);
        }
        
        if (now.after(expiresAt)) {
            System.out.println("Step 11: OTP has expired (expiresAt=" + expiresAt + ")");
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    "OTP has expired.",
                    "Please request a new code"
                )
            );
            otpVerified = false;
            System.out.println("Step 12: Returning null due to expired OTP");
            return null;
        }
        
        System.out.println("Step 13: OTP is valid and not expired");
        
        latestOtp.setIsVerified(true);
        System.out.println("Step 14: Marking OTP as verified");
        providerOtpDaoImpl.updateOtp(latestOtp);
        System.out.println("Step 15: Updated OTP record in database");
        
        FacesContext.getCurrentInstance().addMessage(
            null,
            new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "OTP Verified ","OTP Verified Successfully"));
        otpVerified = true;
        System.out.println("Step 16: otpVerified set to true");
        
        System.out.println("Step 17: Redirecting to GeneratePassword page");
        return "GeneratePassword?faces-redirect=true";
    }
    
    
    // ✅ Resend OTP
    public String resendOtp() throws Exception {
    	String email = (String) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("providerEmail");
            if (email == null || email.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No email in session.", null)
                );
                return null;
            }

            String newOtp = providerOtpDaoImpl.generateOtp(provider);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Timestamp expiresAt = new Timestamp(now.getTime() + 2 * 60 * 1000);

            Session session = SessionHelper.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            try {
                ProviderOtp otp = providerOtpDaoImpl.getLatestOtp(email);
                if (otp == null) {
                    otp = new ProviderOtp();
                    otp.setEmail(email);
                    otp.setCreatedAt(now);
                } else {
                    otp.setCreatedAt(now);
                }

                otp.setOtpCode(newOtp);
                otp.setExpiresAt(expiresAt);
                otp.setIsVerified(false);

                session.saveOrUpdate(otp);
                tx.commit();
            } catch (Exception e) {
                if (tx != null && tx.isActive()) tx.rollback();
                session.close();
                throw e;
            }
            session.close();

            MailSend.sendInfo(email, newOtp, null);

            System.out.println("New OTP sent to: " + email + " | OTP: " + newOtp);
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "New OTP Sent.", null)
            );
            return null;
        }

    
    
	// ✅ Login existing provider
    public String login() {
        System.out.println("Login method triggered");

        // 1. Null‐check injected provider bean
        if (provider == null) {
            System.out.println("Provider object is null");
            return null;
        }

        // 2. Normalize inputs
        String email = provider.getEmail() == null 
            ? "" 
            : provider.getEmail().trim().toLowerCase();
        String plainPassword = provider.getPassword() == null 
            ? "" 
            : provider.getPassword().trim();

        // 3. Validate
        if (email.isEmpty() || plainPassword.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Email and password are required.",
                    null
                )
            );
            return null;
        }

        // 4. Encrypt password
        String encryptedPassword = EncryptPassword.getCode(plainPassword);

        // 5. Call DAO inside try/catch
        Provider dbProvider;
        try {
            dbProvider = providerDaoImpl.login(email, encryptedPassword);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                    FacesMessage.SEVERITY_FATAL,
                    "System error. Please try again later.",
                    null
                )
            );
            return null;
        }

        // 6. Check credentials
        if (dbProvider == null) {
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Invalid email or password.",
                    null
                )
            );
            return null;
        }

        // 7. Handle account status
        LoginStatus status = dbProvider.getStatus();
        if (status == LoginStatus.PENDING) {
            // store logged-in user in session
            FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .getSessionMap()
                        .put("loggedInProvider", dbProvider);
            System.out.println("Login successful for: " + dbProvider.getProviderName());
            return "Home?faces-redirect=true";

        } else if (status == LoginStatus.APRROVED) {
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    "Your account is not approved yet.",
                    null
                )
            );
            return null;

        } else {
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Your account is locked or inactive.",
                    null
                )
            );
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
