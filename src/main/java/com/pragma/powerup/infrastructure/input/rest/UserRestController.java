package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.UserClientRequest;
import com.pragma.powerup.application.dto.UserEmployeeRequest;
import com.pragma.powerup.application.dto.UserOwnerRequest;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.infrastructure.constants.InfrastructureConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "User API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(summary = "Create a new user owner",
            description = "Add a new user owner to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User owner created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content())
    })
    @PostMapping("/owner")
    @PreAuthorize(InfrastructureConstants.HAS_ROLE_ADMINISTRATOR)
    public ResponseEntity<Void> saveUserOwner(@Valid @RequestBody UserOwnerRequest userOwnerRequest) {
        userHandler.saveUserOwner(userOwnerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get User by ID",
            description = "Retrieve a user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content())
    })
    @GetMapping("/validate-owner/{userId}")
    @PreAuthorize(InfrastructureConstants.HAS_ROLE_ADMINISTRATOR)
    public ResponseEntity<Void> validateOwner(@Valid @PathVariable Long userId) {
        userHandler.validateOwner(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Create a new user employee",
            description = "Add a new user employee to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content())
    })
    @PostMapping("/employee")
    @PreAuthorize(InfrastructureConstants.HAS_ROLE_OWNER)
    public ResponseEntity<Void> saveUserEmployee(@Valid @RequestBody UserEmployeeRequest userEmployeeRequest) {
        userHandler.saveUserEmployee(userEmployeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Create a new user client",
            description = "Add a new user client to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content())
    })
    @PostMapping("/client")
    public ResponseEntity<Void> saveUserClient(@Valid @RequestBody UserClientRequest userClientRequest) {
        userHandler.saveUserClient(userClientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get phone by user ID",
            description = "Retrieve a phone by its user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Phone not found", content = @Content())
    })
    @GetMapping("/phone/{userId}")
    @PreAuthorize(InfrastructureConstants.HAS_ROLE_EMPLOYEE)
    public ResponseEntity<String> getPhone(@Valid @PathVariable Long userId) {
        String phone = userHandler.getPhone(userId);
        return ResponseEntity.ok(phone);
    }
}
