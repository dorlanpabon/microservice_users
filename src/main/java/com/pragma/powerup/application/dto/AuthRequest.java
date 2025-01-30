package com.pragma.powerup.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    @Schema(description = "Email address of the owner", example = "juan.pabon@pragma.com.co")
    private String email;

    @Schema(description = "Password of the owner (minimum 8 characters)", example = "password123")
    private String password;

}