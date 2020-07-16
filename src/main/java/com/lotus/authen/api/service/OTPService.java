package com.lotus.authen.api.service;

import com.lotus.authen.api.model.*;
import com.lotus.authen.api.repository.*;
import com.lotus.authen.api.repository.models.CustInfo;
import com.lotus.authen.api.repository.models.OTPKey;
import com.lotus.authen.api.repository.models.OTPModel;
import com.lotus.authen.api.security.OTPAlgorithms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OTPService {
    public OTPService(){}

    @Autowired
    CIFRepo cifRepo;

    @Autowired
    OTPRepo otpRepo;

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.client.secret}")
    private String keycloakSecret;

    @Value("${keycloak.grant.type}")
    private String keycloakGrantType;

    @Value("${keycloak.auth.pass}")
    private String keycloakPassword;

    public ResponseEntity<Object> generateAndSendSMS(String mobileNumber){
        try {
            CustInfo custInfo = cifRepo.findByMobileNumber(mobileNumber);
            OTPResponse otpResponse = new OTPResponse();
            if (null != custInfo.getCifId()) {
                OTPAlgorithms otpAlgorithms = new OTPAlgorithms();
                String refCode = otpAlgorithms.geek_RefCode(6);
                String otp = otpAlgorithms.OTP(6);
                System.out.println(" otp : "+otp);
                saveOTPAndRef(mobileNumber, custInfo.getCifId(), otp, refCode);
                otpResponse.setMobileNumber(mobileNumber);
                otpResponse.setRefCode(refCode);
                otpResponse.setResponseCode("200");
                otpResponse.setResponseMessage("SUCCESS");
                return new ResponseEntity<>(otpResponse, HttpStatus.OK);
            } else {
                otpResponse.setResponseCode("404");
                otpResponse.setResponseMessage("Can not find mobile number");
                return new ResponseEntity<>(otpResponse, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setResponseCode("500");
            errorResponse.setResponseStatus("ERROR");
            errorResponse.setException(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> verifyOTPAndGenToken(RequestToken requestToken, String clientID){
        try {
            CustInfo custInfo = cifRepo.findByMobileNumber(requestToken.getMobileNumber());
            ResponseToken responseToken = new ResponseToken();
            if (null != custInfo.getCifId()) {
                OTPModel otpModel = otpRepo.findById_CifIdAndId_OtpAndId_RefCodeAndId_MobileNumber(custInfo.getCifId(), requestToken.getOtp(),
                        requestToken.getRefCode(), requestToken.getMobileNumber());
                if (null != otpModel && otpModel.getCountStatus() < 3) {
                    System.out.println(" otpModel : " + otpModel.getId().getRefCode());
                    RestTemplate restTemplate = new RestTemplate();
                    String url = keycloakUrl;

                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
                    headers.add("Accept", MediaType.APPLICATION_JSON.toString());

                    MultiValueMap<String, String> requestEncode = new LinkedMultiValueMap<String, String>();
                    requestEncode.add("client_id", clientID);
                    requestEncode.add("grant_type", keycloakGrantType);
                    requestEncode.add("username", custInfo.getCifId());
                    requestEncode.add("password", keycloakPassword);
                    requestEncode.add("scope", "offline_access");

                    HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestEncode, headers);

                    ResponseEntity<ResponseToken> responseEntity = restTemplate.exchange(url, HttpMethod.POST, formEntity, ResponseToken.class);
                    responseToken = responseEntity.getBody();

                }
                otpModel.setCountStatus(otpModel.getCountStatus() + 1);
                otpRepo.save(otpModel);
                responseToken.setResponseCode("200");
                responseToken.setResponseMessage("SUCCESS");
                return new ResponseEntity<>(responseToken, HttpStatus.OK);
            } else {
                responseToken.setResponseCode("404");
                responseToken.setResponseMessage("Can not find mobile number");
                return new ResponseEntity<>(responseToken, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setResponseCode("500");
            errorResponse.setResponseStatus("ERROR");
            errorResponse.setException(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> refreshToken(RequestRefreshToken requestRefreshToken, String clientID, String Authorization){
        try {
            ResponseToken responseToken = new ResponseToken();

            RestTemplate restTemplate = new RestTemplate();
            String url = keycloakUrl;

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());

            MultiValueMap<String, String> requestEncode = new LinkedMultiValueMap<String, String>();
            requestEncode.add("client_id", clientID);
            requestEncode.add("client_secret", keycloakSecret);
            requestEncode.add("grant_type", "refresh_token");
            requestEncode.add("refresh_token", requestRefreshToken.getRefreshToken());

            HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestEncode, headers);

            ResponseEntity<ResponseToken> responseEntity = restTemplate.exchange(url, HttpMethod.POST, formEntity, ResponseToken.class);
            responseToken = responseEntity.getBody();

            responseToken.setResponseCode("200");
            responseToken.setResponseMessage("SUCCESS");
            return new ResponseEntity<>(responseToken, HttpStatus.OK);
        }catch (Exception ex){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setResponseCode("500");
            errorResponse.setResponseStatus("ERROR");
            errorResponse.setException(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void saveOTPAndRef(String mobileNumber, String cifID, String otp, String refCode){
        try {
            OTPModel otpModel = new OTPModel();
            OTPKey otpKey = new OTPKey();
                otpKey.setCifId(cifID);
                otpKey.setMobileNumber(mobileNumber);
                otpKey.setOtp(otp);
                otpKey.setRefCode(refCode);
            otpModel.setCountStatus(0);
            otpModel.setId(otpKey);
            otpRepo.save(otpModel);
        }catch (Exception ex){
           throw ex;
        }
    }

}
