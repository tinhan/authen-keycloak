package com.lotus.authen.api.controller;

import com.lotus.authen.api.model.*;
import com.lotus.authen.api.service.OTPService;
import com.lotus.authen.api.service.PINService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/authen")
@Api(value = "Authentication API", description = "Shows list of all APIs")
public class AuthenResource {

    @Autowired
    OTPService otpService;

    @Autowired
    PINService pinService;

    @ApiOperation(value = "Send sms to client mobile and return refCode", nickname = "send sms", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = OTPResponse.class),
            @ApiResponse(code = 403, message = "FOR_BIDDEN", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "FAILURE", response = ErrorResponse.class) })
    @RequestMapping(value = "/token/sms", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> sendOTPtoUser(
            @RequestHeader @Valid final String requestID,
            @RequestHeader String appID,
            @RequestHeader @Valid final String clientID,
            @RequestHeader @Valid final String mobileNumber) {
        return otpService.generateAndSendSMS(mobileNumber);
    }

    @ApiOperation(value = "This API used for Verify OTP and Connect to Keycloak to generate token, then return to client", nickname = "verify otp and generate token", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = OTPResponse.class),
            @ApiResponse(code = 403, message = "FOR_BIDDEN", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "FAILURE", response = ErrorResponse.class) })
    @RequestMapping(value = "/token/generate", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> verifyOTPandGenToken(
            @RequestHeader @Valid final String requestID,
            @RequestHeader String appID,
            @RequestHeader @Valid final String clientID,
            @RequestBody @Valid RequestToken requestToken) {
        return otpService.verifyOTPAndGenToken(requestToken, clientID);
    }

    @ApiOperation(value = "This API used for Refresh new access token", nickname = "refresh token", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = OTPResponse.class),
            @ApiResponse(code = 403, message = "FOR_BIDDEN", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "FAILURE", response = ErrorResponse.class) })
    @RequestMapping(value = "/token/refresh", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> helloPost(
            @RequestHeader @Valid final String requestID,
            @RequestHeader String appID,
            @RequestHeader @Valid final String clientID,
            @RequestHeader final String Authorization,
            @RequestBody @Valid RequestRefreshToken requestRefreshToken) {
        return otpService.refreshToken(requestRefreshToken, clientID, Authorization);
    }

    @ApiOperation(value = "This API used for Verify OTP and Connect to Keycloak to generate authed token, then return to client", nickname = "verify PIN and generate Authed token", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = OTPResponse.class),
            @ApiResponse(code = 403, message = "FOR_BIDDEN", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "NOT_FOUND", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "FAILURE", response = ErrorResponse.class) })
    @RequestMapping(value = "/pin/varify", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> verificationPIN(
            @RequestHeader @Valid final String requestID,
            @RequestHeader String appID,
            @RequestHeader @Valid final String clientID,
            @RequestBody @Valid RequestPin requestPin) {
        return pinService.verifyPINAndGenToken(requestPin, clientID);
    }
/*
    @ApiOperation(value = "This API used for Verify OTP and Connect to Keycloak to generate token, then return to client")
    @RequestMapping(value = "/pin/create", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseToken createUserPIN(
         @RequestHeader @Valid final String requestID,
         @RequestHeader String appID,
         @RequestHeader @Valid final String clientID,
         @RequestBody @Valid RequestToken requestToken) {
    return new ResponseToken();
    }

    @ApiOperation(value = "This API used for Verify OTP and Connect to Keycloak to generate token, then return to client")
    @RequestMapping(value = "/pin/reset", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseToken resetPIN(
            @RequestHeader @Valid final String requestID,
            @RequestHeader String appID,
            @RequestHeader @Valid final String clientID,
            @RequestBody @Valid RequestToken requestToken) {
        return new ResponseToken();
    }

    @ApiOperation(value = "This API used for Verify OTP and Connect to Keycloak to generate token, then return to client")
    @RequestMapping(value = "/pin/status", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseToken getPINStatus(
            @RequestHeader @Valid final String requestID,
            @RequestHeader String appID,
            @RequestHeader @Valid final String clientID,
            @RequestBody @Valid RequestToken requestToken) {
        return new ResponseToken();
    }
*/
}