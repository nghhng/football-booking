package nghhng.facilityservice.dao.part;

import com.fasterxml.jackson.annotation.JsonProperty;

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
