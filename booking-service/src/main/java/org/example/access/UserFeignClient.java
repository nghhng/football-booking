package org.example.access;

import org.example.access.model.GetUserByUsernameRequest;
import org.example.access.model.GetUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserFeignClient {

    @PostMapping("getByUsername")
    GetUserResponse getUserByUsername(@RequestBody GetUserByUsernameRequest getUserByUsernameRequest);
}
