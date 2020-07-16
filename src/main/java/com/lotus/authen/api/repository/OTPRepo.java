package com.lotus.authen.api.repository;

import com.lotus.authen.api.repository.models.OTPModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OTPRepo extends JpaRepository<OTPModel, Integer> {
    OTPModel findById_CifIdAndId_OtpAndId_RefCodeAndId_MobileNumber(String cifID, String otp, String refCode,String mobileNumber);
}
