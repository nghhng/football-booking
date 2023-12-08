package nghhng.facilityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nghhng.facilityservice.dao.Facility;
import nghhng.facilityservice.services.FacilityService;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GetFacilityResponse {
    private List<Facility> data;

    private Integer total;
}
