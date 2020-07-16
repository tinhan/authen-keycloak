package com.lotus.authen.api.model;

public class OTPResponse extends ResponseMessage {

    String mobileNumber;
    String refCode;

    public OTPResponse(){}
    public OTPResponse(String mobileNumber, String refCode, String responseCode, String responseMessage){
        this.mobileNumber = mobileNumber;
        this.refCode = refCode;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
    public String getMobileNumber(String mobileNumber) {
        return this.mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getRefCode() {
        return refCode;
    }
    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }
}
