package com.pragma.powerup.domain.constants;

public class DomainConstants {

    public static final String EMAIL_ALREADY_EXISTS = "The email already exists";
    public static final String DOCUMENT_NUMBER_ALREADY_EXISTS = "The document number already exists";
    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String INVALID_EMAIL = "The email is not valid";
    public static final String INVALID_PHONE = "The phone is not valid";
    public static final String USER_UNDERAGE = "The user must be at least 18 years old";

    public static final int MINIMUM_AGE = 18;
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
    public static final String PHONE_REGEX = "^\\+?\\d{1,13}$";
    public static final String USER_NOT_OWNER = "The user is not an owner";

    public static final String USER_NOT_FOUND = "User not found";
    public static final String EMPLOYEE_ERROR_SAVE = "Error saving employee";
    public static final String PHONE_NOT_FOUND = "Phone not found";

    DomainConstants() {
        throw new IllegalStateException("Utility class");
    }
}
