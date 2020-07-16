package com.lotus.authen.api.model;

public class RequestRefreshToken {
    String refreshToken;

    public RequestRefreshToken(){}

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
