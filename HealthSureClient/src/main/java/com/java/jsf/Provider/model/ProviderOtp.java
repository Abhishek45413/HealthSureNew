package com.java.jsf.Provider.model;

import java.time.LocalDateTime;
import java.util.Date;

public class ProviderOtp {
	 private int otpId;
	    private String providerId;
	    private String otpCode;
	    private LocalDateTime createdAt;
	    private LocalDateTime expiresAt;
	    private boolean isVerified;
	    
		public int getOtpId() {
			return otpId;
		}
		public void setOtpId(int otpId) {
			this.otpId = otpId;
		}
		public String getProviderId() {
			return providerId;
		}
		public void setProviderId(String providerId) {
			this.providerId = providerId;
		}
		public String getOtpCode() {
			return otpCode;
		}
		public void setOtpCode(String otpCode) {
			this.otpCode = otpCode;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime localDateTime) {
			this.createdAt = localDateTime;
		}
		public LocalDateTime getExpiresAt() {
			return expiresAt;
		}
		public void setExpiresAt(LocalDateTime localDateTime) {
			this.expiresAt = localDateTime;
		}
		public boolean isVerified() {
			return isVerified;
		}
		public void setVerified(boolean isVerified) {
			this.isVerified = isVerified;
		}
		
		public ProviderOtp(int otpId, String providerId, String otpCode, LocalDateTime createdAt, LocalDateTime expiresAt,
				boolean isVerified) {
			super();
			this.otpId = otpId;
			this.providerId = providerId;
			this.otpCode = otpCode;
			this.createdAt = createdAt;
			this.expiresAt = expiresAt;
			this.isVerified = isVerified;
		}
		
		public ProviderOtp() {
			super();
			// TODO Auto-generated constructor stub
		}
	    
	    
		
}