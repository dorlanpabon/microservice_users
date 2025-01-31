package com.pragma.powerup.infrastructure.adapter;

import com.pragma.powerup.application.dto.UserEmployeeFeignRequest;
import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.infrastructure.client.EmployeeFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class EmployeeServiceAdapter implements IEmployeeServicePort {

    private final EmployeeFeignClient employeeFeignClient;
    private final HttpServletRequest request;

    @Override
    public boolean saveEmployee(Long employeeId, Long ownerId) {
        try {
            employeeFeignClient.saveEmployee(new UserEmployeeFeignRequest(employeeId, ownerId));
            return true;
        } catch (FeignException e) {
            return false;
        }
    }

    @Override
    public Long getUserId() {
        return (Long) request.getAttribute("userId");
    }
}
