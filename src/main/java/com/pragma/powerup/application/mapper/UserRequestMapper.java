package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.UserClientRequest;
import com.pragma.powerup.application.dto.UserEmployeeRequest;
import com.pragma.powerup.application.dto.UserOwnerRequest;
import com.pragma.powerup.application.dto.UserResponse;
import com.pragma.powerup.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserRequestMapper {

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "documentNumber", source = "documentNumber")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    User toUser(UserOwnerRequest userOwnerRequest);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userFirstName", source = "firstName")
    @Mapping(target = "userLastName", source = "lastName")
    @Mapping(target = "userDocumentNumber", source = "documentNumber")
    @Mapping(target = "userPhone", source = "phone")
    @Mapping(target = "userBirthDate", source = "birthDate")
    @Mapping(target = "userEmail", source = "email")
    UserResponse toUserResponse(User user);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "documentNumber", source = "documentNumber")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    User toUser(UserEmployeeRequest userEmployeeRequest);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "documentNumber", source = "documentNumber")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    User toUser(UserClientRequest userClientRequest);
}
