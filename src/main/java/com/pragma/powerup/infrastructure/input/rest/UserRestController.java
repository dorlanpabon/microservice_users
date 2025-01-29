package com.pragma.powerup.infrastructure.input.rest;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = InfrastructureConstants.USER_API_TAG)
@RestController
@RequestMapping(InfrastructureConstants.BASE_USERS_URL)
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(summary = InfrastructureConstants.CREATE_USER_SUMMARY,
            description = InfrastructureConstants.CREATE_USER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = InfrastructureConstants.RESPONSE_201),
            @ApiResponse(responseCode = "400", description = InfrastructureConstants.RESPONSE_400, content = @Content())
    })
    @PostMapping(InfrastructureConstants.CREATE_OWNER_USER_URL)
    public ResponseEntity<Void> saveUserOwner(@Valid @RequestBody UserOwnerRequest userOwnerRequest) {
        userHandler.saveUserOwner(userOwnerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
