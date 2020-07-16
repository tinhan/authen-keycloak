package com.lotus.authen.api.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "exception",
        "responseStatus",
        "responseCode"
})
public class ErrorResponse {

    @JsonProperty("exception")
    private String exception;
    @JsonProperty("responseStatus")
    private String responseStatus;
    @JsonProperty("responseCode")
    private String responseCode;

    public ErrorResponse() {
    }

    @JsonProperty("exception")
    public String getException() {
        return exception;
    }

    @JsonProperty("exception")
    public void setException(String exception) {
        this.exception = exception;
    }

    public ErrorResponse withException(String exception) {
        this.exception = exception;
        return this;
    }

    @JsonProperty("responseStatus")
    public String getResponseStatus() {
        return responseStatus;
    }

    @JsonProperty("responseStatus")
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ErrorResponse withResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }


    @JsonProperty("responseCode")
    public String getResponseCode() {
        return responseCode;
    }

    @JsonProperty("responseCode")
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public ErrorResponse withResponseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }
}
