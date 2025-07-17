package com.java.jsf.Provider.daoImpl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.java.jsf.Provider.dao.ProviderOtpDao;
import com.java.jsf.Provider.model.Provider;
import com.java.jsf.Provider.model.ProviderOtp;
import com.java.jsf.Util.MailSend;
import com.java.jsf.Util.SessionHelper;

public class ProviderOtpDaoImpl implements ProviderOtpDao{
	Session session;
	SessionHelper sf;
	
//	@Override
//	public String insertOtp(ProviderOtp otp) throws ClassNotFoundException, SQLException {
//		 session = SessionHelper.getSessionFactory().openSession();
//	        Transaction tx = session.beginTransaction();
//
//	        // Send OTP Email
//	        String subject = "Your OTP for HealthSure Registration";
//	        String body = "Your OTP is: " + otp.getOtpCode() + ". It is valid for 10 minutes.";
//	        MailSend.sendInfo(otp.getProviderId(), subject, body);
//
//	        return "OTP inserted and email sent successfully.";
//	}

	@Override
	public String verifyOtp(String providerId, String otpCode) throws ClassNotFoundException, SQLException {
		session = SessionHelper.getSessionFactory().openSession();
        
		
        String hql = "FROM ProviderOtp WHERE providerId = :providerId AND otpCode = :otpCode AND isVerified = false";
        Query query = session.createQuery(hql);
        query.setParameter("providerId", providerId);
        query.setParameter("otpCode", otpCode);

        ProviderOtp otp = (ProviderOtp) query.uniqueResult();

        if (otp != null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            System.out.println("OTP Found. Expires at: " + otp.getExpiresAt());
            System.out.println("Current Time: " + now);

            if (now.after(otp.getExpiresAt())) {
                return "OTP expired. Please request a new one.";
            }
            else {
            Transaction tx = session.beginTransaction();
            otp.setIsVerified(true);
            session.update(otp);
            tx.commit();
            session.close();

            return "OTP verified successfully.";
            }
        } else {
            session.close();
            return "Invalid OTP or already verified.";
        }
	}

	@Override
	public ProviderOtp getLatestOtp(String providerId) throws ClassNotFoundException, SQLException {
		 session = SessionHelper.getSessionFactory().openSession();

	        String hql = "FROM ProviderOtp WHERE providerId = :providerId ORDER BY createdAt DESC";
	        Query query = session.createQuery(hql);
	        query.setParameter("providerId", providerId);
	        query.setMaxResults(1);

	        ProviderOtp latestOtp = (ProviderOtp) query.uniqueResult();
	        session.close();

	        return latestOtp;
	}

	@Override
	public String markOtpAsVerified(int otpId) throws ClassNotFoundException, SQLException {
		session = SessionHelper.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        String hql = "UPDATE ProviderOtp SET isVerified = true WHERE otpId = :otpId";
        int updated = session.createQuery(hql).setParameter("otpId", otpId).executeUpdate();

        tx.commit();
        session.close();

        if (updated > 0) {
            return "OTP verified.";
        } else {
            return "OTP not found or already verified.";
	}
	
	}

	@Override
	public String generateOtp(Provider provider) throws ClassNotFoundException, SQLException {

		    // Generate a 6-digit OTP
		    int code = new Random().nextInt(900000) + 100000;
		    String otpCode = String.valueOf(code);

		    // Hibernate session and transaction
		    Session session = SessionHelper.getSessionFactory().openSession();
		    Transaction tx = null;

		    try {
		        tx = session.beginTransaction();

		        // Timestamp
		        Timestamp now = new Timestamp(System.currentTimeMillis());
		        Timestamp expiry = new Timestamp(now.getTime() + 2 * 60 * 1000); // 2 minutes


			    // Create OTP entity
		        ProviderOtp otp = new ProviderOtp();
		        otp.setProviderId(provider.getProviderId());
		       // otp.setProviderId(providerId);
		        System.out.println("my providerId is................"+provider.getProviderId());
		        System.out.println(provider.getProviderId());
		        otp.setOtpCode(otpCode);
		        otp.setCreatedAt(now);
		        otp.setExpiresAt(expiry);
		        otp.setIsVerified(false);
		        System.out.println("my otp is................");
		        System.out.println(otp);
		        session.save(otp);
		        tx.commit();
		        

		     // Send OTP Email
		        String subject = "Your OTP for HealthSure Registration";
		        String body = "Your OTP is: " + otp.getOtpCode() + ". It is valid for 2 minutes.";
		        MailSend.sendInfo(provider.getEmail(), subject, body);
			    
		    } catch (Exception e) {
		        if (tx != null) tx.rollback();
		        e.printStackTrace();
		    } finally {
		        session.close();
		    }

		    
		    return otpCode;
		}

	@Override
	public String updateOtp(ProviderOtp otp) throws ClassNotFoundException, SQLException {
		Session session = SessionHelper.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			tx = session.beginTransaction();
			session.save(otp);
			tx.commit();
			return "OTP save successfully.";
		}
		catch (Exception e) {
			e.printStackTrace();
			return "OTP update failed."; 
		}
	}
}
	