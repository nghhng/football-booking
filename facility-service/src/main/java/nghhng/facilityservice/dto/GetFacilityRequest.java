package nghhng.facilityservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import nghhng.facilityservice.dao.part.Address;
import nghhng.facilityservice.dao.part.Field;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
public class GetFacilityRequest {
    private String name;

    private String ward;

    private String city;

    private String ownerId;

    private Integer limit;

    private Integer skip;
}
