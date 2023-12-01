package org.example.access;

import org.example.access.model.GetUserByIdRequest;
import org.example.access.model.GetUserByUsernameRequest;
import org.example.access.model.GetUserResponse;
import org.example.dao.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserFeignClient {

    @PostMapping("getByUsername")
    User getUserByUsername(@RequestBody GetUserByUsernameRequest getUserByUsernameRequest);

    @PostMapping("getById")
    User getUserById(@RequestBody GetUserByIdRequest getUserByIdRequest);
}
