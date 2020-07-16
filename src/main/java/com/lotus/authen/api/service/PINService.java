package com.lotus.authen.api.service;

import com.lotus.authen.api.model.ErrorResponse;
import com.lotus.authen.api.model.RequestPin;
import com.lotus.authen.api.model.ResponseToken;
import com.lotus.authen.api.repository.CIFRepo;
import com.lotus.authen.api.repository.models.CustInfo;
import com.lotus.authen.api.repository.OTPRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class PINService {

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

    public ResponseEntity<Object> verifyPINAndGenToken(RequestPin requestPin, String clientID){
        try {
            CustInfo custInfo = cifRepo.findByMobileNumber(requestPin.getMobileNumber());
            ResponseToken responseToken = new ResponseToken();
            if (null != custInfo.getCifId()) {
                RestTemplate restTemplate = new RestTemplate();
                String url = keycloakUrl;

                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
                headers.add("Accept", MediaType.APPLICATION_JSON.toString());

                MultiValueMap<String, String> requestEncode = new LinkedMultiValueMap<String, String>();
                requestEncode.add("client_id", clientID);
                requestEncode.add("client_secret", keycloakSecret);
                requestEncode.add("grant_type", keycloakGrantType);
                requestEncode.add("username", custInfo.getCifId());
                requestEncode.add("password", keycloakPassword);


                HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestEncode, headers);

                ResponseEntity<ResponseToken> responseEntity = restTemplate.exchange(url, HttpMethod.POST, formEntity, ResponseToken.class);
                responseToken = responseEntity.getBody();

                responseToken.setResponseCode("200");
                responseToken.setResponseMessage("SUCCESS");
                return new ResponseEntity<>(responseToken, HttpStatus.OK);
            } else {
                responseToken.setResponseCode("404");
                responseToken.setResponseMessage("Can not find user");
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
}
