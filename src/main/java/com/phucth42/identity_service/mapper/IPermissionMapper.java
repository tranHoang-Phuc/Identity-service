package com.phucth42.identity_service.mapper;

import com.phucth42.identity_service.dto.request.PermissionRequest;
import com.phucth42.identity_service.dto.response.PermissionResponse;
import com.phucth42.identity_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPermissionMapper {
    Permission toDomainModel(PermissionRequest request);
    PermissionResponse toDtoModel(Permission permission);
}
