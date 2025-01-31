package com.pragma.powerup.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEmployeeFeignRequest {
    private Long employeeId;
    private Long ownerId;
}
