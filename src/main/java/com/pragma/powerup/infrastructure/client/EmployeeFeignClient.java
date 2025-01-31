package com.pragma.powerup.infrastructure.client;

import com.pragma.powerup.application.dto.UserEmployeeFeignRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "small-quare-service", url = "http://localhost:8082/")
public interface EmployeeFeignClient {

    @PostMapping("employees/")
    void saveEmployee(@RequestBody UserEmployeeFeignRequest userEmployeeFeignRequest);

}
