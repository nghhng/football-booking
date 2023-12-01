
package org.example.access.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CreateFacilityRequest {

    @JsonProperty(required = true)
    @NonNull
    private String name;

    @JsonProperty(required = true)
    @NonNull
    private Address address;

    @NonNull
    private String numOfFields;

    @NonNull
    private String ownerId;

    @NonNull
    @JsonProperty("fields")
    public List<Field> fields;

    private class Address {
        @JsonProperty("number")
        private String number;

        @JsonProperty("street")
        private String street;

        @JsonProperty("ward")
        private String ward;

        @JsonProperty("city")
        private String city;
    }

    private class Field {

        @JsonProperty("index")
        private String index;

        @JsonProperty("type")
        private String type;
    }
}
