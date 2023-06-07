package nghhng.facilityservice.access;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${user.service.url}")
public interface UserFeignClient {

    @PostMapping("getByUsername")
    GetUserResponse getUserByUsername(@RequestBody String username);
}
