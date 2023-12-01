package org.example.dao.part;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @JsonProperty("number")
    private String number;

    @JsonProperty("street")
    private String street;

    @JsonProperty("ward")
    private String ward;

    @JsonProperty("city")
    private String city;
}
