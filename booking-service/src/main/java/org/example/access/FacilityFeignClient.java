package org.example.access;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "FACILITY-SERVICE")
public interface FacilityFeignClient {
    @PostMapping("facility/getByFacilityId")
    GetFacilityByIdResponse getFacilityById(@RequestBody GetFacilityByFacilityIdRequest getFacilityByFacilityIdRequest);

    @PostMapping("/price/getPrice")
    List<GetPriceResponse> getPrice(@RequestBody GetPriceRequest getPriceRequest);
}
