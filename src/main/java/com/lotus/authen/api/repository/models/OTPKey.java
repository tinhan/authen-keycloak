package com.lotus.authen.api.repository.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class OTPKey implements Serializable {
    private static final long serialVersionUID = 1163347452811191867L;

    @Column(name = "cif_id")
    String cifId;

    @Column(name = "otp")
    String otp;

    @Column(name = "ref_code")
    String refCode;

    @Column(name = "mobile_number")
    String mobileNumber;

    public String getCifId() {
        return cifId;
    }

    public void setCifId(String cifId) {
        this.cifId = cifId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
