package org.example.access;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {

    @JsonProperty("index")
    private String index;

    @JsonProperty("type")
    private String type;
}
