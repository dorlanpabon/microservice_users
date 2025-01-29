package com.pragma.powerup.infrastructure.constants;

public class InfrastructureConstants {

    // API Routes
    public static final String BASE_USERS_URL = "/users";
    public static final String CREATE_OWNER_USER_URL = "/owner";

    // API Documentation Messages
    public static final String USER_API_TAG = "User API";
    public static final String CREATE_USER_SUMMARY = "Create a new user owner";
    public static final String CREATE_USER_DESCRIPTION = "Add a new user owner to the system";

    // HTTP Response Messages
    public static final String RESPONSE_201 = "User owner created successfully";
    public static final String RESPONSE_400 = "Invalid request data";

    private InfrastructureConstants() {
        throw new IllegalStateException("Utility class");
    }
}
