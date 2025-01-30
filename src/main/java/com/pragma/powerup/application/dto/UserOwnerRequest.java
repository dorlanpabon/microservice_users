package com.pragma.powerup.application.dto;

import com.pragma.powerup.application.constants.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Object that represents the request to create a new owner user")
public class UserOwnerRequest {

    @NotBlank(message = ValidationConstants.FIRST_NAME_REQUIRED)
    @Schema(description = "First name of the owner", example = "Juan")
    private String firstName;

    @NotBlank(message = ValidationConstants.LAST_NAME_REQUIRED)
    @Schema(description = "Last name of the owner", example = "Pabon")
    private String lastName;

    @NotNull(message = ValidationConstants.DOCUMENT_NUMBER_REQUIRED)
    @Min(value = 1, message = ValidationConstants.DOCUMENT_NUMBER_POSITIVE)
    @Schema(description = "Document number of the owner", example = "123456789")
    private String documentNumber;

    @NotBlank(message = ValidationConstants.PHONE_REQUIRED)
    @Size(max = 13, message = ValidationConstants.PHONE_MAX_LENGTH)
    @Pattern(regexp = ValidationConstants.PHONE_REGEX, message = ValidationConstants.PHONE_INVALID_FORMAT)
    @Schema(description = "Phone number of the owner, must include country code", example = "+573005698325")
    private String phone;

    @Past(message = ValidationConstants.BIRTH_DATE_PAST)
    @Schema(description = "Birth date of the owner in format YYYY-MM-DD", example = "1990-05-15")
    private LocalDate birthDate;

    @NotBlank(message = ValidationConstants.EMAIL_REQUIRED)
    @Email(message = ValidationConstants.EMAIL_INVALID_FORMAT)
    @Schema(description = "Email address of the owner", example = "juan.pabon@pragma.com.co")
    private String email;

    @NotBlank(message = ValidationConstants.PASSWORD_REQUIRED)
    @Size(min = 8, message = ValidationConstants.PASSWORD_MIN_LENGTH)
    @Schema(description = "Password of the owner (minimum 8 characters)", example = "password123")
    private String password;
}
