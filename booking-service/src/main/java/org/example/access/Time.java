package nghhng.facilityservice.dao.part;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Time {

    @JsonProperty("hour")
    private int hour;

    @JsonProperty("minute")
    private int minute;
}
