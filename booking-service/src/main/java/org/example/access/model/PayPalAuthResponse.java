package org.example.access.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayPalAuthResponse {

    @JsonProperty("access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}
