package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAvailableFieldsResponse {
    @JsonProperty("number")
    private String number;

    @JsonProperty("type")
    private String type;
}
