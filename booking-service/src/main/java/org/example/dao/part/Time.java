package org.example.dao.part;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Time {

    @JsonProperty("hour")
    private int hour;

    @JsonProperty("minute")
    private int minute;

    public String toString(){
        return hour + ":" + minute;
    }
}
