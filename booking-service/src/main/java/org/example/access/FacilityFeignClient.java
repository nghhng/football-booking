package org.example.access;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name = "facility-service", url = "${facility.service.url}")
public interface FacilityFeignClient {
    @PostMapping("getByFacilityId")
    GetFacilityByIdResponse getFacilityById(@RequestBody GetFacilityByFacilityIdRequest getFacilityByFacilityIdRequest);
}
