package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.UserEmployeeRequest;
import com.pragma.powerup.application.dto.UserOwnerRequest;
import com.pragma.powerup.application.handler.IUserHandler;
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
    @PreAuthorize("hasRole('ADMINISTRATOR')")
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
    @PreAuthorize("hasRole('OWNER')")
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
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> saveUserEmployee(@Valid @RequestBody UserEmployeeRequest userEmployeeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
