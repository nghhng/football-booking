package org.example.access;

import org.example.access.model.GetFacilityByFacilityIdRequest;
import org.example.access.model.GetFacilityByIdResponse;
import org.example.access.model.GetPriceRequest;
import org.example.access.model.GetPriceResponse;
import org.example.dao.Facility;
import tunght.toby.common.config.FeignClientInterceptorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "FACILITY-SERVICE", url = "${facility.service.url}", configuration = FeignClientInterceptorConfig.class)
public interface FacilityFeignClient {
    @PostMapping("facility/getByFacilityId")
    Facility getFacilityById(@RequestBody GetFacilityByFacilityIdRequest getFacilityByFacilityIdRequest);

    @PostMapping("/price/getPrice")
    List<GetPriceResponse> getPrice(@RequestBody GetPriceRequest getPriceRequest);
}
