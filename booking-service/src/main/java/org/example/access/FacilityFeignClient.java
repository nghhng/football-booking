package org.example.access;

import tunght.toby.common.config.FeignClientInterceptorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "FACILITY-SERVICE", url = "localhost:7200", configuration = FeignClientInterceptorConfig.class)
public interface FacilityFeignClient {
    @PostMapping("facility/getByFacilityId")
    GetFacilityByIdResponse getFacilityById(@RequestBody GetFacilityByFacilityIdRequest getFacilityByFacilityIdRequest);

    @PostMapping("/price/getPrice")
    List<GetPriceResponse> getPrice(@RequestBody GetPriceRequest getPriceRequest);
}
