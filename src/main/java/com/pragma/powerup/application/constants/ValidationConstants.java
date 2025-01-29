package com.pragma.powerup.application.constants;

public class ValidationConstants {

    // First Name
    public static final String FIRST_NAME_REQUIRED = "The firstName is required";

    // Last Name
    public static final String LAST_NAME_REQUIRED = "The lastName is required";

    // Document Number
    public static final String DOCUMENT_NUMBER_REQUIRED = "The documentNumber is required";
    public static final String DOCUMENT_NUMBER_POSITIVE = "The documentNumber must be a positive number";

    // Phone
    public static final String PHONE_REQUIRED = "The phone is required";
    public static final String PHONE_MAX_LENGTH = "The phone must have a maximum of 13 characters";
    public static final String PHONE_INVALID_FORMAT = "The phone must have a valid format and include +";
    public static final String PHONE_REGEX = "^\\+?\\d{1,13}$";

    // Birth Date
    public static final String BIRTH_DATE_PAST = "The birthDate must be in the past";

    // Email
    public static final String EMAIL_REQUIRED = "The email is required";
    public static final String EMAIL_INVALID_FORMAT = "The email must have a valid format";

    // Password
    public static final String PASSWORD_REQUIRED = "The password is required";
    public static final String PASSWORD_MIN_LENGTH = "The password must have a minimum of 8 characters";

    private ValidationConstants() {
        throw new IllegalStateException("Utility class");
    }
}
