package com.pragma.powerup.application.dto;

import com.pragma.powerup.application.constants.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Object that represents the request to create a new client user")
public class UserClientRequest {

    @NotBlank(message = ValidationConstants.FIRST_NAME_REQUIRED)
    @Schema(description = "First name of the client", example = "Juan")
    private String firstName;

    @NotBlank(message = ValidationConstants.LAST_NAME_REQUIRED)
    @Schema(description = "Last name of the client", example = "Pabon")
    private String lastName;

    @NotNull(message = ValidationConstants.DOCUMENT_NUMBER_REQUIRED)
    @Min(value = 1, message = ValidationConstants.DOCUMENT_NUMBER_POSITIVE)
    @Schema(description = "Document number of the client", example = "1234567894")
    private String documentNumber;

    @NotBlank(message = ValidationConstants.PHONE_REQUIRED)
    @Size(max = 13, message = ValidationConstants.PHONE_MAX_LENGTH)
    @Pattern(regexp = ValidationConstants.PHONE_REGEX, message = ValidationConstants.PHONE_INVALID_FORMAT)
    @Schema(description = "Phone number of the client, must include country code", example = "+573005698325")
    private String phone;

    @NotBlank(message = ValidationConstants.EMAIL_REQUIRED)
    @Email(message = ValidationConstants.EMAIL_INVALID_FORMAT)
    @Schema(description = "Email address of the client", example = "juan.pabon4@pragma.com.co")
    private String email;

    @NotBlank(message = ValidationConstants.PASSWORD_REQUIRED)
    @Size(min = 8, message = ValidationConstants.PASSWORD_MIN_LENGTH)
    @Schema(description = "Password of the client (minimum 8 characters)", example = "password123")
    private String password;
}