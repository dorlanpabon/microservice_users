package com.pragma.powerup.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userDocumentNumber;
    private String userPhone;
    private String userBirthDate;
    private String userEmail;
    private String userPassword;
}
