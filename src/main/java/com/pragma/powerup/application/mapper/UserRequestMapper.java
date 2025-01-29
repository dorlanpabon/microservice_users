package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.UserOwnerRequest;
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
}
