package nghhng.facilityservice.access;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @PostMapping("getByUsername")
    GetUserResponse getUserByUsername(@RequestBody GetUserByUsernameRequest getUserByUsernameRequest);
}
