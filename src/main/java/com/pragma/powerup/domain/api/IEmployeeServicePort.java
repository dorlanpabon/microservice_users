package com.pragma.powerup.domain.api;

public interface IEmployeeServicePort {

    boolean saveEmployee(Long employeeId, Long ownerId);

    Long getUserId();
}
